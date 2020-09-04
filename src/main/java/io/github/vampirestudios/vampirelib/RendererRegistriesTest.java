package io.github.vampirestudios.vampirelib;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RendererRegistriesTest implements ModInitializer {
	public static final String ID = "vampirelib";

	// Count Label related items
	public static final Item OBFUSCATED_COUNT = new StorageItem(new Item.Settings().group(ItemGroup.MISC));

	// Durability Bar related items
	public static final Item ENERGY_STORAGE = new StorageItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Item MANA_STORAGE = new StorageItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Item WATER_LAVA_BUCKET = new WaterLavaBucket(new Item.Settings().group(ItemGroup.MISC));
	public static final Item DISCO_BALL = new Item(new Item.Settings().group(ItemGroup.MISC));

	// Cooldown Overlay related items
	public static final Item LONG_COOLDOWN = new CooldownItem(new Item.Settings().group(ItemGroup.MISC), 4 * 20);
	public static final Item HIDDEN_COOLDOWN = new CooldownItem(new Item.Settings().group(ItemGroup.MISC), 4 * 20);

	// Pre/Post-Rendering related items
	public static final Item MYSTERIOUS_BOOK = new Item(new Item.Settings().maxCount(1).group(ItemGroup.MISC));
	public static final Item TUNISIAN_DIAMOND = new Item(new Item.Settings().maxCount(1).group(ItemGroup.MISC));

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	@Override
	public void onInitialize() {
		// Items used to demonstrate custom count labels
		Registry.register(Registry.ITEM, id("obfuscated_count"), OBFUSCATED_COUNT);

		// Items used to demonstrate custom durability bars
		Registry.register(Registry.ITEM, id("energy_storage"), ENERGY_STORAGE);
		Registry.register(Registry.ITEM, id("mana_storage"), MANA_STORAGE);
		Registry.register(Registry.ITEM, id("water_lava_bucket"), WATER_LAVA_BUCKET);
		Registry.register(Registry.ITEM, id("disco_ball"), DISCO_BALL);

		// Items used to demonstrate custom cooldown overlays
		Registry.register(Registry.ITEM, id("long_cooldown"), LONG_COOLDOWN);
		Registry.register(Registry.ITEM, id("hidden_cooldown"), HIDDEN_COOLDOWN);

		// Items used to demostrate pre/post-rendering
		Registry.register(Registry.ITEM, id("mysterious_book"), MYSTERIOUS_BOOK);
		Registry.register(Registry.ITEM, id("tunisian_diamond"), TUNISIAN_DIAMOND);
	}
}