package io.github.vampirestudios.vampirelib.mixins.entity;

import io.github.vampirestudios.vampirelib.api.Climbable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(
			method = "isClimbing",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"),
			locals = LocalCapture.CAPTURE_FAILSOFT,
			cancellable = true
	)
	public void isClimbing(CallbackInfoReturnable<Boolean> callbackInfoReturnable, BlockState blockState, Block block) {
		if (block instanceof Climbable && ((Climbable) block).getClimbBehavior(this, blockState, new BlockPos(this)).normalClimbing) {
			callbackInfoReturnable.setReturnValue(true);
		}
	}

	@Inject(
			method = "canEnterTrapdoor",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"),
			locals = LocalCapture.CAPTURE_FAILSOFT,
			cancellable = true
	)
	private void canEnterTrapdoor(BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<Boolean> callbackInfoReturnable, BlockState downBlockState) {
		Block downBlock = downBlockState.getBlock();
		if (downBlock instanceof Climbable
				&& ((Climbable) downBlock).getClimbBehavior(this, downBlockState, blockPos.down()) == Climbable.ClimbBehavior.Ladder
				&& downBlockState.get(LadderBlock.FACING) == blockState.get(TrapdoorBlock.FACING)
		)
			callbackInfoReturnable.setReturnValue(true);
	}
}
