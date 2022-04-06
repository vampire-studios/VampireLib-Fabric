package io.github.vampirestudios.vampirelib.init;

import net.minecraft.world.level.block.entity.BlockEntityType;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.blocks.BlueprintChestBlock;
import io.github.vampirestudios.vampirelib.blocks.BlueprintTrappedChestBlock;
import io.github.vampirestudios.vampirelib.blocks.entity.VChestBlockEntity;
import io.github.vampirestudios.vampirelib.blocks.entity.VTrappedChestBlockEntity;

public class VBlockEntityTypes {
    public static final BlockEntityType<VChestBlockEntity> CHEST = VampireLib.REGISTRY_HELPER.registerBlockEntity(VChestBlockEntity::new, BlueprintChestBlock.class, "chest");
    public static final BlockEntityType<VTrappedChestBlockEntity> TRAPPED_CHEST = VampireLib.REGISTRY_HELPER.registerBlockEntity(VTrappedChestBlockEntity::new, BlueprintTrappedChestBlock.class, "trapped_chest");

    public static void init() {}
}
