package io.github.vampirestudios.vampirelib.api.boat.item;

import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType;
import io.github.vampirestudios.vampirelib.impl.boat.item.TerraformBoatDispenserBehavior;
import io.github.vampirestudios.vampirelib.impl.boat.item.TerraformBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Supplier;

/**
 * This class provides utilities for the {@linkplain TerraformBoatItem item forms} of {@linkplain TerraformBoatType Terraform boats},
 * such as {@linkplain #registerBoatItem(ResourceLocation, Supplier, Item.Properties) registering them and their dispenser behavior}.
 */
public final class TerraformBoatItemHelper {
	private static final CreativeModeTab DEFAULT_ITEM_GROUP = CreativeModeTab.TAB_TRANSPORTATION;

	private TerraformBoatItemHelper() {
		return;
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}
	 * with the default item group ({@link CreativeModeTab#TAB_TRANSPORTATION})
	 * and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 *
	 * <p>To register a boat item and its dispenser behavior:
	 *
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT);
	 * }</pre>
	 *
	 * @see #registerBoatItem(ResourceLocation, Supplier, CreativeModeTab) Helper that allows specifying a custom item group
	 * @see #registerBoatItem(ResourceLocation, Supplier, Item.Properties) Helper that allows specifying a custom item settings
	 *
	 * @param id the {@linkplain ResourceLocation identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(ResourceLocation id, Supplier<TerraformBoatType> boatSupplier) {
		return registerBoatItem(id, boatSupplier, DEFAULT_ITEM_GROUP);
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item}
	 * with a specified item group
	 * and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 *
	 * <p>To register a boat item and its dispenser behavior:
	 *
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, ItemGroup.TRANSPORTATION);
	 * }</pre>
	 *
	 * @see #registerBoatItem(ResourceLocation, Supplier, Item.Properties) Helper that allows specifying a custom item settings
	 *
	 * @param id the {@linkplain ResourceLocation identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(ResourceLocation id, Supplier<TerraformBoatType> boatSupplier, CreativeModeTab group) {
		return registerBoatItem(id, boatSupplier, new Item.Properties().stacksTo(1).tab(group));
	}

	/**
	 * Registers a {@linkplain TerraformBoatItem boat item} and its corresponding {@link #registerBoatDispenserBehavior dispenser behavior}.
	 *
	 * <p>To register a boat item and its dispenser behavior:
	 *
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatItem(new Identifier("examplemod", "mahogany_boat"), () -> MAHOGANY_BOAT, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));
	 * }</pre>
	 *
	 * @param id the {@linkplain ResourceLocation identifier} to register the item with
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item and dispenser behavior
	 */
	public static Item registerBoatItem(ResourceLocation id, Supplier<TerraformBoatType> boatSupplier, Item.Properties settings) {
		Item item = Registry.register(Registry.ITEM, id, new TerraformBoatItem(boatSupplier, settings));

		registerBoatDispenserBehavior(item, boatSupplier);
		return item;
	}

	/**
	 * Registers a {@linkplain DispenseItemBehavior dispenser behavior} that spawns a {@linkplain io.github.vampirestudios.vampirelib.impl.boat.TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
	 *
	 * <p>To register a boat dispenser behavior:
	 *
	 * <pre>{@code
	 *     TerraformBoatItemHelper.registerBoatDispenserBehavior(MAHOGANY_BOAT_ITEM, () -> MAHOGANY_BOAT);
	 * }</pre>
	 *
	 * @param item the item that should be assigned to this dispenser behavior
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 */
	public static void registerBoatDispenserBehavior(ItemLike item, Supplier<TerraformBoatType> boatSupplier) {
		DispenserBlock.registerBehavior(item, new TerraformBoatDispenserBehavior(boatSupplier));
	}
}
