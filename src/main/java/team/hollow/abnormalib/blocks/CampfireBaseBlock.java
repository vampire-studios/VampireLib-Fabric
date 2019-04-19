package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class CampfireBaseBlock extends CampfireBlock {

    public CampfireBaseBlock() {
        super(FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE).hardness(2.0F).sounds(BlockSoundGroup.WOOD).lightLevel(15).ticksRandomly().build());
    }

}
