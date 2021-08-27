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

package io.github.vampirestudios.vampirelib.recipe.crafting;

import com.google.gson.JsonObject;

import net.minecraft.block.Blocks;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import io.github.vampirestudios.vampirelib.VampireLib;

/**
 * A Recipe for the anvil. It takes 2 ingredients as input and returns an ItemStack.
 */
public class BlacksmithingRecipe implements Recipe<Inventory> {
    public static final RecipeSerializer<BlacksmithingRecipe> SERIALIZER = new Serializer();

    private Identifier id;
    private Ingredient base;
    private Ingredient addition;
    private ItemStack result;
    private int cost;

    public BlacksmithingRecipe(Identifier id,Ingredient base, Ingredient addition, ItemStack result, int cost)
    {
        this.id = id;
        this.base = base;
        this.addition = addition;
        this.result = result;
        this.cost = cost;
    }
    @Override
    public boolean matches(Inventory inventory, World level)
    {
        return this.base.test(inventory.getStack(0)) && this.addition.test(inventory.getStack(1));
    }
    @Override
    public ItemStack craft(Inventory inventory)
    {
        return this.result.copy();
    }  
    @Override
    public boolean fits(int width, int heigth)
    {
        return true;
    }
    @Override
    public ItemStack getOutput()
    {
        return this.result;
    }
    @Override
    public Identifier getId()
    {   
        return this.id;
    }
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SERIALIZER;
    }
    @Override
    public RecipeType<?> getType()
    {
        return VampireLib.BLACKSMITHING;
    }

    public int getCost() {
    	return this.cost;
    }

    public ItemStack getToastSymbol() 
    {
	    return new ItemStack(Blocks.ANVIL);
    }

    public static class Serializer implements RecipeSerializer<BlacksmithingRecipe> {

        @Override
        public BlacksmithingRecipe read(Identifier id, JsonObject jsonobj) {
            Ingredient base = Ingredient.fromJson(JsonHelper.asObject(jsonobj, "base"));
            Ingredient addition = Ingredient.fromJson(JsonHelper.asObject(jsonobj, "addition"));
            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.asObject(jsonobj, "result"));
            int cost = JsonUtils.getIntOr("cost", jsonobj, 0);
            return new BlacksmithingRecipe(id, base, addition, result, cost);
        }

        @Override
        public BlacksmithingRecipe read(Identifier id, PacketByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromPacket(buffer);
            Ingredient ingredient1 = Ingredient.fromPacket(buffer);
            ItemStack result = buffer.readItemStack();
            int cost = buffer.readInt();
            return new BlacksmithingRecipe(id, ingredient, ingredient1, result, cost);
        }

        @Override
        public void write(PacketByteBuf buffer, BlacksmithingRecipe recipe) {
            recipe.base.write(buffer);
            recipe.addition.write(buffer);
            buffer.writeItemStack(recipe.result);
            buffer.writeInt(recipe.cost);
        }
    }
}