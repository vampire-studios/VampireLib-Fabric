/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;

import io.github.vampirestudios.vampirelib.api.callbacks.EntityHealthChangeCallback;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	/**
	 * Callback injector for monitoring entity health changes.
	 */
	@Inject(method = "setHealth", at = @At("HEAD"), cancellable = true)
	private void entityHealthChange(float health, CallbackInfo ci) {
		InteractionResult result = EntityHealthChangeCallback.EVENT.invoker()
				.health(((LivingEntity) (Object) this), health);
		if (result == InteractionResult.FAIL) ci.cancel();
	}

}
