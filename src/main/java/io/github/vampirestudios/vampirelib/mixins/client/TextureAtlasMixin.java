/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.mixins.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Stream;

import com.mojang.blaze3d.platform.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import org.quiltmc.loader.api.QuiltLoader;

import net.fabricmc.fabric.impl.client.texture.FabricSprite;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.api.callbacks.client.TextureStitchCallback;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {
	private static final int vl_EMISSIVE_ALPHA = 254 << 24;

	@Inject(
			method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite$Info;IIIII)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;",
			at = @At("HEAD"), cancellable = true)
	private void vl_loadSprites(ResourceManager container, TextureAtlasSprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y, CallbackInfoReturnable<TextureAtlasSprite> cir) {
		if (!QuiltLoader.isModLoaded("optifabric") && !QuiltLoader.isModLoaded("bclib")) {
			ResourceLocation location = info.name();
			ResourceLocation emissiveLocation = new ResourceLocation(location.getNamespace(),
					"textures/" + location.getPath() + "_e.png");
			if (container.getResource(emissiveLocation).isPresent()) {
				NativeImage sprite = null;
				NativeImage emission = null;
				try {
					ResourceLocation spriteLocation = new ResourceLocation(location.getNamespace(),
							"textures/" + location.getPath() + ".png");
					InputStream resource = container.open(spriteLocation);
					sprite = NativeImage.read(resource);

					resource = container.open(emissiveLocation);
					emission = NativeImage.read(resource);
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

	@Inject(method = "prepareToStitch",
			at = @At(value = "INVOKE",
					 target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 0,
					 shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void vl$preStitch(ResourceManager resourceManager, Stream<ResourceLocation> stream, ProfilerFiller profilerFiller, int i, CallbackInfoReturnable<TextureAtlas.Preparations> cir, Set<ResourceLocation> set) {
		TextureStitchCallback.PRE.invoker().stitch((TextureAtlas) (Object) this, set::add);
	}

	@Inject(method = "reload", at = @At("RETURN"))
	private void vl$postStitch(TextureAtlas.Preparations preparations, CallbackInfo ci) {
		TextureStitchCallback.POST.invoker().stitch((TextureAtlas) (Object) this);
	}

}
