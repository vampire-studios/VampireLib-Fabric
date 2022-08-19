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

package io.github.vampirestudios.vampirelib.mixins;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import io.github.vampirestudios.vampirelib.api.registry.LandPathNodeTypesRegistry;

@Mixin(WalkNodeEvaluator.class)
public class LandPathNodeMakerMixin {
	/**
	 * Overrides the node type for the specified position in a path.
	 */
	@Inject(method = "getBlockPathTypeRaw", at = @At(value = "INVOKE",
													 target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
													 shift = At.Shift.BY, by = 2),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
	private static void getCommonNodeType(@NotNull BlockGetter world, BlockPos pos, @NotNull CallbackInfoReturnable<BlockPathTypes> cir, BlockState state) {
		BlockPathTypes nodeType = LandPathNodeTypesRegistry.getPathNodeType(state, world, pos, false);

		if (nodeType != null) {
			cir.setReturnValue(nodeType);
		}
	}

	/**
	 * Overrides the node type for the specified position, if the position is found as neighbor block in a path.
	 */
	@Inject(method = "checkNeighbourBlocks", at = @At(value = "INVOKE",
													  target = "Lnet/minecraft/world/level/BlockGetter;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
													  shift = At.Shift.BY, by = 2),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
	private static void getNodeTypeFromNeighbors(BlockGetter world, BlockPos.MutableBlockPos pos, BlockPathTypes nodeType, CallbackInfoReturnable<BlockPathTypes> cir, int i, int j, int k, int l, int m, int n, BlockState state) {
		BlockPathTypes neighborNodeType = LandPathNodeTypesRegistry.getPathNodeType(state, world, pos, true);

		if (neighborNodeType != null) {
			cir.setReturnValue(neighborNodeType);
		}
	}
}