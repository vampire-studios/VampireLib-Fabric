package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CustomLadderBlock extends LadderBlock {
	public CustomLadderBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

}
