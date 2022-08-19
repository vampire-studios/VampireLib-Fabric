/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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