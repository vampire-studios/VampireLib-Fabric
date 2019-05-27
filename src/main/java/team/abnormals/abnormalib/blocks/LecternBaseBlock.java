package team.abnormals.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.Material;

public class LecternBaseBlock extends LecternBlock {

    public LecternBaseBlock() {
        super(FabricBlockSettings.of(Material.WOOD).strength(2.5f, 1.0f).build());
    }

}