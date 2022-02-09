package io.github.vampirestudios.vampirelib.blocks;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BeehiveBlock;

import io.github.vampirestudios.vampirelib.api.VanillaTargetedItemGroupFiller;

public class BaseBeehiveBlock extends BeehiveBlock {
    private final VanillaTargetedItemGroupFiller FILLER;

    public BaseBeehiveBlock(Properties properties) {
        super(properties);
        FILLER = new VanillaTargetedItemGroupFiller(Items.BEEHIVE);
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab group, @NotNull NonNullList<ItemStack> list) {
        FILLER.fillItem(this.asItem(), group, list);
    }

}