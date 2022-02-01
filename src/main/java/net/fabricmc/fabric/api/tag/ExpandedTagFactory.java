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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * A factory for accessing datapack tags.
 */
public abstract class ExpandedTagFactory<T> implements TagFactory<T> {
	public static final TagFactory<Level> DIMENSIONS = TagFactory.of(Registry.DIMENSION_REGISTRY, "tags/dimensions");
	public static final TagFactory<DimensionType> DIMENSION_TYPE = TagFactory.of(Registry.DIMENSION_TYPE_REGISTRY, "tags/dimension_types");
	public static final TagFactory<ConfiguredWorldCarver<?>> CONFIGURED_CARVER = TagFactory.of(Registry.CONFIGURED_CARVER_REGISTRY, "tags/worldgen/configured_carvers");
	public static final TagFactory<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = TagFactory.of(Registry.CONFIGURED_FEATURE_REGISTRY, "tags/worldgen/configured_features");
	public static final TagFactory<ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURE_FEATURE = TagFactory.of(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, "tags/worldgen/configured_structure_features");
	public static final TagFactory<Feature<?>> FEATURE = TagFactory.of(Registry.FEATURE_REGISTRY, "tags/worldgen/features");
	public static final TagFactory<NoiseGeneratorSettings> NOISE_SETTINGS = TagFactory.of(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, "tags/worldgen/noise_settings");
	public static final TagFactory<PlacedFeature> PLACED_FEATURE = TagFactory.of(Registry.PLACED_FEATURE_REGISTRY, "tags/worldgen/placed_features");
	public static final TagFactory<MobEffect> MOB_EFFECTS = TagFactory.of(Registry.MOB_EFFECT_REGISTRY, "tags/mob_effects");
	public static final TagFactory<Enchantment> ENCHANTMENTS = TagFactory.of(Registry.ENCHANTMENT_REGISTRY, "tags/enchantments");
	public static final TagFactory<Potion> POTIONS = TagFactory.of(Registry.POTION_REGISTRY, "tags/potions");
	public static final TagFactory<Motive> MOTIVES = TagFactory.of(Registry.MOTIVE_REGISTRY, "tags/motives");
}
