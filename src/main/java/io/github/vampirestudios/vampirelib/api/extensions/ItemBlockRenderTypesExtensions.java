package io.github.vampirestudios.vampirelib.api.extensions;

import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.client.ChunkRenderTypeSet;

public interface ItemBlockRenderTypesExtensions {
	ChunkRenderTypeSet getRenderLayers(BlockState state);
}
