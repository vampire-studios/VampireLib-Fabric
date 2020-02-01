package io.github.vampirestudios.vampirelib.mixins.entity;

import io.github.vampirestudios.vampirelib.api.Climbable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public class EntityMixin {
	private static BlockState vampireLib_blockState;
	private static BlockPos vampireLib_blockPos;

	@Inject(method = "move", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void onGetMoveBlock(MovementType movementType, Vec3d movement, CallbackInfo callbackInfo, Vec3d someVec3d, BlockPos blockPos, BlockState blockState) {
		vampireLib_blockPos = blockPos;
		vampireLib_blockState = blockState;
	}

	@ModifyVariable(method = "move", ordinal = 2, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;horizontalSpeed:F", ordinal = 0))
	public double setYMovement(double oldYMovement) {
		Block block = vampireLib_blockState.getBlock();
		if (block instanceof Climbable && ((Climbable) block).getClimbBehavior((Entity)(Object) this, vampireLib_blockState, vampireLib_blockPos).normalClimbing) {
			return 0D;
		}
		return oldYMovement;
	}
}
