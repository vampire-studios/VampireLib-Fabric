package team.hollow.abnormalib.blocks;

import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;

public class PressurePlateBaseBlock extends PressurePlateBlock {

    public PressurePlateBaseBlock(Material material, ActivationRule sensitivity) {
        super(sensitivity, Settings.of(material));
    }

}
