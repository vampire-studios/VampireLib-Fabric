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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import io.github.vampirestudios.vampirelib.api.itemGroupSorting.VanillaTargetedItemGroupFiller;

public class CampfireBaseBlock extends CampfireBlock {
	private final VanillaTargetedItemGroupFiller FILLER;

	public CampfireBaseBlock(boolean soul) {
		super(!soul, soul ? 1 : 2, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F)
				.sound(SoundType.WOOD)
				.lightLevel(value -> soul ? 10 : 15).randomTicks());
		FILLER = new VanillaTargetedItemGroupFiller(soul ? Blocks.SOUL_CAMPFIRE.asItem() : Blocks.CAMPFIRE.asItem());
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
		FILLER.fillItem(this.asItem(), group, list);
	}

}
