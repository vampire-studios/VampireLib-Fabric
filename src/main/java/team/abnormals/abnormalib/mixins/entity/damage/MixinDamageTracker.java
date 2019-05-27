package team.abnormals.abnormalib.mixins.entity.damage;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import team.abnormals.abnormalib.api.Climbable;

@Mixin(DamageTracker.class)
public abstract class MixinDamageTracker {

    @Shadow
    private String fallDeathSuffix;

    @Shadow
    @Final
    private LivingEntity entity;

    @Inject(method = "setFallDeathSuffix", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void setFallDeathSuffix(CallbackInfo ci) {

        final Block block = entity.world.getBlockState(new BlockPos(entity.x, entity.getBoundingBox().minY, entity.z)).getBlock();
        fallDeathSuffix = ((Climbable) block).getFallDeathSuffix();

        if (!fallDeathSuffix.equals("generic")) {
            ci.cancel();
        }
    }
}