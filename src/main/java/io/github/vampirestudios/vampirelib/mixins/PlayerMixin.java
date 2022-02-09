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
    @Inject(method = "isScoping", at = @At("RETURN"), cancellable = true)
    public void isScoping(CallbackInfoReturnable<Boolean> cir) {
        boolean currentScoping = cir.getReturnValue();
        if (currentScoping != PlayerEvents.oldScoping) {
            PlayerEvents.oldScoping = currentScoping;
            InteractionResult result = PlayerEvents.SCOPING.invoker().onPlayerScoping(currentScoping, (Player)(Object)this);
            if (result == InteractionResult.FAIL) PlayerEvents.oldScoping = !currentScoping;
            System.out.println(currentScoping);
        }
        cir.setReturnValue(PlayerEvents.oldScoping);
    }
}
