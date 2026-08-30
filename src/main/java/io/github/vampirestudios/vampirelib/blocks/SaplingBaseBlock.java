package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SaplingBaseBlock extends SaplingBlock {
	public SaplingBaseBlock(TreeGrower treeGrower, BlockBehaviour.Properties properties) {
		super(treeGrower, properties);
	}

}
