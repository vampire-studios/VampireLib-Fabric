package team.abnormals.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class BaseModBlock extends Block {

    public BaseModBlock(FabricBlockSettings settings) {
        super(settings.build());
    }

    public BaseModBlock(Material material) {
        super(FabricBlockSettings.of(material).build());
    }

    public BaseModBlock(Settings settings) {
        super(settings);
    }

}