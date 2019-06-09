package team.abnormals.abnormalib.utils.registry;

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
    public static Map<Tag<Item>, Set<ChiselEntry>> toolsToEntries = new HashMap<>();

    public static void create(Identifier identifier, Tag<Item> toolTag, Collection<Block> chiselBlocks) {
        ChiselEntry chiselEntry = new ChiselEntry(chiselBlocks);
        chiselRegistry.put(identifier, chiselEntry);
        if (toolsToEntries.containsKey(toolTag)) {
            toolsToEntries.get(toolTag).add(chiselEntry);
        } else {
            toolsToEntries.put(toolTag, new HashSet<>(Collections.singleton(chiselEntry)));
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
                for (Map.Entry<Tag<Item>, Set<ChiselEntry>> toolToEntries : toolsToEntries.entrySet()) {
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
            }
            return ActionResult.PASS;
        });
    }

    protected static BlockState copyTo(BlockState from, BlockState to) {
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