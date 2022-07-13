package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import io.github.vampirestudios.vampirelib.api.CustomDestroyEffectsBlock;

@Environment(EnvType.CLIENT)
@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
	@Shadow
	protected ClientLevel level;

	@Inject(method = "destroy", at = @At("HEAD"), cancellable = true)
	private void port_lib$customDestroyEffects(BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
		if (blockState.getBlock() instanceof CustomDestroyEffectsBlock custom) {
			if (custom.applyCustomDestroyEffects(blockState, level, blockPos, (ParticleEngine) (Object) this)) {
				ci.cancel();
			}
		}
	}
}