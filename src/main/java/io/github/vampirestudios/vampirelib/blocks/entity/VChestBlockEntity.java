package io.github.vampirestudios.vampirelib.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.init.VBlockEntityTypes;

/**
 * A {@link ChestBlockEntity} extension used for Blueprint's chests.
 */
public class VChestBlockEntity extends ChestBlockEntity {

	protected VChestBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	public VChestBlockEntity(BlockPos pos, BlockState state) {
		super(VBlockEntityTypes.CHEST, pos, state);
	}

}