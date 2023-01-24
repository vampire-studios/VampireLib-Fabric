/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import io.github.vampirestudios.vampirelib.api.callbacks.PlayerDamageListener;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityDamageMixin {
	@Inject(method = "hurt", at = @At("HEAD"))
	private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
		ServerPlayer player = (ServerPlayer) (Object) this;

		if (player.level.isClientSide) return;

		boolean cancel = PlayerDamageListener.EVENT.invoker().onDamage(player, source, amount);
		if (cancel) ci.cancel();
	}
}
