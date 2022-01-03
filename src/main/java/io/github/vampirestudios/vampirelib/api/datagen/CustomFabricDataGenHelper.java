package io.github.vampirestudios.vampirelib.api.datagen;

import com.mojang.serialization.Lifecycle;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import io.github.vampirestudios.vampirelib.api.CustomTagProviders;

public class CustomFabricDataGenHelper {
    /**
     * A fake registry instance to be used for {@link CustomTagProviders.DynamicRegistryTagProvider}.
     *
     * <p>In {@link TagsProvider#run}, it checks for whether the registry has all the elements added to the builder.
     * This would be fine for static registry, but there won't be any instance dynamic registry available.
     * Therefore, this simply return true for all {@link Registry#containsKey(ResourceKey)} call.
     */
    @SuppressWarnings("rawtypes")
    private static final Registry FAKE_DYNAMIC_REGISTRY = new MappedRegistry<>(ResourceKey.createRegistryKey(new ResourceLocation("fabric:fake_dynamic_registry")), Lifecycle.experimental()) {
        @Override
        public boolean containsKey(ResourceLocation id) {
            return true;
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> Registry<T> getFakeDynamicRegistry() {
        return FAKE_DYNAMIC_REGISTRY;
    }
}
