/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.tag.extension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Main;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.StaticTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tags.TagLoader;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.fabric.mixin.tag.extension.DynamicRegistryManagerAccessor;

@SuppressWarnings("ClassCanBeRecord")
public final class TagFactoryImpl<T> implements TagFactory<T> {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final Map<ResourceKey<? extends Registry<?>>, StaticTagHelper<?>> TAG_LISTS = new HashMap<>();
    public static final Set<StaticTagHelper<?>> DYNAMICS = new HashSet<>();

    public static <T> TagFactory<T> of(Supplier<TagCollection<T>> tagGroupSupplier) {
        return new TagFactoryImpl<>(tagGroupSupplier);
    }

    @SuppressWarnings("unchecked")
    public static <T> TagFactory<T> of(ResourceKey<? extends Registry<T>> registryKey, String dataType) {
        StaticTagHelper<T> tagList;

        // Use already registered tag list for the registry if it has the same dataType, in case multiple mods tried to do it.
        if (TAG_LISTS.containsKey(registryKey)) {
            tagList = (StaticTagHelper<T>) TAG_LISTS.get(registryKey);
            // Throw an exception if the tagList has different dataType.
            Preconditions.checkArgument(tagList.getDirectory().equals(dataType), "Tag list for registry %s is already existed with data type %s", registryKey.location(), tagList.getDirectory());
        } else {
            tagList = StaticTags.create(registryKey, dataType);
            TAG_LISTS.put(registryKey, tagList);

            // Check whether the registry dynamic.
            if (DynamicRegistryManagerAccessor.getInfos().containsKey(registryKey)) {
                DYNAMICS.add(tagList);
            }
        }

        return of(tagList::getAllTags);
    }

    /**
     * Manually load tags for dynamic registries and add the resulting tag group to the tag list.
     *
     * <p>Minecraft loads the resource manager before dynamic registries, making tags for them failed to load
     * if it mentions datapack entries. The solution is to manually load tags after the registry is loaded.
     *
     * <p>Look at server's {@link Main#main} function calls for {@link ServerResourceManager#reload} and
     * {@link RegistryOps#method_36574} for the relevant code.
     */
    public static void loadDynamicRegistryTags(RegistryAccess registryManager, ResourceManager resourceManager) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int loadedTags = 0;

        for (StaticTagHelper<?> tagList : DYNAMICS) {
            ResourceKey<? extends Registry<?>> registryKey = tagList.getKey();
            Registry<?> registry = registryManager.registryOrThrow(registryKey);
            TagLoader<?> tagGroupLoader = new TagLoader<>(registry::getOptional, tagList.getDirectory());
            TagCollection<?> tagGroup = tagGroupLoader.loadAndBuild(resourceManager);
            ((FabricTagManagerHooks) SerializationTags.getInstance()).fabric_addTagGroup(registryKey, tagGroup);
            tagList.getMissingTags(SerializationTags.getInstance());
            loadedTags += tagGroup.getAllTags().size();
        }

        if (loadedTags > 0) {
            LOGGER.info("Loaded {} dynamic registry tags in {}", loadedTags, stopwatch);
        }
    }

    private final Supplier<TagCollection<T>> tagGroupSupplier;

    private TagFactoryImpl(Supplier<TagCollection<T>> tagGroupSupplier) {
        this.tagGroupSupplier = tagGroupSupplier;
    }

    @Override
    public Tag.Named<T> create(ResourceLocation id) {
        return new TagDelegate<>(id, tagGroupSupplier);
    }
}