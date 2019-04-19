package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;

public class WallBaseBlock extends WallBlock {

    public WallBaseBlock(BlockState state) {
        super(FabricBlockSettings.of(state.getMaterial()).build());
    }

}
