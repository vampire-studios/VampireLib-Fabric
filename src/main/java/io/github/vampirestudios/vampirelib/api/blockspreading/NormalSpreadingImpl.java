/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.blockspreading;

import net.minecraft.world.level.block.Block;

public class NormalSpreadingImpl {
	private Block block;
	private BlockSpreadingType type;

	/**
	 * @param block The block which should spread to other blocks
	 * @param type  the type of spreading the block should use, check {@link BlockSpreadingType} to look at which types exist
	 */
	public NormalSpreadingImpl(Block block, BlockSpreadingType type) {
		this.block = block;
		this.type = type;
	}

	/**
	 * Gets the block which should spread to other blocks.
	 *
	 * @return block
	 */
	public Block getBlock() {
		return this.block;
	}

	/**
	 * Sets the block which should spread to other blocks.
	 *
	 * @param block block
	 */
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * Gets the defined spreading type the block should use.
	 *
	 * @return type
	 */
	public BlockSpreadingType getType() {
		return this.type;
	}

	/**
	 * Sets the type of spreading the block should use, check {@link BlockSpreadingType} to look at which types exist.
	 *
	 * @param type type
	 */
	public void setType(BlockSpreadingType type) {
		this.type = type;
	}
}
