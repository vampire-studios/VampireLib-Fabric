package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;

public class SlabBaseBlock extends SlabBlock {

    public SlabBaseBlock() {
        super(FabricBlockSettings.of(Material.WOOD).build());
    }

}