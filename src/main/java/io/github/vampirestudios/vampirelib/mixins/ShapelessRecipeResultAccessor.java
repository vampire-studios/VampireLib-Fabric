package io.github.vampirestudios.vampirelib.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

@Mixin(ShapelessRecipeBuilder.Result.class)
public interface ShapelessRecipeResultAccessor {
    @Accessor
    List<Ingredient> getIngredients();
}
