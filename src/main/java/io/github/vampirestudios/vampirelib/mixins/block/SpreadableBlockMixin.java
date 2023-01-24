/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

import io.github.vampirestudios.vampirelib.api.blockspreading.BlockSpreadingType;
import io.github.vampirestudios.vampirelib.api.blockspreading.SpreadBehaviors;
import io.github.vampirestudios.vampirelib.api.blockspreading.SpreadingBlock;

@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class SpreadableBlockMixin extends SnowyDirtBlock implements SpreadingBlock {

	protected SpreadableBlockMixin(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Shadow
	private static boolean canBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
		BlockPos blockPos = pos.above();
		BlockState blockState = levelReader.getBlockState(blockPos);
		if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LayerLightEngine.getLightBlockInto(levelReader, state, pos, blockState, blockPos, Direction.UP,
					blockState.getLightBlock(levelReader, blockPos));
			return i < levelReader.getMaxLightLevel();
		}
	}

	@Shadow
	private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockPos = pos.above();
		return canBeGrass(state, level, pos) && !level.getFluidState(blockPos).is(FluidTags.WATER);
	}

	/**
	 * @author OliviaTheVampire
	 */
	@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
		if (!canBeGrass(state, world, pos)) {
			if (!world.hasChunksAt(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			if (SpreadBehaviors.getSpreadingBehavior(state.getBlock(), BlockSpreadingType.REVERT) !=
					null) //Forge: switch to use SpreadBehaviors API, so this class can be used more easily
				world.setBlockAndUpdate(pos,
						SpreadBehaviors.getSpreadState(state, world, pos, BlockSpreadingType.REVERT));
		} else {
			if (!world.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3)))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			if (world.getLightEmission(pos.above()) >= 9) {
				this.spread(state, world, pos, random, 4, 1);
				BlockState blockState = this.defaultBlockState();

				for (int i = 0; i < 4; ++i) {
					BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (world.getBlockState(blockPos).is(Blocks.DIRT) && canPropagate(blockState, world, blockPos)) {
						world.setBlockAndUpdate(blockPos, blockState.setValue(SNOWY,
								world.getBlockState(blockPos.above())
										.is(Blocks.SNOW)));
					}
				}
			}
		}
		ci.cancel();
	}
}
