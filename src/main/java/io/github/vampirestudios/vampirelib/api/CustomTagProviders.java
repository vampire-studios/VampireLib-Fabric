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

import io.github.vampirestudios.vampirelib.api.datagen.CustomFabricTagBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

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
	 * Extend this class to create {@link DimensionType} tags in the "biomes" tag directory.
	 */
	public abstract static class ExpandedBiomeTagProvider extends FabricTagProvider<Biome> {
		protected ExpandedBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.BIOME, "biomes", "Biome Tags");
		}

		public ResourceKeyTagBuilder<Biome> tagCustom(Tag.Named<Biome> tag) {
			return new ResourceKeyTagBuilder<>(super.tag(tag), this.registry);
		}
	}

	public abstract static class NoiseSettingsTagProvider extends FabricTagProvider<NoiseGeneratorSettings> {
		protected NoiseSettingsTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE_GENERATOR_SETTINGS, "worldgen/noise_settings", "Noise Settings Tags");
		}

		public ResourceKeyTagBuilder<NoiseGeneratorSettings> tagCustom(Tag.Named<NoiseGeneratorSettings> tag) {
			return new ResourceKeyTagBuilder<>(super.tag(tag), this.registry);
		}
	}

	public abstract static class DimensionTypeTagProvider extends FabricTagProvider<DimensionType> {
		protected DimensionTypeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Minecraft.getInstance().getSingleplayerServer().overworld().registryAccess()
				.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), "worldgen/dimension_type", "Dimension Type Tags");
		}

		public ResourceKeyTagBuilder<DimensionType> tagCustom(Tag.Named<DimensionType> tag) {
			return new ResourceKeyTagBuilder<>(super.tag(tag), this.registry);
		}
	}

	public abstract static class DimensionTagProvider extends FabricTagProvider<Level> {
		protected DimensionTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Minecraft.getInstance().getSingleplayerServer().overworld().registryAccess()
				.registryOrThrow(Registry.DIMENSION_REGISTRY), "worldgen/dimension", "Dimension Tags");
		}

		public ResourceKeyTagBuilder<Level> tagCustom(Tag.Named<Level> tag) {
			return new ResourceKeyTagBuilder<>(super.tag(tag), this.registry);
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

	public static class ResourceKeyTagBuilder<T> extends CustomFabricTagBuilder<T> {
		private final TagsProvider.TagAppender<T> parent;
		private final Registry<T> registry;

		public ResourceKeyTagBuilder(TagsProvider.TagAppender<T> parent, Registry<T> registry) {
			super(parent);
			this.parent = parent;
			this.registry = registry;
		}

		public ResourceKeyTagBuilder<T> add(ResourceKey<T> element) {
			parent.add(getValue(element));
			return this;
		}

		public T getValue(ResourceKey<T> biome) {
			return registry.get(biome);
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link ResourceKeyTagBuilder} instance
		 */
		@SafeVarargs
		public final ResourceKeyTagBuilder<T> addTags(Tag.Named<T>... values) {
			for (Tag.Named<T> value : values) {
				this.addTag(value);
			}
			return this;
		}

		@SafeVarargs
		public final ResourceKeyTagBuilder<T> add(ResourceKey<T>... toAdd) {
			for (ResourceKey<T> value : toAdd) {
				this.add(getValue(value));
			}
			return this;
		}
	}
}
