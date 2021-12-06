package io.github.vampirestudios.vampirelib.api.datagen;

import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public abstract class CustomBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    protected CustomBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public CustomFabricTagBuilder<Block> tagCustom(Tag.Named<Block> tag) {
        return new CustomFabricTagBuilder(super.tag(tag));
    }
}
