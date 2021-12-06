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

import java.nio.file.Path;
import java.util.Locale;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;

import io.github.vampirestudios.vampirelib.api.FabricLanguageProvider;
import io.github.vampirestudios.vampirelib.api.datagen.CustomBlockTagProvider;
import io.github.vampirestudios.vampirelib.api.datagen.CustomItemTagProvider;
import io.github.vampirestudios.vampirelib.init.VTags;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;

import static io.github.vampirestudios.vampirelib.VampireLib.TEST_CONTENT_ENABLED;
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
		if (TEST_CONTENT_ENABLED) {
			dataGenerator.addProvider(WoodTypeBlockStateDefinitionProvider::new);
			dataGenerator.addProvider(WoodTypeEnglishLanguageProvider::new);
			dataGenerator.addProvider(WoodTypeFrenchLanguageProvider::new);
			dataGenerator.addProvider(WoodTypeRecipeProvider::new);
		}
		TestBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(TestBlockTagsProvider::new);
		dataGenerator.addProvider(new TestItemTagsProvider(dataGenerator, blockTagsProvider));
	}

	private static class WoodTypeBlockStateDefinitionProvider extends FabricBlockStateDefinitionProvider {
		private WoodTypeBlockStateDefinitionProvider(FabricDataGenerator generator) {
			super(generator);
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD1);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD2);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD3);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD4);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD5);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD6);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD7);

			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD1);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD2);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD3);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD4);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD5);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD6);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD7);
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
			addWoodTypeLang(VampireLib.TEST_WOOD);
			addWoodTypeLang(VampireLib.TEST_WOOD1);
			addWoodTypeLang(VampireLib.TEST_WOOD2);
			addWoodTypeLang(VampireLib.TEST_WOOD3);
			addWoodTypeLang(VampireLib.TEST_WOOD4);
			addWoodTypeLang(VampireLib.TEST_WOOD5);
			addWoodTypeLang(VampireLib.TEST_WOOD6);
			addWoodTypeLang(VampireLib.TEST_WOOD7);

			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD1);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD2);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD3);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD4);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD5);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD6);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD7);
		}

		private void addWoodTypeLang(WoodRegistry woodRegistry) {
			woodRegistry.generateLang(this);
		}
	}

	private static class WoodTypeFrenchLanguageProvider extends FabricLanguageProvider {
		private WoodTypeFrenchLanguageProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, "fr_fr");
			addWoodTypeLang(VampireLib.TEST_WOOD);
			addWoodTypeLang(VampireLib.TEST_WOOD1);
			addWoodTypeLang(VampireLib.TEST_WOOD2);
			addWoodTypeLang(VampireLib.TEST_WOOD3);
			addWoodTypeLang(VampireLib.TEST_WOOD4);
			addWoodTypeLang(VampireLib.TEST_WOOD5);
			addWoodTypeLang(VampireLib.TEST_WOOD6);
			addWoodTypeLang(VampireLib.TEST_WOOD7);

			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD1);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD2);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD3);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD4);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD5);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD6);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD7);
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
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD1);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD2);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD3);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD4);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD5);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD6);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD7);

			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD1);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD2);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD3);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD4);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD5);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD6);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD7);
		}

		private void generateWoodTypeRecipes(Consumer<FinishedRecipe> exporter, WoodRegistry woodRegistry) {
			woodRegistry.generateRecipes(exporter);
		}
	}

	private static class TestBlockTagsProvider extends CustomBlockTagProvider {
		private TestBlockTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected Path getPath(ResourceLocation id) {
			return this.generator.getOutputFolder().resolve("data/%s/tags/%s/%s.json".formatted("c", "blocks", id.getPath()));
		}

		@Override
		protected void generateTags() {
			tagCustom(BARRELS).addTag(BARRELS_WOODEN);
			tagCustom(BARRELS_WOODEN).add(Blocks.BARREL);
			tagCustom(CHESTS_ENDER).add(Blocks.ENDER_CHEST);
			tagCustom(CHESTS_TRAPPED).add(Blocks.TRAPPED_CHEST);
			tagCustom(CHESTS_WOODEN).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
			tagCustom(CHESTS).addTags(CHESTS_ENDER, CHESTS_TRAPPED, CHESTS_WOODEN);
			tagCustom(COBBLESTONE).add(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLED_DEEPSLATE);
			tagCustom(DIRT).add(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.ROOTED_DIRT);
			tagCustom(END_STONES).add(Blocks.END_STONE);
			tagCustom(ENDERMAN_PLACE_ON_BLACKLIST);
			tagCustom(FENCE_GATES).addTags(FENCE_GATES_WOODEN);
			tagCustom(FENCE_GATES_WOODEN).add(Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE);
			tagCustom(FENCES).addTags(FENCES_NETHER_BRICK, FENCES_WOODEN);
			tagCustom(FENCES_NETHER_BRICK).add(Blocks.NETHER_BRICK_FENCE);
			tagCustom(FENCES_WOODEN).add(Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE, Blocks.JUNGLE_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE, Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE);
			tagCustom(GLASS).addTags(GLASS_COLORLESS, STAINED_GLASS, GLASS_TINTED);
			tagCustom(GLASS_COLORLESS).add(Blocks.GLASS);
			tagCustom(GLASS_SILICA).add(Blocks.GLASS, Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS);
			tagCustom(GLASS_TINTED).add(Blocks.TINTED_GLASS);
			addColored(tagCustom(STAINED_GLASS)::add, GLASS, "{color}_stained_glass");
			tagCustom(GLASS_PANES).addTags(GLASS_PANES_COLORLESS, STAINED_GLASS_PANES);
			tagCustom(GLASS_PANES_COLORLESS).add(Blocks.GLASS_PANE);
			addColored(tagCustom(STAINED_GLASS_PANES)::add, GLASS_PANES, "{color}_stained_glass_pane");
			tagCustom(GRAVEL).add(Blocks.GRAVEL);
			tagCustom(NETHERRACK).add(Blocks.NETHERRACK);
			tagCustom(OBSIDIAN).add(Blocks.OBSIDIAN);
			tagCustom(ORE_BEARING_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE);
			tagCustom(ORE_BEARING_GROUND_NETHERRACK).add(Blocks.NETHERRACK);
			tagCustom(ORE_BEARING_GROUND_STONE).add(Blocks.STONE);
			tagCustom(ORE_RATES_DENSE).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
			tagCustom(ORE_RATES_SINGULAR).add(Blocks.ANCIENT_DEBRIS, Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.NETHER_QUARTZ_ORE);
			tagCustom(ORE_RATES_SPARSE).add(Blocks.NETHER_GOLD_ORE);
			tagCustom(ORES).addTags(ORES_COAL, ORES_COPPER, ORES_DIAMOND, ORES_EMERALD, ORES_GOLD, ORES_IRON, ORES_LAPIS, ORES_REDSTONE, ORES_QUARTZ, ORES_NETHERITE_SCRAP);
			tagCustom(ORES_COAL).add(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE);
			tagCustom(ORES_COPPER).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE);
			tagCustom(ORES_DIAMOND).add(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
			tagCustom(ORES_EMERALD).add(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE);
			tagCustom(ORES_GOLD).add(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE);
			tagCustom(ORES_IRON).add(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE);
			tagCustom(ORES_LAPIS).add(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);
			tagCustom(ORES_QUARTZ).add(Blocks.NETHER_QUARTZ_ORE);
			tagCustom(ORES_REDSTONE).add(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
			tagCustom(ORES_NETHERITE_SCRAP).add(Blocks.ANCIENT_DEBRIS);
			tagCustom(ORES_IN_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
			tagCustom(ORES_IN_GROUND_NETHERRACK).add(Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE);
			tagCustom(ORES_IN_GROUND_STONE).add(Blocks.COAL_ORE, Blocks.COPPER_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
			tagCustom(SAND).addTags(SAND_COLORLESS, SAND_RED);
			tagCustom(SAND_COLORLESS).add(Blocks.SAND);
			tagCustom(SAND_RED).add(Blocks.RED_SAND);
			tagCustom(SANDSTONE).add(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
			tagCustom(STONE).add(Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.INFESTED_STONE, Blocks.STONE, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.INFESTED_DEEPSLATE, Blocks.TUFF);
			tagCustom(STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_AMETHYST, STORAGE_BLOCKS_COAL, STORAGE_BLOCKS_COPPER, STORAGE_BLOCKS_DIAMOND, STORAGE_BLOCKS_EMERALD, STORAGE_BLOCKS_GOLD, STORAGE_BLOCKS_IRON, STORAGE_BLOCKS_LAPIS, STORAGE_BLOCKS_QUARTZ, STORAGE_BLOCKS_RAW_COPPER, STORAGE_BLOCKS_RAW_GOLD, STORAGE_BLOCKS_RAW_IRON, STORAGE_BLOCKS_REDSTONE, STORAGE_BLOCKS_NETHERITE);
			tagCustom(STORAGE_BLOCKS_AMETHYST).add(Blocks.AMETHYST_BLOCK);
			tagCustom(STORAGE_BLOCKS_COAL).add(Blocks.COAL_BLOCK);
			tagCustom(STORAGE_BLOCKS_COPPER).add(Blocks.COPPER_BLOCK, Blocks.CUT_COPPER);
			tagCustom(STORAGE_BLOCKS_DIAMOND).add(Blocks.DIAMOND_BLOCK);
			tagCustom(STORAGE_BLOCKS_EMERALD).add(Blocks.EMERALD_BLOCK);
			tagCustom(STORAGE_BLOCKS_GOLD).add(Blocks.GOLD_BLOCK);
			tagCustom(STORAGE_BLOCKS_IRON).add(Blocks.IRON_BLOCK);
			tagCustom(STORAGE_BLOCKS_LAPIS).add(Blocks.LAPIS_BLOCK);
			tagCustom(STORAGE_BLOCKS_QUARTZ).add(Blocks.QUARTZ_BLOCK);
			tagCustom(STORAGE_BLOCKS_RAW_COPPER).add(Blocks.RAW_COPPER_BLOCK);
			tagCustom(STORAGE_BLOCKS_RAW_GOLD).add(Blocks.RAW_GOLD_BLOCK);
			tagCustom(STORAGE_BLOCKS_RAW_IRON).add(Blocks.RAW_IRON_BLOCK);
			tagCustom(STORAGE_BLOCKS_REDSTONE).add(Blocks.REDSTONE_BLOCK);
			tagCustom(STORAGE_BLOCKS_NETHERITE).add(Blocks.NETHERITE_BLOCK);
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

	/*private static class TestBiomeTagsProvider extends CustomTagProviders.ExpandedBiomeTagProvider {
		private TestBiomeTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected Path getPath(ResourceLocation id) {
			return this.generator.getOutputFolder().resolve("data/%s/tags/%s/%s.json".formatted("c", "worldgen/biomes", id.getPath()));
		}

		@Override
		protected void generateTags() {
			this.tag(Tags.Biomes.HILLS)
				.add(Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.DESERT_HILLS,
					Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.JUNGLE_HILLS, Biomes.SNOWY_TAIGA_HILLS,
					Biomes.SWAMP_HILLS, Biomes.TAIGA_HILLS, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_HILLS)
				.add(Biomes.FLOWER_FOREST, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.SAVANNA_PLATEAU, Biomes.SNOWY_MOUNTAINS, Biomes.TALL_BIRCH_FOREST);
			this.tag(Tags.Biomes.PLATEAUS)
				.add(Biomes.BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU)
				.add(Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA_PLATEAU);
			this.tag(Tags.Biomes.MODIFIED)
				.add(Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU);
			this.tag(Tags.Biomes.RARE)
				.add(Biomes.JUNGLE_EDGE, Biomes.SAVANNA_PLATEAU, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES,
					Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS,
					Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST,
					Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA,
					Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU,
					Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.BAMBOO_JUNGLE,
					Biomes.BAMBOO_JUNGLE_HILLS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE);

			this.tag(Tags.Biomes.OCEANS)
				.addTags(Tags.Biomes.DEEP_OCEANS, Tags.Biomes.SHALLOW_OCEANS);
			this.tag(Tags.Biomes.DEEP_OCEANS)
				.add(Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_WARM_OCEAN);
			this.tag(Tags.Biomes.SHALLOW_OCEANS)
				.add(Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.OCEAN, Biomes.WARM_OCEAN);
			this.tag(Tags.Biomes.RIVERS)
				.add(Biomes.RIVER, Biomes.FROZEN_RIVER);
			this.tag(Tags.Biomes.WATER)
				.addTags(Tags.Biomes.OCEANS, Tags.Biomes.RIVERS);


			this.tag(Tags.Biomes.BADLANDS)
				.add(Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.ERODED_BADLANDS,
					Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU);
			this.tag(Tags.Biomes.BEACHES)
				.add(Biomes.BEACH, Biomes.MUSHROOM_FIELD_SHORE, Biomes.SNOWY_BEACH, Biomes.STONE_SHORE);
			this.tag(Tags.Biomes.DESERTS)
				.add(Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES);
			this.tag(Tags.Biomes.FORESTS)
				.addTags(Tags.Biomes.BIRCH_FORESTS, Tags.Biomes.DARK_FORESTS, Tags.Biomes.JUNGLE_FORESTS,
					Tags.Biomes.NETHER_FORESTS, Tags.Biomes.OAK_FORESTS, Tags.Biomes.TAIGA_FORESTS);
			this.tag(Tags.Biomes.BIRCH_FORESTS)
				.add(Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);
			this.tag(Tags.Biomes.DARK_FORESTS)
				.add(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS);
			this.tag(Tags.Biomes.JUNGLE_FORESTS)
				.addTags(Tags.Biomes.BAMBOO_JUNGLE_FORESTS)
				.add(Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE);
			this.tag(Tags.Biomes.BAMBOO_JUNGLE_FORESTS)
				.add(Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS);
			this.tag(Tags.Biomes.NETHER_FORESTS)
				.add(Biomes.CRIMSON_FOREST, Biomes.WARPED_FOREST);
			this.tag(Tags.Biomes.OAK_FORESTS)
				.add(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.WOODED_HILLS);
			this.tag(Tags.Biomes.TAIGA_FORESTS)
				.add(Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS)
				.add(Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS)
				.add(Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS);
			this.tag(Tags.Biomes.MUSHROOM)
				.add(Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE);
			this.tag(Tags.Biomes.MOUNTAINS)
				.add(Biomes.MOUNTAIN_EDGE, Biomes.MOUNTAINS, Biomes.GRAVELLY_MOUNTAINS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.TAIGA_MOUNTAINS, Biomes.WOODED_MOUNTAINS)
				.add(Biomes.MODIFIED_JUNGLE, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU);
			this.tag(Tags.Biomes.PLAINS)
				.add(Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SUNFLOWER_PLAINS);
			this.tag(Tags.Biomes.GRASSLANDS)
				.addTags(Tags.Biomes.PLAINS, Tags.Biomes.SAVANNAS);
			this.tag(Tags.Biomes.SAVANNAS)
				.add(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU);
			this.tag(Tags.Biomes.SNOWY)
				.add(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_RIVER)
				.add(Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA);
			this.tag(Tags.Biomes.SWAMPS)
				.add(Biomes.SWAMP, Biomes.SWAMP_HILLS);
			this.tag(Tags.Biomes.VOIDS)
				.add(Biomes.THE_VOID);

			this.tag(VTags.Biomes.OVERWORLD)
				.add(Biomes.BADLANDS, Biomes.BAMBOO_JUNGLE, Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.COLD_OCEAN,
					Biomes.DARK_FOREST, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN,
					Biomes.DEEP_OCEAN, Biomes.DESERT, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST,
					Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA,
					Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.ICE_SPIKES, Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.LUKEWARM_OCEAN,
					Biomes.WINDSWEPT_HILLS, Biomes.MUSHROOM_FIELDS, Biomes.OCEAN, Biomes.PLAINS, Biomes.RIVER, Biomes.SAVANNA,
					Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA, Biomes.SNOWY_BEACH, Biomes.SNOWY_TAIGA, Biomes.SNOWY_PLAINS,
					Biomes.STONY_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.TAIGA, Biomes.OLD_GROWTH_BIRCH_FOREST,
					Biomes.WARM_OCEAN, Biomes.WOODED_BADLANDS, getBiome(Biomes.WINDSWEPT_FOREST));
			this.tagCustom(VTags.Biomes.NETHER)
				.addTags(VTags.Biomes.NETHER_FORESTS)
				.add(getBiome(Biomes.NETHER_WASTES), getBiome(Biomes.SOUL_SAND_VALLEY), getBiome(Biomes.BASALT_DELTAS));
			this.tagCustom(VTags.Biomes.END)
				.add(getBiome(Biomes.THE_END), getBiome(Biomes.SMALL_END_ISLANDS), getBiome(Biomes.END_BARRENS), getBiome(Biomes.END_MIDLANDS), getBiome(Biomes.END_HIGHLANDS));
		}

		public Biome getBiome(ResourceKey<Biome> biome) {
			return BuiltinRegistries.BIOME.get(biome);
		}

	}*/

	private static class TestItemTagsProvider extends CustomItemTagProvider {
		private TestItemTagsProvider(FabricDataGenerator dataGenerator, CustomBlockTagProvider blockTagProvider) {
			super(dataGenerator, blockTagProvider);
		}

		@Override
		protected void generateTags() {
			copy(VTags.Blocks.BARRELS, VTags.Items.BARRELS);
			copy(VTags.Blocks.BARRELS_WOODEN, VTags.Items.BARRELS_WOODEN);
			tagCustom(VTags.Items.BONES).add(Items.BONE);
			tagCustom(VTags.Items.BOOKSHELVES).add(Items.BOOKSHELF);
			copy(VTags.Blocks.CHESTS, VTags.Items.CHESTS);
			copy(VTags.Blocks.CHESTS_ENDER, VTags.Items.CHESTS_ENDER);
			copy(VTags.Blocks.CHESTS_TRAPPED, VTags.Items.CHESTS_TRAPPED);
			copy(VTags.Blocks.CHESTS_WOODEN, VTags.Items.CHESTS_WOODEN);
			copy(VTags.Blocks.COBBLESTONE, VTags.Items.COBBLESTONE);
			tagCustom(VTags.Items.CROPS).addTags(VTags.Items.CROPS_BEETROOT, VTags.Items.CROPS_CARROT, VTags.Items.CROPS_NETHER_WART, VTags.Items.CROPS_POTATO, VTags.Items.CROPS_WHEAT);
			tagCustom(VTags.Items.CROPS_BEETROOT).add(Items.BEETROOT);
			tagCustom(VTags.Items.CROPS_CARROT).add(Items.CARROT);
			tagCustom(VTags.Items.CROPS_NETHER_WART).add(Items.NETHER_WART);
			tagCustom(VTags.Items.CROPS_POTATO).add(Items.POTATO);
			tagCustom(VTags.Items.CROPS_WHEAT).add(Items.WHEAT);
			tagCustom(VTags.Items.DUSTS).addTags(VTags.Items.DUSTS_GLOWSTONE, VTags.Items.DUSTS_PRISMARINE, VTags.Items.DUSTS_REDSTONE);
			tagCustom(VTags.Items.DUSTS_GLOWSTONE).add(Items.GLOWSTONE_DUST);
			tagCustom(VTags.Items.DUSTS_PRISMARINE).add(Items.PRISMARINE_SHARD);
			tagCustom(VTags.Items.DUSTS_REDSTONE).add(Items.REDSTONE);
			addColored(tagCustom(VTags.Items.DYES)::addTags, VTags.Items.DYES, "{color}_dye");
			tagCustom(VTags.Items.EGGS).add(Items.EGG);
			tagCustom(VTags.Items.ENCHANTING_FUELS).addTag(VTags.Items.GEMS_LAPIS);
			copy(VTags.Blocks.END_STONES, VTags.Items.END_STONES);
			tagCustom(VTags.Items.ENDER_PEARLS).add(Items.ENDER_PEARL);
			tagCustom(VTags.Items.FEATHERS).add(Items.FEATHER);
			copy(VTags.Blocks.FENCE_GATES, VTags.Items.FENCE_GATES);
			copy(VTags.Blocks.FENCE_GATES_WOODEN, VTags.Items.FENCE_GATES_WOODEN);
			copy(VTags.Blocks.FENCES, VTags.Items.FENCES);
			copy(VTags.Blocks.FENCES_NETHER_BRICK, VTags.Items.FENCES_NETHER_BRICK);
			copy(VTags.Blocks.FENCES_WOODEN, VTags.Items.FENCES_WOODEN);
			tagCustom(VTags.Items.GEMS).addTags(VTags.Items.GEMS_AMETHYST, VTags.Items.GEMS_DIAMOND, VTags.Items.GEMS_EMERALD, VTags.Items.GEMS_LAPIS, VTags.Items.GEMS_PRISMARINE, VTags.Items.GEMS_QUARTZ);
			tagCustom(VTags.Items.GEMS_AMETHYST).add(Items.AMETHYST_SHARD);
			tagCustom(VTags.Items.GEMS_DIAMOND).add(Items.DIAMOND);
			tagCustom(VTags.Items.GEMS_EMERALD).add(Items.EMERALD);
			tagCustom(VTags.Items.GEMS_LAPIS).add(Items.LAPIS_LAZULI);
			tagCustom(VTags.Items.GEMS_PRISMARINE).add(Items.PRISMARINE_CRYSTALS);
			tagCustom(VTags.Items.GEMS_QUARTZ).add(Items.QUARTZ);
			copy(VTags.Blocks.GLASS, VTags.Items.GLASS);
			copy(VTags.Blocks.GLASS_SILICA, VTags.Items.GLASS_SILICA);
			copy(VTags.Blocks.GLASS_TINTED, VTags.Items.GLASS_TINTED);
			func_240521_a_Colored(VTags.Blocks.GLASS, VTags.Items.GLASS);
			copy(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
			func_240521_a_Colored(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
			copy(VTags.Blocks.GRAVEL, VTags.Items.GRAVEL);
			tagCustom(VTags.Items.GUNPOWDER).add(Items.GUNPOWDER);
			tagCustom(VTags.Items.HEADS).add(Items.CREEPER_HEAD, Items.DRAGON_HEAD, Items.PLAYER_HEAD, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.ZOMBIE_HEAD);
			tagCustom(VTags.Items.INGOTS).addTags(VTags.Items.INGOTS_BRICK, VTags.Items.INGOTS_COPPER, VTags.Items.INGOTS_GOLD, VTags.Items.INGOTS_IRON, VTags.Items.INGOTS_NETHERITE, VTags.Items.INGOTS_NETHER_BRICK);
			tagCustom(VTags.Items.INGOTS_BRICK).add(Items.BRICK);
			tagCustom(VTags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
			tagCustom(VTags.Items.INGOTS_GOLD).add(Items.GOLD_INGOT);
			tagCustom(VTags.Items.INGOTS_IRON).add(Items.IRON_INGOT);
			tagCustom(VTags.Items.INGOTS_NETHERITE).add(Items.NETHERITE_INGOT);
			tagCustom(VTags.Items.INGOTS_NETHER_BRICK).add(Items.NETHER_BRICK);
			tagCustom(VTags.Items.LEATHER).add(Items.LEATHER);
			tagCustom(VTags.Items.MUSHROOMS).add(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
			tagCustom(VTags.Items.NETHER_STARS).add(Items.NETHER_STAR);
			copy(VTags.Blocks.NETHERRACK, VTags.Items.NETHERRACK);
			tagCustom(VTags.Items.NUGGETS).addTags(VTags.Items.NUGGETS_IRON, VTags.Items.NUGGETS_GOLD);
			tagCustom(VTags.Items.NUGGETS_IRON).add(Items.IRON_NUGGET);
			tagCustom(VTags.Items.NUGGETS_GOLD).add(Items.GOLD_NUGGET);
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
			tagCustom(VTags.Items.RAW_MATERIALS).addTags(VTags.Items.RAW_MATERIALS_COPPER, VTags.Items.RAW_MATERIALS_GOLD, VTags.Items.RAW_MATERIALS_IRON);
			tagCustom(VTags.Items.RAW_MATERIALS_COPPER).add(Items.RAW_COPPER);
			tagCustom(VTags.Items.RAW_MATERIALS_GOLD).add(Items.RAW_GOLD);
			tagCustom(VTags.Items.RAW_MATERIALS_IRON).add(Items.RAW_IRON);
			tagCustom(VTags.Items.RODS).addTags(VTags.Items.RODS_BLAZE, VTags.Items.RODS_WOODEN);
			tagCustom(VTags.Items.RODS_BLAZE).add(Items.BLAZE_ROD);
			tagCustom(VTags.Items.RODS_WOODEN).add(Items.STICK);
			copy(VTags.Blocks.SAND, VTags.Items.SAND);
			copy(VTags.Blocks.SAND_COLORLESS, VTags.Items.SAND_COLORLESS);
			copy(VTags.Blocks.SAND_RED, VTags.Items.SAND_RED);
			copy(VTags.Blocks.SANDSTONE, VTags.Items.SANDSTONE);
			tagCustom(VTags.Items.SEEDS).addTags(VTags.Items.SEEDS_BEETROOT, VTags.Items.SEEDS_MELON, VTags.Items.SEEDS_PUMPKIN, VTags.Items.SEEDS_WHEAT);
			tagCustom(VTags.Items.SEEDS_BEETROOT).add(Items.BEETROOT_SEEDS);
			tagCustom(VTags.Items.SEEDS_MELON).add(Items.MELON_SEEDS);
			tagCustom(VTags.Items.SEEDS_PUMPKIN).add(Items.PUMPKIN_SEEDS);
			tagCustom(VTags.Items.SEEDS_WHEAT).add(Items.WHEAT_SEEDS);
			tagCustom(VTags.Items.SHEARS).add(Items.SHEARS);
			tagCustom(VTags.Items.SLIMEBALLS).add(Items.SLIME_BALL);
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
			tagCustom(VTags.Items.STRING).add(Items.STRING);
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
