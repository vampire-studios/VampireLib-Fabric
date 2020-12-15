package io.github.vampirestudios.vampirelib.mixins.client;

import io.github.vampirestudios.vampirelib.boat.CustomBoatEntity;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntityRenderer.class)
public class BoatEntityRendererMixin {
    @Inject(method = "getTexture", at = @At("HEAD"))
    private void injectCustomTexture(BoatEntity boat, CallbackInfoReturnable<Identifier> cir) {
        if (boat instanceof CustomBoatEntity) {
            System.out.println(((CustomBoatEntity) boat).getBoatSkin());
            cir.setReturnValue(((CustomBoatEntity) boat).getBoatSkin());
        }
    }
}