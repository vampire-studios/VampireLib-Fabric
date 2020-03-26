/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

import java.util.Objects;

public class PostBaseBlock extends Block implements Waterloggable {
    public static final EnumProperty<Direction.Axis> AXIS;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape Y_SHAPE;
    protected static final VoxelShape X_SHAPE;
    protected static final VoxelShape Z_SHAPE;
    protected static final VoxelShape Y_COLLISION;

    static {
        AXIS = Properties.AXIS;
        WATERLOGGED = Properties.WATERLOGGED;
        Y_SHAPE = Block.createCuboidShape(6f, 0f, 6f, 10f, 16f, 10f);
        Y_COLLISION = Block.createCuboidShape(6f, 0f, 6f, 10f, 24f, 10f);
        X_SHAPE = Block.createCuboidShape(0f, 6f, 6f, 16f, 10f, 10f);
        Z_SHAPE = Block.createCuboidShape(6f, 6f, 0f, 10f, 10f, 16f);
    }

    public PostBaseBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction.Axis axis = Objects.requireNonNull(state).get(AXIS);
        switch (axis) {
            case X:
                return X_SHAPE;
            case Z:
                return Z_SHAPE;
            default:
                return Y_SHAPE;
        }
    }

    public BlockState rotate(BlockState blockState_1, BlockRotation rotation_1) {
        switch (Objects.requireNonNull(rotation_1)) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (blockState_1.get(AXIS)) {
                    case X:
                        return blockState_1.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return blockState_1.with(AXIS, Direction.Axis.X);
                    default:
                        return blockState_1;
                }
            default:
                return blockState_1;
        }
    }

    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, ShapeContext verticalEntityPosition_1) {
        Direction.Axis axis = blockState_1.get(AXIS);
        if (axis == Direction.Axis.Y) return Y_COLLISION;
        else return super.getCollisionShape(blockState_1, blockView_1, blockPos_1, verticalEntityPosition_1);
    }

    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
        FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
        return this.getDefaultState().with(AXIS, Direction.Axis.Y).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos pos, BlockPos pos1) {
        if (blockState.get(WATERLOGGED)) {
            iWorld.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }

        return super.getStateForNeighborUpdate(blockState, direction, blockState2, iWorld, pos, pos1);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean canPathfindThrough(BlockState state, BlockView blockView, BlockPos pos, NavigationType navigationType) {
        return false;
    }

}