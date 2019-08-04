package io.github.vampirestudios.vampirelib.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import io.github.vampirestudios.vampirelib.api.Climbable;

@Mixin(Block.class)
public abstract class MixinBlock implements Climbable {

    @Override
    public Climbable.ClimbBehavior canClimb(LivingEntity entity, BlockState state, BlockPos pos) {
        return ClimbBehavior.Vanilla;
    }

}