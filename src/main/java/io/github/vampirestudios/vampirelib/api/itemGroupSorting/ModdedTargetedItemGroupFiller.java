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

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.utils.ItemStackUtils;

/**
 * Implementation class of {@link ItemGroupFiller} for filling {@link Item}s after a target {@link Item}.
 *
 * @see ItemGroupFiller
 */
public final class ModdedTargetedItemGroupFiller implements ItemGroupFiller {
	private final String modId;
	private final Item targetItem;
	private final Map<CreativeModeTab, OffsetValue> offsetMap = Maps.newHashMap();

	public ModdedTargetedItemGroupFiller(String modId, Item targetItem) {
		this.modId = modId;
		this.targetItem = targetItem;
	}

	@Override
	public void fillItem(Item item, CreativeModeTab group, NonNullList<ItemStack> items) {
		if (FabricLoader.getInstance().isModLoaded(modId) && ItemStackUtils.isAllowedInTab(item, group)) {
			OffsetValue offset = offsetMap.computeIfAbsent(group, (key) -> new OffsetValue());
			Set<Item> itemsProcessed = offset.itemsProcessed;
			if (itemsProcessed.contains(item)) {
				offset.reset();
			}
			int index = ItemStackUtils.findIndexOfItem(this.targetItem, items);
			if (index != -1) {
				items.add(index + offset.offset, new ItemStack(item));
				itemsProcessed.add(item);
				offset.offset++;
			} else {
				items.add(new ItemStack(item));
			}
		}
	}

	static class OffsetValue {
		private final Set<Item> itemsProcessed = Sets.newHashSet();
		private int offset = 1;

		/**
		 * Vanilla doesn't cache its item group items, so we must make sure the offsets are reset when the process is run again.
		 */
		private void reset() {
			this.offset = 1;
			this.itemsProcessed.clear();
		}
	}
}