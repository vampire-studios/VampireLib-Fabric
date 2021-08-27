package io.github.vampirestudios.vampirelib.recipe.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import io.github.vampirestudios.vampirelib.VampireLib;

public interface IBrewingRecipe extends Recipe<IBrewingContainer> {
    @Override
    default boolean matches(final IBrewingContainer container, final Level level) {
        return isBase(container.base()) && isReagent(container.reagent());
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
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