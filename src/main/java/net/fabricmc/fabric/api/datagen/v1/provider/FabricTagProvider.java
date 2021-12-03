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

package net.fabricmc.fabric.api.datagen.v1.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implement this class (or one of the inner classes) to generate a tag list.
 *
 * <p>Register your implementation using {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 *
 * <p>Commonly used implementations of this class are provided:
 *
 * @see BlockTagProvider
 * @see ItemTagProvider
 * @see FluidTagProvider
 * @see EntityTypeTagProvider
 * @see GameEventTagProvider
 */
public abstract class FabricTagProvider<T> extends TagsProvider<T> {
	private final String path;
	private final String name;

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
	protected FabricTagProvider(FabricDataGenerator dataGenerator, Registry<T> registry, String path, String name) {
		super(dataGenerator, registry);
		this.path = path;
		this.name = name;
	}

	/**
	 * Implement this method and then use {@link FabricTagProvider#getOrCreateRawBuilder} to get and register new tag builders.
	 */
	protected abstract void generateTags();

	/**
	 * Creates a new instance of {@link FabricTagBuilder} for the given {@link Tag.Named} tag.
	 *
	 * @param tag The {@link Tag.Named} tag to create the builder for
	 * @return The {@link FabricTagBuilder} instance
	 */
	@Override
	public FabricTagBuilder<T> tag(Tag.Named<T> tag) {
		return new FabricTagBuilder<>(super.tag(tag));
	}

	@Override
	protected Path getPath(ResourceLocation id) {
		return this.generator.getOutputFolder().resolve("data/%s/tags/%s/%s.json".formatted(id.getNamespace(), path, id.getPath()));
	}

	@Override
	protected final void addTags() {
		generateTags();
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Extend this class to create {@link Block} tags in the "/blocks" tag directory.
	 */
	public abstract static class BlockTagProvider extends FabricTagProvider<Block> {
		public BlockTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.BLOCK, "blocks", "Block Tags");
		}
	}

	/**
	 * Extend this class to create {@link Item} tags in the "/items" tag directory.
	 */
	public abstract static class ItemTagProvider extends FabricTagProvider<Item> {
		@Nullable
		private final Function<Tag.Named<Block>, Tag.Builder> blockTagBuilderProvider;

		/**
		 * Construct an {@link ItemTagProvider} tag provider <b>with</b> an associated {@link BlockTagProvider} tag provider.
		 *
		 * @param dataGenerator a {@link ItemTagProvider} tag provider
		 */
		public ItemTagProvider(FabricDataGenerator dataGenerator, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
			super(dataGenerator, Registry.ITEM, "items", "Item Tags");

			this.blockTagBuilderProvider = blockTagProvider == null ? null : blockTagProvider::getOrCreateRawBuilder;
		}

		/**
		 * Construct an {@link ItemTagProvider} tag provider <b>without</b> an associated {@link BlockTagProvider} tag provider.
		 *
		 * @param dataGenerator a {@link ItemTagProvider} tag provider
		 */
		public ItemTagProvider(FabricDataGenerator dataGenerator) {
			this(dataGenerator, null);
		}

		/**
		 * Copy the entries from a tag with the {@link Block} type into this item tag.
		 *
		 * <p>The {@link ItemTagProvider} tag provider must be constructed with an associated {@link BlockTagProvider} tag provider to use this method.
		 *
		 * @param blockTag The block tag to copy from.
		 * @param itemTag The item tag to copy to.
		 */
		public void copy(Tag.Named<Block> blockTag, Tag.Named<Item> itemTag) {
			Tag.Builder blockTagBuilder = Objects.requireNonNull(this.blockTagBuilderProvider, "Pass Block tag provider via constructor to use copy").apply(blockTag);
			Tag.Builder itemTagBuilder = this.getOrCreateRawBuilder(itemTag);
			blockTagBuilder.getEntries().forEach(itemTagBuilder::add);
		}
	}

	/**
	 * Extend this class to create {@link Fluid} tags in the "/fluids" tag directory.
	 */
	public abstract static class FluidTagProvider extends FabricTagProvider<Fluid> {
		public FluidTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.FLUID, "fluids", "Fluid Tags");
		}
	}

	/**
	 * Extend this class to create {@link EntityType} tags in the "/entity_types" tag directory.
	 */
	public abstract static class EntityTypeTagProvider extends FabricTagProvider<EntityType<?>> {
		public EntityTypeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.ENTITY_TYPE, "entity_types", "Entity Type Tags");
		}
	}

	/**
	 * Extend this class to create {@link GameEvent} tags in the "/game_events" tag directory.
	 */
	public abstract static class GameEventTagProvider extends FabricTagProvider<GameEvent> {
		public GameEventTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.GAME_EVENT, "game_events", "Game Event Tags");
		}
	}

	/**
	 * Extend this class to create {@link MobEffect} tags in the "/mob_effects" tag directory.
	 */
	public abstract static class MobEffectTagProvider extends FabricTagProvider<MobEffect> {
		public MobEffectTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.MOB_EFFECT, "mob_effects", "Mob Effect Tags");
		}
	}

	/**
	 * Extend this class to create {@link DimensionType} tags in the "worldgen/biomes" tag directory.
	 */
	public abstract static class ExpandedBiomeTagProvider extends FabricTagProvider<Biome> {
		public ExpandedBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.BIOME, "worldgen/biomes", "Expanded Biome Tags");
		}
	}

	/**
	 * Extend this class to create {@link Biome} tags in the "/biomes" tag directory.
	 */
	public abstract static class BiomeTagProvider extends FabricTagProvider<Biome> {
		public BiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.BIOME, "biomes", "Biome Tags");
		}
	}

	/**
	 * Extend this class to create {@link NormalNoise.NoiseParameters} tags in the "worldgen/biomes" tag directory.
	 */
	public abstract static class NoiseTagProvider extends FabricTagProvider<NormalNoise.NoiseParameters> {
		public NoiseTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE, "worldgen/noises", "Noise Tags");
		}
	}

	/**
	 * An extension to {@link TagAppender} that provides additional functionality.
	 */
	public static class FabricTagBuilder<T> extends TagAppender<T> {
		private final TagAppender<T> parent;

		private FabricTagBuilder(TagAppender<T> parent) {
			super(parent.builder, parent.registry, parent.source);
			this.parent = parent;
		}

		/**
		 * Set the value of the `replace` flag in a Tag.
		 *
		 * <p>When set to true the tag will replace any existing tag entries.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		public FabricTagBuilder<T> setReplace(boolean replace) {
			((net.fabricmc.fabric.impl.datagen.v1.FabricTagBuilder) builder).fabric_setReplace(replace);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		@Override
		public FabricTagBuilder<T> add(T element) {
			parent.add(element);
			return this;
		}

		/**
		 * Add an optional {@link ResourceLocation} to the tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		@Override
		public FabricTagBuilder<T> addOptional(ResourceLocation id) {
			parent.addOptional(id);
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		@Override
		public FabricTagBuilder<T> addTag(Tag.Named<T> tag) {
			parent.addTag(tag);
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		public FabricTagBuilder<T> addTags(Tag.Named<T>... values) {
			for (Tag.Named<T> value : values) {
				this.addTag(value);
			}
			return this;
		}

		/**
		 * Add another optional tag to this tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		@Override
		public FabricTagBuilder<T> addOptionalTag(ResourceLocation id) {
			parent.addOptionalTag(id);
			return this;
		}
	}
}
