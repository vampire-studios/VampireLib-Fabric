package team.abnormals.abnormalib.mixins.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import team.abnormals.abnormalib.api.Climbable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(method = "isClimbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void isClimbing(CallbackInfoReturnable<Boolean> cir, final BlockState state) {

        final Climbable climbable = (Climbable) state.getBlock();
        final LivingEntity thisLivingEntity = (LivingEntity) (Object) this;

        Climbable.ClimbBehavior behavior = climbable.canClimb(thisLivingEntity, state, thisLivingEntity.getBlockPos());
        if (behavior != Climbable.ClimbBehavior.Vanilla) {

            if (behavior == Climbable.ClimbBehavior.True) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
            cir.cancel();
        }
    }
}