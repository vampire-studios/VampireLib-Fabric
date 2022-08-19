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

package io.github.vampirestudios.vampirelib.blocks;

import java.util.Objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PostBaseBlock extends Block implements SimpleWaterloggedBlock {
	public static final EnumProperty<Direction.Axis> AXIS;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape Y_SHAPE;
	protected static final VoxelShape X_SHAPE;
	protected static final VoxelShape Z_SHAPE;
	protected static final VoxelShape Y_COLLISION;

	static {
		AXIS = BlockStateProperties.AXIS;
		WATERLOGGED = BlockStateProperties.WATERLOGGED;
		Y_SHAPE = Block.box(6f, 0f, 6f, 10f, 16f, 10f);
		Y_COLLISION = Block.box(6f, 0f, 6f, 10f, 24f, 10f);
		X_SHAPE = Block.box(0f, 6f, 6f, 16f, 10f, 10f);
		Z_SHAPE = Block.box(6f, 6f, 0f, 10f, 10f, 16f);
	}

	public PostBaseBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(
				this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
	}

	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Direction.Axis axis = Objects.requireNonNull(state).getValue(AXIS);
		switch (axis) {
			case X:
				return X_SHAPE;
			case Z:
				return Z_SHAPE;
			default:
				return Y_SHAPE;
		}
	}

	public BlockState rotate(BlockState blockState_1, Rotation rotation_1) {
		switch (Objects.requireNonNull(rotation_1)) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (blockState_1.getValue(AXIS)) {
					case X:
						return blockState_1.setValue(AXIS, Direction.Axis.Z);
					case Z:
						return blockState_1.setValue(AXIS, Direction.Axis.X);
					default:
						return blockState_1;
				}
			default:
				return blockState_1;
		}
	}

	public VoxelShape getCollisionShape(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1, CollisionContext verticalEntityPosition_1) {
		Direction.Axis axis = blockState_1.getValue(AXIS);
		if (axis == Direction.Axis.Y) return Y_COLLISION;
		else
			return super.getCollisionShape(blockState_1, blockView_1, blockPos_1, verticalEntityPosition_1);
	}

	public BlockState getStateForPlacement(BlockPlaceContext itemPlacementContext_1) {
		BlockPos blockPos_1 = itemPlacementContext_1.getClickedPos();
		FluidState fluidState_1 = itemPlacementContext_1.getLevel().getFluidState(blockPos_1);
		return this.defaultBlockState().setValue(AXIS, Direction.Axis.Y)
				.setValue(WATERLOGGED, fluidState_1.getType() == Fluids.WATER);
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor iWorld, BlockPos pos, BlockPos pos1) {
		if (blockState.getValue(WATERLOGGED)) {
			iWorld.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(iWorld));
		}

		return super.updateShape(blockState, direction, blockState2, iWorld, pos, pos1);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	public boolean isPathfindable(BlockState state, BlockGetter blockView, BlockPos pos, PathComputationType navigationType) {
		return false;
	}

}
