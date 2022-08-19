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

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback for the player dropping an item entity.
 * Is hooked in before the item is dropped.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further processing and drops the item.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and does not drop the item.</ul>
 */
public interface PlayerDropItemCallback {
	Event<PlayerDropItemCallback> EVENT = EventFactory.createArrayBacked(PlayerDropItemCallback.class, listeners ->
			(player, stack) -> {
				for (PlayerDropItemCallback event : listeners) {
					InteractionResult result = event.interact(player, stack);
					if (result != InteractionResult.PASS) return result;
				}

				return InteractionResult.PASS;
			}
	);

	InteractionResult interact(Player player, ItemStack stack);
}
