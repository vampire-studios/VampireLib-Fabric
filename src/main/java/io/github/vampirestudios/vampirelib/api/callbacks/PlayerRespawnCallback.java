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

package io.github.vampirestudios.vampirelib.api.callbacks;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Represents a callback for when a {@link ServerPlayer} is respawned.
 *
 * <p>This occurs when a player dies, or changes dimensions, such as returning to the overworld from the end.
 */
public interface PlayerRespawnCallback {
	Event<PlayerRespawnCallback> EVENT = EventFactory.createArrayBacked(PlayerRespawnCallback.class, callbacks ->
			(newPlayer, oldPlayer, alive) -> {
				for (PlayerRespawnCallback callback : callbacks) {
					callback.onRespawn(newPlayer, oldPlayer, alive);
				}
			});

	/**
	 * Called when a player respawns.
	 *
	 * <p>Note the player can be repositioned.
	 *
	 * <p>When this method is called, {@code newPlayer} will be at its spawn position.
	 *
	 * <p>The player may be repositioned by callbacks to change where it respawns.
	 * The {@code oldPlayer}'s coordinates and world will be the same as before the respawn,
	 *
	 * <p>Player's data has been copied from the {@code oldPlayer} to {@code newPlayer}.
	 * The actual copied data depends on various factors such as the value of {@code isAlive} or of the {@link GameRules#RULE_KEEPINVENTORY keepInventory} GameRule.
	 *
	 * @param newPlayer The new {@link ServerPlayer} that will be spawned.
	 * @param oldPlayer The old {@link ServerPlayer} that is being removed.
	 * @param isAlive   Whether the old player is still alive.
	 */
	void onRespawn(ServerPlayer newPlayer, ServerPlayer oldPlayer, boolean isAlive);
}
