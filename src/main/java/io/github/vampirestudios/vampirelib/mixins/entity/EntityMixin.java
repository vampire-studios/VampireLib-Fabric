package io.github.vampirestudios.vampirelib.mixins.entity;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import io.github.vampirestudios.vampirelib.entity.StepHeightEntity;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    public float maxUpStep;

    @Inject(method = "collide", at = @At(value = "JUMP", opcode = Opcodes.IFGE))
    public void port_lib$modifyStepHeight(Vec3 movement, CallbackInfoReturnable<Vec3> cir) {
        if (this instanceof StepHeightEntity stepHeightEntity) {
            this.maxUpStep = stepHeightEntity.getStepHeight();
        }
    }

}
