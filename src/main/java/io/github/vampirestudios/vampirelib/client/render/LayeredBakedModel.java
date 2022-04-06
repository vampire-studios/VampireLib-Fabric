package io.github.vampirestudios.vampirelib.client.render;

import java.util.Collections;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public interface LayeredBakedModel {
	/**
	 * If {@see isLayered()} returns true, this is called to get the list of layers to draw.
	 */
	default List<Pair<BakedModel, RenderType>> getLayerModels(ItemStack itemStack, boolean fabulous) {
		return Collections.singletonList(Pair.of((BakedModel) this, ItemBlockRenderTypes.getRenderType(itemStack, fabulous)));
	}
}