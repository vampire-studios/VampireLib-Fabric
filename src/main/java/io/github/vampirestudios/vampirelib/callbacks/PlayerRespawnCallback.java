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

package io.github.vampirestudios.vampirelib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;

/**
 * Represents a callback for when a {@link ServerPlayerEntity} is respawned.
 *
 * <p>This occurs when a player dies, or changes dimensions, such as returning to the overworld from the end.
 *
 * @apiNote This callback may be used to reposition the respawning player, or copy modded data between the old and the new.
 */
public interface PlayerRespawnCallback {
	Event<PlayerRespawnCallback> EVENT = EventFactory.createArrayBacked(PlayerRespawnCallback.class, (callbacks) -> (newPlayer, oldPlayer, alive) -> {
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
	 * The actual copied data depends on various factors such as the value of {@code isAlive} or of the {@link GameRules#KEEP_INVENTORY keepInventory} GameRule.
	 *
	 * @param newPlayer The new {@link ServerPlayerEntity} that will be spawned.
	 * @param oldPlayer The old {@link ServerPlayerEntity} that is being removed.
	 * @param isAlive Whether the old player is still alive.
	 */
	void onRespawn(ServerPlayerEntity newPlayer, ServerPlayerEntity oldPlayer, boolean isAlive);
}