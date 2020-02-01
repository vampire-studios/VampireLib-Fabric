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

package io.github.vampirestudios.vampirelib.utils.registry;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BlockChiseler {

    public static Map<Identifier, ChiselEntry> chiselRegistry = new HashMap<>();
    public static Map<Tag<Item>, Set<ChiselEntry>> toolTagsToEntries = new HashMap<>();
    public static Map<Item, Set<ChiselEntry>> itemsToEntries = new HashMap<>();

    public static void create(Identifier identifier, Tag<Item> toolTag, Collection<Block> chiselBlocks) {
        ChiselEntry chiselEntry = new ChiselEntry(chiselBlocks);
        chiselRegistry.put(identifier, chiselEntry);
        if (toolTagsToEntries.containsKey(toolTag)) {
            toolTagsToEntries.get(toolTag).add(chiselEntry);
        } else {
            toolTagsToEntries.put(toolTag, new HashSet<>(Collections.singleton(chiselEntry)));
        }
    }

    public static void create(Identifier identifier, Item item, Collection<Block> chiselBlocks) {
        ChiselEntry chiselEntry = new ChiselEntry(chiselBlocks);
        chiselRegistry.put(identifier, chiselEntry);
        if (itemsToEntries.containsKey(item)) {
            itemsToEntries.get(item).add(chiselEntry);
        } else {
            itemsToEntries.put(item, new HashSet<>(Collections.singleton(chiselEntry)));
        }
    }

    public static void add(Identifier identifier, Collection<Block> carvedBlocks) {
        if (chiselRegistry.containsKey(identifier)) {
            chiselRegistry.get(identifier).chiselDeque.addAll(carvedBlocks);
        } else
            throw new RuntimeException("Unknown chisel entry \"" + identifier + "\".");
    }

    public static void setup() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient()) {
                BlockState hitBlockState = world.getBlockState(hitResult.getBlockPos());
                ItemStack heldStack = player.getStackInHand(hand);
                for (Map.Entry<Tag<Item>, Set<ChiselEntry>> toolToEntries : toolTagsToEntries.entrySet()) {
                    if (!toolToEntries.getKey().contains(heldStack.getItem()))
                        continue;
                    for (ChiselEntry chiselEntry : toolToEntries.getValue()) {
                        Block newBlock;
                        if (player.isSneaking())
                            newBlock = chiselEntry.getPreviousBlock(hitBlockState.getBlock());
                        else
                            newBlock = chiselEntry.getNextBlock(hitBlockState.getBlock());
                        if (newBlock == null) continue;

                        world.playSound(null, hitResult.getBlockPos(), SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        world.setBlockState(hitResult.getBlockPos(), copyTo(hitBlockState, newBlock.getDefaultState()));
                        if (heldStack.getItem().isDamageable())
                            heldStack.damage(1, player, playerEntity -> player.sendToolBreakStatus(hand));
                        return ActionResult.SUCCESS;
                    }
                }
                for (Map.Entry<Item, Set<ChiselEntry>> itemsToEntry : itemsToEntries.entrySet()) {
                    if (itemsToEntry.getKey() != heldStack.getItem())
                        continue;
                    for (ChiselEntry chiselEntry : itemsToEntry.getValue()) {
                        Block newBlock;
                        if (player.isSneaking())
                            newBlock = chiselEntry.getPreviousBlock(hitBlockState.getBlock());
                        else
                            newBlock = chiselEntry.getNextBlock(hitBlockState.getBlock());
                        if (newBlock == null) continue;

                        world.playSound(null, hitResult.getBlockPos(), SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        world.setBlockState(hitResult.getBlockPos(), copyTo(hitBlockState, newBlock.getDefaultState()));
                        if (heldStack.getItem().isDamageable())
                            heldStack.damage(1, player, playerEntity -> player.sendToolBreakStatus(hand));
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    private static BlockState copyTo(BlockState from, BlockState to) {
        Collection<Property<?>> fromProperties = from.getProperties();
        Collection<Property<?>> toProperties = to.getProperties();
        for (Property property : fromProperties) {
            if (toProperties.contains(property)) {
                to = to.with(property, from.get(property));
            }
        }
        return to;
    }

    static class ChiselEntry {
        Deque<Block> chiselDeque;

        ChiselEntry(Collection<Block> blocks) {
            this.chiselDeque = new ConcurrentLinkedDeque<>(blocks);
        }

        Block getNextBlock(Block oldBlock) {
            for (Iterator<Block> iterator = chiselDeque.iterator(); iterator.hasNext(); ) {
                if (iterator.next() == oldBlock) {
                    if (iterator.hasNext())
                        return iterator.next();
                    else
                        return chiselDeque.getFirst();
                }
            }
            return null;
        }

        Block getPreviousBlock(Block oldBlock) {
            for (Iterator<Block> iterator = chiselDeque.descendingIterator(); iterator.hasNext(); ) {
                if (iterator.next() == oldBlock) {
                    if (iterator.hasNext())
                        return iterator.next();
                    else
                        return chiselDeque.getLast();
                }
            }
            return null;
        }
    }

}