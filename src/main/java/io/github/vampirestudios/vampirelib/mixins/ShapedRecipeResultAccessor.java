package io.github.vampirestudios.vampirelib.mixins;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

@Mixin(ShapedRecipeBuilder.Result.class)
public interface ShapedRecipeResultAccessor {
    @Accessor
    Map<Character, Ingredient> getKey();
}
