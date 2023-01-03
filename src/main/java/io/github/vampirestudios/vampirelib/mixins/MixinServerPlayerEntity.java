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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import io.github.vampirestudios.vampirelib.api.callbacks.PlayerDropItemCallback;

@Mixin(ServerPlayer.class)
public class MixinServerPlayerEntity {
	@Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
			at = @At("HEAD"))
	private void onPlayerDropItem(final ItemStack stack, final boolean dropAtFeet, final boolean saveThrower, final CallbackInfoReturnable<ItemEntity> info) {
		ServerPlayer player = ServerPlayer.class.cast(this);
		InteractionResult result = PlayerDropItemCallback.EVENT.invoker().interact(player, stack);
		if (result == InteractionResult.FAIL) info.cancel();
	}
}
