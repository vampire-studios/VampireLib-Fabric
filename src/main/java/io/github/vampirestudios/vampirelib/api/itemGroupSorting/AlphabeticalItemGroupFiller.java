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

import java.util.function.Predicate;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import io.github.vampirestudios.vampirelib.utils.ItemStackUtils;

/**
 * Implementation class of {@link ItemGroupFiller} for filling {@link Item}s alphabetically.
 * <p>{@link #shouldInclude} is used to test what items should be considered when inserting an item at its alphabetical position.</p>
 *
 * @see ItemGroupFiller
 */
public final class AlphabeticalItemGroupFiller implements ItemGroupFiller {
	private final Predicate<Item> shouldInclude;

	public AlphabeticalItemGroupFiller(Predicate<Item> shouldInclude) {
		this.shouldInclude = shouldInclude;
	}

	/**
	 * Creates an {@link AlphabeticalItemGroupFiller} that fills items alphabetically for items that are an instance of a class. (e.g. Having a modded spawn egg filled alphabetically into the vanilla's spawn eggs)
	 *
	 * @param clazz The class to test for.
	 * @param <I>   The type of the class, must extend {@link Item}.
	 *
	 * @return An {@link AlphabeticalItemGroupFiller} that fills items alphabetically for items that are an instance of a class. (e.g. Having a modded spawn egg filled alphabetically into the vanilla's spawn eggs)
	 */
	public static <I extends Item> AlphabeticalItemGroupFiller forClass(Class<I> clazz) {
		return new AlphabeticalItemGroupFiller(clazz::isInstance);
	}

	@Override
	public void fillItem(Item item, CreativeModeTab group, NonNullList<ItemStack> items) {
		if (ItemStackUtils.isAllowedInTab(item, group)) {
			ResourceLocation location = Registry.ITEM.getKey(item);
			String itemName = location.getPath();
			int insert = -1;
			for (int i = 0; i < items.size(); i++) {
				Item next = items.get(i).getItem();
				if (this.shouldInclude.test(next)) {
					ResourceLocation nextName = Registry.ITEM.getKey(next);
					if (itemName.compareTo(nextName.getPath()) > 0) {
						insert = i + 1;
					} else if (insert == -1) {
						insert += i + 1;
					} else {
						break;
					}
				}
			}
			if (insert == -1) {
				items.add(new ItemStack(item));
			} else {
				items.add(insert, new ItemStack(item));
			}
		}
	}
}
