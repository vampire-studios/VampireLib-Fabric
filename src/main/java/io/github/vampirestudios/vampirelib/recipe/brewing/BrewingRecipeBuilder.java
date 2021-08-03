/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package io.github.vampirestudios.vampirelib.recipe.brewing;

import java.util.function.Consumer;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import io.github.vampirestudios.vampirelib.VampireLib;

/**
 * Class for building brewing recipes.
 */
public abstract class BrewingRecipeBuilder {
    protected final RecipeSerializer<?> type;

    public BrewingRecipeBuilder(RecipeSerializer<?> type) {
        this.type = type;
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a normal brewing recipe
     *
     * @param base    the {@link Ingredient} used as a brewing base (bottom slots)
     * @param reagent the {@link Ingredient} used as a brewing reagent (top slot)
     * @param result  the {@link ItemStack} result of the brewing recipe (replaces base after brewing)
     * @return the builder for a normal brewing recipe
     */
    public static BrewingRecipeBuilder brewing(Ingredient base, Ingredient reagent, ItemStack result) {
        return new NormalBrewingRecipeBuilder(base, reagent, result);
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a container brewing recipe
     *
     * @param base    the {@link Item} used as a container in the bottom slots
     * @param reagent the {@link Tag <Item>} used as a brewing reagent (top slot)
     * @param result  the {@link Item} result of the brewing recipe (replaces base after brewing, potions will be copied)
     * @return the builder for a container brewing recipe
     */
    public static BrewingRecipeBuilder container(Item base, Tag<Item> reagent, Item result) {
        return container(base, Ingredient.fromTag(reagent), result);
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a container brewing recipe
     *
     * @param base    the {@link Item} used as a container in the bottom slots
     * @param reagent the {@link Item} used as a brewing reagent (top slot)
     * @param result  the {@link Item} result of the brewing recipe (replaces base after brewing, potions will be copied)
     * @return the builder for a container brewing recipe
     */
    public static BrewingRecipeBuilder container(Item base, Item reagent, Item result) {
        return container(base, Ingredient.ofItems(reagent), result);
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a container brewing recipe
     *
     * @param base    the {@link Item} used as a container in the bottom slots
     * @param reagent the {@link Ingredient} used as a brewing reagent (top slot)
     * @param result  the {@link Item} result of the brewing recipe (replaces base after brewing, potions will be copied)
     * @return the builder for a container brewing recipe
     */
    public static BrewingRecipeBuilder container(Item base, Ingredient reagent, Item result) {
        return new ContainerBrewingRecipeBuilder(base, reagent, result);
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a mixing brewing recipe
     *
     * @param base    the {@link Potion} used as a base (bottom slots)
     * @param reagent the {@link Tag<Item>} used as a brewing reagent (top slot)
     * @param result  the {@link Potion} result of the brewing recipe (replaces base after brewing)
     * @return the builder for a mixing brewing recipe
     */
    public static BrewingRecipeBuilder mixing(Potion base, Tag<Item> reagent, Potion result) {
        return mixing(base, Ingredient.fromTag(reagent), result);
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a mixing brewing recipe
     *
     * @param base    the {@link Potion} used as a base (bottom slots)
     * @param reagent the {@link Item} used as a brewing reagent (top slot)
     * @param result  the {@link Potion} result of the brewing recipe (replaces base after brewing)
     * @return the builder for a mixing brewing recipe
     */
    public static BrewingRecipeBuilder mixing(Potion base, Item reagent, Potion result) {
        return mixing(base, Ingredient.ofItems(reagent), result);
    }

    /**
     * Create a {@link BrewingRecipeBuilder} for a mixing brewing recipe
     *
     * @param base    the {@link Potion} used as a base (bottom slots)
     * @param reagent the {@link Ingredient} used as a brewing reagent (top slot)
     * @param result  the {@link Potion} result of the brewing recipe (replaces base after brewing)
     * @return the builder for a mixing brewing recipe
     */
    public static BrewingRecipeBuilder mixing(Potion base, Ingredient reagent, Potion result) {
        return new MixingBrewingRecipeBuilder(base, reagent, result);
    }

    /**
     * Save the recipe with the given key
     *
     * @param recipe the consumer to which the built recipe will be passed
     * @param key    the key to save the recipe under
     */
    public void save(Consumer<RecipeJsonProvider> recipe, String key) {
        this.save(recipe, new Identifier(key));
    }

    /**
     * Save the recipe with the given location/id
     *
     * @param recipe   the consumer to which the built recipe will be passed
     * @param location the location to save the recipe under
     */
    public void save(Consumer<RecipeJsonProvider> recipe, Identifier location) {
        validate(location);
        recipe.accept(build(location));
    }

    protected abstract void validate(Identifier id);

    /**
     * Build the recipe with the given location
     *
     * @param location the location/id of the recipe
     */
    protected abstract RecipeJsonProvider build(Identifier location);

    public static class NormalBrewingRecipeBuilder extends BrewingRecipeBuilder {
        private final Ingredient base;
        private final Ingredient reagent;
        private final ItemStack result;

        public NormalBrewingRecipeBuilder(Ingredient base, Ingredient reagent, ItemStack result) {
            super(VampireLib.BREWING_SERIALIZER);
            this.base = base;
            this.reagent = reagent;
            this.result = result;
        }

        @Override
        protected void validate(Identifier id) {
            if (!Registry.ITEM.containsId(Registry.ITEM.getId(this.result.getItem())))
                throw new IllegalArgumentException("Tried to use not registered item as result for " + id);
        }

        @Override
        protected RecipeJsonProvider build(final Identifier location) {
            return new Result(location, this.type, this.base, this.reagent, this.result);
        }

        public record Result(Identifier id,
                             RecipeSerializer<?> type,
                             Ingredient base,
                             Ingredient reagent,
                             ItemStack result) implements RecipeJsonProvider {

            @Override
            public void serialize(JsonObject tag) {
                tag.add("base", this.base.toJson());
                tag.add("reagent", this.reagent.toJson());
                JsonObject o = new JsonObject();
                final Identifier result = Registry.ITEM.getId(this.result.getItem());
                o.addProperty("item", result.toString());
                o.addProperty("count", this.result.getCount());
                if (this.result.hasNbt()) {
                    o.add("nbt", JsonHelper.deserialize(this.result.getNbt().toString()));
                }
                tag.add("result", o);
            }

            @Override
            public Identifier getRecipeId() {
                return this.id;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return this.type;
            }

            @Override
            public JsonObject toAdvancementJson() {
                return null;
            }

            @Nullable
            @Override
            public Identifier getAdvancementId() {
                return null;
            }
        }
    }

    public static class MixingBrewingRecipeBuilder extends BrewingRecipeBuilder {
        private final Potion base;
        private final Ingredient reagent;
        private final Potion result;

        public MixingBrewingRecipeBuilder(Potion base, Ingredient reagent, Potion result) {
            super(VampireLib.MIXING_BREWING_SERIALIZER);
            this.base = base;
            this.reagent = reagent;
            this.result = result;
        }

        @Override
        protected void validate(final Identifier id) {
            if (!Registry.POTION.containsId(Registry.POTION.getId(this.base)))
                throw new IllegalArgumentException("Tried to use not registered potion as base for " + id);
            if (!Registry.POTION.containsId(Registry.POTION.getId(this.result)))
                throw new IllegalArgumentException("Tried to use not registered potion as result for " + id);
        }

        @Override
        protected RecipeJsonProvider build(Identifier location) {
            return new Result(location, this.type, this.base, this.reagent, this.result);
        }

        public record Result(Identifier id,
                             RecipeSerializer<?> type,
                             Potion base,
                             Ingredient reagent,
                             Potion result) implements RecipeJsonProvider {

            @Override
            public void serialize(JsonObject tag) {
                final Identifier base = Registry.POTION.getId(this.base);
                final Identifier result = Registry.POTION.getId(this.result);
                tag.addProperty("base", base.toString());
                tag.add("reagent", this.reagent.toJson());
                tag.addProperty("result", result.toString());
            }

            @Override
            public Identifier getRecipeId() {
                return this.id;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return this.type;
            }

            @Override
            public JsonObject toAdvancementJson() {
                return null;
            }

            @Nullable
            @Override
            public Identifier getAdvancementId() {
                return null;
            }
        }
    }

    public static class ContainerBrewingRecipeBuilder extends BrewingRecipeBuilder {
        private final Item base;
        private final Ingredient reagent;
        private final Item result;

        public ContainerBrewingRecipeBuilder(Item base, Ingredient reagent, Item result) {
            super(VampireLib.CONTAINER_BREWING_SERIALIZER);
            this.base = base;
            this.reagent = reagent;
            this.result = result;
        }

        @Override
        protected void validate(final Identifier id) {
            if (!Registry.ITEM.containsId(Registry.ITEM.getId(this.base)))
                throw new IllegalArgumentException("Tried to use not registered item as base for " + id);
            if (!Registry.ITEM.containsId(Registry.ITEM.getId(this.result)))
                throw new IllegalArgumentException("Tried to use not registered item as result for " + id);
        }

        @Override
        protected RecipeJsonProvider build(Identifier location) {
            return new Result(location, this.type, this.base, this.reagent, this.result);
        }

        public record Result(Identifier id,
                             RecipeSerializer<?> type,
                             Item base,
                             Ingredient reagent,
                             Item result) implements RecipeJsonProvider {

            @Override
            public void serialize(JsonObject tag) {
                final Identifier base = Registry.ITEM.getId(this.base);
                final Identifier result = Registry.ITEM.getId(this.result);
                tag.addProperty("base", base.toString());
                tag.add("reagent", this.reagent.toJson());
                tag.addProperty("result", result.toString());
            }

            @Override
            public Identifier getRecipeId() {
                return this.id;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return this.type;
            }

            @Override
            public JsonObject toAdvancementJson() {
                return null;
            }

            @Nullable
            @Override
            public Identifier getAdvancementId() {
                return null;
            }
        }
    }
}