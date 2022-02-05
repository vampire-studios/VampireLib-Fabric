package io.github.vampirestudios.vampirelib.mixins.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.api.blockspreading.BlockSpreadingType;
import io.github.vampirestudios.vampirelib.api.blockspreading.SpreadingBlock;

@Mixin(MyceliumBlock.class)
public class MyceliumBlockMixin implements SpreadingBlock {
    @Override
    public BlockSpreadingType getBlockSpreadingType(BlockState state) {
        return BlockSpreadingType.MYCELIUM;
    }
}