package io.github.vampirestudios.vampirelib.mixins;

import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;

import io.github.vampirestudios.vampirelib.crafting.CraftingHelper;

import static io.github.vampirestudios.vampirelib.utils.ShapedRecipeUtil.HEIGHT;
import static io.github.vampirestudios.vampirelib.utils.ShapedRecipeUtil.WIDTH;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeMixin {
	@ModifyConstant(
			method = "patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			constant = @Constant(intValue = 3, ordinal = 0)
	)
	private static int vl$modifyMaxHeight(int original) {
		return HEIGHT;
	}

	@ModifyConstant(
			method = "patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			constant = @Constant(intValue = 3, ordinal = 1)
	)
	private static int vl$modifyMaxWidth(int original) {
		return WIDTH;
	}

	@ModifyConstant(method = "patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			constant = @Constant(stringValue = "Invalid pattern: too many rows, 3 is maximum")
	)
	private static String vl$changeHeightWarning(String original) {
		return "Invalid pattern: too many rows, " + HEIGHT + " is maximum";
	}

	@ModifyConstant(method = "patternFromJson(Lcom/google/gson/JsonArray;)[Ljava/lang/String;",
			constant = @Constant(stringValue = "Invalid pattern: too many columns, 3 is maximum")
	)
	private static String vl$changeWidthWarning(String original) {
		return "Invalid pattern: too many columns, " + WIDTH + " is maximum";
	}

	@Inject(method = "itemStackFromJson", at = @At("HEAD"), cancellable = true)
	private static void vl$customNbtItemStack(JsonObject json, CallbackInfoReturnable<ItemStack> cir) {
		if (json.has("nbt"))
			cir.setReturnValue(CraftingHelper.getItemStack(json, true, true));
	}
}