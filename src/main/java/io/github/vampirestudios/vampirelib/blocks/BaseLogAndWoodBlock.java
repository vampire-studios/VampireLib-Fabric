package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

import io.github.vampirestudios.vampirelib.api.VanillaTargetedItemGroupFiller;

public class BaseLogAndWoodBlock extends RotatedPillarBlock {
    private final VanillaTargetedItemGroupFiller FILLER;

    public BaseLogAndWoodBlock(Properties properties, Block parent) {
        super(properties);
        FILLER = new VanillaTargetedItemGroupFiller(parent.asItem());
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
        FILLER.fillItem(this.asItem(), group, list);
    }

}