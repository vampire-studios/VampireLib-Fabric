package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DoorBaseBlock extends DoorBlock {
    public DoorBaseBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public ItemStack getPickStack(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1) {
        return new ItemStack(this);
    }

}
