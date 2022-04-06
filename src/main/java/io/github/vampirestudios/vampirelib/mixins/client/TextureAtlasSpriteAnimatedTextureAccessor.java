package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

@Mixin(TextureAtlasSprite.AnimatedTexture.class)
public interface TextureAtlasSpriteAnimatedTextureAccessor {
	@Invoker("getFrameX")
	int vl$getFrameX(int frameIndex);

	@Invoker("getFrameY")
	int vl$getFrameY(int frameIndex);
}