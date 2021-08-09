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

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import io.github.vampirestudios.vampirelib.VampireLib;

public record ContainerBrewingRecipe(@NotNull Identifier id,
                                     @NotNull Item base,
                                     @NotNull Ingredient reagent,
                                     @NotNull Item result) implements IBrewingRecipe {
    @Override
    public ItemStack craft(final IBrewingContainer container) {
        return PotionUtil.setPotion(new ItemStack(result()), PotionUtil.getPotion(container.base()));
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return id();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VampireLib.CONTAINER_BREWING_SERIALIZER;
    }

    @Override
    public boolean isReagent(final ItemStack reagent) {
        return reagent().test(reagent);
    }

    @Override
    public boolean isBase(final ItemStack base) {
        return base.isOf(base());
    }

    public static class Serializer implements RecipeSerializer<ContainerBrewingRecipe> {
        @Override
        public ContainerBrewingRecipe read(final Identifier id, final JsonObject json) {
            Item base = Registry.ITEM.get(new Identifier(JsonHelper.getString(json, "base")));
            Ingredient reagent = Ingredient.fromJson(JsonHelper.getObject(json, "reagent"));
            Item result = Registry.ITEM.get(new Identifier(JsonHelper.getString(json, "result")));
            return new ContainerBrewingRecipe(id, base, reagent, result);
        }

        @Nullable
        @Override
        public ContainerBrewingRecipe read(final Identifier id, final PacketByteBuf buf) {
            Item base = Registry.ITEM.get(buf.readIdentifier());
            Ingredient reagent = Ingredient.fromPacket(buf);
            Item result = Registry.ITEM.get(buf.readIdentifier());
            return new ContainerBrewingRecipe(id, base, reagent, result);
        }

        @Override
        public void write(final PacketByteBuf buf, final ContainerBrewingRecipe recipe) {
            buf.writeIdentifier(Registry.ITEM.getId(recipe.base()));
            recipe.reagent().write(buf);
            buf.writeIdentifier(Registry.ITEM.getId(recipe.result()));
        }
    }
}