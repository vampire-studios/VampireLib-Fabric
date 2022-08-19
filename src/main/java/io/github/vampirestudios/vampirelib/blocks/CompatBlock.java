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

package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import io.github.vampirestudios.vampirelib.api.itemGroupSorting.ModdedTargetedItemGroupFiller;

public class CompatBlock extends Block {
	private final ModdedTargetedItemGroupFiller FILLER;

	public CompatBlock(String modId, Block modBlock, Properties settings) {
		super(settings);
		FILLER = new ModdedTargetedItemGroupFiller(modId, modBlock.asItem());
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
		FILLER.fillItem(this.asItem(), group, stacks);
	}

}
