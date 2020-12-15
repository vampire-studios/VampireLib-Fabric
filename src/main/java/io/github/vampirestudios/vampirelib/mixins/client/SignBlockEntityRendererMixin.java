package io.github.vampirestudios.vampirelib.mixins.client;

import io.github.vampirestudios.vampirelib.api.CustomSign;
import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
    @Inject(method = "getModelTexture", at = @At("HEAD"), cancellable = true)
    private static void injectCustomTexture(Block block, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (block instanceof CustomSign) cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((CustomSign) block).getTexture()));
    }
}