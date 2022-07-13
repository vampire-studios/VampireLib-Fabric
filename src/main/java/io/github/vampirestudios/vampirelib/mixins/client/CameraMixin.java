package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.api.extensions.CameraExtensions;

@Mixin(Camera.class)
public abstract class CameraMixin implements CameraExtensions {
	@Shadow
	private float yRot;

	@Shadow
	private float xRot;

	@Shadow
	private boolean initialized;

	@Shadow
	private BlockGetter level;

	@Shadow
	@Final
	private BlockPos.MutableBlockPos blockPosition;

	@Unique
	@Override
	public void setAnglesInternal(float yaw, float pitch) {
		this.yRot = yaw;
		this.xRot = pitch;
	}

	@Unique
	@Override
	public BlockState getBlockAtCamera() {
		if (!this.initialized)
			return Blocks.AIR.defaultBlockState();
		else
			return this.level.getBlockState(this.blockPosition);
	}
}