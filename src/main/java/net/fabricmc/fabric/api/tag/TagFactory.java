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

package net.fabricmc.fabric.api.tag;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

import net.fabricmc.fabric.impl.tag.extension.TagFactoryImpl;

/**
 * A factory for accessing datapack tags.
 */
public interface TagFactory<T> {
	TagFactory<Item> ITEM = of(ItemTags::getAllTags);
	TagFactory<Block> BLOCK = of(BlockTags::getAllTags);
	TagFactory<Fluid> FLUID = of(FluidTags::getAllTags);
	TagFactory<GameEvent> GAME_EVENT = of(GameEventTags::getAllTags);
	TagFactory<EntityType<?>> ENTITY_TYPE = of(EntityTypeTags::getAllTags);
	TagFactory<Biome> BIOME = of(Registry.BIOME_REGISTRY, "tags/biomes");

	/**
	 * Create a new tag factory for specified registry.
	 *
	 * @param registryKey the key of the registry.
	 * @param dataType    the data type of this tag group, vanilla uses "tags/[plural]" format for built-in groups.
	 */
	static <T> TagFactory<T> of(ResourceKey<? extends Registry<T>> registryKey, String dataType) {
		return TagFactoryImpl.of(registryKey, dataType);
	}

	static <T> TagFactory<T> of(Supplier<TagCollection<T>> tagGroupSupplier) {
		return TagFactoryImpl.of(tagGroupSupplier);
	}

	Tag.Named<T> create(ResourceLocation id);
}