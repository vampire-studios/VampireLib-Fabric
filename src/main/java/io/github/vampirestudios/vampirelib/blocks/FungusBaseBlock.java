package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import io.github.vampirestudios.vampirelib.api.itemGroupSorting.VanillaTargetedItemGroupFiller;

public class FungusBaseBlock extends FungusBlock {
    private final VanillaTargetedItemGroupFiller FILLER;

    public FungusBaseBlock(ConfiguredFeature<HugeFungusConfiguration, ?> supplier) {
        super(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_CYAN).instabreak().noCollission().sound(SoundType.FUNGUS), () -> Holder.direct(supplier));
        FILLER = new VanillaTargetedItemGroupFiller(Blocks.WARPED_FUNGUS.asItem());
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
        FILLER.fillItem(this.asItem(), group, list);
    }

}