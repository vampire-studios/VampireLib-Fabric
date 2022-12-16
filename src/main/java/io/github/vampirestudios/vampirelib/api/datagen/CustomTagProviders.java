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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public abstract class CustomTagProviders<T> extends FabricTagProvider<T> {
	/**
	 * Construct a new {@link FabricTagProvider} with the default computed path.
	 *
	 * <p>Common implementations of this class are provided. For example @see BlockTagProvider
	 *
	 * @param output        The {@link FabricDataOutput} instance
	 * @param registriesFuture      The backing registry for the Tag type.
	 */
	public CustomTagProviders(FabricDataOutput output, ResourceKey<? extends Registry<T>> registryKey, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registryKey, registriesFuture);
	}

	/**
	 * Implement this method and then use {@link FabricTagProvider#tag} to get and register new tag builders.
	 */
	protected abstract void addTags(HolderLookup.Provider arg);

	/**
	 * Override to enable adding objects to the tag builder directly.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	protected ResourceKey<T> reverseLookup(T element) {
		Registry registry = BuiltInRegistries.REGISTRY.get((ResourceKey) registryKey);

		if (registry != null) {
			Optional<Holder<T>> key = registry.getResourceKey(element);

			if (key.isPresent()) {
				return (ResourceKey<T>) key.get();
			}
		}

		throw new UnsupportedOperationException("Adding objects is not supported by " + getClass());
	}

	public CustomFabricTagBuilder getOrCreateTagBuilderCustom(TagKey<T> tag) {
		return new CustomFabricTagBuilder(super.tag(tag));
	}

	@Override
	public CustomFabricTagBuilder tag(@NotNull TagKey<T> tag) {
		return new CustomFabricTagBuilder(super.tag(tag));
	}

	public abstract static class CustomBlockTagProvider extends CustomTagProviders<Block> {
		public CustomBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, Registries.BLOCK, registriesFuture);
		}

		public CustomFabricTagBuilder tagCustom(TagKey<Block> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	public abstract static class CustomItemTagProvider extends CustomTagProviders<Item> {
		@Nullable
		private final Function<TagKey<Block>, TagBuilder> blockTagBuilderProvider;

		protected CustomItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable CustomBlockTagProvider blockTagProvider) {
			super(output, Registries.ITEM, completableFuture);

			this.blockTagBuilderProvider = blockTagProvider == null ? null : blockTagProvider::getOrCreateRawBuilder;
		}

		/**
		 * Construct an {@link ItemTagProvider} tag provider <b>without</b> an associated {@link BlockTagProvider} tag provider.
		 */
		public CustomItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			this(output, completableFuture, null);
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
			TagBuilder blockTagBuilder = Objects.requireNonNull(this.blockTagBuilderProvider, "Pass Block tag provider via constructor to use copy").apply(blockTag);
			TagBuilder itemTagBuilder = this.getOrCreateRawBuilder(itemTag);
			blockTagBuilder.build().forEach(itemTagBuilder::add);
		}

		public CustomFabricTagBuilder tagCustom(TagKey<Item> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	/**
	 * Extend this class to create {@link Fluid} tags in the "/fluids" tag directory.
	 */
	public abstract static class FluidTagProvider extends CustomTagProviders<Fluid> {
		public FluidTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, Registries.FLUID, completableFuture);
		}

		@Override
		protected ResourceKey<Fluid> reverseLookup(Fluid element) {
			return element.builtInRegistryHolder().key();
		}

		public CustomFabricTagBuilder tagCustom(TagKey<Fluid> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	/**
	 * Extend this class to create {@link Enchantment} tags in the "/enchantments" tag directory.
	 */
	public abstract static class EnchantmentTagProvider extends CustomTagProviders<Enchantment> {
		public EnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, Registries.ENCHANTMENT, completableFuture);
		}

		@Override
		protected ResourceKey<Enchantment> reverseLookup(Enchantment element) {
			return BuiltInRegistries.ENCHANTMENT.getResourceKey(element)
					.orElseThrow(() -> new IllegalArgumentException("Enchantment " + element + " is not registered"));
		}

		public CustomFabricTagBuilder tagCustom(TagKey<Enchantment> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	/**
	 * Extend this class to create {@link EntityType} tags in the "/entity_types" tag directory.
	 */
	public abstract static class EntityTypeTagProvider extends CustomTagProviders<EntityType<?>> {
		public EntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, Registries.ENTITY_TYPE, completableFuture);
		}

		@Override
		protected ResourceKey<EntityType<?>> reverseLookup(EntityType<?> element) {
			return element.builtInRegistryHolder().key();
		}

		public CustomFabricTagBuilder tagCustom(TagKey<EntityType<?>> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	/**
	 * Extend this class to create {@link GameEvent} tags in the "/game_events" tag directory.
	 */
	public abstract static class GameEventTagProvider extends CustomTagProviders<GameEvent> {
		public GameEventTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, Registries.GAME_EVENT, completableFuture);
		}

		@Override
		protected ResourceKey<GameEvent> reverseLookup(GameEvent element) {
			return element.builtInRegistryHolder().key();
		}

		public CustomFabricTagBuilder tagCustom(TagKey<GameEvent> tag) {
			return this.getOrCreateTagBuilderCustom(tag);
		}
	}

	public final class CustomFabricTagBuilder extends TagsProvider.TagAppender<T> {
		private final TagsProvider.TagAppender<T> parent;

		public CustomFabricTagBuilder(TagsProvider.TagAppender<T> parent) {
			super(parent.builder);
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
		public CustomFabricTagBuilder setReplace(boolean replace) {
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
		public CustomFabricTagBuilder add(T element) {
			add(reverseLookup(element));
			return this;
		}

		/**
		 * Add multiple elements to the tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 */
		@SafeVarargs
		public final CustomFabricTagBuilder add(T... element) {
			Stream.of(element).map(CustomTagProviders.this::reverseLookup).forEach(this::addAlt);
			return this;
		}

		/**
		 * Add an element to the tag.
		 *
		 * @return the {@link FabricTagBuilder} instance
		 * @see #add(ResourceLocation)
		 */
		public CustomFabricTagBuilder addAlt(ResourceKey<T> registryKey) {
			parent.add(registryKey);
			return this;
		}

		/**
		 * Add a single element to the tag.
		 *
		 * @param id id
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder add(ResourceLocation id) {
			builder.addElement(id);
			return this;
		}

		/**
		 * Add an optional {@link ResourceLocation} to the tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder addOptional(@NotNull ResourceLocation id) {
			this.parent.addOptional(id);
			return this;
		}

		/**
		 * Add another tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder addTag(@NotNull TagKey<T> tag) {
			this.parent.addTag(tag);
			return this;
		}

		/**
		 * Add another optional tag to this tag.
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		@Override
		public CustomFabricTagBuilder addOptionalTag(ResourceLocation id) {
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
		public CustomFabricTagBuilder forceAddTag(TagKey<T> tag) {
			builder.addElement(tag.location());
			return this;
		}

		/**
		 * Add multiple elements to this tag.
		 *
		 * @param ids idk
		 *
		 * @return the {@link CustomFabricTagBuilder} instance
		 */
		public CustomFabricTagBuilder add(ResourceLocation... ids) {
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
		public final CustomFabricTagBuilder addTags(TagKey<T>... values) {
			for (TagKey<T> value : values) {
				this.addTag(value);
			}

			return this;
		}
	}
}
