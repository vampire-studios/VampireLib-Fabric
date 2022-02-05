package io.github.vampirestudios.vampirelib.api.blockspreading;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface SpreadingBehavior {
	/**
	 * Used for {@link SpreadingBlock}, which allows extending spread behavior.
	 *
	 * @param state previous state at this position
	 * @param level the world the state is in
	 * @param pos the current position of the block
	 * @return new state to place at the location
	 */
	BlockState getStateForSpread(BlockState state, Level level, BlockPos pos);
}