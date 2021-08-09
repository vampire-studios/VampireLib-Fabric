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

import java.util.Optional;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.world.World;

import io.github.vampirestudios.vampirelib.VampireLib;

public class BrewingRecipeHelper {
    /**
     * Used by the brewing stand to determine if its contents can be brewed.
     * Extra parameters exist to allow modders to create bigger brewing stands
     * without much hassle
     */
    public static boolean canBrew(World level, Inventory container, int reagentSlot, int[] baseSlots) {
        final ItemStack reagent = container.getStack(reagentSlot);
        if (reagent.isEmpty()) return false;
        final RecipeManager recipeManager = level.getRecipeManager();
        for (int baseSlot : baseSlots) {
            ItemStack base = container.getStack(baseSlot);
            final IBrewingContainer c = new IBrewingContainer.Impl(base, reagent);
            Optional<IBrewingRecipe> recipeFor = recipeManager.getFirstMatch(VampireLib.BREWING, c, level);
            if (recipeFor.isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Used by the brewing stand to brew its inventory Extra parameters exist to
     * allow modders to create bigger brewing stands without much hassle
     */
    public static void brewPotions(World level, Inventory container, int reagentSlot, int[] baseSlots) {
        final ItemStack reagent = container.getStack(reagentSlot);
        final RecipeManager recipeManager = level.getRecipeManager();
        for (int baseSlot : baseSlots) {
            final IBrewingContainer wrapper = new IBrewingContainer.Impl(container.getStack(baseSlot), reagent);
            recipeManager.getFirstMatch(VampireLib.BREWING, wrapper, level)
                    .ifPresent(recipe -> container.setStack(baseSlot, recipe.craft(wrapper)));
        }
    }

    /**
     * Returns true if the passed ItemStack is a valid reagent for any of the
     * recipes in the registry.
     */
    public static boolean isValidIngredient(World level, ItemStack stack) {
        if (stack.isEmpty()) return false;

        for (IBrewingRecipe recipe : level.getRecipeManager().listAllOfType(VampireLib.BREWING)) {
            if (recipe.isReagent(stack)) return true;
        }
        return false;
    }

    /**
     * Returns true if the passed ItemStack is a valid base for any of the
     * recipes in the registry.
     */
    public static boolean isValidInput(World level, ItemStack stack) {
        if (stack.getCount() != 1) return false;
        for (IBrewingRecipe recipe : level.getRecipeManager().listAllOfType(VampireLib.BREWING)) {
            if (recipe.isBase(stack)) return true;
        }
        return false;
    }
}