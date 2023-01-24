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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public final class SpreadBehaviors {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Table<Block, BlockSpreadingType, SpreadingBehavior> SPREADERS = HashBasedTable.create();

	static {
		setupVanillaBehavior();
	}

	private SpreadBehaviors() {
	}

	/**
	 * This method allows you to add spreading behavior, which is used by {@link SpreadingBlock}.
	 *
	 * @param block    blocks
	 * @param type     type
	 * @param behavior behavior
	 */
	private static void addSpreaderBehavior(Block block, BlockSpreadingType type, SpreadingBehavior behavior) {
		if (SPREADERS.contains(block, type)) {
			LOGGER.info("Replacing spreading behavior for block '{}' and spreader type '{}'", block, type.getName());
		}

		SPREADERS.put(block, type, behavior);
	}

	public static boolean canSpread(BlockState state, BlockSpreadingType type) {
		return getSpreadingBehavior(state.getBlock(), type) != null;
	}

	public static BlockState getSpreadState(BlockState state, Level level, BlockPos pos, BlockSpreadingType type) {
		SpreadingBehavior behavior = getSpreadingBehavior(state.getBlock(), type);
		if (behavior == null) return state;
		return behavior.getStateForSpread(state, level, pos);
	}

	public static SpreadingBehavior getSpreadingBehavior(Block block, BlockSpreadingType type) {
		return SPREADERS.get(block, type);
	}

	private static void setupVanillaBehavior() {
		addSpreaderBehavior(Blocks.DIRT, BlockSpreadingType.GRASS, new SnowySpreaderBehavior(Blocks.GRASS_BLOCK));
		addSpreaderBehavior(Blocks.GRASS_BLOCK, BlockSpreadingType.REVERT, new SimpleSpreaderBehavior(Blocks.DIRT));

		addSpreaderBehavior(Blocks.DIRT, BlockSpreadingType.MYCELIUM, new SnowySpreaderBehavior(Blocks.MYCELIUM));
		addSpreaderBehavior(Blocks.MYCELIUM, BlockSpreadingType.REVERT, new SimpleSpreaderBehavior(Blocks.DIRT));

		addSpreaderBehavior(Blocks.NETHERRACK, BlockSpreadingType.CRIMSON,
				new SimpleSpreaderBehavior(Blocks.CRIMSON_NYLIUM));
		addSpreaderBehavior(Blocks.CRIMSON_NYLIUM, BlockSpreadingType.REVERT,
				new SimpleSpreaderBehavior(Blocks.NETHERRACK));

		addSpreaderBehavior(Blocks.NETHERRACK, BlockSpreadingType.WARPED,
				new SimpleSpreaderBehavior(Blocks.WARPED_NYLIUM));
		addSpreaderBehavior(Blocks.WARPED_NYLIUM, BlockSpreadingType.REVERT,
				new SimpleSpreaderBehavior(Blocks.NETHERRACK));
	}

	public static class SimpleSpreaderBehavior implements SpreadingBehavior {
		private final BlockState state;

		public SimpleSpreaderBehavior(BlockState state) {
			this.state = state;
		}

		public SimpleSpreaderBehavior(Block block) {
			this(block.defaultBlockState());
		}

		public BlockState getState() {
			return this.state;
		}

		@Override
		public BlockState getStateForSpread(BlockState stateIn, Level levelIn, BlockPos posIn) {
			return this.state;
		}
	}

	public static class SnowySpreaderBehavior extends SimpleSpreaderBehavior {
		public SnowySpreaderBehavior(BlockState state) {
			super(state);
		}

		public SnowySpreaderBehavior(Block block) {
			super(block);
		}

		@Override
		public BlockState getStateForSpread(BlockState stateIn, Level levelIn, BlockPos posIn) {
			return this.getState()
					.setValue(BlockStateProperties.SNOWY, levelIn.getBlockState(posIn.above()).is(Blocks.SNOW));
		}
	}
}
