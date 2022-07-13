package io.github.vampirestudios.vampirelib.api;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface CustomDestroyEffectsBlock {
	/**
	 * Custom effects when your block is broken.
	 * @return true to cancel vanilla effects
	 */
	@Environment(EnvType.CLIENT)
	boolean applyCustomDestroyEffects(BlockState state, ClientLevel Level, BlockPos pos, ParticleEngine engine);
}