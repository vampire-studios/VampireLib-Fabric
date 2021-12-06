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

package io.github.vampirestudios.vampirelib.api;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import io.github.vampirestudios.vampirelib.api.datagen.CustomFabricTagBuilder;

public class CustomTagProviders {
	/**
	 * Extend this class to create {@link MobEffect} tags in the "/mob_effects" tag directory.
	 */
	public abstract static class MobEffectTagProvider extends FabricTagProvider<MobEffect> {
		protected MobEffectTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.MOB_EFFECT, "mob_effects", "Mob Effect Tags");
		}

		public CustomFabricTagBuilder<MobEffect> tagCustom(Tag.Named<MobEffect> tag) {
			return new CustomFabricTagBuilder(super.tag(tag));
		}
	}

	/**
	 * Extend this class to create {@link DimensionType} tags in the "worldgen/biomes" tag directory.
	 */
	public abstract static class ExpandedBiomeTagProvider extends FabricTagProvider<Biome> {
		protected ExpandedBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.BIOME, "worldgen/biomes", "Expanded Biome Tags");
		}

		public CustomFabricTagBuilder<Biome> tagCustom(Tag.Named<Biome> tag) {
			return new CustomFabricTagBuilder(super.tag(tag));
		}
	}

	/**
	 * Extend this class to create {@link NormalNoise.NoiseParameters} tags in the "worldgen/biomes" tag directory.
	 */
	public abstract static class NoiseTagProvider extends FabricTagProvider<NormalNoise.NoiseParameters> {
		protected NoiseTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE, "worldgen/noises", "Noise Tags");
		}

		public CustomFabricTagBuilder<NormalNoise.NoiseParameters> tagCustom(Tag.Named<NormalNoise.NoiseParameters> tag) {
			return new CustomFabricTagBuilder(super.tag(tag));
		}
	}
}