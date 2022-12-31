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

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;

public class FenceGateBaseBlock extends FenceGateBlock {
	public FenceGateBaseBlock(Block vanillaBlock, Properties properties) {
		super(properties, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
	}

}
