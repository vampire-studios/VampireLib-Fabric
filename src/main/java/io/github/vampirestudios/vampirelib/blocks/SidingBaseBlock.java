/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
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

import io.github.vampirestudios.vampirelib.enums.SidingType;
import io.github.vampirestudios.vampirelib.utils.VProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

public class SidingBaseBlock extends Block implements Waterloggable {
    public static final EnumProperty<SidingType> TYPE;
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING_HORIZONTAL;
    protected static final VoxelShape SINGLE_SHAPE_SOUTH;
    protected static final VoxelShape SINGLE_SHAPE_NORTH;
    protected static final VoxelShape SINGLE_SHAPE_EAST;
    protected static final VoxelShape SINGLE_SHAPE_WEST;
    protected static final VoxelShape[] CORNER_SHAPES;

    static {
        TYPE = VProperties.SIDING_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        FACING_HORIZONTAL = Properties.HORIZONTAL_FACING;
        SINGLE_SHAPE_NORTH = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        SINGLE_SHAPE_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        SINGLE_SHAPE_EAST = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
        SINGLE_SHAPE_WEST = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        CORNER_SHAPES = new VoxelShape[]{
                VoxelShapes.union(SINGLE_SHAPE_SOUTH, SINGLE_SHAPE_WEST),
                VoxelShapes.union(SINGLE_SHAPE_WEST, SINGLE_SHAPE_NORTH),
                VoxelShapes.union(SINGLE_SHAPE_NORTH, SINGLE_SHAPE_EAST),
                VoxelShapes.union(SINGLE_SHAPE_EAST, SINGLE_SHAPE_SOUTH)
        };
    }

    public SidingBaseBlock(Block.Settings block$Settings_1) {
        super(block$Settings_1);
        this.setDefaultState(this.getDefaultState().with(TYPE, SidingType.SINGLE).with(FACING_HORIZONTAL, Direction.NORTH).with(WATERLOGGED, false));
    }

    private static SidingType getAdjustedType(BlockView blockView, BlockPos blockPos, Direction facing) {
        BlockState oppositeBlockState = blockView.getBlockState(blockPos.offset(facing));
        if (oppositeBlockState.getBlock() instanceof SidingBaseBlock) {
            if (oppositeBlockState.get(FACING_HORIZONTAL) == facing.rotateYClockwise()) {
                return SidingType.CORNER_RIGHT;
            } else if (oppositeBlockState.get(FACING_HORIZONTAL) == facing.rotateYCounterclockwise()) {
                return SidingType.CORNER_LEFT;
            }
        }
        return SidingType.SINGLE;
    }

    public boolean method_9526(BlockState blockState_1) {
        return blockState_1.get(TYPE) != SidingType.DOUBLE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(TYPE, FACING_HORIZONTAL, WATERLOGGED);
    }

    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext) {
        SidingType slabType_1 = blockState_1.get(TYPE);
        Direction facing = blockState_1.get(FACING_HORIZONTAL);
        switch (slabType_1) {
            case DOUBLE:
                return VoxelShapes.fullCube();
            case CORNER_RIGHT:
                return CORNER_SHAPES[facing.getHorizontal()];
            case CORNER_LEFT:
                return CORNER_SHAPES[facing.rotateYCounterclockwise().getHorizontal()];
            default:
                switch (facing) {
                    case SOUTH:
                        return SINGLE_SHAPE_SOUTH;
                    case EAST:
                        return SINGLE_SHAPE_EAST;
                    case WEST:
                        return SINGLE_SHAPE_WEST;
                    default:
                        return SINGLE_SHAPE_NORTH;
                }
        }
    }

    public BlockState getPlacementState(ItemPlacementContext placementContext) {
        BlockPos blockPos = placementContext.getBlockPos();
        BlockState blockState = placementContext.getWorld().getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            return blockState.with(TYPE, SidingType.DOUBLE).with(FACING_HORIZONTAL, blockState.get(FACING_HORIZONTAL)).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = placementContext.getWorld().getFluidState(blockPos);
            Direction playerHorizontalFacing = placementContext.getPlayerLookDirection();
            Direction facing = placementContext.getPlayerFacing();
            double xPos = placementContext.getHitPos().getX() - blockPos.getX();
            double zPos = placementContext.getHitPos().getZ() - blockPos.getZ();
            Direction direction = playerHorizontalFacing.getOpposite();
            if (facing.getAxis().isVertical()) {
                if (direction == Direction.EAST || direction == Direction.WEST) {
                    if (xPos > 0.5) direction = Direction.WEST;
                    else direction = Direction.EAST;
                } else {
                    if (zPos > 0.5) direction = Direction.NORTH;
                    else direction = Direction.SOUTH;
                }
            }

            BlockState resultBlockState = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

            return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(FACING_HORIZONTAL, direction).with(TYPE, getAdjustedType(placementContext.getWorld(), blockPos, direction));
        }
    }

    public FluidState getFluidState(BlockState blockState_1) {
        return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
    }

    public boolean tryFillWithFluid(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1, FluidState fluidState_1) {
        return blockState_1.get(TYPE) != SidingType.DOUBLE && Waterloggable.super.tryFillWithFluid(iWorld_1, blockPos_1, blockState_1, fluidState_1);
    }

    public boolean canFillWithFluid(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1, Fluid fluid_1) {
        return blockState_1.get(TYPE) != SidingType.DOUBLE && Waterloggable.super.canFillWithFluid(blockView_1, blockPos_1, blockState_1, fluid_1);
    }

    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState_2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos_2) {
        if (blockState.get(WATERLOGGED) && direction != blockState.get(FACING_HORIZONTAL).getOpposite()) {
            iWorld.getFluidTickScheduler().schedule(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }

        return direction.getAxis().isHorizontal() ? blockState.with(TYPE, getAdjustedType(iWorld, blockPos, blockState.get(FACING_HORIZONTAL))) : super.getStateForNeighborUpdate(blockState, direction, blockState_2, iWorld, blockPos, blockPos_2);
    }

    public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
        switch (blockPlacementEnvironment_1) {
            case LAND:
                return blockState_1.get(TYPE) == SidingType.SINGLE;
            case WATER:
                return blockView_1.getFluidState(blockPos_1).matches(FluidTags.WATER);
            case AIR:
            default:
                return false;
        }
    }
}