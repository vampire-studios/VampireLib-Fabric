package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;

public class FenceBaseBlock extends FenceBlock {

    public FenceBaseBlock(BlockState state) {
        super(FabricBlockSettings.of(state.getMaterial()).hardness(2.0F).resistance(5.0F).sounds(state.getSoundGroup()).build());
        setDefaultState(this.stateFactory.getDefaultState().with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE));
    }

}