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

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

/**
 * A factory for accessing datapack tags.
 */
public interface ExpandedTagFactory<T> extends TagFactory<T> {
	TagFactory<Biome> BIOME_EXPANDED = TagFactory.of(Registry.BIOME_KEY, "tags/worldgen/biomes");
	TagFactory<DimensionType> DIMENSION_TYPE = TagFactory.of(Registry.DIMENSION_TYPE_KEY, "tags/dimension_types");
	TagFactory<ChunkGeneratorSettings> NOISE_SETTINGS = TagFactory.of(Registry.CHUNK_GENERATOR_SETTINGS_KEY, "tags/worldgen/noise_settings");
	TagFactory<DimensionOptions> DIMENSIONS = TagFactory.of(Registry.DIMENSION_KEY, "tags/dimensions");
}