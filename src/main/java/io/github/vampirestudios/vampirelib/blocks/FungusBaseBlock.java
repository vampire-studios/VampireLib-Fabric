package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FungusBaseBlock extends NetherFungusBlock {
	public FungusBaseBlock(ResourceKey<Feature> supplier, Block requiredBlock, TagKey<Block> supportedBlocks, BlockBehaviour.Properties properties) {
		super(supplier, requiredBlock, supportedBlocks, properties);
	}

}
