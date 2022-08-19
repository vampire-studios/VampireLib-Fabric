/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import net.minecraft.tags.TagBuilder;
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
	 * @param registry      The backing registry for the Tag type.
	 */

	protected CustomTagProviders(FabricDataGenerator dataGenerator, Registry<T> registry) {
		super(dataGenerator, registry);
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
			super(dataGenerator, Registry.BLOCK);
		}

		public CustomFabricTagBuilder<Block> tagCustom(TagKey<Block> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	public abstract static class CustomBiomeTagProvider extends CustomTagProviders<Biome> {
		protected CustomBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.BIOME);
		}

		public CustomFabricTagBuilder<Biome> tagCustom(TagKey<Biome> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}

		public TagAppender<Biome> tagCustom(ResourceKey<Biome> biome, TagKey<Biome> tag) {
			return getOrCreateTagBuilder(tag).add(biome);
		}
	}

	public abstract static class CustomItemTagProvider extends CustomTagProviders<Item> {
		@Nullable
		private final Function<TagKey<Block>, TagBuilder> blockTagBuilderProvider;

		protected CustomItemTagProvider(FabricDataGenerator dataGenerator, @Nullable CustomBlockTagProvider blockTagProvider) {
			super(dataGenerator, Registry.ITEM);

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
		 * @param itemTag  The item tag to copy to.
		 */
		public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
			TagBuilder blockTagBuilder = Objects.requireNonNull(this.blockTagBuilderProvider, "Pass Block tag provider via constructor to use copy")
					.apply(blockTag);
			TagBuilder itemTagBuilder = this.getOrCreateRawBuilder(itemTag);
			blockTagBuilder.build().stream()
					.filter(entry -> entry.verifyIfPresent(this.registry::containsKey, id -> true))
					.forEach(itemTagBuilder::add);
		}

		public CustomFabricTagBuilder<Item> tagCustom(TagKey<Item> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	public abstract static class VEntityTagProvider extends CustomTagProviders<EntityType<?>> {
		public VEntityTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.ENTITY_TYPE);
		}

		public CustomFabricTagBuilder<EntityType<?>> tagCustom(TagKey<EntityType<?>> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}
	}

	/**
	 * Extend this class to create dynamic registry tags.
	 *
	 * @param <T> idk
	 */
	public abstract static class DynamicRegistryTagProvider<T> extends CustomTagProviders<T> {
		/**
		 * Construct a new {@link CustomTagProviders.DynamicRegistryTagProvider}.
		 *
		 * @param dataGenerator The data generator instance
		 * @param registry      The registry key of the dynamic registry
		 *
		 * @throws IllegalArgumentException if the registry is static registry
		 */
		protected DynamicRegistryTagProvider(FabricDataGenerator dataGenerator, Registry<T> registry) {
			super(dataGenerator, registry);
			Preconditions.checkArgument(DynamicRegistryManagerAccessor.getInfos().containsKey(registry.key()),
					"Only dynamic registries are supported in this tag provider.");
		}
	}

	/**
	 * Extend this class to create {@link MobEffect} tags in the "/mob_effects" tag directory.
	 */

	public abstract static class MobEffectTagProvider extends CustomTagProviders<MobEffect> {
		protected MobEffectTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.MOB_EFFECT);
		}

		public CustomFabricTagBuilder<MobEffect> tagCustom(TagKey<MobEffect> tag) {
			return new CustomFabricTagBuilder<MobEffect>(super.tag(tag));
		}
	}

	public abstract static class NoiseSettingsTagProvider extends DynamicRegistryTagProvider<NoiseGeneratorSettings> {
		protected NoiseSettingsTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE_GENERATOR_SETTINGS);
		}

		public CustomFabricTagBuilder<NoiseGeneratorSettings> tagCustom(TagKey<NoiseGeneratorSettings> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}
	}

	public abstract static class DimensionTypeTagProvider extends CustomTagProviders<DimensionType> {
		protected DimensionTypeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Minecraft.getInstance().getSingleplayerServer().overworld().registryAccess()
					.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY));
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
			super(dataGenerator, BuiltinRegistries.NOISE);
		}

		public CustomFabricTagBuilder<NormalNoise.NoiseParameters> tagCustom(TagKey<NormalNoise.NoiseParameters> tag) {
			return new CustomFabricTagBuilder<>(super.tag(tag));
		}
	}

	public static final class CustomFabricTagBuilder<T> extends TagsProvider.TagAppender<T> {
		private final TagsProvider.TagAppender<T> parent;

		public CustomFabricTagBuilder(TagsProvider.TagAppender<T> parent) {
			super(parent.builder, parent.registry);
			this.parent = parent;
		}

		/**
		 * Set the value of the `replace` flag in a Tag.
		 *
		 * <p>When set to true the tag will replace any existing tag entries.
		 *
		 * @param replace replace
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
		 * @param element element
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> add(T element) {
			this.parent.add(element);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @param id id
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> add(ResourceLocation id) {
			builder.addElement(id);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @param registryKey registry key
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> add(ResourceKey<? extends T> registryKey) {
			return this.add(registryKey.location());
		}

		/**
		 * Add an optional {@link ResourceLocation} to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> addOptional(@NotNull ResourceLocation id) {
			this.parent.addOptional(id);
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> addTag(@NotNull TagKey<T> tag) {
			this.parent.addTag(tag);
			return this;
		}

		/**
		 * Add another optional tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder<T> addOptionalTag(ResourceLocation id) {
			this.parent.addOptionalTag(id);
			return this;
		}

		/**
		 * Add another tag to this tag, ignoring any warning.
		 *
		 * <p><b>Note:</b> only use this method if you sure that the tag will be always available at runtime.
		 * If not, use {@link #addOptionalTag(ResourceLocation)} instead.
		 *
		 * @param tag tag
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> forceAddTag(TagKey<T> tag) {
			builder.addElement(tag.location());
			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@SafeVarargs
		@Override
		public final CustomFabricTagBuilder<T> add(T... elements) {
			for (T element : elements) {
				this.add(element);
			}

			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @param ids idk
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder<T> add(ResourceLocation... ids) {
			for (ResourceLocation id : ids) {
				this.add(id);
			}

			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @param values values
		 *
		 * @return the {@link FabricTagProvider.FabricTagBuilder} instance
		 */
		@SafeVarargs
		public final CustomFabricTagBuilder<T> addTags(TagKey<T>... values) {
			for (TagKey<T> value : values) {
				this.addTag(value);
			}

			return this;
		}
	}
}
