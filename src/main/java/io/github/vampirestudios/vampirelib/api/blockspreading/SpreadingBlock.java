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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public interface SpreadingBlock {
	BlockSpreadingType getBlockSpreadingType(BlockState state);

	default void spread(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, int tries, int range) {
		if (!level.hasChunksAt(pos.offset(-(range + 1), -(range + 1), -(range + 1)),
				pos.offset(range + 1, range + 1, range + 1))) {
			return;
		}

		range = (range * 2) + 1;
		for (int i = 0; i < tries; ++i) {
			BlockPos blockpos = pos.offset(random.nextInt(range) - 1, random.nextInt(5) - 3, random.nextInt(range) - 1);
			BlockState targetState = level.getBlockState(blockpos);
			if (SpreadBehaviors.getSpreadingBehavior(targetState.getBlock(), this.getBlockSpreadingType(state)) != null) {
				level.setBlockAndUpdate(blockpos, SpreadBehaviors.getSpreadState(targetState, level, pos,
						this.getBlockSpreadingType(state)));
			}
		}
	}
}
