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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LecternBaseBlock extends LecternBlock {

    public LecternBaseBlock() {
        super(AbstractBlock.Settings.copy(Blocks.LECTERN));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new LecternBlockEntity();
    }

    public static boolean putBookIfAbsent(World world, BlockPos pos, BlockState state, ItemStack stack) {
        if (!state.get(HAS_BOOK)) {
            if (!world.isClient) {
                putBook(world, pos, state, stack);
            }
            return true;
        } else {
            return false;
        }
    }

    private static void putBook(World world, BlockPos pos, BlockState state, ItemStack stack) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof LecternBlockEntity) {
            LecternBlockEntity lecternBe = (LecternBlockEntity)be;
            lecternBe.setBook(stack.split(1));
            setHasBook(world, pos, state, true);
            world.playSound(null, pos, SoundEvents.ITEM_BOOK_PUT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void onStateReplaced(BlockState state1, World world, BlockPos pos, BlockState state2, boolean boolean_1) {
        if (state1.getBlock() != state2.getBlock()) {
            if (state1.get(HAS_BOOK)) {
                this.dropBook(state1, world, pos);
            }
            if (state1.get(POWERED)) {
                world.updateNeighborsAlways(pos.down(1), this);
            }
            super.onStateReplaced(state1, world, pos, state2, boolean_1);
        }
    }

    private void dropBook(BlockState state, World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof LecternBlockEntity) {
            LecternBlockEntity lecternBe = (LecternBlockEntity)be;
            Direction direction_1 = state.get(FACING);
            ItemStack stack = lecternBe.getBook().copy();
            float float_1 = 0.25F * (float)direction_1.getOffsetX();
            float float_2 = 0.25F * (float)direction_1.getOffsetZ();
            ItemEntity itemEntity_1 = new ItemEntity(world, (double)pos.getX() + 0.5D + (double)float_1, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5D + (double)float_2, stack);
            itemEntity_1.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity_1);
            lecternBe.clear();
        }
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (state.get(HAS_BOOK)) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof LecternBlockEntity) {
                return ((LecternBlockEntity)be).getComparatorOutput();
            }
        }
        return 0;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (state.get(HAS_BOOK)) {
            if (!world.isClient) {
                this.openContainer(world, pos, player);
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    private void openContainer(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof LecternBlockEntity) {
            player.openHandledScreen((LecternBlockEntity)be);
            player.incrementStat(Stats.INTERACT_WITH_LECTERN);
        }
    }

}