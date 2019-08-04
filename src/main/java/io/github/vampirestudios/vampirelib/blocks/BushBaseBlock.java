package io.github.vampirestudios.vampirelib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;

public class BushBaseBlock extends PlantBlock {

    public BushBaseBlock() {
        super(FabricBlockSettings.of(Material.PLANT).build());
    }

}
