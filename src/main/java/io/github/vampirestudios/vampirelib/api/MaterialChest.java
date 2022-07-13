package io.github.vampirestudios.vampirelib.api;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

public interface MaterialChest {
    Material getMaterial(BlockEntity blockEntity, ChestType chestType);
}