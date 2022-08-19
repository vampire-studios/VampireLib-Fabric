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

package io.github.vampirestudios.vampirelib.api.itemGroupSorting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Interface implemented on classes for special filling of {@link Item}s in {@link CreativeModeTab}s.
 */
@FunctionalInterface
public interface ItemGroupFiller {
	/**
	 * Fills an {@link Item} for a {@link CreativeModeTab} given a {@link NonNullList} of the {@link ItemStack}s for that {@link CreativeModeTab}.
	 *
	 * @param item  The {@link Item} to fill.
	 * @param tab   The {@link CreativeModeTab} to fill into.
	 * @param items A {@link NonNullList} of the {@link ItemStack}s for the {@link CreativeModeTab}.
	 */
	void fillItem(Item item, CreativeModeTab tab, NonNullList<ItemStack> items);
}
