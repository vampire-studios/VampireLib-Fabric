package io.github.vampirestudios.vampirelib.recipe.brewing;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

import io.github.vampirestudios.vampirelib.VampireLib;

public interface IBrewingRecipe extends Recipe<IBrewingContainer> {
    @Override
    default boolean matches(final IBrewingContainer container, final World level) {
        return isBase(container.base()) && isReagent(container.reagent());
    }

    @Override
    default boolean fits(int width, int height) {
        return true;
    }

    @Override
    default RecipeType<?> getType()
    {
        return VampireLib.BREWING;
    }

    boolean isReagent(final ItemStack reagent);

    boolean isBase(final ItemStack base);
}