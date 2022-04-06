package io.github.vampirestudios.vampirelib.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.init.VBlockEntityTypes;

/**
 * A {@link VChestBlockEntity} extension used for Blueprint's trapped chests.
 */
public class VTrappedChestBlockEntity extends VChestBlockEntity {

	public VTrappedChestBlockEntity(BlockPos pos, BlockState state) {
		super(VBlockEntityTypes.TRAPPED_CHEST, pos, state);
	}

	@Override
	protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int int1, int int2) {
		super.signalOpenCount(level, pos, state, int1, int2);
		level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
	}

}