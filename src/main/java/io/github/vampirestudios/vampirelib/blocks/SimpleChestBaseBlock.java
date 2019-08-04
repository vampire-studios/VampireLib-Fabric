package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.Identifier;

public class SimpleChestBaseBlock extends ChestBlock {
    public final Identifier type;

    public SimpleChestBaseBlock(Identifier type) {
        super(Settings.copy(Blocks.CHEST));
        this.type = type;
    }
}
