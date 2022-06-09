package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.item.crafting.ShapelessRecipe;

import io.github.vampirestudios.vampirelib.utils.ShapedRecipeUtil;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipe_SerializerMixin {
	@ModifyConstant(
			method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/minecraft/world/item/crafting/ShapelessRecipe;",
			constant = @Constant(intValue = 9)
	)
	private static int vl$modifyMaxItemsInRecipe(int original) {
		return ShapedRecipeUtil.HEIGHT * ShapedRecipeUtil.WIDTH;
	}
}