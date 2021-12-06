package io.github.vampirestudios.vampirelib.api.datagen;

import org.jetbrains.annotations.Nullable;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public abstract class CustomItemTagProvider extends FabricTagProvider.ItemTagProvider {
    protected CustomItemTagProvider(FabricDataGenerator dataGenerator, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(dataGenerator, blockTagProvider);
    }

    public CustomFabricTagBuilder<Item> tagCustom(Tag.Named<Item> tag) {
        return new CustomFabricTagBuilder(super.tag(tag));
    }
}
