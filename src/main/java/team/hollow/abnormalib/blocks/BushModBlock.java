package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;

public class BushModBlock extends PlantBlock {

    public BushModBlock(Material material) {
        super(FabricBlockSettings.of(material).build());
    }

}