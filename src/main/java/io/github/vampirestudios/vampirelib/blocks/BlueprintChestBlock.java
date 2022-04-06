package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.api.IChestBlock;
import io.github.vampirestudios.vampirelib.blocks.entity.VChestBlockEntity;
import io.github.vampirestudios.vampirelib.init.VBlockEntityTypes;

/**
 * A {@link ChestBlock} extension used for Blueprint's chests.
 */
public class BlueprintChestBlock extends ChestBlock implements IChestBlock {
	public final String type;

	public BlueprintChestBlock(String type, Properties props) {
		super(props, () -> VBlockEntityTypes.CHEST);
		this.type = type;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new VChestBlockEntity(pos, state);
	}

	@Override
	public String getChestType() {
		return type;
	}
}