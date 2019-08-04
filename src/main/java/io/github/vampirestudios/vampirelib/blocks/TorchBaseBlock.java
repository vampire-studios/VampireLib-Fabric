package io.github.vampirestudios.vampirelib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.TorchBlock;

public class TorchBaseBlock extends TorchBlock {

    public TorchBaseBlock() {
        super(FabricBlockSettings.of(Material.WOOD).build());
    }

}
