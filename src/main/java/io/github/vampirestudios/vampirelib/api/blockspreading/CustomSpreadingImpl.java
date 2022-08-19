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

package io.github.vampirestudios.vampirelib.api.blockspreading;

import net.minecraft.world.level.block.Block;

public class CustomSpreadingImpl {
	private Block block;
	private BlockSpreadingType type;
	private SpreadingBehavior behavior;

	/**
	 * @param block    The block which should spread to other blocks
	 * @param type     the type of spreading the block should use, check {@link BlockSpreadingType} to look at which types exist
	 * @param behavior The custom spreading behaviour, defines to what block to change the block this spreads onto into
	 */
	public CustomSpreadingImpl(Block block, BlockSpreadingType type, SpreadingBehavior behavior) {
		this.block = block;
		this.type = type;
		this.behavior = behavior;
	}

	/**
	 * Gets the block which should spread to other blocks.
	 *
	 * @return the block for this.
	 */
	public Block getBlock() {
		return this.block;
	}

	/**
	 * Sets the block which should spread to other blocks.
	 *
	 * @param block block thingy
	 */
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * Gets the defined spreading type the block should use.
	 *
	 * @return the block for this.
	 */
	public BlockSpreadingType getType() {
		return this.type;
	}

	/**
	 * Sets the type of spreading the block should use, check {@link BlockSpreadingType} to look at which types exist.
	 *
	 * @param type sets the spreading type
	 */
	public void setType(BlockSpreadingType type) {
		this.type = type;
	}

	/**
	 * Gets the defined spreading behaviour.
	 *
	 * @return the block for this.
	 */
	public SpreadingBehavior getBehavior() {
		return this.behavior;
	}

	/**
	 * Sets the spreading behaviour for this block, defines what to change the block this spreads onto into.
	 *
	 * @param behavior sets the behaviour
	 */
	public void setBehavior(SpreadingBehavior behavior) {
		this.behavior = behavior;
	}
}
