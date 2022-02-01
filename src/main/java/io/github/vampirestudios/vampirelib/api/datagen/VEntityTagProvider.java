package io.github.vampirestudios.vampirelib.api.datagen;

import net.minecraft.core.Registry;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public abstract class VEntityTagProvider extends FabricTagProvider<EntityType<?>> {
    public VEntityTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.ENTITY_TYPE, "entity_types", "Entity Type Tags");
    }

    public CustomFabricTagBuilder<EntityType<?>> tagCustom(Tag.Named<EntityType<?>> tag) {
        return new CustomFabricTagBuilder<>(super.tag(tag));
    }
}
