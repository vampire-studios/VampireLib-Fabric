package io.github.vampirestudios.vampirelib.mixins;

import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Ingredient.class)
public interface IngredientAccessor {
	@Accessor
	Ingredient.Value[] getValues();
}
