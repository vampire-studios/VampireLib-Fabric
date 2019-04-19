package team.hollow.abnormalib.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class StairsBaseBlock extends StairsBlock {

    public StairsBaseBlock(BlockState blockState_1) {
        super(blockState_1, Settings.copy(blockState_1.getBlock()));
    }

}