package org.quiltmc.qsl.block.content.registry.impl;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;
import org.quiltmc.qsl.block.content.registry.api.FlammableBlockEntry;
import org.quiltmc.qsl.block.content.registry.api.ReversibleBlockEntry;
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class BlockContentRegistriesInitializer implements ModInitializer {
	private static final Map<Block, BlockState> INITIAL_PATH_STATES = ImmutableMap.copyOf(ShovelItem.FLATTENABLES);
	private static final Map<Block, Block> INITIAL_STRIPPED_BLOCKS = ImmutableMap.copyOf(AxeItem.STRIPPABLES);

	public static final BiMap<Block, Block> INITIAL_OXIDATION_BLOCKS = HashBiMap.create();
	public static final BiMap<Block, Block> OXIDATION_INCREASE_BLOCKS = HashBiMap.create();
	public static final BiMap<Block, Block> OXIDATION_DECREASE_BLOCKS = HashBiMap.create();

	public static final BiMap<Block, Block> INITIAL_WAXED_BLOCKS = HashBiMap.create();
	public static final BiMap<Block, Block> WAXED_UNWAXED_BLOCKS = HashBiMap.create();
	public static final BiMap<Block, Block> UNWAXED_WAXED_BLOCKS = HashBiMap.create();

	private static final Map<Block, FlammableBlockEntry> INITIAL_FLAMMABLE_BLOCKS;

	static {
		var builder = ImmutableMap.<Block, FlammableBlockEntry>builder();
		FireBlock fireBlock = ((FireBlock) Blocks.FIRE);
		fireBlock.burnOdds.keySet().forEach(block -> builder.put(block, new FlammableBlockEntry(fireBlock.igniteOdds.getInt(block), fireBlock.burnOdds.getInt(block))));
		INITIAL_FLAMMABLE_BLOCKS = builder.build();
	}

	private static final Map<GameEvent, Integer> INITIAL_SCULK_SENSOR_BLOCK_EVENTS = ImmutableMap.copyOf(SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT);

	@Override
	public void onInitialize(ModContainer mod) {
		// Force load the maps
		WeatheringCopper.NEXT_BY_BLOCK.get();
		HoneycombItem.WAXABLES.get();

		resetMaps();
		ResourceLoaderEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, error) -> resetMaps());
	}

	private static void resetMaps() {
		ShovelItem.FLATTENABLES.clear();
		addMapToAttachment(INITIAL_PATH_STATES, BlockContentRegistries.FLATTENABLE_BLOCK);
		setMapFromAttachment(ShovelItem.FLATTENABLES::put, BlockContentRegistries.FLATTENABLE_BLOCK);

		AxeItem.STRIPPABLES.clear();
		addMapToAttachment(INITIAL_STRIPPED_BLOCKS, BlockContentRegistries.STRIPPABLE_BLOCK);
		setMapFromAttachment(AxeItem.STRIPPABLES::put, BlockContentRegistries.STRIPPABLE_BLOCK);

		OXIDATION_INCREASE_BLOCKS.clear();
		OXIDATION_DECREASE_BLOCKS.clear();
		addMapToAttachment(INITIAL_OXIDATION_BLOCKS.entrySet().stream().collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> new ReversibleBlockEntry(entry.getValue(), true)
		)), BlockContentRegistries.OXIDIZABLE_BLOCK);
		setMapFromAttachment((entry, value) -> OXIDATION_INCREASE_BLOCKS.put(entry, value.block()), BlockContentRegistries.OXIDIZABLE_BLOCK);
		setMapFromAttachment((entry, value) -> value.reversible() ? OXIDATION_DECREASE_BLOCKS.put(value.block(), entry) : null, BlockContentRegistries.OXIDIZABLE_BLOCK);

		UNWAXED_WAXED_BLOCKS.clear();
		WAXED_UNWAXED_BLOCKS.clear();
		addMapToAttachment(INITIAL_WAXED_BLOCKS.entrySet().stream().collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> new ReversibleBlockEntry(entry.getValue(), true)
		)), BlockContentRegistries.WAXABLE_BLOCK);
		setMapFromAttachment((entry, value) -> UNWAXED_WAXED_BLOCKS.put(entry, value.block()), BlockContentRegistries.WAXABLE_BLOCK);
		setMapFromAttachment((entry, value) -> value.reversible() ? WAXED_UNWAXED_BLOCKS.put(value.block(), entry) : null, BlockContentRegistries.OXIDIZABLE_BLOCK);

		FireBlock fireBlock = ((FireBlock) Blocks.FIRE);
		fireBlock.burnOdds.clear();
		fireBlock.igniteOdds.clear();
		addMapToAttachment(INITIAL_FLAMMABLE_BLOCKS, BlockContentRegistries.FLAMMABLE_BLOCK);
		BlockContentRegistries.FLAMMABLE_BLOCK.registry().stream().forEach(entry -> BlockContentRegistries.FLAMMABLE_BLOCK.getValue(entry).ifPresent(v -> {
			fireBlock.burnOdds.put(entry, v.burn());
			fireBlock.igniteOdds.put(entry, v.spread());
		}));

		SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.clear();
		addMapToAttachment(INITIAL_SCULK_SENSOR_BLOCK_EVENTS, BlockContentRegistries.SCULK_FREQUENCY);
		setMapFromAttachment(SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT::put, BlockContentRegistries.SCULK_FREQUENCY);
	}

	private static <T, V> void setMapFromAttachment(BiFunction<T, V, ?> map, RegistryEntryAttachment<T, V> attachment) {
		attachment.registry().stream().forEach(entry -> attachment.getValue(entry).ifPresent(v -> map.apply(entry, v)));
	}

	private static <T, V> void addMapToAttachment(Map<T, V> map, RegistryEntryAttachment<T, V> attachment) {
		map.forEach(attachment::put);
	}
}