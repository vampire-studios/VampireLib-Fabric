package team.abnormals.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;

public class FlowerPotBaseBlock extends FlowerPotBlock {

    public FlowerPotBaseBlock(Block block_1) {
        super(block_1, FabricBlockSettings.of(Material.PART).breakInstantly().build());
    }

}
