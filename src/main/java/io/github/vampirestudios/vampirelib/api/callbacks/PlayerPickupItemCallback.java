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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback for the player picking up an item entity.
 * Is hooked in before the item is picked up.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further processing and picks up the item.
 * <li>PASS falls back to further processing. If all listeners return PASS, the item is picked up.
 * <li>FAIL cancels further processing and does not pick up the item.</ul>
 */
public interface PlayerPickupItemCallback {
	Event<PlayerPickupItemCallback> EVENT = EventFactory.createArrayBacked(PlayerPickupItemCallback.class, listeners ->
			(player, entity) -> {
				for (PlayerPickupItemCallback event : listeners) {
					InteractionResult result = event.interact(player, entity);
					if (result != InteractionResult.PASS) return result;
				}

				return InteractionResult.PASS;
			}
	);

	InteractionResult interact(Player player, ItemEntity pickupEntity);
}
