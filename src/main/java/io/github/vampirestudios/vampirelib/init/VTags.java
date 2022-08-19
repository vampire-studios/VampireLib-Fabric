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

package io.github.vampirestudios.vampirelib.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class VTags {

	public static class Blocks {
		public static final TagKey<Block> LEAF_PILES = tag("leaf_piles");
		public static final TagKey<Block> LEAF_CARPETS = tag("leaf_carpets");
		public static final TagKey<Block> WART_PILES = tag("wart_piles");
		public static final TagKey<Block> WART_CARPETS = tag("wart_carpets");

		public static final TagKey<Block> BARRELS_WOODEN = tag("barrels/wooden");
		public static final TagKey<Block> BARRELS = tag("barrels");
		public static final TagKey<Block> CHESTS_ENDER = tag("chests/ender");
		public static final TagKey<Block> CHESTS_TRAPPED = tag("chests/trapped");
		public static final TagKey<Block> CHESTS_WOODEN = tag("chests/wooden");
		public static final TagKey<Block> CHESTS = tag("chests");
		public static final TagKey<Block> COBBLESTONE = tag("cobblestone");
		public static final TagKey<Block> COBBLESTONE_NORMAL = tag("cobblestone/normal");
		public static final TagKey<Block> COBBLESTONE_INFESTED = tag("cobblestone/infested");
		public static final TagKey<Block> COBBLESTONE_MOSSY = tag("cobblestone/mossy");
		public static final TagKey<Block> COBBLESTONE_DEEPSLATE = tag("cobblestone/deepslate");
		public static final TagKey<Block> DIRT = tag("dirt");
		public static final TagKey<Block> END_STONES = tag("end_stones");
		public static final TagKey<Block> ENDERMAN_PLACE_ON_BLACKLIST = tag("enderman_place_on_blacklist");
		public static final TagKey<Block> FENCE_GATES = tag("fence_gates");
		public static final TagKey<Block> FENCE_GATES_WOODEN = tag("fence_gates/wooden");
		public static final TagKey<Block> FENCES = tag("fences");
		public static final TagKey<Block> FENCES_NETHER_BRICK = tag("fences/nether_brick");
		public static final TagKey<Block> FENCES_WOODEN = tag("fences/wooden");

		public static final TagKey<Block> GLASS = tag("glass");
		public static final TagKey<Block> GLASS_BLACK = tag("glass/black");
		public static final TagKey<Block> GLASS_BLUE = tag("glass/blue");
		public static final TagKey<Block> GLASS_BROWN = tag("glass/brown");
		public static final TagKey<Block> GLASS_COLORLESS = tag("glass/colorless");
		public static final TagKey<Block> GLASS_CYAN = tag("glass/cyan");
		public static final TagKey<Block> GLASS_GRAY = tag("glass/gray");
		public static final TagKey<Block> GLASS_GREEN = tag("glass/green");
		public static final TagKey<Block> GLASS_LIGHT_BLUE = tag("glass/light_blue");
		public static final TagKey<Block> GLASS_LIGHT_GRAY = tag("glass/light_gray");
		public static final TagKey<Block> GLASS_LIME = tag("glass/lime");
		public static final TagKey<Block> GLASS_MAGENTA = tag("glass/magenta");
		public static final TagKey<Block> GLASS_ORANGE = tag("glass/orange");
		public static final TagKey<Block> GLASS_PINK = tag("glass/pink");
		public static final TagKey<Block> GLASS_PURPLE = tag("glass/purple");
		public static final TagKey<Block> GLASS_RED = tag("glass/red");
		/**
		 * Glass which is made from sand and only minor additional ingredients like dyes
		 */
		public static final TagKey<Block> GLASS_SILICA = tag("glass/silica");
		public static final TagKey<Block> GLASS_TINTED = tag("glass/tinted");
		public static final TagKey<Block> GLASS_WHITE = tag("glass/white");
		public static final TagKey<Block> GLASS_YELLOW = tag("glass/yellow");

		public static final TagKey<Block> GLASS_PANES = tag("glass_panes");
		public static final TagKey<Block> GLASS_PANES_BLACK = tag("glass_panes/black");
		public static final TagKey<Block> GLASS_PANES_BLUE = tag("glass_panes/blue");
		public static final TagKey<Block> GLASS_PANES_BROWN = tag("glass_panes/brown");
		public static final TagKey<Block> GLASS_PANES_COLORLESS = tag("glass_panes/colorless");
		public static final TagKey<Block> GLASS_PANES_CYAN = tag("glass_panes/cyan");
		public static final TagKey<Block> GLASS_PANES_GRAY = tag("glass_panes/gray");
		public static final TagKey<Block> GLASS_PANES_GREEN = tag("glass_panes/green");
		public static final TagKey<Block> GLASS_PANES_LIGHT_BLUE = tag("glass_panes/light_blue");
		public static final TagKey<Block> GLASS_PANES_LIGHT_GRAY = tag("glass_panes/light_gray");
		public static final TagKey<Block> GLASS_PANES_LIME = tag("glass_panes/lime");
		public static final TagKey<Block> GLASS_PANES_MAGENTA = tag("glass_panes/magenta");
		public static final TagKey<Block> GLASS_PANES_ORANGE = tag("glass_panes/orange");
		public static final TagKey<Block> GLASS_PANES_PINK = tag("glass_panes/pink");
		public static final TagKey<Block> GLASS_PANES_PURPLE = tag("glass_panes/purple");
		public static final TagKey<Block> GLASS_PANES_RED = tag("glass_panes/red");
		public static final TagKey<Block> GLASS_PANES_WHITE = tag("glass_panes/white");
		public static final TagKey<Block> GLASS_PANES_YELLOW = tag("glass_panes/yellow");

		public static final TagKey<Block> GRAVEL = tag("gravel");
		public static final TagKey<Block> NETHERRACK = tag("netherrack");
		public static final TagKey<Block> OBSIDIAN = tag("obsidian");
		/**
		 * Blocks which are often replaced by deepslate ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_DEEPSLATE}, during world generation
		 */
		public static final TagKey<Block> ORE_BEARING_GROUND_DEEPSLATE = tag("ore_bearing_ground/deepslate");
		/**
		 * Blocks which are often replaced by netherrack ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_NETHERRACK}, during world generation
		 */
		public static final TagKey<Block> ORE_BEARING_GROUND_NETHERRACK = tag("ore_bearing_ground/netherrack");
		/**
		 * Blocks which are often replaced by stone ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_STONE}, during world generation
		 */
		public static final TagKey<Block> ORE_BEARING_GROUND_STONE = tag("ore_bearing_ground/stone");
		/**
		 * Ores which on average result in more than one resource worth of materials
		 */
		public static final TagKey<Block> ORE_RATES_DENSE = tag("ore_rates/dense");
		/**
		 * Ores which on average result in one resource worth of materials
		 */
		public static final TagKey<Block> ORE_RATES_SINGULAR = tag("ore_rates/singular");
		/**
		 * Ores which on average result in less than one resource worth of materials
		 */
		public static final TagKey<Block> ORE_RATES_SPARSE = tag("ore_rates/sparse");
		public static final TagKey<Block> ORES = tag("ores");
		public static final TagKey<Block> ORES_COAL = tag("ores/coal");
		public static final TagKey<Block> ORES_COPPER = tag("ores/copper");
		public static final TagKey<Block> ORES_DIAMOND = tag("ores/diamond");
		public static final TagKey<Block> ORES_EMERALD = tag("ores/emerald");
		public static final TagKey<Block> ORES_GOLD = tag("ores/gold");
		public static final TagKey<Block> ORES_IRON = tag("ores/iron");
		public static final TagKey<Block> ORES_LAPIS = tag("ores/lapis");
		public static final TagKey<Block> ORES_NETHERITE_SCRAP = tag("ores/netherite_scrap");
		public static final TagKey<Block> ORES_QUARTZ = tag("ores/quartz");
		public static final TagKey<Block> ORES_REDSTONE = tag("ores/redstone");
		/**
		 * Ores in deepslate (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_DEEPSLATE}) which could logically use deepslate as recipe input or output
		 */
		public static final TagKey<Block> ORES_IN_GROUND_DEEPSLATE = tag("ores_in_ground/deepslate");
		/**
		 * Ores in netherrack (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_NETHERRACK}) which could logically use netherrack as recipe input or output
		 */
		public static final TagKey<Block> ORES_IN_GROUND_NETHERRACK = tag("ores_in_ground/netherrack");
		/**
		 * Ores in stone (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_STONE}) which could logically use stone as recipe input or output
		 */
		public static final TagKey<Block> ORES_IN_GROUND_STONE = tag("ores_in_ground/stone");

		public static final TagKey<Block> SAND = tag("sand");
		public static final TagKey<Block> SAND_COLORLESS = tag("sand/colorless");
		public static final TagKey<Block> SAND_RED = tag("sand/red");

		public static final TagKey<Block> SANDSTONE = tag("sandstone");
		public static final TagKey<Block> STAINED_GLASS = tag("stained_glass");
		public static final TagKey<Block> STAINED_GLASS_PANES = tag("stained_glass_panes");
		public static final TagKey<Block> STONE = tag("stone");
		public static final TagKey<Block> STORAGE_BLOCKS = tag("storage_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_AMETHYST = tag("storage_blocks/amethyst");
		public static final TagKey<Block> STORAGE_BLOCKS_COAL = tag("storage_blocks/coal");
		public static final TagKey<Block> STORAGE_BLOCKS_COPPER = tag("storage_blocks/copper");
		public static final TagKey<Block> STORAGE_BLOCKS_DIAMOND = tag("storage_blocks/diamond");
		public static final TagKey<Block> STORAGE_BLOCKS_EMERALD = tag("storage_blocks/emerald");
		public static final TagKey<Block> STORAGE_BLOCKS_GOLD = tag("storage_blocks/gold");
		public static final TagKey<Block> STORAGE_BLOCKS_IRON = tag("storage_blocks/iron");
		public static final TagKey<Block> STORAGE_BLOCKS_LAPIS = tag("storage_blocks/lapis");
		public static final TagKey<Block> STORAGE_BLOCKS_NETHERITE = tag("storage_blocks/netherite");
		public static final TagKey<Block> STORAGE_BLOCKS_QUARTZ = tag("storage_blocks/quartz");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_COPPER = tag("storage_blocks/raw_copper");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_GOLD = tag("storage_blocks/raw_gold");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_IRON = tag("storage_blocks/raw_iron");
		public static final TagKey<Block> STORAGE_BLOCKS_REDSTONE = tag("storage_blocks/redstone");

		public static final TagKey<Block> NEEDS_WOOD_TOOL = tag("needs_wood_tool");
		public static final TagKey<Block> NEEDS_GOLD_TOOL = tag("needs_gold_tool");
		public static final TagKey<Block> NEEDS_NETHERITE_TOOL = tag("needs_netherite_tool");

		public static final TagKey<Block> LADDERS = tag("ladders");

		public static TagKey<Block> tag(String id) {
			return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("c", id));
		}
	}

	public static class Items {

		public static final TagKey<Item> BARRELS = tag("barrels");
		public static final TagKey<Item> BARRELS_WOODEN = tag("barrels/wooden");
		public static final TagKey<Item> BONES = tag("bones");
		public static final TagKey<Item> BOOKSHELVES = tag("bookshelves");
		public static final TagKey<Item> CHESTS = tag("chests");
		public static final TagKey<Item> CHESTS_ENDER = tag("chests/ender");
		public static final TagKey<Item> CHESTS_TRAPPED = tag("chests/trapped");
		public static final TagKey<Item> CHESTS_WOODEN = tag("chests/wooden");
		public static final TagKey<Item> COBBLESTONE = tag("cobblestone");
		public static final TagKey<Item> COBBLESTONE_NORMAL = tag("cobblestone/normal");
		public static final TagKey<Item> COBBLESTONE_INFESTED = tag("cobblestone/infested");
		public static final TagKey<Item> COBBLESTONE_MOSSY = tag("cobblestone/mossy");
		public static final TagKey<Item> COBBLESTONE_DEEPSLATE = tag("cobblestone/deepslate");
		public static final TagKey<Item> CROPS = tag("crops");
		public static final TagKey<Item> CROPS_BEETROOT = tag("crops/beetroot");
		public static final TagKey<Item> CROPS_CARROT = tag("crops/carrot");
		public static final TagKey<Item> CROPS_NETHER_WART = tag("crops/nether_wart");
		public static final TagKey<Item> CROPS_POTATO = tag("crops/potato");
		public static final TagKey<Item> CROPS_WHEAT = tag("crops/wheat");
		public static final TagKey<Item> DUSTS = tag("dusts");
		public static final TagKey<Item> DUSTS_PRISMARINE = tag("dusts/prismarine");
		public static final TagKey<Item> DUSTS_REDSTONE = tag("dusts/redstone");
		public static final TagKey<Item> DUSTS_GLOWSTONE = tag("dusts/glowstone");

		public static final TagKey<Item> DYES = tag("dyes");
		public static final TagKey<Item> DYES_BLACK = tag("dyes/black");
		public static final TagKey<Item> DYES_RED = tag("dyes/red");
		public static final TagKey<Item> DYES_GREEN = tag("dyes/green");
		public static final TagKey<Item> DYES_BROWN = tag("dyes/brown");
		public static final TagKey<Item> DYES_BLUE = tag("dyes/blue");
		public static final TagKey<Item> DYES_PURPLE = tag("dyes/purple");
		public static final TagKey<Item> DYES_CYAN = tag("dyes/cyan");
		public static final TagKey<Item> DYES_LIGHT_GRAY = tag("dyes/light_gray");
		public static final TagKey<Item> DYES_GRAY = tag("dyes/gray");
		public static final TagKey<Item> DYES_PINK = tag("dyes/pink");
		public static final TagKey<Item> DYES_LIME = tag("dyes/lime");
		public static final TagKey<Item> DYES_YELLOW = tag("dyes/yellow");
		public static final TagKey<Item> DYES_LIGHT_BLUE = tag("dyes/light_blue");
		public static final TagKey<Item> DYES_MAGENTA = tag("dyes/magenta");
		public static final TagKey<Item> DYES_ORANGE = tag("dyes/orange");
		public static final TagKey<Item> DYES_WHITE = tag("dyes/white");

		public static final TagKey<Item> EGGS = tag("eggs");
		public static final TagKey<Item> ENCHANTING_FUELS = tag(
				"enchanting_fuels"/*, Set.of(net.minecraft.world.item.Items.LAPIS_LAZULI)*/);
		public static final TagKey<Item> END_STONES = tag("end_stones");
		public static final TagKey<Item> ENDER_PEARLS = tag("ender_pearls");
		public static final TagKey<Item> FEATHERS = tag("feathers");
		public static final TagKey<Item> FENCE_GATES = tag("fence_gates");
		public static final TagKey<Item> FENCE_GATES_WOODEN = tag("fence_gates/wooden");
		public static final TagKey<Item> FENCES = tag("fences");
		public static final TagKey<Item> FENCES_NETHER_BRICK = tag("fences/nether_brick");
		public static final TagKey<Item> FENCES_WOODEN = tag("fences/wooden");
		public static final TagKey<Item> GEMS = tag("gems");
		public static final TagKey<Item> GEMS_DIAMOND = tag("gems/diamond");
		public static final TagKey<Item> GEMS_EMERALD = tag("gems/emerald");
		public static final TagKey<Item> GEMS_AMETHYST = tag("gems/amethyst");
		public static final TagKey<Item> GEMS_LAPIS = tag("gems/lapis");
		public static final TagKey<Item> GEMS_PRISMARINE = tag("gems/prismarine");
		public static final TagKey<Item> GEMS_QUARTZ = tag("gems/quartz");

		public static final TagKey<Item> GLASS = tag("glass");
		public static final TagKey<Item> GLASS_BLACK = tag("glass/black");
		public static final TagKey<Item> GLASS_BLUE = tag("glass/blue");
		public static final TagKey<Item> GLASS_BROWN = tag("glass/brown");
		public static final TagKey<Item> GLASS_COLORLESS = tag("glass/colorless");
		public static final TagKey<Item> GLASS_CYAN = tag("glass/cyan");
		public static final TagKey<Item> GLASS_GRAY = tag("glass/gray");
		public static final TagKey<Item> GLASS_GREEN = tag("glass/green");
		public static final TagKey<Item> GLASS_LIGHT_BLUE = tag("glass/light_blue");
		public static final TagKey<Item> GLASS_LIGHT_GRAY = tag("glass/light_gray");
		public static final TagKey<Item> GLASS_LIME = tag("glass/lime");
		public static final TagKey<Item> GLASS_MAGENTA = tag("glass/magenta");
		public static final TagKey<Item> GLASS_ORANGE = tag("glass/orange");
		public static final TagKey<Item> GLASS_PINK = tag("glass/pink");
		public static final TagKey<Item> GLASS_PURPLE = tag("glass/purple");
		public static final TagKey<Item> GLASS_RED = tag("glass/red");
		/**
		 * Glass which is made from sand and only minor additional ingredients like dyes
		 */
		public static final TagKey<Item> GLASS_SILICA = tag("glass/silica");
		public static final TagKey<Item> GLASS_TINTED = tag("glass/tinted");
		public static final TagKey<Item> GLASS_WHITE = tag("glass/white");
		public static final TagKey<Item> GLASS_YELLOW = tag("glass/yellow");

		public static final TagKey<Item> GLASS_PANES = tag("glass_panes");
		public static final TagKey<Item> GLASS_PANES_BLACK = tag("glass_panes/black");
		public static final TagKey<Item> GLASS_PANES_BLUE = tag("glass_panes/blue");
		public static final TagKey<Item> GLASS_PANES_BROWN = tag("glass_panes/brown");
		public static final TagKey<Item> GLASS_PANES_COLORLESS = tag("glass_panes/colorless");
		public static final TagKey<Item> GLASS_PANES_CYAN = tag("glass_panes/cyan");
		public static final TagKey<Item> GLASS_PANES_GRAY = tag("glass_panes/gray");
		public static final TagKey<Item> GLASS_PANES_GREEN = tag("glass_panes/green");
		public static final TagKey<Item> GLASS_PANES_LIGHT_BLUE = tag("glass_panes/light_blue");
		public static final TagKey<Item> GLASS_PANES_LIGHT_GRAY = tag("glass_panes/light_gray");
		public static final TagKey<Item> GLASS_PANES_LIME = tag("glass_panes/lime");
		public static final TagKey<Item> GLASS_PANES_MAGENTA = tag("glass_panes/magenta");
		public static final TagKey<Item> GLASS_PANES_ORANGE = tag("glass_panes/orange");
		public static final TagKey<Item> GLASS_PANES_PINK = tag("glass_panes/pink");
		public static final TagKey<Item> GLASS_PANES_PURPLE = tag("glass_panes/purple");
		public static final TagKey<Item> GLASS_PANES_RED = tag("glass_panes/red");
		public static final TagKey<Item> GLASS_PANES_WHITE = tag("glass_panes/white");
		public static final TagKey<Item> GLASS_PANES_YELLOW = tag("glass_panes/yellow");

		public static final TagKey<Item> GRAVEL = tag("gravel");
		public static final TagKey<Item> GUNPOWDER = tag("gunpowder");
		public static final TagKey<Item> HEADS = tag("heads");
		public static final TagKey<Item> INGOTS = tag("ingots");
		public static final TagKey<Item> INGOTS_BRICK = tag("ingots/brick");
		public static final TagKey<Item> INGOTS_COPPER = tag("ingots/copper");
		public static final TagKey<Item> INGOTS_GOLD = tag("ingots/gold");
		public static final TagKey<Item> INGOTS_IRON = tag("ingots/iron");
		public static final TagKey<Item> INGOTS_NETHERITE = tag("ingots/netherite");
		public static final TagKey<Item> INGOTS_NETHER_BRICK = tag("ingots/nether_brick");
		public static final TagKey<Item> LEATHER = tag("leather");
		public static final TagKey<Item> MUSHROOMS = tag("mushrooms");
		public static final TagKey<Item> NETHER_STARS = tag("nether_stars");
		public static final TagKey<Item> NETHERRACK = tag("netherrack");
		public static final TagKey<Item> NUGGETS = tag("nuggets");
		public static final TagKey<Item> NUGGETS_GOLD = tag("nuggets/gold");
		public static final TagKey<Item> NUGGETS_IRON = tag("nuggets/iron");
		public static final TagKey<Item> OBSIDIAN = tag("obsidian");
		/**
		 * Blocks which are often replaced by deepslate ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_DEEPSLATE}, during world generation
		 */
		public static final TagKey<Item> ORE_BEARING_GROUND_DEEPSLATE = tag("ore_bearing_ground/deepslate");
		/**
		 * Blocks which are often replaced by netherrack ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_NETHERRACK}, during world generation
		 */
		public static final TagKey<Item> ORE_BEARING_GROUND_NETHERRACK = tag("ore_bearing_ground/netherrack");
		/**
		 * Blocks which are often replaced by stone ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_STONE}, during world generation
		 */
		public static final TagKey<Item> ORE_BEARING_GROUND_STONE = tag("ore_bearing_ground/stone");
		/**
		 * Ores which on average result in more than one resource worth of materials
		 */
		public static final TagKey<Item> ORE_RATES_DENSE = tag("ore_rates/dense");
		/**
		 * Ores which on average result in one resource worth of materials
		 */
		public static final TagKey<Item> ORE_RATES_SINGULAR = tag("ore_rates/singular");
		/**
		 * Ores which on average result in less than one resource worth of materials
		 */
		public static final TagKey<Item> ORE_RATES_SPARSE = tag("ore_rates/sparse");
		public static final TagKey<Item> ORES = tag("ores");
		public static final TagKey<Item> ORES_COAL = tag("ores/coal");
		public static final TagKey<Item> ORES_COPPER = tag("ores/copper");
		public static final TagKey<Item> ORES_DIAMOND = tag("ores/diamond");
		public static final TagKey<Item> ORES_EMERALD = tag("ores/emerald");
		public static final TagKey<Item> ORES_GOLD = tag("ores/gold");
		public static final TagKey<Item> ORES_IRON = tag("ores/iron");
		public static final TagKey<Item> ORES_LAPIS = tag("ores/lapis");
		public static final TagKey<Item> ORES_NETHERITE_SCRAP = tag("ores/netherite_scrap");
		public static final TagKey<Item> ORES_QUARTZ = tag("ores/quartz");
		public static final TagKey<Item> ORES_REDSTONE = tag("ores/redstone");
		/**
		 * Ores in deepslate (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_DEEPSLATE}) which could logically use deepslate as recipe input or output
		 */
		public static final TagKey<Item> ORES_IN_GROUND_DEEPSLATE = tag("ores_in_ground/deepslate");
		/**
		 * Ores in netherrack (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_NETHERRACK}) which could logically use netherrack as recipe input or output
		 */
		public static final TagKey<Item> ORES_IN_GROUND_NETHERRACK = tag("ores_in_ground/netherrack");
		/**
		 * Ores in stone (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_STONE}) which could logically use stone as recipe input or output
		 */
		public static final TagKey<Item> ORES_IN_GROUND_STONE = tag("ores_in_ground/stone");
		public static final TagKey<Item> RAW_MATERIALS = tag("raw_materials");
		public static final TagKey<Item> RAW_MATERIALS_COPPER = tag("raw_materials/copper");
		public static final TagKey<Item> RAW_MATERIALS_GOLD = tag("raw_materials/gold");
		public static final TagKey<Item> RAW_MATERIALS_IRON = tag("raw_materials/iron");
		public static final TagKey<Item> RODS = tag("rods");
		public static final TagKey<Item> RODS_BLAZE = tag("rods/blaze");
		public static final TagKey<Item> RODS_WOODEN = tag("rods/wooden");

		public static final TagKey<Item> SAND = tag("sand");
		public static final TagKey<Item> SAND_COLORLESS = tag("sand/colorless");
		public static final TagKey<Item> SAND_RED = tag("sand/red");

		public static final TagKey<Item> SANDSTONE = tag("sandstone");
		public static final TagKey<Item> SEEDS = tag("seeds");
		public static final TagKey<Item> SEEDS_BEETROOT = tag("seeds/beetroot");
		public static final TagKey<Item> SEEDS_MELON = tag("seeds/melon");
		public static final TagKey<Item> SEEDS_PUMPKIN = tag("seeds/pumpkin");
		public static final TagKey<Item> SEEDS_WHEAT = tag("seeds/wheat");
		public static final TagKey<Item> SHEARS = tag("shears");
		public static final TagKey<Item> SLIMEBALLS = tag("slimeballs");
		public static final TagKey<Item> STAINED_GLASS = tag("stained_glass");
		public static final TagKey<Item> STAINED_GLASS_PANES = tag("stained_glass_panes");
		public static final TagKey<Item> STONE = tag("stone");
		public static final TagKey<Item> STORAGE_BLOCKS = tag("storage_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_AMETHYST = tag("storage_blocks/amethyst");
		public static final TagKey<Item> STORAGE_BLOCKS_COAL = tag("storage_blocks/coal");
		public static final TagKey<Item> STORAGE_BLOCKS_COPPER = tag("storage_blocks/copper");
		public static final TagKey<Item> STORAGE_BLOCKS_DIAMOND = tag("storage_blocks/diamond");
		public static final TagKey<Item> STORAGE_BLOCKS_EMERALD = tag("storage_blocks/emerald");
		public static final TagKey<Item> STORAGE_BLOCKS_GOLD = tag("storage_blocks/gold");
		public static final TagKey<Item> STORAGE_BLOCKS_IRON = tag("storage_blocks/iron");
		public static final TagKey<Item> STORAGE_BLOCKS_LAPIS = tag("storage_blocks/lapis");
		public static final TagKey<Item> STORAGE_BLOCKS_NETHERITE = tag("storage_blocks/netherite");
		public static final TagKey<Item> STORAGE_BLOCKS_QUARTZ = tag("storage_blocks/quartz");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_COPPER = tag("storage_blocks/raw_copper");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_GOLD = tag("storage_blocks/raw_gold");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_IRON = tag("storage_blocks/raw_iron");
		public static final TagKey<Item> STORAGE_BLOCKS_REDSTONE = tag("storage_blocks/redstone");
		public static final TagKey<Item> STRING = tag("string");
		/**
		 * A tag containing all existing tools.
		 */
		public static final TagKey<Item> TOOLS = tag("tools");
		/**
		 * A tag containing all existing swords.
		 */
		public static final TagKey<Item> TOOLS_SWORDS = tag("tools/swords");
		/**
		 * A tag containing all existing axes.
		 */
		public static final TagKey<Item> TOOLS_AXES = tag("tools/axes");
		/**
		 * A tag containing all existing pickaxes.
		 */
		public static final TagKey<Item> TOOLS_PICKAXES = tag("tools/pickaxes");
		/**
		 * A tag containing all existing shovels.
		 */
		public static final TagKey<Item> TOOLS_SHOVELS = tag("tools/shovels");
		/**
		 * A tag containing all existing hoes.
		 */
		public static final TagKey<Item> TOOLS_HOES = tag("tools/hoes");
		/**
		 * A tag containing all existing shields.
		 */
		public static final TagKey<Item> TOOLS_SHIELDS = tag("tools/shields");
		/**
		 * A tag containing all existing bows.
		 */
		public static final TagKey<Item> TOOLS_BOWS = tag("tools/bows");
		/**
		 * A tag containing all existing crossbows.
		 */
		public static final TagKey<Item> TOOLS_CROSSBOWS = tag("tools/crossbows");
		/**
		 * A tag containing all existing fishing rods.
		 */
		public static final TagKey<Item> TOOLS_FISHING_RODS = tag("tools/fishing_rods");
		/**
		 * A tag containing all existing tridents.
		 */
		public static final TagKey<Item> TOOLS_TRIDENTS = tag("tools/tridents");
		/**
		 * A tag containing all existing armors.
		 */
		public static final TagKey<Item> ARMORS = tag("armors");
		/**
		 * A tag containing all existing helmets.
		 */
		public static final TagKey<Item> ARMORS_HELMETS = tag("armors/helmets");
		/**
		 * A tag containing all chestplates.
		 */
		public static final TagKey<Item> ARMORS_CHESTPLATES = tag("armors/chestplates");
		/**
		 * A tag containing all existing leggings.
		 */
		public static final TagKey<Item> ARMORS_LEGGINGS = tag("armors/leggings");
		/**
		 * A tag containing all existing boots.
		 */
		public static final TagKey<Item> ARMORS_BOOTS = tag("armors/boots");

		public static final TagKey<Item> BUCKETS_WATER = tag("buckets/water");
		public static final TagKey<Item> BUCKETS_LAVA = tag("buckets/lava");
		public static final TagKey<Item> BUCKETS_MILK = tag("buckets/milk");
		public static final TagKey<Item> BUCKETS_POWDER_SNOW = tag("buckets/powder_snow");
		public static final TagKey<Item> BUCKETS = tag("buckets");

		public static final TagKey<Item> LADDERS = tag("ladders");

		public static TagKey<Item> tag(String id) {
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", id));
		}
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> BOSSES = tag("bosses");

		/**
		 * Mobs that are dragons, draconic, or close enough to being dragons
		 */
		public static final TagKey<EntityType<?>> DRAGONS = tag("dragons");

		public static final TagKey<EntityType<?>> GOLEMS = tag("golems");

		/**
		 * Mobs that are build with blocks and then summoned
		 */
		public static final TagKey<EntityType<?>> BUILDABLE_MOBS = tag("buildable_mobs");

		public static final TagKey<EntityType<?>> BIG_NOSES = tag("big_noses");
		public static final TagKey<EntityType<?>> ILLAGERS = tag("illagers");

		/**
		 * Mobs that are logically composed of an element
		 * If your mob is made of a different element, and you want cross-compatibility between mods, feel free to make a new tag
		 * If your mob is made of a combination of elements, feel free to add all appropriate tags to it
		 */
		public static final TagKey<EntityType<?>> ELEMENTAL = tag("elemental");
		public static final TagKey<EntityType<?>> ELEMENTAL_FIRE = tag("elemental/fire");
		public static final TagKey<EntityType<?>> ELEMENTAL_ICE = tag("elemental/ice");
		public static final TagKey<EntityType<?>> ELEMENTAL_METAL = tag("elemental/metal");

		/**
		 * Attack entities that have logical elemental properties
		 * If your attack entity is made of a different element, and you want cross-compatibility between mods, feel free to make a new tag
		 * If your attack entity is made of a combination of elements, feel free to add all appropriate tags to it
		 */
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS = tag("elemental_attacks");
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS_ELECTRIC = tag("elemental_attacks/electric");
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS_FIRE = tag("elemental_attacks/fire");
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS_ICE = tag("elemental_attacks/ice");

		public static final TagKey<EntityType<?>> ARTHROPODS = tag("animals/arthropods");

		public static final TagKey<EntityType<?>> AVIANS = tag("avians");
		public static final TagKey<EntityType<?>> AVIANS_FOWLS = tag("avians/fowls");

		//Mobs which are living in the water
		public static final TagKey<EntityType<?>> AQUATIC = tag("aquatic");
		public static final TagKey<EntityType<?>> FISH = tag("aquatic/fish");
		public static final TagKey<EntityType<?>> CEPHALOPODS = tag("aquatic/cephalopods");
		public static final TagKey<EntityType<?>> GUARDIANS = tag("aquatic/guardians");

		public static final TagKey<EntityType<?>> REPTILES = tag("reptiles");

		public static final TagKey<EntityType<?>> MAMMALS = tag("mammals");
		public static final TagKey<EntityType<?>> MAMMALS_BOVINES = tag("mammals/bovines");
		public static final TagKey<EntityType<?>> MAMMALS_BOVINES_CATTLE = tag("mammals/bovines_cattle");
		public static final TagKey<EntityType<?>> MAMMALS_CAMELIDS = tag("mammals/camelids");
		public static final TagKey<EntityType<?>> MAMMALS_CANIDS = tag("mammals/canids");
		public static final TagKey<EntityType<?>> MAMMALS_CAPRINES = tag("mammals/caprines");
		public static final TagKey<EntityType<?>> MAMMALS_EQUINES = tag("mammals/equines");
		public static final TagKey<EntityType<?>> MAMMALS_FELINES = tag("mammals/felines");
		public static final TagKey<EntityType<?>> MAMMALS_SWINES = tag("mammals/swines");
		public static final TagKey<EntityType<?>> MAMMALS_URSIDS = tag("mammals/ursids");

		public static final TagKey<EntityType<?>> GHOSTS = tag("ghosts");

		public static final TagKey<EntityType<?>> MILKABLE = tag("milkable");
		public static final TagKey<EntityType<?>> MUSHROOM_COWS = tag("mushroom_cows");

		public static final TagKey<EntityType<?>> BLIND_MOBS = tag("blind_mobs");

		public static final TagKey<EntityType<?>> FLYING = tag("flying");
		public static final TagKey<EntityType<?>> LAND = tag("land");

		/**
		 * Mobs that prefer to live in lava
		 */
		public static final TagKey<EntityType<?>> VOLCANIC = tag("volcanic");

		public static final TagKey<EntityType<?>> HELL_MOBS = tag("hell_mobs");

		/**
		 * Mobs that are meant to be antagonistic to the player
		 */
		public static final TagKey<EntityType<?>> ENEMIES = tag("enemies");
		public static final TagKey<EntityType<?>> CREEPERS = tag("creepers");

		public static final TagKey<EntityType<?>> UNDEAD = tag("undead");
		public static final TagKey<EntityType<?>> SKELETONS = tag("undead/skeletons");
		public static final TagKey<EntityType<?>> ZOMBIES = tag("undead/zombies");

		/**
		 * Mobs that simulate an intelligent, independent relationship of some sort with the player
		 */
		public static final TagKey<EntityType<?>> NPC = tag("npc");

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class Biomes {
		// Tags specifying aquatic biomes

		// Tags specifying that a biome is a variant of a normal biome
		public static final TagKey<Biome> RARE = tag("rare");
		public static final TagKey<Biome> PLATEAUS = tag("plateaus"); // plateau variants of other biomes
		public static final TagKey<Biome> HILLS = tag("hills"); // hills variants of other biomes

		// Tags specifying aquatic biomes
		public static final TagKey<Biome> OCEANS = tag("water/oceans");
		public static final TagKey<Biome> SHALLOW_OCEANS = tag("water/oceans/shallow");
		public static final TagKey<Biome> DEEP_OCEANS = tag("water/oceans/deep");
		public static final TagKey<Biome> RIVERS = tag("water/rivers");
		public static final TagKey<Biome> WATER = tag("water");

		// Tags specifying generic types of biomes
		public static final TagKey<Biome> BADLANDS = tag("badlands");
		public static final TagKey<Biome> BEACHES = tag("beaches");
		public static final TagKey<Biome> DESERTS = tag("deserts");
		public static final TagKey<Biome> FORESTS = tag("forests");
		public static final TagKey<Biome> BIRCH_FORESTS = tag("forests/birch");
		public static final TagKey<Biome> DARK_FORESTS = tag("forests/dark");
		public static final TagKey<Biome> JUNGLE_FORESTS = tag("forests/jungles");
		public static final TagKey<Biome> BAMBOO_JUNGLE_FORESTS = tag("forests/jungles/bamboo");
		public static final TagKey<Biome> NETHER_FORESTS = tag("forests/nether");
		public static final TagKey<Biome> OAK_FORESTS = tag("forests/oak");
		public static final TagKey<Biome> TAIGA_FORESTS = tag("forests/taigas");
		public static final TagKey<Biome> GRASSLANDS = tag("grasslands"); // plains and savannas
		public static final TagKey<Biome> MUSHROOM = tag("mushroom");
		public static final TagKey<Biome> MOUNTAINS = tag("mountains");
		public static final TagKey<Biome> PEAKS = tag("peaks");
		public static final TagKey<Biome> PLAINS = tag("plains");
		public static final TagKey<Biome> SAVANNAS = tag("savannas");
		public static final TagKey<Biome> SNOWY = tag("snowy"); // indicates that a biome has snow and/or ice
		public static final TagKey<Biome> SLOPES = tag("slopes");
		public static final TagKey<Biome> SWAMPS = tag("swamps");
		public static final TagKey<Biome> VOIDS = tag("voids");

		// Tags specifying that a biome generates in a vanilla dimension. Specifying none of these indicates that the biome only generates in a modded dimension
		public static final TagKey<Biome> OVERWORLD = tag("overworld");
		public static final TagKey<Biome> OVERWORLD_SURFACE = tag("overworld/surface");
		public static final TagKey<Biome> OVERWORLD_UNDERGROUND = tag("overworld/underground");
		public static final TagKey<Biome> NETHER = tag("nether");
		public static final TagKey<Biome> END = tag("end");

		public static final TagKey<Biome> IS_HOT = tag("is_hot");
		public static final TagKey<Biome> IS_HOT_OVERWORLD = tag("is_hot/overworld");
		public static final TagKey<Biome> IS_HOT_NETHER = tag("is_hot/nether");
		public static final TagKey<Biome> IS_HOT_END = tag("is_hot/end");

		public static final TagKey<Biome> IS_COLD = tag("is_cold");
		public static final TagKey<Biome> IS_COLD_OVERWORLD = tag("is_cold/overworld");
		public static final TagKey<Biome> IS_COLD_NETHER = tag("is_cold/nether");
		public static final TagKey<Biome> IS_COLD_END = tag("is_cold/end");

		public static final TagKey<Biome> IS_SPARSE = tag("is_sparse");
		public static final TagKey<Biome> IS_SPARSE_OVERWORLD = tag("is_sparse/overworld");
		public static final TagKey<Biome> IS_SPARSE_NETHER = tag("is_sparse/nether");
		public static final TagKey<Biome> IS_SPARSE_END = tag("is_sparse/end");
		public static final TagKey<Biome> IS_DENSE = tag("is_dense");
		public static final TagKey<Biome> IS_DENSE_OVERWORLD = tag("is_dense/overworld");
		public static final TagKey<Biome> IS_DENSE_NETHER = tag("is_dense/nether");
		public static final TagKey<Biome> IS_DENSE_END = tag("is_dense/end");

		public static final TagKey<Biome> IS_WET = tag("is_wet");
		public static final TagKey<Biome> IS_WET_OVERWORLD = tag("is_wet/overworld");
		public static final TagKey<Biome> IS_WET_NETHER = tag("is_wet/nether");
		public static final TagKey<Biome> IS_WET_END = tag("is_wet/end");
		public static final TagKey<Biome> IS_DRY = tag("is_dry");
		public static final TagKey<Biome> IS_DRY_OVERWORLD = tag("is_dry/overworld");
		public static final TagKey<Biome> IS_DRY_NETHER = tag("is_dry/nether");
		public static final TagKey<Biome> IS_DRY_END = tag("is_dry/end");

		public static final TagKey<Biome> IS_CONIFEROUS = tag("is_coniferous");

		public static final TagKey<Biome> IS_SPOOKY = tag("is_spooky");
		public static final TagKey<Biome> IS_DEAD = tag("is_dead");
		public static final TagKey<Biome> IS_LUSH = tag("is_lush");
		public static final TagKey<Biome> IS_MUSHROOM = tag("is_mushroom");
		public static final TagKey<Biome> IS_MAGICAL = tag("is_magical");
		public static final TagKey<Biome> IS_RARE = tag("is_rare");
		public static final TagKey<Biome> IS_PLATEAU = tag("is_plateau");
		public static final TagKey<Biome> IS_MODIFIED = tag("is_modified");

		public static final TagKey<Biome> IS_WATER = tag("is_water");

		public static final TagKey<Biome> IS_PLAINS = tag("is_plains");
		public static final TagKey<Biome> IS_SWAMP = tag("is_swamp");
		public static final TagKey<Biome> IS_SANDY = tag("is_sandy");
		public static final TagKey<Biome> IS_SNOWY = tag("is_snowy");
		public static final TagKey<Biome> IS_WASTELAND = tag("is_wasteland");
		public static final TagKey<Biome> IS_VOID = tag("is_void");
		public static final TagKey<Biome> IS_UNDERGROUND = tag("is_underground");

		public static final TagKey<Biome> IS_CAVE = tag("is_cave");
		public static final TagKey<Biome> IS_PEAK = tag("is_peak");
		public static final TagKey<Biome> IS_SLOPE = tag("is_slope");
		public static final TagKey<Biome> IS_MOUNTAIN = tag("is_mountain");

		public static final TagKey<Biome> IS_GRASSLAND = tag("is_grassland");
		public static final TagKey<Biome> IS_ICY = tag("is_icy");
		public static final TagKey<Biome> IS_DESERT = tag("is_desert");
		public static final TagKey<Biome> IS_OUTER_END = tag("is_outer_end");

		public static final TagKey<Biome> WITH_DEFAULT_MONSTER_SPAWNS = tag("with_default_monster_spawns");
		public static final TagKey<Biome> WITHOUT_DEFAULT_MONSTER_SPAWNS = tag("without_default_monster_spawns");

		private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class NoiseSettings {
		public static final TagKey<NoiseGeneratorSettings> AMPLIFIED = tag("amplified");
		public static final TagKey<NoiseGeneratorSettings> CAVES = tag("caves");
		public static final TagKey<NoiseGeneratorSettings> END = tag("end");
		public static final TagKey<NoiseGeneratorSettings> FLOATING_ISLANDS = tag("floating_islands");
		public static final TagKey<NoiseGeneratorSettings> NETHER = tag("nether");
		public static final TagKey<NoiseGeneratorSettings> OVERWORLD = tag("overworld");

		private static TagKey<NoiseGeneratorSettings> tag(String name) {
			return TagKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class DimensionTypes {
		public static final TagKey<DimensionType> END = tag("end");
		public static final TagKey<DimensionType> NETHER = tag("nether");
		public static final TagKey<DimensionType> OVERWORLD = tag("overworld");
		public static final TagKey<DimensionType> OVERWORLD_CAVES = tag("overworld/caves");

		private static TagKey<DimensionType> tag(String name) {
			return TagKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class MobEffects {
		public static final TagKey<MobEffect> NEGATIVES = tag("negatives");
		public static final TagKey<MobEffect> POSITIVES = tag("positives");

		public static final TagKey<MobEffect> INSTANT = tag("instant");
		public static final TagKey<MobEffect> LASTING = tag("lasting");

		private static TagKey<MobEffect> tag(String name) {
			return TagKey.create(Registry.MOB_EFFECT_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class Enchantments {
		public static final TagKey<Enchantment> TOOL_ENCHANTMENTS = tag("tools");
		public static final TagKey<Enchantment> PICKAXE_ENCHANTMENTS = tag("tools/pickaxes");
		public static final TagKey<Enchantment> AXE_ENCHANTMENTS = tag("tools/axes");
		public static final TagKey<Enchantment> HOE_ENCHANTMENTS = tag("tools/hoes");
		public static final TagKey<Enchantment> SHOVEL_ENCHANTMENTS = tag("tools/shovels");

		public static final TagKey<Enchantment> WEAPON_ENCHANTMENTS = tag("weapons");
		public static final TagKey<Enchantment> SWORD_ENCHANTMENTS = tag("weapons/sword");

		public static final TagKey<Enchantment> BOW_ENCHANTMENTS = tag("range/bows");
		public static final TagKey<Enchantment> CROSSBOW_ENCHANTMENTS = tag("range/crossbows");
		public static final TagKey<Enchantment> TRIDENT_ENCHANTMENTS = tag("range/tridents");

		public static final TagKey<Enchantment> CURSES = tag("curses");

		public static final TagKey<Enchantment> ARMOR_ENCHANTMENTS = tag("armors");
		public static final TagKey<Enchantment> HEAD_ENCHANTMENTS = tag("armors/head");
		public static final TagKey<Enchantment> CHEST_ENCHANTMENTS = tag("armors/chest");
		public static final TagKey<Enchantment> LEGS_ENCHANTMENTS = tag("armors/legs");
		public static final TagKey<Enchantment> FEET_ENCHANTMENTS = tag("armors/feet");

		private static TagKey<Enchantment> tag(String name) {
			return TagKey.create(Registry.ENCHANTMENT_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class Potions {
		public static final TagKey<Potion> NEGATIVE_POTIONS = tag("negatives");
		public static final TagKey<Potion> POSITIVE_POTIONS = tag("positives");

		public static final TagKey<Potion> INSTANT_POTIONS = tag("instant");
		public static final TagKey<Potion> LASTING_POTIONS = tag("lasting");

		private static TagKey<Potion> tag(String name) {
			return TagKey.create(Registry.POTION_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class PaintingVariants {
		public static final TagKey<PaintingVariant> ONE_X_ONE = tag("1x1");
		public static final TagKey<PaintingVariant> TWO_X_ONE = tag("2x1");
		public static final TagKey<PaintingVariant> ONE_X_TWO = tag("1x2");
		public static final TagKey<PaintingVariant> TWO_X_TWO = tag("2x2");
		public static final TagKey<PaintingVariant> FOUR_X_TWO = tag("4x2");
		public static final TagKey<PaintingVariant> FOUR_X_THREE = tag("4x3");
		public static final TagKey<PaintingVariant> FOUR_X_FOUR = tag("4x4");

		private static TagKey<PaintingVariant> tag(String name) {
			return TagKey.create(Registry.PAINTING_VARIANT_REGISTRY, new ResourceLocation("c", name));
		}
	}

}
