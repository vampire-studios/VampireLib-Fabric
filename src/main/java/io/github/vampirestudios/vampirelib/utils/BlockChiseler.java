package io.github.vampirestudios.vampirelib.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class BlockChiseler {

	public static Map<Identifier, ChiselEntry> chiselRegistry = new HashMap<>();
	public static Map<TagKey<Item>, Set<ChiselEntry>> toolTagsToEntries = new HashMap<>();
	public static Map<Item, Set<ChiselEntry>> itemsToEntries = new HashMap<>();

	public static void create(Identifier identifier, TagKey<Item> toolTag, Collection<Block> chiselBlocks) {
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

	public static void create(Identifier identifier, TagKey<Item> toolTag, ChiselEntry chiselEntry) {
		chiselRegistry.put(identifier, chiselEntry);
		if (toolTagsToEntries.containsKey(toolTag)) {
			toolTagsToEntries.get(toolTag).add(chiselEntry);
		} else {
			toolTagsToEntries.put(toolTag, new HashSet<>(Collections.singleton(chiselEntry)));
		}
	}

	public static void create(Identifier identifier, Item item, ChiselEntry chiselEntry) {
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
			throw new RuntimeException("Unknown chisel entry: \"" + identifier + "\".");
	}

	public static void setup() {
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (!world.isClientSide()) {
				BlockState hitBlockState = world.getBlockState(hitResult.getBlockPos());
				ItemStack heldStack = player.getItemInHand(hand);
				for (Map.Entry<TagKey<Item>, Set<ChiselEntry>> toolToEntries : toolTagsToEntries.entrySet()) {
					if (!new ItemStack(heldStack.getItem()).is(toolToEntries.getKey()))
						continue;
					return loopThroughChiselEntries(player, world, hand, hitResult, hitBlockState, heldStack,
							toolToEntries.getValue());
				}
				for (Map.Entry<Item, Set<ChiselEntry>> itemsToEntry : itemsToEntries.entrySet()) {
					if (itemsToEntry.getKey() != heldStack.getItem())
						continue;
					return loopThroughChiselEntries(player, world, hand, hitResult, hitBlockState, heldStack,
							itemsToEntry.getValue());
				}
			}
			return InteractionResult.PASS;
		});
	}

	private static InteractionResult loopThroughChiselEntries(
			Player player, Level level, InteractionHand hand, BlockHitResult hitResult,
			BlockState hitBlockState, ItemStack heldStack, Set<ChiselEntry> entries
	) {
		for (ChiselEntry chiselEntry : entries) {
			Block newBlock;
			if (player.isShiftKeyDown())
				newBlock = chiselEntry.getPreviousBlock(hitBlockState.getBlock());
			else
				newBlock = chiselEntry.getNextBlock(hitBlockState.getBlock());
			if (newBlock == null) continue;

			level.playSound(null, hitResult.getBlockPos(), SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.setBlockAndUpdate(hitResult.getBlockPos(), copyTo(hitBlockState, newBlock.defaultBlockState()));
			if (heldStack.has(DataComponents.MAX_DAMAGE))
				heldStack.hurtAndBreak(1, player, hand);
		}
		return InteractionResult.SUCCESS;
	}

	private static BlockState copyTo(BlockState from, BlockState to) {
		for (Property<?> property : from.getProperties()) {
			if (to.hasProperty(property)) {
				to = copyProperty(from, to, property);
			}
		}

		return to;
	}

	private static <T extends Comparable<T>> BlockState copyProperty(
		BlockState from,
		BlockState to,
		Property<T> property
	) {
		return to.setValue(property, from.getValue(property));
	}

	public static class ChiselEntry {
		Deque<Block> chiselDeque;

		ChiselEntry(Collection<Block> blocks) {
			this.chiselDeque = new ConcurrentLinkedDeque<>(blocks);
		}

		Block getNextBlock(Block oldBlock) {
			for (Iterator<Block> iterator = chiselDeque.iterator();
				 iterator.hasNext(); ) {
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
			for (Iterator<Block> iterator = chiselDeque.descendingIterator();
				 iterator.hasNext(); ) {
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
