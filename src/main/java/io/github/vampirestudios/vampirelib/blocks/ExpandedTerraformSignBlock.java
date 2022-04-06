package io.github.vampirestudios.vampirelib.blocks;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import io.github.vampirestudios.vampirelib.api.VanillaTargetedItemGroupFiller;

public class ExpandedTerraformSignBlock extends TerraformSignBlock {
    private final VanillaTargetedItemGroupFiller FILLER;

    public ExpandedTerraformSignBlock(Block vanillaBlock, ResourceLocation texture, Properties settings) {
        super(texture, settings);
        FILLER = new VanillaTargetedItemGroupFiller(vanillaBlock.asItem());
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
        FILLER.fillItem(this.asItem(), group, list);
    }

}