package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class FenceGateBaseBlock extends FenceGateBlock {

    public FenceGateBaseBlock() {
        super(FabricBlockSettings.of(Material.WOOD).hardness(3.0F).sounds(BlockSoundGroup.WOOD).build());
    }

}
