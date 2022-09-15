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

package io.github.vampirestudios.vampirelib;

import static io.github.vampirestudios.vampirelib.VampireLib.TEST_CONTENT_ENABLED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BARRELS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BARRELS_WOODEN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BOOKSHELVES;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS_ENDER;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS_TRAPPED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.CHESTS_WOODEN;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.COBBLESTONE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.COBBLESTONE_DEEPSLATE;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.COBBLESTONE_INFESTED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.COBBLESTONE_MOSSY;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.COBBLESTONE_NORMAL;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import net.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabric.api.tag.convention.v1.ConventionalItemTags;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.ItemValue;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.item.crafting.Ingredient.Value;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import io.github.vampirestudios.vampirelib.api.datagen.CustomTagProviders;
import io.github.vampirestudios.vampirelib.api.datagen.FabricLanguageProvider;
import io.github.vampirestudios.vampirelib.api.datagen.LanguageConsumer;
import io.github.vampirestudios.vampirelib.init.VTags;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;

public class VampireLibDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		if (TEST_CONTENT_ENABLED) {
			dataGenerator.addProvider(WoodTypeBlockStateDefinitionProvider::new);
			dataGenerator.addProvider(WoodTypeEnglishLanguageProvider::new);
			dataGenerator.addProvider(WoodTypeFrenchLanguageProvider::new);
			dataGenerator.addProvider(WoodTypeRecipeProvider::new);
			WoodTypeBlockTagProvider blockTagsProvider = dataGenerator.addProvider(WoodTypeBlockTagProvider::new);
			dataGenerator.addProvider(new WoodTypeItemTagProvider(dataGenerator, blockTagsProvider));
		}
		VBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(VBlockTagsProvider::new);
		dataGenerator.addProvider(new VItemTagsProvider(dataGenerator, blockTagsProvider));
		dataGenerator.addProvider(VRecipeReplacementProvider::new);
		dataGenerator.addProvider(VEntityTypeTagsProvider::new);
		dataGenerator.addProvider(VBiomeTagsProvider::new);
		dataGenerator.addProvider(VNoiseSettingsTagsProvider::new);
//        dataGenerator.addProvider(VDimensionTypeTagsProvider::new);
	}

	//Wood Type Test Generation
	private static class WoodTypeBlockStateDefinitionProvider extends FabricModelProvider {
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
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD8);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD9);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD10);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD11);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD12);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD13);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD14);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD15);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_WOOD16);

			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD1);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD2);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD3);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD4);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD5);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD6);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD7);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD8);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD9);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD10);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD11);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD12);
			generateWoodTypeAssets(blockStateModelGenerator, VampireLib.TEST_NETHER_WOOD13);
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
		}

		@Override
		public void generateLanguages(LanguageConsumer languageConsumer) {
			addWoodTypeLang(VampireLib.TEST_WOOD, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD1, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD2, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD3, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD4, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD5, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD6, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD7, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD8, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD9, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD10, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD11, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD12, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD13, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD14, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD15, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD16, languageConsumer);

			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD1, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD2, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD3, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD4, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD5, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD6, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD7, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD8, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD9, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD10, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD11, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD12, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD13, languageConsumer);
		}

		private void addWoodTypeLang(WoodRegistry woodRegistry, LanguageConsumer languageConsumer) {
			woodRegistry.generateLang(languageConsumer);
		}
	}

	private static class WoodTypeFrenchLanguageProvider extends FabricLanguageProvider {
		private WoodTypeFrenchLanguageProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, "fr_fr");
		}

		@Override
		public void generateLanguages(LanguageConsumer languageConsumer) {
			addWoodTypeLang(VampireLib.TEST_WOOD, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD1, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD2, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD3, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD4, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD5, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD6, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD7, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD8, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD9, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD10, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD11, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD12, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD13, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD14, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD15, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_WOOD16, languageConsumer);

			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD1, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD2, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD3, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD4, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD5, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD6, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD7, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD8, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD9, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD10, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD11, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD12, languageConsumer);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD13, languageConsumer);
		}

		private void addWoodTypeLang(WoodRegistry woodRegistry, LanguageConsumer languageConsumer) {
			woodRegistry.generateLang(languageConsumer);
		}
	}

	private static class WoodTypeRecipeProvider extends FabricRecipeProvider {
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
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD8);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD9);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD10);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD11);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD12);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD13);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD14);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD15);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_WOOD16);

			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD1);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD2);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD3);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD4);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD5);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD6);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD7);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD8);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD9);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD10);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD11);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD12);
			generateWoodTypeRecipes(exporter, VampireLib.TEST_NETHER_WOOD13);
		}

		private void generateWoodTypeRecipes(Consumer<FinishedRecipe> exporter, WoodRegistry woodRegistry) {
			woodRegistry.generateRecipes(exporter);
		}
	}

	private static class WoodTypeBlockTagProvider extends CustomTagProviders.CustomBlockTagProvider {
		private WoodTypeBlockTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD1);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD2);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD3);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD4);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD5);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD6);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD7);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD8);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD9);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD10);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD11);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD12);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD13);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD14);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD15);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD16);

			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD1);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD2);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD3);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD4);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD5);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD6);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD7);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD8);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD9);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD10);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD11);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD12);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD13);
		}

		private void generateWoodTypeRecipes(WoodRegistry woodRegistry) {
			woodRegistry.generateBlockTags(this);
		}
	}

	private static class WoodTypeItemTagProvider extends CustomTagProviders.CustomItemTagProvider {
		private WoodTypeItemTagProvider(FabricDataGenerator dataGenerator, CustomBlockTagProvider blockTagProvider) {
			super(dataGenerator, blockTagProvider);
		}

		@Override
		protected void generateTags() {
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD1);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD2);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD3);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD4);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD5);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD6);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD7);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD8);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD9);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD10);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD11);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD12);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD13);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD14);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD15);
			this.generateWoodTypeRecipes(VampireLib.TEST_WOOD16);

			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD1);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD2);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD3);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD4);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD5);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD6);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD7);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD8);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD9);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD10);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD11);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD12);
			this.generateWoodTypeRecipes(VampireLib.TEST_NETHER_WOOD13);
		}

		private void generateWoodTypeRecipes(WoodRegistry woodRegistry) {
			woodRegistry.generateItemTags(this);
		}
	}

	//VampireLib generators
	private static class VBlockTagsProvider extends CustomTagProviders.CustomBlockTagProvider {
		private VBlockTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
			tagCustom(BARRELS).addTag(BARRELS_WOODEN);
			tagCustom(BARRELS_WOODEN).add(Blocks.BARREL);
			tag(BOOKSHELVES).add(Blocks.BOOKSHELF);
			tagCustom(CHESTS).addTags(CHESTS_ENDER, CHESTS_TRAPPED, CHESTS_WOODEN);
			tagCustom(CHESTS_ENDER).add(Blocks.ENDER_CHEST);
			tagCustom(CHESTS_TRAPPED).add(Blocks.TRAPPED_CHEST);
			tagCustom(CHESTS_WOODEN).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
			tag(COBBLESTONE).addTag(COBBLESTONE_NORMAL).addTag(COBBLESTONE_INFESTED).addTag(COBBLESTONE_MOSSY)
					.addTag(COBBLESTONE_DEEPSLATE);
			tag(COBBLESTONE_NORMAL).add(Blocks.COBBLESTONE);
			tag(COBBLESTONE_INFESTED).add(Blocks.INFESTED_COBBLESTONE);
			tag(COBBLESTONE_MOSSY).add(Blocks.MOSSY_COBBLESTONE);
			tag(COBBLESTONE_DEEPSLATE).add(Blocks.COBBLED_DEEPSLATE);
			tag(DIRT).add(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.MYCELIUM,
					Blocks.ROOTED_DIRT);
			tag(END_STONES).add(Blocks.END_STONE);
			tag(ENDERMAN_PLACE_ON_BLACKLIST);
			tag(FENCE_GATES).addTag(FENCE_GATES_WOODEN);
			tag(FENCE_GATES_WOODEN).add(Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE,
					Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE,
					Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE, Blocks.MANGROVE_FENCE_GATE);
			tagCustom(FENCES).addTags(FENCES_NETHER_BRICK, FENCES_WOODEN);
			tag(FENCES_NETHER_BRICK).add(Blocks.NETHER_BRICK_FENCE);
			getOrCreateTagBuilder(FENCES_WOODEN).forceAddTag(BlockTags.WOODEN_FENCES);
			tag(GLASS).addTag(GLASS_COLORLESS).addTag(STAINED_GLASS).addTag(GLASS_TINTED);
			tag(GLASS_COLORLESS).add(Blocks.GLASS);
			tag(GLASS_SILICA).add(Blocks.GLASS, Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS,
					Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS,
					Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS,
					Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIME_STAINED_GLASS,
					Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS,
					Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS,
					Blocks.YELLOW_STAINED_GLASS);
			tag(GLASS_TINTED).add(Blocks.TINTED_GLASS);
			addColored(tag(STAINED_GLASS)::add, GLASS, "{color}_stained_glass");
			tag(GLASS_PANES).addTag(GLASS_PANES_COLORLESS).addTag(STAINED_GLASS_PANES);
			tag(GLASS_PANES_COLORLESS).add(Blocks.GLASS_PANE);
			addColored(tag(STAINED_GLASS_PANES)::add, GLASS_PANES, "{color}_stained_glass_pane");
			tag(GRAVEL).add(Blocks.GRAVEL);
			tag(NETHERRACK).add(Blocks.NETHERRACK);
			tag(OBSIDIAN).add(Blocks.OBSIDIAN);
			tag(ORE_BEARING_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE);
			tag(ORE_BEARING_GROUND_NETHERRACK).add(Blocks.NETHERRACK);
			tag(ORE_BEARING_GROUND_STONE).add(Blocks.STONE);
			tag(ORE_RATES_DENSE).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
					Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
			tag(ORE_RATES_SINGULAR).add(Blocks.ANCIENT_DEBRIS, Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
					Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
					Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DIAMOND_ORE,
					Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.NETHER_QUARTZ_ORE);
			tag(ORE_RATES_SPARSE).add(Blocks.NETHER_GOLD_ORE);
			tagCustom(ORES).addTags(ORES_COAL, ORES_COPPER, ORES_DIAMOND, ORES_EMERALD, ORES_GOLD, ORES_IRON,
					ORES_LAPIS, ORES_REDSTONE, ORES_QUARTZ, ORES_NETHERITE_SCRAP);
			getOrCreateTagBuilder(ORES_COAL).forceAddTag(BlockTags.COAL_ORES);
			getOrCreateTagBuilder(ORES_COPPER).forceAddTag(BlockTags.COPPER_ORES);
			getOrCreateTagBuilder(ORES_DIAMOND).forceAddTag(BlockTags.DIAMOND_ORES);
			getOrCreateTagBuilder(ORES_EMERALD).forceAddTag(BlockTags.EMERALD_ORES);
			getOrCreateTagBuilder(ORES_GOLD).forceAddTag(BlockTags.GOLD_ORES);
			getOrCreateTagBuilder(ORES_IRON).forceAddTag(BlockTags.IRON_ORES);
			getOrCreateTagBuilder(ORES_LAPIS).forceAddTag(BlockTags.LAPIS_ORES);
			tag(ORES_QUARTZ).add(Blocks.NETHER_QUARTZ_ORE);
			getOrCreateTagBuilder(ORES_REDSTONE).forceAddTag(BlockTags.REDSTONE_ORES);
			tag(ORES_NETHERITE_SCRAP).add(Blocks.ANCIENT_DEBRIS);
			tag(ORES_IN_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COPPER_ORE,
					Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
					Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE,
					Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
			tag(ORES_IN_GROUND_NETHERRACK).add(Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE);
			tag(ORES_IN_GROUND_STONE).add(Blocks.COAL_ORE, Blocks.COPPER_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE,
					Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
			tagCustom(SAND).addTags(SAND_COLORLESS, SAND_RED);
			tag(SAND_COLORLESS).add(Blocks.SAND);
			tag(SAND_RED).add(Blocks.RED_SAND);
			tag(SANDSTONE).add(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE,
					Blocks.SMOOTH_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE,
					Blocks.CHISELED_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
			tag(STONE).add(Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.INFESTED_STONE, Blocks.STONE,
					Blocks.POLISHED_ANDESITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.DEEPSLATE,
					Blocks.POLISHED_DEEPSLATE, Blocks.INFESTED_DEEPSLATE, Blocks.TUFF);
			tagCustom(STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_AMETHYST, STORAGE_BLOCKS_COAL, STORAGE_BLOCKS_COPPER,
					STORAGE_BLOCKS_DIAMOND, STORAGE_BLOCKS_EMERALD, STORAGE_BLOCKS_GOLD,
					STORAGE_BLOCKS_IRON, STORAGE_BLOCKS_LAPIS, STORAGE_BLOCKS_QUARTZ,
					STORAGE_BLOCKS_RAW_COPPER, STORAGE_BLOCKS_RAW_GOLD,
					STORAGE_BLOCKS_RAW_IRON, STORAGE_BLOCKS_REDSTONE,
					STORAGE_BLOCKS_NETHERITE);
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

			generateVillagerJobSitesTag();
			generateSandstoneTags();
		}

		private void generateVillagerJobSitesTag() {
			getOrCreateTagBuilder(ConventionalBlockTags.VILLAGER_JOB_SITES)
					.add(Blocks.BARREL)
					.add(Blocks.BLAST_FURNACE)
					.add(Blocks.BREWING_STAND)
					.add(Blocks.CARTOGRAPHY_TABLE)
					.add(Blocks.CAULDRON)
					.add(Blocks.COMPOSTER)
					.add(Blocks.FLETCHING_TABLE)
					.add(Blocks.GRINDSTONE)
					.add(Blocks.LECTERN)
					.add(Blocks.LOOM)
					.add(Blocks.SMITHING_TABLE)
					.add(Blocks.SMOKER)
					.add(Blocks.STONECUTTER);
		}

		private void generateSandstoneTags() {
			getOrCreateTagBuilder(ConventionalBlockTags.NORMAL_SANDSTONE_BLOCKS)
					.add(Blocks.SANDSTONE)
					.add(Blocks.CUT_SANDSTONE)
					.add(Blocks.CHISELED_SANDSTONE)
					.add(Blocks.SMOOTH_SANDSTONE);
			getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_BLOCKS)
					.add(Blocks.RED_SANDSTONE)
					.add(Blocks.CUT_RED_SANDSTONE)
					.add(Blocks.CHISELED_RED_SANDSTONE)
					.add(Blocks.SMOOTH_RED_SANDSTONE);
			getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_BLOCKS)
					.addOptionalTag(ConventionalBlockTags.NORMAL_SANDSTONE_BLOCKS)
					.addOptionalTag(ConventionalBlockTags.RED_SANDSTONE_BLOCKS);

			getOrCreateTagBuilder(ConventionalBlockTags.NORMAL_SANDSTONE_SLABS)
					.add(Blocks.SANDSTONE_SLAB)
					.add(Blocks.CUT_SANDSTONE_SLAB)
					.add(Blocks.SMOOTH_SANDSTONE_SLAB);
			getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_SLABS)
					.add(Blocks.RED_SANDSTONE_SLAB)
					.add(Blocks.CUT_RED_SANDSTONE_SLAB)
					.add(Blocks.SMOOTH_RED_SANDSTONE_SLAB);
			getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_SLABS)
					.addOptionalTag(ConventionalBlockTags.NORMAL_SANDSTONE_SLABS)
					.addOptionalTag(ConventionalBlockTags.RED_SANDSTONE_SLABS);

			getOrCreateTagBuilder(ConventionalBlockTags.NORMAL_SANDSTONE_STAIRS)
					.add(Blocks.SANDSTONE_STAIRS)
					.add(Blocks.SMOOTH_SANDSTONE_STAIRS);
			getOrCreateTagBuilder(ConventionalBlockTags.RED_SANDSTONE_STAIRS)
					.add(Blocks.RED_SANDSTONE_STAIRS)
					.add(Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
			getOrCreateTagBuilder(ConventionalBlockTags.SANDSTONE_STAIRS)
					.addOptionalTag(ConventionalBlockTags.NORMAL_SANDSTONE_SLABS)
					.addOptionalTag(ConventionalBlockTags.RED_SANDSTONE_SLABS);
		}

		private void addColored(Consumer<Block> consumer, TagKey<Block> group, String pattern) {
			String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
			for (DyeColor color  : DyeColor.values()) {
				ResourceLocation key = new ResourceLocation("minecraft", pattern.replace("{color}",  color.getName()));
				TagKey<Block> tag = getForgeTag(prefix + color.getName());
				Block block = Registry.BLOCK.get(key);
				if (block == Blocks.AIR)
					throw new IllegalStateException("Unknown vanilla block: " + key);
				tag(tag).add(block);
				consumer.accept(block);
			}
		}

		@SuppressWarnings("unchecked")
		private TagKey<Block> getForgeTag(String name) {
			try {
				name = name.toUpperCase(Locale.ENGLISH);
				return (TagKey<Block>) VTags.Blocks.class.getDeclaredField(name).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(VTags.Blocks.class.getName() + " is missing tag name: " + name);
			}
		}

	}

	private static class VBiomeTagsProvider extends BiomeTagsProvider {
		private VBiomeTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void addTags() {
			tag(VTags.Biomes.HILLS)
//                .addOptionalTag(ConventionalBiomeTags.EXTREME_HILLS.location())
					.add(Biomes.FLOWER_FOREST, Biomes.ICE_SPIKES, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS,
							Biomes.OLD_GROWTH_BIRCH_FOREST);
			tag(BiomeTags.IS_HILL).addTag(VTags.Biomes.HILLS);
			tag(VTags.Biomes.PLATEAUS)
					.add(Biomes.SAVANNA_PLATEAU, Biomes.WOODED_BADLANDS, Biomes.MEADOW);
			tag(VTags.Biomes.RARE)
					.addTag(VTags.Biomes.MUSHROOM)
					.add(Biomes.SPARSE_JUNGLE, Biomes.SAVANNA_PLATEAU, Biomes.SUNFLOWER_PLAINS,
							Biomes.WINDSWEPT_GRAVELLY_HILLS,
							Biomes.FLOWER_FOREST, Biomes.ICE_SPIKES, Biomes.OLD_GROWTH_BIRCH_FOREST,
							Biomes.OLD_GROWTH_SPRUCE_TAIGA,
							Biomes.WINDSWEPT_SAVANNA, Biomes.ERODED_BADLANDS, Biomes.BAMBOO_JUNGLE);

			tag(VTags.Biomes.OCEANS)
//                .addOptionalTag(ConventionalBiomeTags.OCEAN.location())
					.addTag(VTags.Biomes.DEEP_OCEANS).addTag(VTags.Biomes.SHALLOW_OCEANS);
			tag(VTags.Biomes.DEEP_OCEANS)
					.add(Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN);
			tag(VTags.Biomes.SHALLOW_OCEANS)
					.add(Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.OCEAN, Biomes.WARM_OCEAN);
			tag(VTags.Biomes.RIVERS)
//                .addOptionalTag(ConventionalBiomeTags.RIVER.location())
					.add(Biomes.RIVER, Biomes.FROZEN_RIVER);
			tag(VTags.Biomes.WATER)
					.addTag(VTags.Biomes.OCEANS).addTag(VTags.Biomes.RIVERS);
			tag(BiomeTags.IS_OCEAN).addTag(VTags.Biomes.SHALLOW_OCEANS);
			tag(BiomeTags.IS_DEEP_OCEAN).addTag(VTags.Biomes.DEEP_OCEANS);
			tag(BiomeTags.IS_RIVER).addTag(VTags.Biomes.RIVERS);


			tag(VTags.Biomes.BADLANDS)
//                .addOptionalTag(ConventionalBiomeTags.MESA.location())
//                .addOptionalTag(ConventionalBiomeTags.BADLANDS.location())
					.add(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS);
			tag(BiomeTags.IS_BADLANDS).addTag(VTags.Biomes.BADLANDS);
			tag(VTags.Biomes.BEACHES)
//                .addOptionalTag(ConventionalBiomeTags.STONY_SHORES.location())
//                .addOptionalTag(ConventionalBiomeTags.BEACH.location())
					.add(Biomes.BEACH, Biomes.SNOWY_BEACH, Biomes.STONY_SHORE);
			tag(BiomeTags.IS_BEACH).addTag(VTags.Biomes.BEACHES);
			tag(VTags.Biomes.DESERTS)
//                .addOptionalTag(ConventionalBiomeTags.DESERT.location())
					.add(Biomes.DESERT);
			tag(VTags.Biomes.FORESTS)
//                .addOptionalTag(ConventionalBiomeTags.FOREST.location())
//                .addOptionalTag(ConventionalBiomeTags.FLOWER_FORESTS.location())
//                .addOptionalTag(ConventionalBiomeTags.TREE_DECIDUOUS.location())
					.addTag(VTags.Biomes.BIRCH_FORESTS).addTag(VTags.Biomes.DARK_FORESTS)
					.addTag(VTags.Biomes.JUNGLE_FORESTS).addTag(VTags.Biomes.NETHER_FORESTS)
					.addTag(VTags.Biomes.OAK_FORESTS).addTag(VTags.Biomes.TAIGA_FORESTS);
			tag(VTags.Biomes.BIRCH_FORESTS)
					.add(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
			tag(VTags.Biomes.DARK_FORESTS)
					.add(Biomes.DARK_FOREST);
			tag(VTags.Biomes.JUNGLE_FORESTS)
					.addTag(VTags.Biomes.BAMBOO_JUNGLE_FORESTS)
					.add(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE);
			tag(VTags.Biomes.BAMBOO_JUNGLE_FORESTS)
					.add(Biomes.BAMBOO_JUNGLE);
			tag(BiomeTags.IS_JUNGLE).addTag(VTags.Biomes.JUNGLE_FORESTS);
			tag(VTags.Biomes.NETHER_FORESTS)
					.add(Biomes.CRIMSON_FOREST, Biomes.WARPED_FOREST);
			tag(BiomeTags.IS_NETHER).addTag(VTags.Biomes.NETHER_FORESTS);
			tag(VTags.Biomes.OAK_FORESTS)
					.add(Biomes.FOREST, Biomes.FLOWER_FOREST);
			tag(VTags.Biomes.TAIGA_FORESTS)
//                .addOptionalTag(ConventionalBiomeTags.TREE_CONIFEROUS.location())
					.add(Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.GROVE);
			tag(BiomeTags.IS_FOREST).addTag(VTags.Biomes.FORESTS);
			tag(VTags.Biomes.MUSHROOM)
//                .addOptionalTag(ConventionalBiomeTags.MUSHROOM.location())
					.add(Biomes.MUSHROOM_FIELDS);
			tag(VTags.Biomes.MOUNTAINS)
					.add(Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST,
							Biomes.WINDSWEPT_SAVANNA);
			tag(BiomeTags.IS_MOUNTAIN).addTag(VTags.Biomes.MOUNTAINS);
			tag(VTags.Biomes.PLAINS)
//                .addOptionalTag(ConventionalBiomeTags.PLAINS.location())
					.add(Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW);
			tag(VTags.Biomes.GRASSLANDS)
					.addTag(VTags.Biomes.PLAINS).addTag(VTags.Biomes.SAVANNAS);
			tag(VTags.Biomes.SAVANNAS)
//                .addOptionalTag(ConventionalBiomeTags.TREE_SAVANNA.location())
					.add(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA);
			tag(VTags.Biomes.SNOWY)
//                .addOptionalTag(ConventionalBiomeTags.CLIMATE_COLD.location())
//                .addOptionalTag(ConventionalBiomeTags.SNOWY.location())
//                .addOptionalTag(ConventionalBiomeTags.SNOWY_PLAINS.location())
					.add(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_RIVER,
							Biomes.SNOWY_BEACH, Biomes.SNOWY_TAIGA, Biomes.SNOWY_PLAINS,
							Biomes.GROVE, Biomes.SNOWY_SLOPES, Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS);
			tag(VTags.Biomes.SWAMPS)
//                .addOptionalTag(ConventionalBiomeTags.SWAMP.location())
					.add(Biomes.SWAMP);
			tag(VTags.Biomes.SLOPES)
//                .addOptionalTag(ConventionalBiomeTags.MOUNTAIN_SLOPE.location())
					.add(Biomes.MEADOW, Biomes.GROVE, Biomes.SNOWY_SLOPES);
			tag(VTags.Biomes.PEAKS)
//                .addOptionalTag(ConventionalBiomeTags.MOUNTAIN_PEAK.location())
					.add(Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS, Biomes.STONY_PEAKS);
			tag(VTags.Biomes.VOIDS)
					.add(Biomes.THE_VOID);

			tag(VTags.Biomes.OVERWORLD)
					.addTag(VTags.Biomes.OVERWORLD_SURFACE).addTag(VTags.Biomes.OVERWORLD_UNDERGROUND);
			tag(VTags.Biomes.OVERWORLD_SURFACE)
					.addTag(VTags.Biomes.BADLANDS)
					.addTag(VTags.Biomes.FORESTS)
					.addTag(VTags.Biomes.DESERTS)
					.addTag(VTags.Biomes.BEACHES)
					.addTag(VTags.Biomes.GRASSLANDS)
					.addTag(VTags.Biomes.MUSHROOM)
					.addTag(VTags.Biomes.PEAKS)
					.addTag(VTags.Biomes.SAVANNAS)
					.addTag(VTags.Biomes.SNOWY)
					.addTag(VTags.Biomes.SLOPES)
					.addTag(VTags.Biomes.SWAMPS)
					.add(Biomes.BADLANDS,
							Biomes.BAMBOO_JUNGLE,
							Biomes.BEACH,
							Biomes.BIRCH_FOREST,
							Biomes.COLD_OCEAN,
							Biomes.DARK_FOREST,
							Biomes.DEEP_COLD_OCEAN,
							Biomes.DEEP_FROZEN_OCEAN,
							Biomes.DEEP_LUKEWARM_OCEAN,
							Biomes.DEEP_OCEAN,
							Biomes.DESERT,
							Biomes.ERODED_BADLANDS,
							Biomes.FLOWER_FOREST,
							Biomes.FOREST,
							Biomes.FROZEN_OCEAN,
							Biomes.FROZEN_PEAKS,
							Biomes.FROZEN_RIVER,
							Biomes.GROVE,
							Biomes.ICE_SPIKES,
							Biomes.JAGGED_PEAKS,
							Biomes.JUNGLE,
							Biomes.LUKEWARM_OCEAN,
							Biomes.MEADOW,
							Biomes.MUSHROOM_FIELDS,
							Biomes.OCEAN,
							Biomes.OLD_GROWTH_BIRCH_FOREST,
							Biomes.OLD_GROWTH_PINE_TAIGA,
							Biomes.OLD_GROWTH_SPRUCE_TAIGA,
							Biomes.PLAINS,
							Biomes.RIVER,
							Biomes.SAVANNA,
							Biomes.SAVANNA_PLATEAU,
							Biomes.SNOWY_BEACH,
							Biomes.SNOWY_SLOPES,
							Biomes.SNOWY_TAIGA,
							Biomes.SNOWY_PLAINS,
							Biomes.SPARSE_JUNGLE,
							Biomes.STONY_PEAKS,
							Biomes.STONY_SHORE,
							Biomes.SUNFLOWER_PLAINS,
							Biomes.SWAMP,
							Biomes.TAIGA,
							Biomes.WARM_OCEAN,
							Biomes.WOODED_BADLANDS,
							Biomes.WINDSWEPT_FOREST,
							Biomes.WINDSWEPT_GRAVELLY_HILLS,
							Biomes.WINDSWEPT_HILLS,
							Biomes.WINDSWEPT_SAVANNA);
			tag(VTags.Biomes.OVERWORLD_UNDERGROUND)
//                .addOptionalTag(ConventionalBiomeTags.UNDERGROUND.location())
					.add(Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
			tag(VTags.Biomes.NETHER)
					.addTag(VTags.Biomes.NETHER_FORESTS)
					.add(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS);
			tag(BiomeTags.IS_NETHER).addTag(VTags.Biomes.NETHER);
			tag(VTags.Biomes.END)
//                .addOptionalTag(ConventionalBiomeTags.END_ISLANDS.location())
//                .addOptionalTag(ConventionalBiomeTags.IN_THE_END.location())
					.add(Biomes.THE_END, Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS);
//            tag(BiomeTags.IS_END).addTag(VTags.Biomes.END);

			tag(Biomes.PLAINS, VTags.Biomes.IS_PLAINS, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.DESERT, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_DRY_OVERWORLD, VTags.Biomes.IS_SANDY, VTags.Biomes.IS_DESERT);
			tag(Biomes.TAIGA, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_CONIFEROUS);
			tag(Biomes.SWAMP, VTags.Biomes.IS_WET_OVERWORLD, VTags.Biomes.IS_SWAMP, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.NETHER_WASTES, VTags.Biomes.IS_HOT_NETHER, VTags.Biomes.IS_DRY_NETHER);
			tag(Biomes.THE_END, VTags.Biomes.IS_COLD_END, VTags.Biomes.IS_DRY_END);
			tag(Biomes.FROZEN_OCEAN, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_SNOWY);
			tag(Biomes.FROZEN_RIVER, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_SNOWY);
			tag(Biomes.SNOWY_PLAINS, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_SNOWY, VTags.Biomes.IS_WASTELAND,
					VTags.Biomes.IS_PLAINS);
			tag(Biomes.MUSHROOM_FIELDS, VTags.Biomes.IS_MUSHROOM, VTags.Biomes.IS_RARE, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.JUNGLE, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_WET_OVERWORLD,
					VTags.Biomes.IS_DENSE_OVERWORLD);
			tag(Biomes.SPARSE_JUNGLE, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_WET_OVERWORLD,
					VTags.Biomes.IS_RARE);
			tag(Biomes.BEACH, VTags.Biomes.IS_WET_OVERWORLD, VTags.Biomes.IS_SANDY, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.SNOWY_BEACH, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_SNOWY);
			tag(Biomes.DARK_FOREST, VTags.Biomes.IS_SPOOKY, VTags.Biomes.IS_DENSE_OVERWORLD, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.SNOWY_TAIGA, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_CONIFEROUS, VTags.Biomes.IS_SNOWY);
			tag(Biomes.OLD_GROWTH_PINE_TAIGA, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_CONIFEROUS);
			tag(Biomes.WINDSWEPT_FOREST, VTags.Biomes.IS_SPARSE_OVERWORLD);
			tag(Biomes.SAVANNA, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_SPARSE_OVERWORLD);
			tag(Biomes.SAVANNA_PLATEAU, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_SPARSE_OVERWORLD,
					VTags.Biomes.IS_RARE, VTags.Biomes.IS_SLOPE, VTags.Biomes.IS_PLATEAU);
			tag(Biomes.BADLANDS, VTags.Biomes.IS_SANDY, VTags.Biomes.IS_DRY_OVERWORLD);
			tag(Biomes.WOODED_BADLANDS, VTags.Biomes.IS_SANDY, VTags.Biomes.IS_DRY_OVERWORLD,
					VTags.Biomes.IS_SPARSE_OVERWORLD, VTags.Biomes.IS_SLOPE, VTags.Biomes.IS_PLATEAU);
			tag(Biomes.MEADOW, VTags.Biomes.IS_PLAINS, VTags.Biomes.IS_PLATEAU, VTags.Biomes.IS_SLOPE, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.GROVE, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_CONIFEROUS, VTags.Biomes.IS_SNOWY,
					VTags.Biomes.IS_SLOPE);
			tag(Biomes.SNOWY_SLOPES, VTags.Biomes.IS_SPARSE_OVERWORLD, VTags.Biomes.IS_SNOWY, VTags.Biomes.IS_SLOPE);
			tag(Biomes.JAGGED_PEAKS, VTags.Biomes.IS_SPARSE_OVERWORLD, VTags.Biomes.IS_SNOWY, VTags.Biomes.IS_PEAK);
			tag(Biomes.STONY_PEAKS, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_PEAK);
			tag(VTags.Biomes.IS_COLD_END, Biomes.SMALL_END_ISLANDS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS,
					Biomes.END_BARRENS);
			tag(VTags.Biomes.IS_DRY_END, Biomes.SMALL_END_ISLANDS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS,
					Biomes.END_BARRENS);
			tag(Biomes.WARM_OCEAN, VTags.Biomes.IS_HOT_OVERWORLD);
			tag(VTags.Biomes.IS_COLD_OVERWORLD, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN,
					Biomes.SNOWY_SLOPES, Biomes.JAGGED_PEAKS);
			tag(Biomes.THE_VOID, VTags.Biomes.IS_VOID);
			tag(Biomes.FOREST, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.SUNFLOWER_PLAINS, VTags.Biomes.IS_PLAINS, VTags.Biomes.IS_RARE, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.WINDSWEPT_GRAVELLY_HILLS, VTags.Biomes.IS_SPARSE_OVERWORLD, VTags.Biomes.IS_RARE);
			tag(Biomes.FLOWER_FOREST, VTags.Biomes.IS_RARE);
			tag(Biomes.ICE_SPIKES, VTags.Biomes.IS_COLD_OVERWORLD, VTags.Biomes.IS_SNOWY, VTags.Biomes.IS_RARE);
			tag(Biomes.BIRCH_FOREST, VTags.Biomes.IS_TEMPERATE_OVERWORLD, VTags.Biomes.IS_DENSE_OVERWORLD);
			tag(Biomes.OLD_GROWTH_BIRCH_FOREST, VTags.Biomes.IS_DENSE_OVERWORLD, VTags.Biomes.IS_RARE, VTags.Biomes.IS_TEMPERATE_OVERWORLD);
			tag(Biomes.OLD_GROWTH_SPRUCE_TAIGA, VTags.Biomes.IS_DENSE_OVERWORLD, VTags.Biomes.IS_RARE);
			tag(Biomes.WINDSWEPT_SAVANNA, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_DRY_OVERWORLD,
					VTags.Biomes.IS_SPARSE_OVERWORLD, VTags.Biomes.IS_RARE);
			tag(Biomes.ERODED_BADLANDS, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_DRY_OVERWORLD,
					VTags.Biomes.IS_SPARSE_OVERWORLD, VTags.Biomes.IS_RARE);
			tag(Biomes.BAMBOO_JUNGLE, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_WET_OVERWORLD,
					VTags.Biomes.IS_RARE);
			tag(Biomes.LUSH_CAVES, VTags.Biomes.IS_UNDERGROUND, VTags.Biomes.IS_LUSH, VTags.Biomes.IS_WET_OVERWORLD,
					VTags.Biomes.IS_CAVE);
			tag(Biomes.DRIPSTONE_CAVES, VTags.Biomes.IS_UNDERGROUND, VTags.Biomes.IS_SPARSE_OVERWORLD,
					VTags.Biomes.IS_CAVE);
			tag(Biomes.SOUL_SAND_VALLEY, VTags.Biomes.IS_HOT_NETHER, VTags.Biomes.IS_DRY_NETHER);
			tag(Biomes.CRIMSON_FOREST, VTags.Biomes.IS_HOT_NETHER, VTags.Biomes.IS_DRY_NETHER);
			tag(Biomes.WARPED_FOREST, VTags.Biomes.IS_HOT_NETHER, VTags.Biomes.IS_DRY_NETHER);
			tag(Biomes.BASALT_DELTAS, VTags.Biomes.IS_HOT_NETHER, VTags.Biomes.IS_DRY_NETHER);
			tag(Biomes.MANGROVE_SWAMP, VTags.Biomes.IS_WET_OVERWORLD, VTags.Biomes.IS_HOT_OVERWORLD, VTags.Biomes.IS_SWAMP);
			tag(Biomes.DEEP_DARK, VTags.Biomes.IS_UNDERGROUND, VTags.Biomes.IS_RARE, VTags.Biomes.IS_SPOOKY);

            tag(VTags.Biomes.IS_HOT).addTag(VTags.Biomes.IS_HOT_OVERWORLD).addTag(VTags.Biomes.IS_HOT_NETHER).addOptionalTag(VTags.Biomes.IS_HOT_END.location());
            tag(VTags.Biomes.IS_COLD).addTag(VTags.Biomes.IS_COLD_OVERWORLD).addOptionalTag(VTags.Biomes.IS_COLD_NETHER.location()).addTag(VTags.Biomes.IS_COLD_END);
            tag(VTags.Biomes.IS_SPARSE).addTag(VTags.Biomes.IS_SPARSE_OVERWORLD).addOptionalTag(VTags.Biomes.IS_SPARSE_NETHER.location()).addOptionalTag(VTags.Biomes.IS_SPARSE_END.location());
            tag(VTags.Biomes.IS_DENSE).addTag(VTags.Biomes.IS_DENSE_OVERWORLD).addOptionalTag(VTags.Biomes.IS_DENSE_NETHER.location()).addOptionalTag(VTags.Biomes.IS_DENSE_END.location());
            tag(VTags.Biomes.IS_WET).addTag(VTags.Biomes.IS_WET_OVERWORLD).addOptionalTag(VTags.Biomes.IS_WET_NETHER.location()).addOptionalTag(VTags.Biomes.IS_WET_END.location());
            tag(VTags.Biomes.IS_DRY).addTag(VTags.Biomes.IS_DRY_OVERWORLD).addTag(VTags.Biomes.IS_DRY_NETHER).addTag(VTags.Biomes.IS_DRY_END);
			tag(VTags.Biomes.IS_TEMPERATE).addTag(VTags.Biomes.IS_TEMPERATE_OVERWORLD);

			tag(VTags.Biomes.IS_WATER).addTag(BiomeTags.IS_OCEAN).addTag(BiomeTags.IS_RIVER);
			tag(VTags.Biomes.IS_MOUNTAIN).addTag(VTags.Biomes.IS_PEAK).addTag(VTags.Biomes.IS_SLOPE);

			tag(net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags.ICY)
					.add(Biomes.FROZEN_PEAKS)
					.add(Biomes.ICE_SPIKES);

			tag(ConventionalBiomeTags.AQUATIC_ICY)
					.add(Biomes.FROZEN_RIVER)
					.add(Biomes.DEEP_FROZEN_OCEAN)
					.add(Biomes.FROZEN_OCEAN);

			this.tag(VTags.Biomes.IS_GRASSLAND).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
			this.tag(VTags.Biomes.IS_ICY).add(Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES);
			this.tag(VTags.Biomes.IS_OUTER_END)
					.add(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS);

			this.tag(VTags.Biomes.WITHOUT_DEFAULT_MONSTER_SPAWNS).add(Biomes.MUSHROOM_FIELDS, Biomes.DEEP_DARK);
			TagAppender<Biome> withMonsterSpawns = this.tag(VTags.Biomes.WITH_DEFAULT_MONSTER_SPAWNS);
			MultiNoiseBiomeSource.Preset.OVERWORLD.possibleBiomes().forEach((biome) -> {
				if (biome != Biomes.MUSHROOM_FIELDS && biome != Biomes.DEEP_DARK)
					withMonsterSpawns.add(biome);
			});
		}

		@SafeVarargs
		private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags) {
			for (TagKey<Biome> key : tags) {
				tag(key).add(biome);
			}
		}

		@SafeVarargs
		private void tag(TagKey<Biome> tag, ResourceKey<Biome>... biomes) {
			tag(tag).add(biomes);
		}
	}

	private static class VItemTagsProvider extends CustomTagProviders.CustomItemTagProvider {
		private VItemTagsProvider(FabricDataGenerator dataGenerator, CustomTagProviders.CustomBlockTagProvider blockTagProvider) {
			super(dataGenerator, blockTagProvider);
		}

		@Override
		protected void generateTags() {
			copyOreTags();
			copyStorageBlockTags();
			copyGlassTags();
			copyFenceTags();
			copyCobblestoneTags();
			copyChestTags();
			copyMiscTags();

			generateGemTags();
			generateRawTags();
			generateRodTags();
			generateHeadTags();
			generateIngotTags();
			generateNuggetTags();
			generateMobLootTags();
			generateCropTags();
			generateDustTags();
			generateSeedTags();
			generateMiscTags();
			generateToolTags();
			generateArmorTags();
			generateFoodTags();
			generateVillageJobSitesTag();
			generateSandstoneTags();
		}

		private void copyOreTags() {
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
		}

		private void copyStorageBlockTags() {
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
		}

		private void copyGlassTags() {
			copy(VTags.Blocks.GLASS, VTags.Items.GLASS);
			copy(VTags.Blocks.GLASS_SILICA, VTags.Items.GLASS_SILICA);
			copy(VTags.Blocks.GLASS_TINTED, VTags.Items.GLASS_TINTED);
			copy(VTags.Blocks.GLASS_COLORLESS, VTags.Items.GLASS_COLORLESS);
			copyColored(VTags.Blocks.GLASS, VTags.Items.GLASS);
			copy(VTags.Blocks.GLASS_PANES_COLORLESS, VTags.Items.GLASS_PANES_COLORLESS);
			copy(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
			copy(VTags.Blocks.STAINED_GLASS_PANES, VTags.Items.STAINED_GLASS_PANES);
			copyColored(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
		}

		private void copyFenceTags() {
			copy(VTags.Blocks.FENCE_GATES, VTags.Items.FENCE_GATES);
			copy(VTags.Blocks.FENCE_GATES_WOODEN, VTags.Items.FENCE_GATES_WOODEN);
			copy(VTags.Blocks.FENCES, VTags.Items.FENCES);
			copy(VTags.Blocks.FENCES_NETHER_BRICK, VTags.Items.FENCES_NETHER_BRICK);
			copy(VTags.Blocks.FENCES_WOODEN, VTags.Items.FENCES_WOODEN);
		}

		private void copyCobblestoneTags() {
			copy(VTags.Blocks.COBBLESTONE_NORMAL, VTags.Items.COBBLESTONE_NORMAL);
			copy(VTags.Blocks.COBBLESTONE_INFESTED, VTags.Items.COBBLESTONE_INFESTED);
			copy(VTags.Blocks.COBBLESTONE_MOSSY, VTags.Items.COBBLESTONE_MOSSY);
			copy(VTags.Blocks.COBBLESTONE_DEEPSLATE, VTags.Items.COBBLESTONE_DEEPSLATE);
			copy(VTags.Blocks.COBBLESTONE_DEEPSLATE, ItemTags.STONE_CRAFTING_MATERIALS);
			copy(VTags.Blocks.COBBLESTONE_NORMAL, ItemTags.STONE_CRAFTING_MATERIALS);

			getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS)
					.add(Items.ANDESITE, Items.DIORITE, Items.GRANITE);
			getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS)
					.addTag(ItemTags.STONE_CRAFTING_MATERIALS);
		}

		private void copyChestTags() {
			copy(VTags.Blocks.CHESTS, VTags.Items.CHESTS);
			copy(VTags.Blocks.CHESTS_ENDER, VTags.Items.CHESTS_ENDER);
			copy(VTags.Blocks.CHESTS_TRAPPED, VTags.Items.CHESTS_TRAPPED);
			copy(VTags.Blocks.CHESTS_WOODEN, VTags.Items.CHESTS_WOODEN);
		}

		private void copyMiscTags() {
			copy(VTags.Blocks.END_STONES, VTags.Items.END_STONES);
			copy(VTags.Blocks.GRAVEL, VTags.Items.GRAVEL);
			copy(VTags.Blocks.NETHERRACK, VTags.Items.NETHERRACK);
			copy(VTags.Blocks.OBSIDIAN, VTags.Items.OBSIDIAN);
		}

		private void generateGemTags() {
			getOrCreateTagBuilder(VTags.Items.GEMS)
					.addTag(VTags.Items.GEMS_AMETHYST)
					.addTag(VTags.Items.GEMS_DIAMOND)
					.addTag(VTags.Items.GEMS_EMERALD)
					.addTag(VTags.Items.GEMS_LAPIS)
					.addTag(VTags.Items.GEMS_PRISMARINE)
					.addTag(VTags.Items.GEMS_QUARTZ);

			getOrCreateTagBuilder(VTags.Items.GEMS_AMETHYST)
					.add(Items.AMETHYST_SHARD);
			getOrCreateTagBuilder(VTags.Items.GEMS_DIAMOND)
					.add(Items.DIAMOND);
			getOrCreateTagBuilder(VTags.Items.GEMS_EMERALD)
					.add(Items.EMERALD);
			getOrCreateTagBuilder(VTags.Items.GEMS_LAPIS)
					.add(Items.LAPIS_LAZULI);
			getOrCreateTagBuilder(VTags.Items.GEMS_PRISMARINE)
					.add(Items.PRISMARINE_CRYSTALS);
			getOrCreateTagBuilder(VTags.Items.GEMS_QUARTZ)
					.add(Items.QUARTZ);
		}

		private void generateRawTags() {
			getOrCreateTagBuilder(VTags.Items.RAW_MATERIALS)
					.addTag(VTags.Items.RAW_MATERIALS_COPPER)
					.addTag(VTags.Items.RAW_MATERIALS_GOLD)
					.addTag(VTags.Items.RAW_MATERIALS_IRON);
			getOrCreateTagBuilder(VTags.Items.RAW_MATERIALS_COPPER).add(Items.RAW_COPPER);
			getOrCreateTagBuilder(VTags.Items.RAW_MATERIALS_GOLD).add(Items.RAW_GOLD);
			getOrCreateTagBuilder(VTags.Items.RAW_MATERIALS_IRON).add(Items.RAW_IRON);
		}

		private void generateRodTags() {
			getOrCreateTagBuilder(VTags.Items.RODS)
					.addTag(VTags.Items.RODS_BLAZE)
					.addTag(VTags.Items.RODS_WOODEN);
			getOrCreateTagBuilder(VTags.Items.RODS_BLAZE).add(Items.BLAZE_ROD);
			getOrCreateTagBuilder(VTags.Items.RODS_WOODEN).add(Items.STICK);
		}

		private void generateHeadTags() {
			getOrCreateTagBuilder(VTags.Items.HEADS)
					.add(Items.CREEPER_HEAD)
					.add(Items.DRAGON_HEAD)
					.add(Items.PLAYER_HEAD)
					.add(Items.SKELETON_SKULL)
					.add(Items.WITHER_SKELETON_SKULL)
					.add(Items.ZOMBIE_HEAD);
		}

		private void generateIngotTags() {
			getOrCreateTagBuilder(VTags.Items.INGOTS)
					.addTag(VTags.Items.INGOTS_BRICK)
					.addTag(VTags.Items.INGOTS_COPPER)
					.addTag(VTags.Items.INGOTS_GOLD)
					.addTag(VTags.Items.INGOTS_IRON)
					.addTag(VTags.Items.INGOTS_NETHERITE)
					.addTag(VTags.Items.INGOTS_NETHER_BRICK);
			getOrCreateTagBuilder(VTags.Items.INGOTS_BRICK)
					.add(Items.BRICK);
			getOrCreateTagBuilder(VTags.Items.INGOTS_COPPER)
					.add(Items.COPPER_INGOT);
			getOrCreateTagBuilder(VTags.Items.INGOTS_GOLD)
					.add(Items.GOLD_INGOT);
			getOrCreateTagBuilder(VTags.Items.INGOTS_IRON)
					.add(Items.IRON_INGOT);
			getOrCreateTagBuilder(VTags.Items.INGOTS_NETHERITE)
					.add(Items.NETHERITE_INGOT);
			getOrCreateTagBuilder(VTags.Items.INGOTS_NETHER_BRICK)
					.add(Items.NETHER_BRICK);
		}

		private void generateNuggetTags() {
			getOrCreateTagBuilder(VTags.Items.NUGGETS)
					.addTag(VTags.Items.NUGGETS_IRON)
					.addTag(VTags.Items.NUGGETS_GOLD);
			tagCustom(VTags.Items.NUGGETS_IRON).add(Items.IRON_NUGGET);
			tagCustom(VTags.Items.NUGGETS_GOLD).add(Items.GOLD_NUGGET);
		}

		private void generateMobLootTags() {
			getOrCreateTagBuilder(VTags.Items.BONES)
					.add(Items.BONE);
			getOrCreateTagBuilder(VTags.Items.EGGS)
					.add(Items.EGG);
			getOrCreateTagBuilder(VTags.Items.ENDER_PEARLS)
					.add(Items.ENDER_PEARL);
			getOrCreateTagBuilder(VTags.Items.FEATHERS)
					.add(Items.FEATHER);
			getOrCreateTagBuilder(VTags.Items.GUNPOWDER)
					.add(Items.GUNPOWDER);
			getOrCreateTagBuilder(VTags.Items.LEATHER)
					.add(Items.LEATHER);
			getOrCreateTagBuilder(VTags.Items.MUSHROOMS)
					.add(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
			getOrCreateTagBuilder(VTags.Items.NETHER_STARS)
					.add(Items.NETHER_STAR);
			getOrCreateTagBuilder(VTags.Items.STRING)
					.add(Items.STRING);
			getOrCreateTagBuilder(VTags.Items.SLIMEBALLS)
					.add(Items.SLIME_BALL);
		}

		private void generateCropTags() {
			getOrCreateTagBuilder(VTags.Items.CROPS)
					.addTag(VTags.Items.CROPS_BEETROOT)
					.addTag(VTags.Items.CROPS_CARROT)
					.addTag(VTags.Items.CROPS_NETHER_WART)
					.addTag(VTags.Items.CROPS_POTATO)
					.addTag(VTags.Items.CROPS_WHEAT);

			getOrCreateTagBuilder(VTags.Items.CROPS_BEETROOT)
					.add(Items.BEETROOT);
			getOrCreateTagBuilder(VTags.Items.CROPS_CARROT)
					.add(Items.CARROT);
			getOrCreateTagBuilder(VTags.Items.CROPS_NETHER_WART)
					.add(Items.NETHER_WART);
			getOrCreateTagBuilder(VTags.Items.CROPS_POTATO)
					.add(Items.POTATO);
			getOrCreateTagBuilder(VTags.Items.CROPS_WHEAT)
					.add(Items.WHEAT);
		}

		private void generateDustTags() {
			getOrCreateTagBuilder(VTags.Items.DUSTS)
					.addTag(VTags.Items.DUSTS_GLOWSTONE)
					.addTag(VTags.Items.DUSTS_PRISMARINE)
					.addTag(VTags.Items.DUSTS_REDSTONE);

			getOrCreateTagBuilder(VTags.Items.DUSTS_GLOWSTONE)
					.add(Items.GLOWSTONE_DUST);
			getOrCreateTagBuilder(VTags.Items.DUSTS_PRISMARINE)
					.add(Items.PRISMARINE_SHARD);
			getOrCreateTagBuilder(VTags.Items.DUSTS_REDSTONE)
					.add(Items.REDSTONE);
		}

		private void generateSeedTags() {
			getOrCreateTagBuilder(VTags.Items.SEEDS)
					.addTag(VTags.Items.SEEDS_BEETROOT)
					.addTag(VTags.Items.SEEDS_MELON)
					.addTag(VTags.Items.SEEDS_PUMPKIN)
					.addTag(VTags.Items.SEEDS_WHEAT);

			getOrCreateTagBuilder(VTags.Items.SEEDS_BEETROOT)
					.add(Items.BEETROOT_SEEDS);
			getOrCreateTagBuilder(VTags.Items.SEEDS_MELON)
					.add(Items.MELON_SEEDS);
			getOrCreateTagBuilder(VTags.Items.SEEDS_PUMPKIN)
					.add(Items.PUMPKIN_SEEDS);
			getOrCreateTagBuilder(VTags.Items.SEEDS_WHEAT)
					.add(Items.WHEAT_SEEDS);
		}

		private void generateMiscTags() {
			getOrCreateTagBuilder(VTags.Items.BOOKSHELVES)
					.add(Items.BOOKSHELF);
			addColored(tagCustom(VTags.Items.DYES)::addTags);
			getOrCreateTagBuilder(VTags.Items.ENCHANTING_FUELS)
					.addTag(VTags.Items.GEMS_LAPIS);
			getOrCreateTagBuilder(VTags.Items.SHEARS)
					.add(Items.SHEARS);
		}

		private void generateToolTags() {
			getOrCreateTagBuilder(VTags.Items.TOOLS)
					.addTag(VTags.Items.TOOLS_SWORDS)
					.addTag(VTags.Items.TOOLS_AXES)
					.addTag(VTags.Items.TOOLS_PICKAXES)
					.addTag(VTags.Items.TOOLS_SHOVELS)
					.addTag(VTags.Items.TOOLS_HOES)
					.addTag(VTags.Items.TOOLS_SHIELDS)
					.addTag(VTags.Items.TOOLS_BOWS)
					.addTag(VTags.Items.TOOLS_CROSSBOWS)
					.addTag(VTags.Items.TOOLS_FISHING_RODS)
					.addTag(VTags.Items.TOOLS_TRIDENTS);

			getOrCreateTagBuilder(VTags.Items.TOOLS_SWORDS)
					.add(Items.WOODEN_SWORD)
					.add(Items.STONE_SWORD)
					.add(Items.IRON_SWORD)
					.add(Items.GOLDEN_SWORD)
					.add(Items.DIAMOND_SWORD)
					.add(Items.NETHERITE_SWORD);
			getOrCreateTagBuilder(VTags.Items.TOOLS_AXES).add(Items.WOODEN_AXE)
					.add(Items.STONE_AXE)
					.add(Items.IRON_AXE)
					.add(Items.GOLDEN_AXE)
					.add(Items.DIAMOND_AXE)
					.add(Items.NETHERITE_AXE);
			getOrCreateTagBuilder(VTags.Items.TOOLS_PICKAXES).add(Items.WOODEN_PICKAXE)
					.add(Items.STONE_PICKAXE)
					.add(Items.IRON_PICKAXE)
					.add(Items.GOLDEN_PICKAXE)
					.add(Items.DIAMOND_PICKAXE)
					.add(Items.NETHERITE_PICKAXE);
			getOrCreateTagBuilder(VTags.Items.TOOLS_SHOVELS).add(Items.WOODEN_SHOVEL)
					.add(Items.STONE_SHOVEL)
					.add(Items.IRON_SHOVEL)
					.add(Items.GOLDEN_SHOVEL)
					.add(Items.DIAMOND_SHOVEL)
					.add(Items.NETHERITE_SHOVEL);
			getOrCreateTagBuilder(VTags.Items.TOOLS_HOES).add(Items.WOODEN_HOE)
					.add(Items.STONE_HOE)
					.add(Items.IRON_HOE)
					.add(Items.GOLDEN_HOE)
					.add(Items.DIAMOND_HOE)
					.add(Items.NETHERITE_HOE);
			getOrCreateTagBuilder(VTags.Items.TOOLS_SHIELDS)
					.add(Items.SHIELD);
			getOrCreateTagBuilder(VTags.Items.TOOLS_BOWS)
					.add(Items.BOW);
			getOrCreateTagBuilder(VTags.Items.TOOLS_CROSSBOWS)
					.add(Items.CROSSBOW);
			getOrCreateTagBuilder(VTags.Items.TOOLS_FISHING_RODS)
					.add(Items.FISHING_ROD);
			getOrCreateTagBuilder(VTags.Items.TOOLS_TRIDENTS)
					.add(Items.TRIDENT);
		}

		private void generateArmorTags() {
			getOrCreateTagBuilder(VTags.Items.ARMORS)
					.addTag(VTags.Items.ARMORS_HELMETS)
					.addTag(VTags.Items.ARMORS_CHESTPLATES)
					.addTag(VTags.Items.ARMORS_LEGGINGS)
					.addTag(VTags.Items.ARMORS_BOOTS);

			getOrCreateTagBuilder(VTags.Items.ARMORS_HELMETS)
					.add(Items.LEATHER_HELMET)
					.add(Items.TURTLE_HELMET)
					.add(Items.CHAINMAIL_HELMET)
					.add(Items.IRON_HELMET)
					.add(Items.GOLDEN_HELMET)
					.add(Items.DIAMOND_HELMET)
					.add(Items.NETHERITE_HELMET);
			getOrCreateTagBuilder(VTags.Items.ARMORS_CHESTPLATES)
					.add(Items.LEATHER_CHESTPLATE)
					.add(Items.CHAINMAIL_CHESTPLATE)
					.add(Items.IRON_CHESTPLATE)
					.add(Items.GOLDEN_CHESTPLATE)
					.add(Items.DIAMOND_CHESTPLATE)
					.add(Items.NETHERITE_CHESTPLATE);
			getOrCreateTagBuilder(VTags.Items.ARMORS_LEGGINGS)
					.add(Items.LEATHER_LEGGINGS)
					.add(Items.CHAINMAIL_LEGGINGS)
					.add(Items.IRON_LEGGINGS)
					.add(Items.GOLDEN_LEGGINGS)
					.add(Items.DIAMOND_LEGGINGS)
					.add(Items.NETHERITE_LEGGINGS);
			getOrCreateTagBuilder(VTags.Items.ARMORS_BOOTS)
					.add(Items.LEATHER_BOOTS)
					.add(Items.CHAINMAIL_BOOTS)
					.add(Items.IRON_BOOTS)
					.add(Items.GOLDEN_BOOTS)
					.add(Items.DIAMOND_BOOTS)
					.add(Items.NETHERITE_BOOTS);
		}

		private void generateFoodTags() {
			getOrCreateTagBuilder(ConventionalItemTags.MEAT)
					.addOptionalTag(ConventionalItemTags.RAW_MEAT)
					.addOptionalTag(ConventionalItemTags.COOKED_MEAT);

			getOrCreateTagBuilder(ConventionalItemTags.POISONOUS_FOOD)
					.add(Items.ROTTEN_FLESH)
					.add(Items.SPIDER_EYE)
					.add(Items.SUSPICIOUS_STEW)
					.add(Items.POISONOUS_POTATO)
					.add(Items.PUFFERFISH);

			getOrCreateTagBuilder(ConventionalItemTags.RAW_MEAT)
					.addOptionalTag(ConventionalItemTags.RAW_FISH)
					.add(Items.BEEF)
					.add(Items.MUTTON)
					.add(Items.CHICKEN)
					.add(Items.PORKCHOP)
					.add(Items.RABBIT);

			getOrCreateTagBuilder(ConventionalItemTags.COOKED_MEAT)
					.addOptionalTag(ConventionalItemTags.COOKED_FISH)
					.add(Items.COOKED_BEEF)
					.add(Items.COOKED_MUTTON)
					.add(Items.COOKED_CHICKEN)
					.add(Items.COOKED_PORKCHOP)
					.add(Items.COOKED_RABBIT);

			getOrCreateTagBuilder(ConventionalItemTags.RAW_FISH)
					.add(Items.COD)
					.add(Items.SALMON);

			getOrCreateTagBuilder(ConventionalItemTags.COOKED_FISH)
					.add(Items.COOKED_COD)
					.add(Items.COOKED_SALMON);
		}

		private void generateVillageJobSitesTag() {
			copy(VTags.Blocks.BARRELS_WOODEN, VTags.Items.BARRELS_WOODEN);
			copy(VTags.Blocks.BARRELS, VTags.Items.BARRELS);

			getOrCreateTagBuilder(ConventionalItemTags.VILLAGER_JOB_SITES)
					.addOptionalTag(VTags.Items.BARRELS)
					.add(Items.BARREL)
					.add(Items.BLAST_FURNACE)
					.add(Items.BREWING_STAND)
					.add(Items.CARTOGRAPHY_TABLE)
					.add(Items.CAULDRON)
					.add(Items.COMPOSTER)
					.add(Items.FLETCHING_TABLE)
					.add(Items.GRINDSTONE)
					.add(Items.LECTERN)
					.add(Items.LOOM)
					.add(Items.SMITHING_TABLE)
					.add(Items.SMOKER)
					.add(Items.STONECUTTER);
		}

		private void generateSandstoneTags() {
			copy(VTags.Blocks.SAND, VTags.Items.SAND);
			copy(VTags.Blocks.SAND_COLORLESS, VTags.Items.SAND_COLORLESS);
			copy(VTags.Blocks.SAND_RED, VTags.Items.SAND_RED);
			copy(VTags.Blocks.SANDSTONE, VTags.Items.SANDSTONE);

			getOrCreateTagBuilder(ConventionalItemTags.NORMAL_SANDSTONE_BLOCKS)
					.add(Items.SANDSTONE)
					.add(Items.CUT_SANDSTONE)
					.add(Items.CHISELED_SANDSTONE)
					.add(Items.SMOOTH_SANDSTONE);
			getOrCreateTagBuilder(ConventionalItemTags.RED_SANDSTONE_BLOCKS)
					.add(Items.RED_SANDSTONE)
					.add(Items.CUT_RED_SANDSTONE)
					.add(Items.CHISELED_RED_SANDSTONE)
					.add(Items.SMOOTH_RED_SANDSTONE);
			getOrCreateTagBuilder(ConventionalItemTags.SANDSTONE_BLOCKS)
					.addOptionalTag(ConventionalItemTags.NORMAL_SANDSTONE_BLOCKS)
					.addOptionalTag(ConventionalItemTags.RED_SANDSTONE_BLOCKS);

			getOrCreateTagBuilder(ConventionalItemTags.NORMAL_SANDSTONE_SLABS)
					.add(Items.SANDSTONE_SLAB)
//                .add(Items.CUT_SANDSTONE_SLAB)
					.add(Items.SMOOTH_SANDSTONE_SLAB);
			getOrCreateTagBuilder(ConventionalItemTags.RED_SANDSTONE_SLABS)
					.add(Items.RED_SANDSTONE_SLAB)
					.add(Items.CUT_RED_SANDSTONE_SLAB)
					.add(Items.SMOOTH_RED_SANDSTONE_SLAB);
			getOrCreateTagBuilder(ConventionalItemTags.SANDSTONE_SLABS)
					.addOptionalTag(ConventionalItemTags.NORMAL_SANDSTONE_SLABS)
					.addOptionalTag(ConventionalItemTags.RED_SANDSTONE_SLABS);

			getOrCreateTagBuilder(ConventionalItemTags.NORMAL_SANDSTONE_STAIRS)
					.add(Items.SANDSTONE_STAIRS)
					.add(Items.SMOOTH_SANDSTONE_STAIRS);
			getOrCreateTagBuilder(ConventionalItemTags.RED_SANDSTONE_STAIRS)
					.add(Items.RED_SANDSTONE_STAIRS)
					.add(Items.SMOOTH_RED_SANDSTONE_STAIRS);
			getOrCreateTagBuilder(ConventionalItemTags.SANDSTONE_STAIRS)
					.addOptionalTag(ConventionalItemTags.NORMAL_SANDSTONE_SLABS)
					.addOptionalTag(ConventionalItemTags.RED_SANDSTONE_SLABS);
		}

		private void addColored(Consumer<TagKey<Item>> consumer) {
			String prefix = VTags.Items.DYES.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
			for (DyeColor color : DyeColor.values()) {
				ResourceLocation key = new ResourceLocation("minecraft",
						"{color}_dye".replace("{color}", color.getName()));
				TagKey<Item> tag = getForgeItemTag(prefix + color.getName());
				Item item = Registry.ITEM.get(key);
				if (item == Items.AIR)
					throw new IllegalStateException("Unknown vanilla item: " + key);
				tag(tag).add(item);
				consumer.accept(tag);
			}
		}

		private void copyColored(TagKey<Block> blockGroup, TagKey<Item> itemGroup) {
			String blockPre = blockGroup.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
			String itemPre = itemGroup.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
			for (DyeColor color : DyeColor.values()) {
				TagKey<Block> from = getForgeBlockTag(blockPre + color.getName());
				TagKey<Item> to = getForgeItemTag(itemPre + color.getName());
				copy(from, to);
			}
			copy(getForgeBlockTag(blockPre + "colorless"), getForgeItemTag(itemPre + "colorless"));
		}

		@SuppressWarnings("unchecked")
		private TagKey<Block> getForgeBlockTag(String name) {
			try {
				name = name.toUpperCase(Locale.ENGLISH);
				return (TagKey<Block>) VTags.Blocks.class.getDeclaredField(name).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(VTags.Blocks.class.getName() + " is missing tag name: " + name);
			}
		}

		@SuppressWarnings("unchecked")
		private TagKey<Item> getForgeItemTag(String name) {
			try {
				name = name.toUpperCase(Locale.ENGLISH);
				return (TagKey<Item>) VTags.Items.class.getDeclaredField(name).get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(VTags.Items.class.getName() + " is missing tag name: " + name);
			}
		}
	}

	private static class VEntityTypeTagsProvider extends CustomTagProviders.VEntityTagProvider {
		public VEntityTypeTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
			tag(VTags.EntityTypes.BOSSES).addTag(VTags.EntityTypes.DRAGONS).add(EntityType.WITHER);

			tag(VTags.EntityTypes.DRAGONS).add(EntityType.ENDER_DRAGON);

			tag(VTags.EntityTypes.GOLEMS).add(EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM);

			tag(VTags.EntityTypes.BUILDABLE_MOBS).addTag(VTags.EntityTypes.GOLEMS).add(EntityType.WITHER);

			tag(VTags.EntityTypes.BIG_NOSES).addTag(VTags.EntityTypes.ILLAGERS)
					.add(EntityType.WITCH, EntityType.VILLAGER, EntityType.ZOMBIE_VILLAGER);
			tag(VTags.EntityTypes.ILLAGERS).add(EntityType.PILLAGER, EntityType.ILLUSIONER, EntityType.VINDICATOR,
					EntityType.EVOKER);

			/*
			 * Many of these mob types do not appear in vanilla Minecraft, and are thus left empty here
			 */
			tagCustom(VTags.EntityTypes.ELEMENTAL).addTags(VTags.EntityTypes.ELEMENTAL_FIRE,
					VTags.EntityTypes.ELEMENTAL_ICE,
					VTags.EntityTypes.ELEMENTAL_METAL);
			tag(VTags.EntityTypes.ELEMENTAL_FIRE).add(EntityType.BLAZE);
			tag(VTags.EntityTypes.ELEMENTAL_ICE).add(EntityType.SNOW_GOLEM);
			tag(VTags.EntityTypes.ELEMENTAL_METAL).add(EntityType.IRON_GOLEM);

			tagCustom(VTags.EntityTypes.ELEMENTAL_ATTACKS).addTags(VTags.EntityTypes.ELEMENTAL_ATTACKS_ELECTRIC,
					VTags.EntityTypes.ELEMENTAL_ATTACKS_FIRE,
					VTags.EntityTypes.ELEMENTAL_ATTACKS_ICE);
			tag(VTags.EntityTypes.ELEMENTAL_ATTACKS_ELECTRIC).add(EntityType.LIGHTNING_BOLT);
			tag(VTags.EntityTypes.ELEMENTAL_ATTACKS_FIRE).add(EntityType.FIREBALL, EntityType.SMALL_FIREBALL);
			tag(VTags.EntityTypes.ELEMENTAL_ATTACKS_ICE).add(EntityType.SNOWBALL);

			tag(VTags.EntityTypes.ARTHROPODS).add(EntityType.BEE, EntityType.CAVE_SPIDER, EntityType.ENDERMITE,
					EntityType.SILVERFISH, EntityType.SPIDER);

			tag(VTags.EntityTypes.AVIANS).addTag(VTags.EntityTypes.AVIANS_FOWLS).add(EntityType.PARROT);
			tag(VTags.EntityTypes.AVIANS_FOWLS).add(EntityType.CHICKEN);

			tag(VTags.EntityTypes.AQUATIC).add(EntityType.AXOLOTL, EntityType.COD, EntityType.DOLPHIN,
					EntityType.ELDER_GUARDIAN,
					EntityType.GLOW_SQUID, EntityType.GUARDIAN, EntityType.PUFFERFISH,
					EntityType.SALMON, EntityType.SQUID,
					EntityType.TROPICAL_FISH,
					EntityType.TURTLE/*, EntityType.FROG, EntityType.TADPOLE*/);
			tag(VTags.EntityTypes.FISH).add(EntityType.COD, EntityType.PUFFERFISH, EntityType.SALMON,
					EntityType.TROPICAL_FISH);
			tag(VTags.EntityTypes.CEPHALOPODS).add(EntityType.GLOW_SQUID, EntityType.SQUID);
			tag(VTags.EntityTypes.GUARDIANS).add(EntityType.ELDER_GUARDIAN, EntityType.GUARDIAN);

			tag(VTags.EntityTypes.REPTILES).add(EntityType.TURTLE);

			tagCustom(VTags.EntityTypes.MAMMALS)
					.addTags(VTags.EntityTypes.MAMMALS_BOVINES, VTags.EntityTypes.MAMMALS_CAMELIDS,
							VTags.EntityTypes.MAMMALS_CANIDS,
							VTags.EntityTypes.MAMMALS_CAPRINES, VTags.EntityTypes.MAMMALS_EQUINES,
							VTags.EntityTypes.MAMMALS_FELINES,
							VTags.EntityTypes.MAMMALS_SWINES, VTags.EntityTypes.MAMMALS_URSIDS
					).add(EntityType.BAT, EntityType.RABBIT);
			tag(VTags.EntityTypes.MAMMALS_BOVINES).addTag(VTags.EntityTypes.MAMMALS_BOVINES_CATTLE)
					.add(EntityType.SHEEP);
			tag(VTags.EntityTypes.MAMMALS_BOVINES_CATTLE).add(EntityType.COW, EntityType.MOOSHROOM);
			tag(VTags.EntityTypes.MAMMALS_CAMELIDS).add(EntityType.LLAMA, EntityType.TRADER_LLAMA);
			tag(VTags.EntityTypes.MAMMALS_CANIDS).add(EntityType.FOX, EntityType.WOLF);
			tag(VTags.EntityTypes.MAMMALS_CAPRINES).add(EntityType.GOAT);
			tag(VTags.EntityTypes.MAMMALS_EQUINES).add(EntityType.DONKEY, EntityType.HORSE, EntityType.ZOMBIE_HORSE,
					EntityType.SKELETON_HORSE, EntityType.MULE);
			tag(VTags.EntityTypes.MAMMALS_FELINES).add(EntityType.CAT, EntityType.OCELOT);
			tag(VTags.EntityTypes.MAMMALS_SWINES).add(EntityType.HOGLIN, EntityType.PIG);
			tag(VTags.EntityTypes.MAMMALS_URSIDS).add(EntityType.PANDA, EntityType.POLAR_BEAR);

			tag(VTags.EntityTypes.GHOSTS).add(EntityType.VEX, EntityType.PHANTOM, EntityType.GHAST);

			tagCustom(VTags.EntityTypes.MILKABLE).addTags(VTags.EntityTypes.MAMMALS_BOVINES_CATTLE,
					VTags.EntityTypes.MAMMALS_CAPRINES);
			tag(VTags.EntityTypes.MUSHROOM_COWS).add(EntityType.MOOSHROOM);

			tag(VTags.EntityTypes.BLIND_MOBS);

			tagCustom(VTags.EntityTypes.FLYING).addTags(VTags.EntityTypes.BOSSES, VTags.EntityTypes.GHOSTS)
					.add(EntityType.BAT, EntityType.BEE,
							EntityType.ENDER_DRAGON, EntityType.PARROT, EntityType.WITHER);
			tagCustom(VTags.EntityTypes.LAND).addTags(VTags.EntityTypes.CREEPERS, VTags.EntityTypes.SKELETONS,
							VTags.EntityTypes.ZOMBIES)
					.add(EntityType.BLAZE, EntityType.CAT, EntityType.CAVE_SPIDER,
							EntityType.CHICKEN, EntityType.DONKEY, EntityType.ENDERMAN,
							EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX,
							EntityType.GOAT, EntityType.HOGLIN, EntityType.HORSE,
							EntityType.ILLUSIONER,
							EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MAGMA_CUBE,
							EntityType.MULE, EntityType.OCELOT, EntityType.PANDA, EntityType.PIG,
							EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER,
							EntityType.PLAYER, EntityType.POLAR_BEAR, EntityType.RABBIT,
							EntityType.RAVAGER, EntityType.SHEEP, EntityType.SHULKER,
							EntityType.SILVERFISH, EntityType.SLIME, EntityType.SNOW_GOLEM,
							EntityType.SPIDER,
							EntityType.TRADER_LLAMA, EntityType.VILLAGER, EntityType.VINDICATOR,
							EntityType.WANDERING_TRADER, EntityType.WITCH, EntityType.WOLF
					);

			tag(VTags.EntityTypes.VOLCANIC).add(EntityType.STRIDER);
			tag(VTags.EntityTypes.HELL_MOBS).addTag(VTags.EntityTypes.VOLCANIC)
					.add(EntityType.MAGMA_CUBE, EntityType.GHAST, EntityType.PIGLIN,
							EntityType.PIGLIN_BRUTE,
							EntityType.BLAZE, EntityType.WITHER_SKELETON,
							EntityType.ZOMBIFIED_PIGLIN, EntityType.HOGLIN
					);

			tagCustom(VTags.EntityTypes.ENEMIES).addTags(VTags.EntityTypes.BOSSES,
							VTags.EntityTypes.CREEPERS/*, EntityTypeTags.RAIDERS*/)
					.add(EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.DROWNED,
							EntityType.ELDER_GUARDIAN, EntityType.ENDERMAN,
							EntityType.ENDERMITE,
							EntityType.GHAST, EntityType.GUARDIAN, EntityType.HOGLIN,
							EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM,
							EntityType.PIGLIN,
							EntityType.PIGLIN_BRUTE, EntityType.SHULKER, EntityType.SILVERFISH,
							EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER,
							EntityType.STRAY,
							EntityType.VEX, EntityType.WITHER_SKELETON, EntityType.ZOGLIN,
							EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,
							EntityType.ZOMBIFIED_PIGLIN
					);
			tag(VTags.EntityTypes.CREEPERS).add(EntityType.CREEPER);

			tagCustom(VTags.EntityTypes.UNDEAD).addTags(VTags.EntityTypes.SKELETONS, VTags.EntityTypes.ZOMBIES)
					.add(EntityType.PHANTOM, EntityType.WITHER);
			tag(VTags.EntityTypes.SKELETONS).add(EntityType.SKELETON, EntityType.WITHER_SKELETON,
					EntityType.SKELETON_HORSE);
			tag(VTags.EntityTypes.ZOMBIES).add(EntityType.DROWNED, EntityType.HUSK, EntityType.ZOGLIN,
					EntityType.ZOMBIE,
					EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER,
					EntityType.ZOMBIFIED_PIGLIN);

			tag(VTags.EntityTypes.NPC).add(EntityType.PIGLIN, EntityType.VILLAGER, EntityType.WANDERING_TRADER);
		}
	}

	private static class VRecipeReplacementProvider extends FabricRecipeProvider {
		private final Map<Item, TagKey<Item>> replacements = new HashMap<>();
		private final Set<ResourceLocation> excludes = new HashSet<>();

		private VRecipeReplacementProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		private void exclude(ItemLike item) {
			excludes.add(Registry.ITEM.getKey(item.asItem()));
		}

		private void replace(ItemLike item, TagKey<Item> tag) {
			replacements.put(item.asItem(), tag);
		}

		@Override
		protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
			replace(Items.STICK, VTags.Items.RODS_WOODEN);
			replace(Items.GOLD_INGOT, VTags.Items.INGOTS_GOLD);
			replace(Items.IRON_INGOT, VTags.Items.INGOTS_IRON);
			replace(Items.NETHERITE_INGOT, VTags.Items.INGOTS_NETHERITE);
			replace(Items.DIAMOND, VTags.Items.GEMS_DIAMOND);
			replace(Items.EMERALD, VTags.Items.GEMS_EMERALD);
			replace(Items.CHEST, VTags.Items.CHESTS_WOODEN);
			replace(Blocks.COBBLESTONE, ItemTags.STONE_CRAFTING_MATERIALS);
			replace(Blocks.COBBLED_DEEPSLATE, VTags.Items.COBBLESTONE_DEEPSLATE);

			exclude(Blocks.GOLD_BLOCK);
			exclude(Items.GOLD_NUGGET);
			exclude(Blocks.IRON_BLOCK);
			exclude(Items.IRON_NUGGET);
			exclude(Blocks.DIAMOND_BLOCK);
			exclude(Blocks.EMERALD_BLOCK);
			exclude(Blocks.NETHERITE_BLOCK);

			exclude(Blocks.COBBLESTONE_STAIRS);
			exclude(Blocks.COBBLESTONE_SLAB);
			exclude(Blocks.COBBLESTONE_WALL);
			exclude(Blocks.COBBLED_DEEPSLATE_STAIRS);
			exclude(Blocks.COBBLED_DEEPSLATE_SLAB);
			exclude(Blocks.COBBLED_DEEPSLATE_WALL);

			buildCraftingRecipes(vanilla -> {
				FinishedRecipe modified = enhance(vanilla);
				if (modified != null)
					exporter.accept(modified);
			});
		}

		private FinishedRecipe enhance(FinishedRecipe vanilla) {
			if (vanilla instanceof ShapelessRecipeBuilder.Result result)
				return enhance(result);
			if (vanilla instanceof ShapedRecipeBuilder.Result result)
				return enhance(result);
			return null;
		}

		private FinishedRecipe enhance(ShapelessRecipeBuilder.Result vanilla) {
			List<Ingredient> ingredients = vanilla.ingredients;
			boolean modified = false;
			for (int x = 0; x < ingredients.size(); x++) {
				Ingredient ing = enhance(vanilla.getId(), ingredients.get(x));
				if (ing != null) {
					ingredients.set(x, ing);
					modified = true;
				}
			}
			return modified ? vanilla : null;
		}

		private FinishedRecipe enhance(ShapedRecipeBuilder.Result vanilla) {
			Map<Character, Ingredient> ingredients = vanilla.key;
			boolean modified = false;
			for (Character x : ingredients.keySet()) {
				Ingredient ing = enhance(vanilla.getId(), ingredients.get(x));
				if (ing != null) {
					ingredients.put(x, ing);
					modified = true;
				}
			}
			return modified ? vanilla : null;
		}

		private Ingredient enhance(ResourceLocation name, Ingredient vanilla) {
			if (excludes.contains(name))
				return null;

			boolean modified = false;
			List<Value> items = new ArrayList<>();
			for (Value entry : vanilla.values) {
				if (entry instanceof ItemValue) {
					ItemStack stack = entry.getItems().stream().findFirst().orElse(ItemStack.EMPTY);
					TagKey<Item> replacement = replacements.get(stack.getItem());
					if (replacement != null) {
						items.add(new TagValue(replacement));
						modified = true;
					} else
						items.add(entry);
				} else
					items.add(entry);
			}
			return modified ? Ingredient.fromValues(items.stream()) : null;
		}
	}

	private static class VNoiseSettingsTagsProvider extends TagsProvider<NoiseGeneratorSettings> {
		private VNoiseSettingsTagsProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator, BuiltinRegistries.NOISE_GENERATOR_SETTINGS);
		}

		@Override
		protected void addTags() {
			this.tag(VTags.NoiseSettings.AMPLIFIED).add(NoiseGeneratorSettings.AMPLIFIED);
			this.tag(VTags.NoiseSettings.CAVES).add(NoiseGeneratorSettings.CAVES);
			this.tag(VTags.NoiseSettings.END).add(NoiseGeneratorSettings.END);
			this.tag(VTags.NoiseSettings.FLOATING_ISLANDS).add(NoiseGeneratorSettings.FLOATING_ISLANDS);
			this.tag(VTags.NoiseSettings.NETHER).add(NoiseGeneratorSettings.NETHER);
			this.tag(VTags.NoiseSettings.OVERWORLD).add(NoiseGeneratorSettings.OVERWORLD);
		}
	}

    /*private static class VDimensionTypeTagsProvider extends CustomTagProviders.DimensionTypeTagProvider {
        private VDimensionTypeTagsProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            this.tag(VTags.DimensionTypes.END).add(DimensionType.END_LOCATION);
            this.tag(VTags.DimensionTypes.NETHER).add(DimensionType.NETHER_LOCATION);
            this.tag(VTags.DimensionTypes.OVERWORLD)
                .addTag(VTags.DimensionTypes.OVERWORLD_CAVES)
                .add(DimensionType.OVERWORLD_LOCATION);
            this.tag(VTags.DimensionTypes.OVERWORLD_CAVES).add(DimensionType.OVERWORLD_CAVES_LOCATION);
        }
    }*/

}
