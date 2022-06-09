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


package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.Objects;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.mixin.datagen.DynamicRegistryManagerAccessor;

public abstract class CustomTagProviders<T> extends FabricTagProvider<T> {

	/**
	 * Construct a new {@link FabricTagProvider}.
	 *
	 * <p>Common implementations of this class are provided. For example @see BlockTagProvider
	 *
	 * @param dataGenerator The data generator instance
	 * @param registry The backing registry for the Tag type.
	 * @param path The directory name to write the tag file names. Example: "blocks" or "items"
	 */

	protected CustomTagProviders(FabricDataGenerator dataGenerator, Registry<T> registry, String path) {
		super(dataGenerator, registry, path);
	}

	public CustomFabricTagBuilder<T> getOrCreateTagBuilderCustom(TagKey<T> tag) {
		return new CustomFabricTagBuilder<>(super.tag(tag));
	}

	@Override
	public CustomFabricTagBuilder<T> tag(@NotNull TagKey<T> tag) {
		return new CustomFabricTagBuilder<>(super.tag(tag));
	}

	public abstract static class CustomBlockTagProvider extends CustomTagProviders<Block> {
		protected CustomBlockTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.BLOCK, "blocks");
		}

		public CustomFabricTagBuilder<Block> tagCustom(TagKey<Block> tag) {
			return getOrCreateTagBuilderCustom(tag);
		}

		@Override
		public String getName() {
			return "Custom Block Tags Provider";
		}
	}

	public abstract static class CustomBiomeTagProvider extends CustomTagProviders<Biome> {
		protected CustomBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.BIOME, "biomes");
		}

		public CustomFabricTagBuilder<Biome> tagCustom(TagKey<Biome> tag) {
			return getOrCreateTagBuilderCustom(tag);
		}
	}

	public abstract static class CustomItemTagProvider extends CustomTagProviders<Item> {
		@Nullable
		private final Function<TagKey<Block>, Tag.Builder> blockTagBuilderProvider;

		protected CustomItemTagProvider(FabricDataGenerator dataGenerator, @Nullable CustomBlockTagProvider blockTagProvider) {
			super(dataGenerator, Registry.ITEM, "items");

			this.blockTagBuilderProvider = blockTagProvider == null ? null : blockTagProvider::getOrCreateRawBuilder;
		}

		/**
		 * Construct an {@link ItemTagProvider} tag provider <b>without</b> an associated {@link BlockTagProvider} tag provider.
		 *
		 * @param dataGenerator a {@link ItemTagProvider} tag provider
		 */
		public CustomItemTagProvider(FabricDataGenerator dataGenerator) {
			this(dataGenerator, null);
		}

		/**
		 * Copy the entries from a tag with the {@link Block} type into this item tag.
		 *
		 * <p>The {@link ItemTagProvider} tag provider must be constructed with an associated {@link BlockTagProvider} tag provider to use this method.
		 *
		 * <p>Any block ids that do not exist in the item registry will be filtered out automatically.
		 *
		 * @param blockTag The block tag to copy from.
		 * @param itemTag The item tag to copy to.
		 */
		public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
			Tag.Builder blockTagBuilder = Objects.requireNonNull(this.blockTagBuilderProvider, "Pass Block tag provider via constructor to use copy").apply(blockTag);
			Tag.Builder itemTagBuilder = this.getOrCreateRawBuilder(itemTag);
			blockTagBuilder.getEntries().filter((entry) -> entry.entry().verifyIfPresent(this.registry::containsKey, (id) -> true)).forEach(itemTagBuilder::add);
		}

		public CustomFabricTagBuilder<Item> tagCustom(TagKey<Item> tag) {
			return getOrCreateTagBuilderCustom(tag);
		}

		@Override
		public String getName() {
			return "Custom Item Tags Provider";
		}
	}

	public abstract static class VEntityTagProvider extends CustomTagProviders<EntityType<?>> {
		public VEntityTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.ENTITY_TYPE, "entity_types");
		}

		public CustomFabricTagBuilder<EntityType<?>> tagCustom(TagKey<EntityType<?>> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}

		@Override
		public String getName() {
			return "Custom Entity Type Tags Provider";
		}
	}

	/**
	 * Extend this class to create dynamic registry tags.
	 */
	public abstract static class DynamicRegistryTagProvider<T> extends CustomTagProviders<T> {
		/**
		 * Construct a new {@link CustomTagProviders.DynamicRegistryTagProvider}.
		 *
		 * @param dataGenerator The data generator instance
		 * @param registry The registry key of the dynamic registry
		 * @param path The directory name to write the tag file names
		 * @throws IllegalArgumentException if the registry is static registry
		 */
		protected DynamicRegistryTagProvider(FabricDataGenerator dataGenerator, Registry<T> registry, String path) {
			super(dataGenerator, registry, path);
			Preconditions.checkArgument(DynamicRegistryManagerAccessor.getInfos().containsKey(registry.key()), "Only dynamic registries are supported in this tag provider.");
		}
	}

	/**
	 * Extend this class to create {@link MobEffect} tags in the "/mob_effects" tag directory.
	 */

	public abstract static class MobEffectTagProvider extends CustomTagProviders<MobEffect> {
		protected MobEffectTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.MOB_EFFECT, "mob_effects");
		}

		public CustomFabricTagBuilder<MobEffect> tagCustom(TagKey<MobEffect> tag) {
			return new CustomFabricTagBuilder<MobEffect>(super.tag(tag));
		}
	}

	public abstract static class NoiseSettingsTagProvider extends DynamicRegistryTagProvider<NoiseGeneratorSettings> {
		protected NoiseSettingsTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE_GENERATOR_SETTINGS, "worldgen/noise_settings");
		}

		public CustomFabricTagBuilder<NoiseGeneratorSettings> tagCustom(TagKey<NoiseGeneratorSettings> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}
	}

	public abstract static class DimensionTypeTagProvider extends CustomTagProviders<DimensionType> {
		protected DimensionTypeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Minecraft.getInstance().getSingleplayerServer().overworld().registryAccess()
				.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), "worldgen/dimension_type");
		}

		public CustomFabricTagBuilder<DimensionType> tagCustom(TagKey<DimensionType> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}
	}

	/*public abstract static class DimensionTagProvider extends CustomTagProviders<Level> {
		protected DimensionTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.DI, "worldgen/dimension", "Dimension Tags");
		}

		public CustomFabricTagBuilder<Level> tagCustom(TagKey<Level> tag) {
			return new CustomFabricTagBuilder<Level>(super.tag(tag));
		}
	}*/

	/**
	 * Extend this class to create {@link NormalNoise.NoiseParameters} tags in the "worldgen/biomes" tag directory.
	 */

	public abstract static class NoiseTagProvider extends DynamicRegistryTagProvider<NormalNoise.NoiseParameters> {
		protected NoiseTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE, "worldgen/noise");
		}

		public CustomFabricTagBuilder<NormalNoise.NoiseParameters> tagCustom(TagKey<NormalNoise.NoiseParameters> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}
	}

	public static final class CustomFabricTagBuilder<T2> extends TagsProvider.TagAppender<T2> {
		private final TagsProvider.TagAppender<T2> parent;

		public CustomFabricTagBuilder(TagsProvider.TagAppender<T2> parent) {
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
		public CustomFabricTagBuilder<T2> setReplace(boolean replace) {
			((net.fabricmc.fabric.impl.datagen.FabricTagBuilder) builder).fabric_setReplace(replace);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T2> add(T2 element) {
			parent.add(element);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T2> add(ResourceLocation id) {
			builder.addElement(id, source);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T2> add(ResourceKey<? extends T2> registryKey) {
			return add(registryKey.location());
		}

		/**
		 * Add an optional {@link ResourceLocation} to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T2> addOptional(@NotNull ResourceLocation id) {
			parent.addOptional(id);
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T2> addTag(@NotNull TagKey<T2> tag) {
			parent.addTag(tag);
			return this;
		}

		/**
		 * Add another optional tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T2> addOptionalTag(ResourceLocation id) {
			parent.addOptionalTag(id);
			return this;
		}

		/**
		 * Add another tag to this tag, ignoring any warning.
		 *
		 * <p><b>Note:</b> only use this method if you sure that the tag will be always available at runtime.
		 * If not, use {@link #addOptionalTag(ResourceLocation)} instead.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T2> forceAddTag(TagKey<T2> tag) {
			builder.addElement(tag.location(), source);
			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@SafeVarargs
		@Override
		public final CustomFabricTagBuilder<T2> add(T2... elements) {
			for (T2 element : elements) {
				add(element);
			}
			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T2> add(ResourceLocation... ids) {
			for (ResourceLocation id : ids) {
				add(id);
			}
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link FabricTagProvider.FabricTagBuilder} instance
		 */
		@SafeVarargs
		public final CustomFabricTagBuilder<T2> addTags(TagKey<T2>... values) {
			for (TagKey<T2> value : values) {
				this.addTag(value);
			}
			return this;
		}

	}

}
