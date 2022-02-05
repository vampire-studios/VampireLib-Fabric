package io.github.vampirestudios.vampirelib.mixins.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.api.blockspreading.BlockSpreadingType;
import io.github.vampirestudios.vampirelib.api.blockspreading.SpreadingBlock;

@Mixin(GrassBlock.class)
public class GrassBlockMixin implements SpreadingBlock {
    @Override
    public BlockSpreadingType getBlockSpreadingType(BlockState state) {
        return BlockSpreadingType.GRASS;
    }
}