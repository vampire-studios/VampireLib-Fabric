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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ColorHandlersCallback {
	Event<Item> ITEM = EventFactory.createArrayBacked(Item.class, callbacks -> ((itemColors, blockColors) -> {
		for (Item event : callbacks) {
			event.registerItemColors(itemColors, blockColors);
		}
	}));

	Event<Block> BLOCK = EventFactory.createArrayBacked(Block.class, callbacks -> blockColors -> {
		for (Block event : callbacks) {
			event.registerBlockColors(blockColors);
		}
	});

	interface Item {
		void registerItemColors(ItemColors itemColors, BlockColors blockColors);
	}

	interface Block {
		void registerBlockColors(BlockColors blockColors);
	}
}
