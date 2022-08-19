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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

import io.github.vampirestudios.vampirelib.api.itemGroupSorting.VanillaTargetedItemGroupFiller;

public class RodBaseBlock extends io.github.vampirestudios.vampirelib.blocks.DirectionalBlock {
	private final VanillaTargetedItemGroupFiller FILLER;

	protected static final VoxelShape BB_AXIS_Y = Block.box(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	protected static final VoxelShape BB_AXIS_Z = Block.box(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
	protected static final VoxelShape BB_AXIS_X = Block.box(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

	@SuppressWarnings("unused")
	public RodBaseBlock(boolean emitsLight) {
		this(Material.STONE, emitsLight);
	}

	@SuppressWarnings("unused")
	public RodBaseBlock(Material material, boolean emitsLight) {
		super(emitsLight ? BlockBehaviour.Properties.of(material).strength(0.3F)
				.lightLevel(blockState -> 13) : BlockBehaviour.Properties.of(
				material));
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
		FILLER = new VanillaTargetedItemGroupFiller(Blocks.END_ROD.asItem());
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
		FILLER.fillItem(this.asItem(), group, list);
	}

	@Override
	public BlockState rotate(BlockState blockState_1, Rotation rotation_1) {
		return blockState_1.setValue(FACING, rotation_1.rotate(blockState_1.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState blockState_1, Mirror mirror_1) {
		return blockState_1.setValue(FACING, mirror_1.mirror(blockState_1.getValue(FACING)));
	}

	@Override
	public VoxelShape getInteractionShape(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
		return switch (blockState_1.getValue(FACING).getAxis()) {
			case X -> BB_AXIS_X;
			case Z -> BB_AXIS_Z;
			case Y -> BB_AXIS_Y;
		};
	}

	@Override
	public boolean propagatesSkylightDown(BlockState blockState_1, BlockGetter blockView_1, BlockPos blockPos_1) {
		return false;
	}

	@Override
	public boolean canSurvive(BlockState blockState_1, LevelReader viewableWorld_1, BlockPos blockPos_1) {
		return true;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext itemPlacementContext_1) {
		Direction direction_1 = itemPlacementContext_1.getHorizontalDirection();
		BlockState blockState_1 = itemPlacementContext_1.getLevel().getBlockState(
				itemPlacementContext_1.getClickedPos().relative(direction_1.getOpposite()));
		return
				blockState_1.getBlock() == this && blockState_1.getValue(FACING) == direction_1 ? this.defaultBlockState()
						.setValue(FACING,
								direction_1.getOpposite()) : this.defaultBlockState()
						.setValue(
								FACING,
								direction_1);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(FACING);
	}

}
