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

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

/**
 * A factory for accessing datapack tags.
 */
public interface ExpandedTagFactory<T> extends TagFactory<T> {
	TagFactory<Biome> BIOME_EXPANDED = TagFactory.of(Registry.BIOME_REGISTRY, "tags/worldgen/biomes");
	TagFactory<DimensionType> DIMENSION_TYPE = TagFactory.of(Registry.DIMENSION_TYPE_REGISTRY, "tags/dimension_types");
	TagFactory<NoiseGeneratorSettings> NOISE_SETTINGS = TagFactory.of(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, "tags/worldgen/noise_settings");
	TagFactory<LevelStem> DIMENSIONS = TagFactory.of(Registry.LEVEL_STEM_REGISTRY, "tags/dimensions");
}