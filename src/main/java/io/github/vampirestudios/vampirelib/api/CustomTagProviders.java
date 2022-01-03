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

import com.google.common.base.Preconditions;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.mixin.tag.extension.DynamicRegistryManagerAccessor;

import io.github.vampirestudios.vampirelib.api.datagen.CustomFabricDataGenHelper;

public abstract class CustomTagProviders<T> extends FabricTagProvider<T> {

	/**
	 * Construct a new {@link FabricTagProvider}.
	 *
	 * <p>Common implementations of this class are provided. For example @see BlockTagProvider
	 *
	 * @param dataGenerator The data generator instance
	 * @param registry The backing registry for the Tag type.
	 * @param path The directory name to write the tag file names. Example: "blocks" or "items"
	 * @param name The name used for {@link DataProvider#getName()}
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	protected CustomTagProviders(FabricDataGenerator dataGenerator, Registry<T> registry, String path, String name) {
		super(dataGenerator, registry, path, name);
//		if (!(this instanceof DynamicRegistryTagProvider) && BuiltinRegistries.REGISTRY.containsKey((ResourceKey) registry.)) {
//			throw new IllegalArgumentException("Using FabricTagProvider to generate dynamic registry tags is not supported, Use DynamicRegistryTagProvider instead.");
//		}
	}

	/**
	 * Extend this class to create dynamic registry tags.
	 */
	public abstract static class DynamicRegistryTagProvider<T> extends CustomTagProviders<T> {
		/**
		 * Construct a new {@link DynamicRegistryTagProvider}.
		 *
		 * @param dataGenerator The data generator instance
		 * @param registryKey The registry key of the dynamic registry
		 * @param path The directory name to write the tag file names
		 * @param name The name used for {@link DataProvider#getName()}
		 * @throws IllegalArgumentException if the registry is static registry
		 */
		protected DynamicRegistryTagProvider(FabricDataGenerator dataGenerator, ResourceKey<? extends Registry<T>> registryKey, String path, String name) {
			super(dataGenerator, CustomFabricDataGenHelper.getFakeDynamicRegistry(), path, name);
			Preconditions.checkArgument(DynamicRegistryManagerAccessor.getInfos().containsKey(registryKey), "Only dynamic registries are supported in this tag provider.");
		}
	}

	/**
	 * Extend this class to create {@link MobEffect} tags in the "/mob_effects" tag directory.
	 */
	public abstract static class MobEffectTagProvider extends CustomTagProviders<MobEffect> {
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
	public abstract static class ExpandedBiomeTagProvider extends DynamicRegistryTagProvider<Biome> {
		protected ExpandedBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.BIOME_REGISTRY, "biomes", "Biome Tags");
		}

		public CustomFabricTagBuilder<Biome> tagCustom(Tag.Named<Biome> tag) {
			return new CustomFabricTagBuilder<Biome>(super.tag(tag));
		}
	}

	public abstract static class NoiseSettingsTagProvider extends CustomTagProviders<NoiseGeneratorSettings> {
		protected NoiseSettingsTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE_GENERATOR_SETTINGS, "worldgen/noise_settings", "Noise Settings Tags");
		}

		public CustomFabricTagBuilder<NoiseGeneratorSettings> tagCustom(Tag.Named<NoiseGeneratorSettings> tag) {
			return new CustomFabricTagBuilder<NoiseGeneratorSettings>(super.tag(tag));
		}
	}

	public abstract static class DimensionTypeTagProvider extends CustomTagProviders<DimensionType> {
		protected DimensionTypeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Minecraft.getInstance().getSingleplayerServer().overworld().registryAccess()
				.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), "worldgen/dimension_type", "Dimension Type Tags");
		}

		public CustomFabricTagBuilder<DimensionType> tagCustom(Tag.Named<DimensionType> tag) {
			return new CustomFabricTagBuilder<DimensionType>(super.tag(tag));
		}
	}

	public abstract static class DimensionTagProvider extends CustomTagProviders<Level> {
		protected DimensionTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Minecraft.getInstance().getSingleplayerServer().overworld().registryAccess()
				.registryOrThrow(Registry.DIMENSION_REGISTRY), "worldgen/dimension", "Dimension Tags");
		}

		public CustomFabricTagBuilder<Level> tagCustom(Tag.Named<Level> tag) {
			return new CustomFabricTagBuilder<Level>(super.tag(tag));
		}
	}

	/**
	 * Extend this class to create {@link NormalNoise.NoiseParameters} tags in the "worldgen/biomes" tag directory.
	 */
	public abstract static class NoiseTagProvider extends CustomTagProviders<NormalNoise.NoiseParameters> {
		protected NoiseTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE, "worldgen/noises", "Noise Tags");
		}

		public CustomFabricTagBuilder<NormalNoise.NoiseParameters> tagCustom(Tag.Named<NormalNoise.NoiseParameters> tag) {
			return new CustomFabricTagBuilder<NormalNoise.NoiseParameters>(super.tag(tag));
		}
	}

	public final class CustomFabricTagBuilder<T> extends TagsProvider.TagAppender<T> {
		private final TagsProvider.TagAppender<T> parent;

		public CustomFabricTagBuilder(TagsProvider.TagAppender<T> parent) {
			super(parent.builder, parent.registry, parent.source);
			this.parent = parent;
		}

		/**
		 * Set the value of the `replace` flag in a Tag.
		 *
		 * <p>When set to true the tag will replace any existing tag entries.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> setReplace(boolean replace) {
			((net.fabricmc.fabric.impl.datagen.FabricTagBuilder) builder).fabric_setReplace(replace);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 * @throws UnsupportedOperationException if the provider is an instance of {@link DynamicRegistryTagProvider}
		 * @see #add(ResourceLocation)
		 */
		@Override
		public CustomFabricTagBuilder<T> add(T element) {
			assertStaticRegistry();
			parent.add(element);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> add(ResourceLocation id) {
			builder.addTag(id, source);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> add(ResourceKey<? extends T> registryKey) {
			return add(registryKey.location());
		}

		/**
		 * Add an optional {@link ResourceLocation} to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> addOptional(ResourceLocation id) {
			parent.addOptional(id);
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> addTag(Tag.Named<T> tag) {
			parent.addTag(tag);
			return this;
		}

		/**
		 * Add another optional tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> addOptionalTag(ResourceLocation id) {
			parent.addOptionalTag(id);
			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 * @throws UnsupportedOperationException if the provider is an instance of {@link CustomTagProviders.DynamicRegistryTagProvider}
		 */
		@SafeVarargs
		@Override
		public final CustomFabricTagBuilder<T> add(T... elements) {
			assertStaticRegistry();

			for (T element : elements) {
				add(element);
			}

			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> add(ResourceLocation... ids) {
			for (ResourceLocation id : ids) {
				add(id);
			}
			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@SafeVarargs
		public final CustomFabricTagBuilder<T> add(ResourceKey<? extends T>... registryKeys) {
			for (ResourceKey<? extends T> registryKey : registryKeys) {
				add(registryKey);
			}
			return this;
		}

		private void assertStaticRegistry() {
			if (CustomTagProviders.this instanceof CustomTagProviders.DynamicRegistryTagProvider) {
				throw new UnsupportedOperationException("Adding object instances is not supported for DynamicRegistryTagProvider.");
			}
		}
	}
}
