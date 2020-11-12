/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.mixins;

import io.github.vampirestudios.vampirelib.api.ElytraRegistry;
import io.github.vampirestudios.vampirelib.api.ShieldRegistry;
import io.github.vampirestudios.vampirelib.callbacks.EntityHealthChangeCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	/**
	 * Callback injector for monitoring entity health changes.
	 */
	@Inject(at = @At("INVOKE"), method = "setHealth(F)V", cancellable = true)
	private void entityHealthChange(float health, CallbackInfo ci) {
		ActionResult result = EntityHealthChangeCallback.EVENT.invoker().health(((LivingEntity) (Object) this), health);
		if (result == ActionResult.FAIL) {
			ci.cancel();
		}
	}

	/**
	 * Sets the preferred equipment slot for modded shields to offhand.
	 */
	@Inject(method = "method_32326", at = @At("HEAD"), cancellable = true)
	private static void addPreferredShieldsSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> info) {
		if (ShieldRegistry.INSTANCE.isShield(stack.getItem())) {
			info.setReturnValue(EquipmentSlot.OFFHAND);
		}
		if (ElytraRegistry.INSTANCE.isElytra(stack.getItem())) {
			info.setReturnValue(EquipmentSlot.CHEST);
		}
	}

}