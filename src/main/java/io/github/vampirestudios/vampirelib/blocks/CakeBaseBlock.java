package io.github.vampirestudios.vampirelib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class CakeBaseBlock extends CakeBlock {
    private final int slices;

    public CakeBaseBlock() {
        this(7);
    }

    public CakeBaseBlock(int slices) {
        super(FabricBlockSettings.of(Material.CAKE).hardness(0.5F).sounds(BlockSoundGroup.WOOL).build());
        this.slices = slices;
    }

    public int getSlices() {
        return slices;
    }
}
