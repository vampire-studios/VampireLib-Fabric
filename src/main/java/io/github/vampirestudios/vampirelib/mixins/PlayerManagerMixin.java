/*
 * Copyright (c) 2023 OliviaTheVampire
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

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeatureSync;

@Mixin(PlayerList.class)
public class PlayerManagerMixin {
	@Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;sendPlayerPermissionLevel(Lnet/minecraft/server/level/ServerPlayer;)V"))
	private void quilt$tellPlayerAboutDebugFeatures(Connection netManager, ServerPlayer player, CallbackInfo ci) {
		DebugFeatureSync.syncFeaturesToClient(player);
	}
}
