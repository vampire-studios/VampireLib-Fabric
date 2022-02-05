package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

import io.github.vampirestudios.vampirelib.callbacks.PlayerEvents;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "isScoping", at = @At("HEAD"), cancellable = true)
    public void isScoping(CallbackInfoReturnable<Boolean> cir) {
        InteractionResult result = PlayerEvents.SCOPING.invoker().onPlayerScoping((Player)(Object)this);
        if (result == InteractionResult.FAIL) cir.cancel();
    }
}
