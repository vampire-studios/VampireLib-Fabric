package io.github.vampirestudios.vampirelib.mixins.client;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import net.fabricmc.fabric.impl.client.texture.FabricSprite;
import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.VampireLib;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {
	private static final int vl_EMISSIVE_ALPHA = 254 << 24;

	@Inject(method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite$Info;IIIII)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", at = @At("HEAD"), cancellable = true)
	private void vl_loadSprites(ResourceManager container, TextureAtlasSprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y, CallbackInfoReturnable<TextureAtlasSprite> cir) {
		if (!FabricLoader.getInstance().isModLoaded("optifabric") && !FabricLoader.getInstance().isModLoaded("bclib")) {
			ResourceLocation location = info.name();
			ResourceLocation emissiveLocation = new ResourceLocation(location.getNamespace(), "textures/" + location.getPath() + "_e.png");
			if (container.hasResource(emissiveLocation)) {
				NativeImage sprite = null;
				NativeImage emission = null;
				try {
					ResourceLocation spriteLocation = new ResourceLocation(location.getNamespace(), "textures/" + location.getPath() + ".png");
					Resource resource = container.getResource(spriteLocation);
					sprite = NativeImage.read(resource.getInputStream());
					resource.close();

					resource = container.getResource(emissiveLocation);
					emission = NativeImage.read(resource.getInputStream());
					resource.close();
				} catch (IOException e) {
					VampireLib.INSTANCE.getLogger().info(e.getMessage());
				}
				if (sprite != null && emission != null) {
					int width = Math.min(sprite.getWidth(), emission.getWidth());
					int height = Math.min(sprite.getHeight(), emission.getHeight());
					for (int posX = 0; posX < width; posX++) {
						for (int posY = 0; posY < height; posY++) {
							int argb = emission.getPixelRGBA(posX, posY);
							int alpha = (argb >> 24) & 255;
							if (alpha > 127) {
								int r = (argb >> 16) & 255;
								int g = (argb >> 8) & 255;
								int b = argb & 255;
								if (r > 0 || g > 0 || b > 0) {
									argb = (argb & 0x00FFFFFF) | vl_EMISSIVE_ALPHA;
									sprite.setPixelRGBA(posX, posY, argb);
								}
							}
						}
					}
					TextureAtlas self = (TextureAtlas) (Object) this;
					FabricSprite result = new FabricSprite(self, info, maxLevel, atlasWidth, atlasHeight, x, y, sprite);
					cir.setReturnValue(result);
				}
			}
		}
	}
}
