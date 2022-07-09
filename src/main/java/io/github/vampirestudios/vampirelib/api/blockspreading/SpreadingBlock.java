package io.github.vampirestudios.vampirelib.api.blockspreading;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public interface SpreadingBlock {
	BlockSpreadingType getBlockSpreadingType(BlockState state);

	default void spread(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, int tries, int range) {
		if (!level.hasChunksAt(pos.offset(-(range + 1), -(range + 1), -(range + 1)), pos.offset(range + 1, range + 1, range + 1)))
			return;
		range = (range * 2) + 1;
		for (int i = 0; i < tries; ++i) {
			BlockPos blockpos = pos.offset(random.nextInt(range) - 1, random.nextInt(5) - 3, random.nextInt(range) - 1);
			BlockState targetState = level.getBlockState(blockpos);
			if (SpreadBehaviors.canSpread(targetState, getBlockSpreadingType(state))) {
				level.setBlockAndUpdate(blockpos, SpreadBehaviors.getSpreadState(targetState, level, pos, getBlockSpreadingType(state)));
			}
		}
	}
}