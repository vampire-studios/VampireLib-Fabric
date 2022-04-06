package io.github.vampirestudios.vampirelib.api;

import java.util.function.Function;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import io.github.vampirestudios.vampirelib.client.model.BlockModelConfiguration;

@Environment(EnvType.CLIENT)
public interface BlockModelExtensions {
  default BlockModelConfiguration getGeometry() {
	  throw new RuntimeException("this should be overridden via mixin. what?");
  }

	default ItemOverrides getOverrides(ModelBakery pModelBakery, BlockModel pModel, Function<Material, TextureAtlasSprite> textureGetter) {
		throw new RuntimeException("this should be overridden via mixin. what?");
	}
}