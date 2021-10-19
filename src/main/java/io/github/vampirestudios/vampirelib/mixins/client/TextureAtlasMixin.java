package io.github.vampirestudios.vampirelib.mixins.client;

import java.io.IOException;
import java.util.Scanner;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.impl.client.texture.FabricSprite;
import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.VampireLib;

@Mixin(SpriteAtlasTexture.class)
public class TextureAtlasMixin {

	@Inject(method = "loadSprite", at = @At("HEAD"), cancellable = true)
	private void vl_loadSprites(ResourceManager resourceManager, Sprite.Info spriteInfo, int atlasWidth, int atlasHeight, int maxLevel, int posX, int posY, CallbackInfoReturnable<Sprite> info) {
		for (String blacklistedEmissiveMod : VampireLib.BLACKLISTED_EMISSIVE_MODS) {
			if (FabricLoader.getInstance().isModLoaded(blacklistedEmissiveMod)) {
				return;
			}
		}
		String emissiveSuffix = "_e";
		if (resourceManager.containsResource(VampireLib.EMISSIVE_FILE)) {
			try(Scanner scanner = new Scanner(resourceManager.getResource(VampireLib.EMISSIVE_FILE).getInputStream())) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.startsWith("suffix.emissive=")) {
						emissiveSuffix = line.substring(16);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Identifier location = spriteInfo.getId();
		Identifier emissiveLocation = new Identifier(location.getNamespace(), "textures/" + location.getPath() + emissiveSuffix + ".png");
		if (resourceManager.containsResource(emissiveLocation)) {
			NativeImage sprite = null;
			NativeImage emission = null;
			try {
				Identifier spriteLocation = new Identifier(location.getNamespace(), "textures/" + location.getPath() + ".png");
				Resource resource = resourceManager.getResource(spriteLocation);
				sprite = NativeImage.read(resource.getInputStream());
				resource.close();

				resource = resourceManager.getResource(emissiveLocation);
				emission = NativeImage.read(resource.getInputStream());
				resource.close();
			}
			catch (IOException e) {
				VampireLib.LOGGER.info(e.getMessage());
			}
			if (sprite != null && emission != null) {
				int width = Math.min(sprite.getWidth(), emission.getWidth());
				int height = Math.min(sprite.getHeight(), emission.getHeight());
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int argb = emission.getColor(x, y);
						int alpha = (argb >> 24) & 255;
						if (alpha > 127) {
							int r = (argb >> 16) & 255;
							int g = (argb >> 8) & 255;
							int b = argb & 255;
							if (r > 0 || g > 0 || b > 0) {
								argb = (argb & 0x00FFFFFF) | VampireLib.EMISSIVE_ALPHA;
								sprite.setColor(x, y, argb);
							}
						}
					}
				}
				SpriteAtlasTexture self = (SpriteAtlasTexture) (Object) this;
				FabricSprite result = new FabricSprite(self, spriteInfo, maxLevel, atlasWidth, atlasHeight, posX, posY, sprite);
				info.setReturnValue(result);
			}
		}
	}
}
