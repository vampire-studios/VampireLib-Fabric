package io.github.vampirestudios.vampirelib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class LeavesBaseBlock extends LeavesBlock {

    public LeavesBaseBlock() {
        super(FabricBlockSettings.of(Material.LEAVES).hardness(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).build());
    }

}
