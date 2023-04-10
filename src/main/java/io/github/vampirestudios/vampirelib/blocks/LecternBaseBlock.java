/*
 * Copyright (c) 2023 OliviaTheVampire
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
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LecternBaseBlock extends LecternBlock {

	public LecternBaseBlock() {
		super(Properties.copy(Blocks.LECTERN));
	}

	public static boolean tryPlaceBook(Player playerEntity, Level world, BlockPos pos, BlockState state, ItemStack stack) {
		if (!state.getValue(HAS_BOOK)) {
			if (!world.isClientSide) {
				placeBook(playerEntity, world, pos, state, stack);
			}
			return true;
		} else {
			return false;
		}
	}

	private static void placeBook(Player playerEntity, Level world, BlockPos pos, BlockState state, ItemStack stack) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof LecternBlockEntity lecternBe) {
			lecternBe.setBook(stack.split(1));
			resetBookState(playerEntity, world, pos, state, true);
			world.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new LecternBlockEntity(blockPos, blockState);
	}

	@Override
	public void onRemove(BlockState state1, Level world, BlockPos pos, BlockState state2, boolean boolean_1) {
		if (state1.getBlock() != state2.getBlock()) {
			if (state1.getValue(HAS_BOOK)) {
				this.popBook(state1, world, pos);
			}
			if (state1.getValue(POWERED)) {
				world.updateNeighborsAt(pos.below(1), this);
			}
			super.onRemove(state1, world, pos, state2, boolean_1);
		}
	}

	private void popBook(BlockState state, Level world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof LecternBlockEntity lecternBe) {
			Direction direction_1 = state.getValue(FACING);
			ItemStack stack = lecternBe.getBook().copy();
			float float_1 = 0.25F * (float) direction_1.getStepX();
			float float_2 = 0.25F * (float) direction_1.getStepZ();
			ItemEntity itemEntity_1 = new ItemEntity(world, (double) pos.getX() + 0.5D + (double) float_1,
					pos.getY() + 1, (double) pos.getZ() + 0.5D + (double) float_2,
					stack);
			itemEntity_1.setDefaultPickUpDelay();
			world.addFreshEntity(itemEntity_1);
			lecternBe.clearContent();
		}
	}

	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		if (state.getValue(HAS_BOOK)) {
			BlockEntity be = world.getBlockEntity(pos);
			if (be instanceof LecternBlockEntity) {
				return ((LecternBlockEntity) be).getRedstoneSignal();
			}
		}
		return 0;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (state.getValue(HAS_BOOK)) {
			if (!world.isClientSide) {
				this.openContainer(world, pos, player);
			}
			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.FAIL;
		}
	}

	private void openContainer(Level world, BlockPos pos, Player player) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof LecternBlockEntity) {
			player.openMenu((LecternBlockEntity) be);
			player.awardStat(Stats.INTERACT_WITH_LECTERN);
		}
	}

}
