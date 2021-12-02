/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vampirestudios.vampirelib;

import java.util.Locale;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import io.github.vampirestudios.vampirelib.init.VTags;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;

import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BARRELS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BARRELS_WOODEN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS_ENDER;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS_TRAPPED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS_WOODEN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.COBBLESTONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.DIRT;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.END_STONES;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.FENCES_NETHER_BRICK;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.FENCES_WOODEN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.FENCE_GATES_WOODEN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GLASS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GLASS_COLORLESS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GLASS_PANES;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GLASS_PANES_COLORLESS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GLASS_SILICA;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GLASS_TINTED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.GRAVEL;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.NETHERRACK;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.OBSIDIAN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_COAL;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_COPPER;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_DIAMOND;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_EMERALD;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_GOLD;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_IN_GROUND_DEEPSLATE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_IN_GROUND_NETHERRACK;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_IN_GROUND_STONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_IRON;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_LAPIS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_NETHERITE_SCRAP;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_QUARTZ;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORES_REDSTONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORE_BEARING_GROUND_DEEPSLATE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORE_BEARING_GROUND_NETHERRACK;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORE_BEARING_GROUND_STONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORE_RATES_DENSE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORE_RATES_SINGULAR;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.ORE_RATES_SPARSE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.SAND;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.SANDSTONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.SAND_COLORLESS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.SAND_RED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STAINED_GLASS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STAINED_GLASS_PANES;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_AMETHYST;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_COAL;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_COPPER;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_DIAMOND;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_EMERALD;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_GOLD;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_IRON;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_LAPIS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_NETHERITE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_QUARTZ;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_RAW_COPPER;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_RAW_GOLD;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_RAW_IRON;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.STORAGE_BLOCKS_REDSTONE;
import static net.minecraft.tags.BlockTags.FENCES;
import static net.minecraft.tags.BlockTags.FENCE_GATES;

public class VampireLibDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
//		dataGenerator.addProvider(WoodTypeBlockStateDefinitionProvider::new);
//		dataGenerator.addProvider(WoodTypeEnglishLanguageProvider::new);
//		dataGenerator.addProvider(WoodTypeFrenchLanguageProvider::new);
//		dataGenerator.addProvider(WoodTypeRecipeProvider::new);
		TestBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(TestBlockTagsProvider::new);
		dataGenerator.addProvider(new TestItemTagsProvider(dataGenerator, blockTagsProvider));
	}

	private static class WoodTypeBlockStateDefinitionProvider extends FabricBlockStateDefinitionProvider {
		private WoodTypeBlockStateDefinitionProvider(FabricDataGenerator generator) {
			super(generator);
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_MOD_WOOD);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_MOD_WOOD1);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_MOD_WOOD2);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_MOD_WOOD3);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_MOD_WOOD4);
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerator) {

		}

		private void generateWoodTypeAssets(BlockModelGenerators blockStateModelGenerator, WoodRegistry woodRegistry) {
			woodRegistry.generateModels(blockStateModelGenerator);
		}
	}

	private static class WoodTypeEnglishLanguageProvider extends FabricLanguageProvider {
		private WoodTypeEnglishLanguageProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, "en_us");
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD1);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD2);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD3);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD4);
		}

		private void addWoodTypeLang(WoodRegistry woodRegistry) {
			woodRegistry.generateLang(this);
		}
	}

	private static class WoodTypeFrenchLanguageProvider extends FabricLanguageProvider {
		private WoodTypeFrenchLanguageProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, "fr_fr");
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD1);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD2);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD3);
			addWoodTypeLang(VampireLib.TEST_MOD_WOOD4);
		}

		private void addWoodTypeLang(WoodRegistry woodRegistry) {
			woodRegistry.generateLang(this);
		}
	}

	private static class WoodTypeRecipeProvider extends FabricRecipesProvider {
		private WoodTypeRecipeProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
			generateWoodTypeRecipes(exporter, VampireLib.TEST_MOD_WOOD);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_MOD_WOOD1);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_MOD_WOOD2);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_MOD_WOOD3);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_MOD_WOOD4);
		}

		private void generateWoodTypeRecipes(Consumer<FinishedRecipe> exporter, WoodRegistry woodRegistry) {
			woodRegistry.generateRecipes(exporter);
		}
	}

	private static class TestBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
		private TestBlockTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
			tag(BARRELS).addTag(BARRELS_WOODEN);
			tag(BARRELS_WOODEN).add(Blocks.BARREL);
			tag(CHESTS_ENDER).add(Blocks.ENDER_CHEST);
			tag(CHESTS_TRAPPED).add(Blocks.TRAPPED_CHEST);
			tag(CHESTS_WOODEN).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
			tag(CHESTS).addTags(CHESTS_ENDER, CHESTS_TRAPPED, CHESTS_WOODEN);
			tag(COBBLESTONE).add(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLED_DEEPSLATE);
			tag(DIRT).add(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.ROOTED_DIRT);
			tag(END_STONES).add(Blocks.END_STONE);
			tag(ENDERMAN_PLACE_ON_BLACKLIST);
			tag(FENCE_GATES).addTags(FENCE_GATES_WOODEN);
			tag(FENCE_GATES_WOODEN).add(Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE);
			tag(FENCES).addTags(FENCES_NETHER_BRICK, FENCES_WOODEN);
			tag(FENCES_NETHER_BRICK).add(Blocks.NETHER_BRICK_FENCE);
			tag(FENCES_WOODEN).addTag(BlockTags.WOODEN_FENCES);
			tag(GLASS).addTags(GLASS_COLORLESS, STAINED_GLASS, GLASS_TINTED);
			tag(GLASS_COLORLESS).add(Blocks.GLASS);
			tag(GLASS_SILICA).add(Blocks.GLASS, Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS);
			tag(GLASS_TINTED).add(Blocks.TINTED_GLASS);
			addColored(tag(STAINED_GLASS)::add, GLASS, "{color}_stained_glass");
			tag(GLASS_PANES).addTags(GLASS_PANES_COLORLESS, STAINED_GLASS_PANES);
			tag(GLASS_PANES_COLORLESS).add(Blocks.GLASS_PANE);
			addColored(tag(STAINED_GLASS_PANES)::add, GLASS_PANES, "{color}_stained_glass_pane");
			tag(GRAVEL).add(Blocks.GRAVEL);
			tag(NETHERRACK).add(Blocks.NETHERRACK);
			tag(OBSIDIAN).add(Blocks.OBSIDIAN);
			tag(ORE_BEARING_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE);
			tag(ORE_BEARING_GROUND_NETHERRACK).add(Blocks.NETHERRACK);
			tag(ORE_BEARING_GROUND_STONE).add(Blocks.STONE);
			tag(ORE_RATES_DENSE).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
			tag(ORE_RATES_SINGULAR).add(Blocks.ANCIENT_DEBRIS, Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.NETHER_QUARTZ_ORE);
			tag(ORE_RATES_SPARSE).add(Blocks.NETHER_GOLD_ORE);
			tag(ORES).addTags(ORES_COAL, ORES_COPPER, ORES_DIAMOND, ORES_EMERALD, ORES_GOLD, ORES_IRON, ORES_LAPIS, ORES_REDSTONE, ORES_QUARTZ, ORES_NETHERITE_SCRAP);
			tag(ORES_COAL).addTag(BlockTags.COAL_ORES);
			tag(ORES_COPPER).addTag(BlockTags.COPPER_ORES);
			tag(ORES_DIAMOND).addTag(BlockTags.DIAMOND_ORES);
			tag(ORES_EMERALD).addTag(BlockTags.EMERALD_ORES);
			tag(ORES_GOLD).addTag(BlockTags.GOLD_ORES);
			tag(ORES_IRON).addTag(BlockTags.IRON_ORES);
			tag(ORES_LAPIS).addTag(BlockTags.LAPIS_ORES);
			tag(ORES_QUARTZ).add(Blocks.NETHER_QUARTZ_ORE);
			tag(ORES_REDSTONE).addTag(BlockTags.REDSTONE_ORES);
			tag(ORES_NETHERITE_SCRAP).add(Blocks.ANCIENT_DEBRIS);
			tag(ORES_IN_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
			tag(ORES_IN_GROUND_NETHERRACK).add(Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE);
			tag(ORES_IN_GROUND_STONE).add(Blocks.COAL_ORE, Blocks.COPPER_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
			tag(SAND).addTags(SAND_COLORLESS, SAND_RED);
			tag(SAND_COLORLESS).add(Blocks.SAND);
			tag(SAND_RED).add(Blocks.RED_SAND);
			tag(SANDSTONE).add(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
			tag(STONE).add(Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.INFESTED_STONE, Blocks.STONE, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.INFESTED_DEEPSLATE, Blocks.TUFF);
			tag(STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_AMETHYST, STORAGE_BLOCKS_COAL, STORAGE_BLOCKS_COPPER, STORAGE_BLOCKS_DIAMOND, STORAGE_BLOCKS_EMERALD, STORAGE_BLOCKS_GOLD, STORAGE_BLOCKS_IRON, STORAGE_BLOCKS_LAPIS, STORAGE_BLOCKS_QUARTZ, STORAGE_BLOCKS_RAW_COPPER, STORAGE_BLOCKS_RAW_GOLD, STORAGE_BLOCKS_RAW_IRON, STORAGE_BLOCKS_REDSTONE, STORAGE_BLOCKS_NETHERITE);
			tag(STORAGE_BLOCKS_AMETHYST).add(Blocks.AMETHYST_BLOCK);
			tag(STORAGE_BLOCKS_COAL).add(Blocks.COAL_BLOCK);
			tag(STORAGE_BLOCKS_COPPER).add(Blocks.COPPER_BLOCK, Blocks.CUT_COPPER);
			tag(STORAGE_BLOCKS_DIAMOND).add(Blocks.DIAMOND_BLOCK);
			tag(STORAGE_BLOCKS_EMERALD).add(Blocks.EMERALD_BLOCK);
			tag(STORAGE_BLOCKS_GOLD).add(Blocks.GOLD_BLOCK);
			tag(STORAGE_BLOCKS_IRON).add(Blocks.IRON_BLOCK);
			tag(STORAGE_BLOCKS_LAPIS).add(Blocks.LAPIS_BLOCK);
			tag(STORAGE_BLOCKS_QUARTZ).add(Blocks.QUARTZ_BLOCK);
			tag(STORAGE_BLOCKS_RAW_COPPER).add(Blocks.RAW_COPPER_BLOCK);
			tag(STORAGE_BLOCKS_RAW_GOLD).add(Blocks.RAW_GOLD_BLOCK);
			tag(STORAGE_BLOCKS_RAW_IRON).add(Blocks.RAW_IRON_BLOCK);
			tag(STORAGE_BLOCKS_REDSTONE).add(Blocks.REDSTONE_BLOCK);
			tag(STORAGE_BLOCKS_NETHERITE).add(Blocks.NETHERITE_BLOCK);
		}

		private void addColored(Consumer<Block> consumer, Tag.Named<Block> group, String pattern) {
			String prefix = group.getName().getPath().toUpperCase(Locale.ENGLISH) + '_';
			for (DyeColor color : DyeColor.values()) {
				ResourceLocation key = new ResourceLocation("minecraft", pattern.replace("{color}", color.getName()));
				Tag.Named<Block> tag = getForgeTag(prefix + color.getName());
				Block block = Registry.BLOCK.get(key);
				if (block == Blocks.AIR)
					throw new IllegalStateException("Unknown vanilla block: " + key);
				tag(tag).add(block);
				consumer.accept(block);
			}
		}

		@SuppressWarnings("unchecked")
		private Tag.Named<Block> getForgeTag(String name) {
			try {
				name = name.toUpperCase(Locale.ENGLISH);
				return (Tag.Named<Block>) VTags.Blocks.class.getDeclaredField(name).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(VTags.Blocks.class.getName() + " is missing tag name: " + name);
			}
		}

	}

	private static class TestItemTagsProvider extends FabricTagProvider.ItemTagProvider {
		private TestItemTagsProvider(FabricDataGenerator dataGenerator, BlockTagProvider blockTagProvider) {
			super(dataGenerator, blockTagProvider);
		}

		@Override
		protected void generateTags() {
			copy(VTags.Blocks.BARRELS, VTags.Items.BARRELS);
			copy(VTags.Blocks.BARRELS_WOODEN, VTags.Items.BARRELS_WOODEN);
			tag(VTags.Items.BONES).add(Items.BONE);
			tag(VTags.Items.BOOKSHELVES).add(Items.BOOKSHELF);
			copy(VTags.Blocks.CHESTS, VTags.Items.CHESTS);
			copy(VTags.Blocks.CHESTS_ENDER, VTags.Items.CHESTS_ENDER);
			copy(VTags.Blocks.CHESTS_TRAPPED, VTags.Items.CHESTS_TRAPPED);
			copy(VTags.Blocks.CHESTS_WOODEN, VTags.Items.CHESTS_WOODEN);
			copy(VTags.Blocks.COBBLESTONE, VTags.Items.COBBLESTONE);
			tag(VTags.Items.CROPS).addTags(VTags.Items.CROPS_BEETROOT, VTags.Items.CROPS_CARROT, VTags.Items.CROPS_NETHER_WART, VTags.Items.CROPS_POTATO, VTags.Items.CROPS_WHEAT);
			tag(VTags.Items.CROPS_BEETROOT).add(Items.BEETROOT);
			tag(VTags.Items.CROPS_CARROT).add(Items.CARROT);
			tag(VTags.Items.CROPS_NETHER_WART).add(Items.NETHER_WART);
			tag(VTags.Items.CROPS_POTATO).add(Items.POTATO);
			tag(VTags.Items.CROPS_WHEAT).add(Items.WHEAT);
			tag(VTags.Items.DUSTS).addTags(VTags.Items.DUSTS_GLOWSTONE, VTags.Items.DUSTS_PRISMARINE, VTags.Items.DUSTS_REDSTONE);
			tag(VTags.Items.DUSTS_GLOWSTONE).add(Items.GLOWSTONE_DUST);
			tag(VTags.Items.DUSTS_PRISMARINE).add(Items.PRISMARINE_SHARD);
			tag(VTags.Items.DUSTS_REDSTONE).add(Items.REDSTONE);
			addColored(tag(VTags.Items.DYES)::addTags, VTags.Items.DYES, "{color}_dye");
			tag(VTags.Items.EGGS).add(Items.EGG);
			tag(VTags.Items.ENCHANTING_FUELS).addTag(VTags.Items.GEMS_LAPIS);
			copy(VTags.Blocks.END_STONES, VTags.Items.END_STONES);
			tag(VTags.Items.ENDER_PEARLS).add(Items.ENDER_PEARL);
			tag(VTags.Items.FEATHERS).add(Items.FEATHER);
			copy(VTags.Blocks.FENCE_GATES, VTags.Items.FENCE_GATES);
			copy(VTags.Blocks.FENCE_GATES_WOODEN, VTags.Items.FENCE_GATES_WOODEN);
			copy(VTags.Blocks.FENCES, VTags.Items.FENCES);
			copy(VTags.Blocks.FENCES_NETHER_BRICK, VTags.Items.FENCES_NETHER_BRICK);
			copy(VTags.Blocks.FENCES_WOODEN, VTags.Items.FENCES_WOODEN);
			tag(VTags.Items.GEMS).addTags(VTags.Items.GEMS_AMETHYST, VTags.Items.GEMS_DIAMOND, VTags.Items.GEMS_EMERALD, VTags.Items.GEMS_LAPIS, VTags.Items.GEMS_PRISMARINE, VTags.Items.GEMS_QUARTZ);
			tag(VTags.Items.GEMS_AMETHYST).add(Items.AMETHYST_SHARD);
			tag(VTags.Items.GEMS_DIAMOND).add(Items.DIAMOND);
			tag(VTags.Items.GEMS_EMERALD).add(Items.EMERALD);
			tag(VTags.Items.GEMS_LAPIS).add(Items.LAPIS_LAZULI);
			tag(VTags.Items.GEMS_PRISMARINE).add(Items.PRISMARINE_CRYSTALS);
			tag(VTags.Items.GEMS_QUARTZ).add(Items.QUARTZ);
			copy(VTags.Blocks.GLASS, VTags.Items.GLASS);
			copy(VTags.Blocks.GLASS_SILICA, VTags.Items.GLASS_SILICA);
			copy(VTags.Blocks.GLASS_TINTED, VTags.Items.GLASS_TINTED);
			func_240521_a_Colored(VTags.Blocks.GLASS, VTags.Items.GLASS);
			copy(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
			func_240521_a_Colored(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
			copy(VTags.Blocks.GRAVEL, VTags.Items.GRAVEL);
			tag(VTags.Items.GUNPOWDER).add(Items.GUNPOWDER);
			tag(VTags.Items.HEADS).add(Items.CREEPER_HEAD, Items.DRAGON_HEAD, Items.PLAYER_HEAD, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.ZOMBIE_HEAD);
			tag(VTags.Items.INGOTS).addTags(VTags.Items.INGOTS_BRICK, VTags.Items.INGOTS_COPPER, VTags.Items.INGOTS_GOLD, VTags.Items.INGOTS_IRON, VTags.Items.INGOTS_NETHERITE, VTags.Items.INGOTS_NETHER_BRICK);
			tag(VTags.Items.INGOTS_BRICK).add(Items.BRICK);
			tag(VTags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
			tag(VTags.Items.INGOTS_GOLD).add(Items.GOLD_INGOT);
			tag(VTags.Items.INGOTS_IRON).add(Items.IRON_INGOT);
			tag(VTags.Items.INGOTS_NETHERITE).add(Items.NETHERITE_INGOT);
			tag(VTags.Items.INGOTS_NETHER_BRICK).add(Items.NETHER_BRICK);
			tag(VTags.Items.LEATHER).add(Items.LEATHER);
			tag(VTags.Items.MUSHROOMS).add(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
			tag(VTags.Items.NETHER_STARS).add(Items.NETHER_STAR);
			copy(VTags.Blocks.NETHERRACK, VTags.Items.NETHERRACK);
			tag(VTags.Items.NUGGETS).addTags(VTags.Items.NUGGETS_IRON, VTags.Items.NUGGETS_GOLD);
			tag(VTags.Items.NUGGETS_IRON).add(Items.IRON_NUGGET);
			tag(VTags.Items.NUGGETS_GOLD).add(Items.GOLD_NUGGET);
			copy(VTags.Blocks.OBSIDIAN, VTags.Items.OBSIDIAN);
			copy(VTags.Blocks.ORE_BEARING_GROUND_DEEPSLATE, VTags.Items.ORE_BEARING_GROUND_DEEPSLATE);
			copy(VTags.Blocks.ORE_BEARING_GROUND_NETHERRACK, VTags.Items.ORE_BEARING_GROUND_NETHERRACK);
			copy(VTags.Blocks.ORE_BEARING_GROUND_STONE, VTags.Items.ORE_BEARING_GROUND_STONE);
			copy(VTags.Blocks.ORE_RATES_DENSE, VTags.Items.ORE_RATES_DENSE);
			copy(VTags.Blocks.ORE_RATES_SINGULAR, VTags.Items.ORE_RATES_SINGULAR);
			copy(VTags.Blocks.ORE_RATES_SPARSE, VTags.Items.ORE_RATES_SPARSE);
			copy(VTags.Blocks.ORES, VTags.Items.ORES);
			copy(VTags.Blocks.ORES_COAL, VTags.Items.ORES_COAL);
			copy(VTags.Blocks.ORES_COPPER, VTags.Items.ORES_COPPER);
			copy(VTags.Blocks.ORES_DIAMOND, VTags.Items.ORES_DIAMOND);
			copy(VTags.Blocks.ORES_EMERALD, VTags.Items.ORES_EMERALD);
			copy(VTags.Blocks.ORES_GOLD, VTags.Items.ORES_GOLD);
			copy(VTags.Blocks.ORES_IRON, VTags.Items.ORES_IRON);
			copy(VTags.Blocks.ORES_LAPIS, VTags.Items.ORES_LAPIS);
			copy(VTags.Blocks.ORES_QUARTZ, VTags.Items.ORES_QUARTZ);
			copy(VTags.Blocks.ORES_REDSTONE, VTags.Items.ORES_REDSTONE);
			copy(VTags.Blocks.ORES_NETHERITE_SCRAP, VTags.Items.ORES_NETHERITE_SCRAP);
			copy(VTags.Blocks.ORES_IN_GROUND_DEEPSLATE, VTags.Items.ORES_IN_GROUND_DEEPSLATE);
			copy(VTags.Blocks.ORES_IN_GROUND_NETHERRACK, VTags.Items.ORES_IN_GROUND_NETHERRACK);
			copy(VTags.Blocks.ORES_IN_GROUND_STONE, VTags.Items.ORES_IN_GROUND_STONE);
			tag(VTags.Items.RAW_MATERIALS).addTags(VTags.Items.RAW_MATERIALS_COPPER, VTags.Items.RAW_MATERIALS_GOLD, VTags.Items.RAW_MATERIALS_IRON);
			tag(VTags.Items.RAW_MATERIALS_COPPER).add(Items.RAW_COPPER);
			tag(VTags.Items.RAW_MATERIALS_GOLD).add(Items.RAW_GOLD);
			tag(VTags.Items.RAW_MATERIALS_IRON).add(Items.RAW_IRON);
			tag(VTags.Items.RODS).addTags(VTags.Items.RODS_BLAZE, VTags.Items.RODS_WOODEN);
			tag(VTags.Items.RODS_BLAZE).add(Items.BLAZE_ROD);
			tag(VTags.Items.RODS_WOODEN).add(Items.STICK);
			copy(VTags.Blocks.SAND, VTags.Items.SAND);
			copy(VTags.Blocks.SAND_COLORLESS, VTags.Items.SAND_COLORLESS);
			copy(VTags.Blocks.SAND_RED, VTags.Items.SAND_RED);
			copy(VTags.Blocks.SANDSTONE, VTags.Items.SANDSTONE);
			tag(VTags.Items.SEEDS).addTags(VTags.Items.SEEDS_BEETROOT, VTags.Items.SEEDS_MELON, VTags.Items.SEEDS_PUMPKIN, VTags.Items.SEEDS_WHEAT);
			tag(VTags.Items.SEEDS_BEETROOT).add(Items.BEETROOT_SEEDS);
			tag(VTags.Items.SEEDS_MELON).add(Items.MELON_SEEDS);
			tag(VTags.Items.SEEDS_PUMPKIN).add(Items.PUMPKIN_SEEDS);
			tag(VTags.Items.SEEDS_WHEAT).add(Items.WHEAT_SEEDS);
			tag(VTags.Items.SHEARS).add(Items.SHEARS);
			tag(VTags.Items.SLIMEBALLS).add(Items.SLIME_BALL);
			copy(VTags.Blocks.STAINED_GLASS, VTags.Items.STAINED_GLASS);
			copy(VTags.Blocks.STAINED_GLASS_PANES, VTags.Items.STAINED_GLASS_PANES);
			copy(VTags.Blocks.STONE, VTags.Items.STONE);
			copy(VTags.Blocks.STORAGE_BLOCKS, VTags.Items.STORAGE_BLOCKS);
			copy(VTags.Blocks.STORAGE_BLOCKS_AMETHYST, VTags.Items.STORAGE_BLOCKS_AMETHYST);
			copy(VTags.Blocks.STORAGE_BLOCKS_COAL, VTags.Items.STORAGE_BLOCKS_COAL);
			copy(VTags.Blocks.STORAGE_BLOCKS_COPPER, VTags.Items.STORAGE_BLOCKS_COPPER);
			copy(VTags.Blocks.STORAGE_BLOCKS_DIAMOND, VTags.Items.STORAGE_BLOCKS_DIAMOND);
			copy(VTags.Blocks.STORAGE_BLOCKS_EMERALD, VTags.Items.STORAGE_BLOCKS_EMERALD);
			copy(VTags.Blocks.STORAGE_BLOCKS_GOLD, VTags.Items.STORAGE_BLOCKS_GOLD);
			copy(VTags.Blocks.STORAGE_BLOCKS_IRON, VTags.Items.STORAGE_BLOCKS_IRON);
			copy(VTags.Blocks.STORAGE_BLOCKS_LAPIS, VTags.Items.STORAGE_BLOCKS_LAPIS);
			copy(VTags.Blocks.STORAGE_BLOCKS_QUARTZ, VTags.Items.STORAGE_BLOCKS_QUARTZ);
			copy(VTags.Blocks.STORAGE_BLOCKS_REDSTONE, VTags.Items.STORAGE_BLOCKS_REDSTONE);
			copy(VTags.Blocks.STORAGE_BLOCKS_RAW_COPPER, VTags.Items.STORAGE_BLOCKS_RAW_COPPER);
			copy(VTags.Blocks.STORAGE_BLOCKS_RAW_GOLD, VTags.Items.STORAGE_BLOCKS_RAW_GOLD);
			copy(VTags.Blocks.STORAGE_BLOCKS_RAW_IRON, VTags.Items.STORAGE_BLOCKS_RAW_IRON);
			copy(VTags.Blocks.STORAGE_BLOCKS_NETHERITE, VTags.Items.STORAGE_BLOCKS_NETHERITE);
			tag(VTags.Items.STRING).add(Items.STRING);
		}

		private void addColored(Consumer<Tag.Named<Item>> consumer, Tag.Named<Item> group, String pattern) {
			String prefix = group.getName().getPath().toUpperCase(Locale.ENGLISH) + '_';
			for (DyeColor color : DyeColor.values()) {
				ResourceLocation key = new ResourceLocation("minecraft", pattern.replace("{color}", color.getName()));
				Tag.Named<Item> tag = getForgeItemTag(prefix + color.getName());
				Item item = Registry.ITEM.get(key);
				if (item == null || item == Items.AIR)
					throw new IllegalStateException("Unknown vanilla item: " + key.toString());
				tag(tag).add(item);
				consumer.accept(tag);
			}
		}

		private void func_240521_a_Colored(Tag.Named<Block> blockGroup, Tag.Named<Item> itemGroup) {
			String blockPre = blockGroup.getName().getPath().toUpperCase(Locale.ENGLISH) + '_';
			String itemPre = itemGroup.getName().getPath().toUpperCase(Locale.ENGLISH) + '_';
			for (DyeColor color : DyeColor.values()) {
				Tag.Named<Block> from = getForgeBlockTag(blockPre + color.getName());
				Tag.Named<Item> to = getForgeItemTag(itemPre + color.getName());
				copy(from, to);
			}
			copy(getForgeBlockTag(blockPre + "colorless"), getForgeItemTag(itemPre + "colorless"));
		}

		@SuppressWarnings("unchecked")
		private Tag.Named<Block> getForgeBlockTag(String name) {
			try {
				name = name.toUpperCase(Locale.ENGLISH);
				return (Tag.Named<Block>) VTags.Blocks.class.getDeclaredField(name).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(VTags.Blocks.class.getName() + " is missing tag name: " + name);
			}
		}

		@SuppressWarnings("unchecked")
		private Tag.Named<Item> getForgeItemTag(String name) {
			try {
				name = name.toUpperCase(Locale.ENGLISH);
				return (Tag.Named<Item>) VTags.Items.class.getDeclaredField(name).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(VTags.Items.class.getName() + " is missing tag name: " + name);
			}
		}
	}
}
