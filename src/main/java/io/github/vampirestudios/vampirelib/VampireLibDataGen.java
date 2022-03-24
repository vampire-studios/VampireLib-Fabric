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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;

import io.github.vampirestudios.vampirelib.api.CustomTagProviders;
import io.github.vampirestudios.vampirelib.api.FabricLanguageProvider;
import io.github.vampirestudios.vampirelib.init.VTags;
import io.github.vampirestudios.vampirelib.mixins.IngredientAccessor;
import io.github.vampirestudios.vampirelib.mixins.ShapedRecipeResultAccessor;
import io.github.vampirestudios.vampirelib.mixins.ShapelessRecipeResultAccessor;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;

import static io.github.vampirestudios.vampirelib.VampireLib.TEST_CONTENT_ENABLED;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BARRELS;
import static io.github.vampirestudios.vampirelib.init.VTags.Blocks.BARRELS_WOODEN;
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

public class VampireLibDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        if (TEST_CONTENT_ENABLED) {
            dataGenerator.addProvider(WoodTypeBlockStateDefinitionProvider::new);
            dataGenerator.addProvider(WoodTypeEnglishLanguageProvider::new);
            dataGenerator.addProvider(WoodTypeFrenchLanguageProvider::new);
            dataGenerator.addProvider(WoodTypeRecipeProvider::new);
        }
        VBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(VBlockTagsProvider::new);
        dataGenerator.addProvider(new VItemTagsProvider(dataGenerator, blockTagsProvider));
        dataGenerator.addProvider(VRecipeReplacementProvider::new);
        dataGenerator.addProvider(VEntityTypeTagsProvider::new);
        dataGenerator.addProvider(VBiomeTagsProvider::new);
        dataGenerator.addProvider(VNoiseSettingsTagsProvider::new);
        dataGenerator.addProvider(VDimensionTypeTagsProvider::new);
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
        }

        @Override
        public void registerTranslations() {
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
        }

        @Override
        public void registerTranslations() {
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

    //VampireLib generators
    private static class VBlockTagsProvider extends CustomTagProviders.CustomBlockTagProvider {
        private VBlockTagsProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected Path getPath(ResourceLocation id) {
            return this.generator.getOutputFolder().resolve("data/%s/tags/blocks/%s.json".formatted("c", id.getPath()));
        }

        @Override
        protected void generateTags() {
            tagCustom(BARRELS_WOODEN).add(Blocks.BARREL);
            tagCustom(BARRELS).addTag(BARRELS_WOODEN);
            tagCustom(CHESTS_ENDER).add(Blocks.ENDER_CHEST);
            tagCustom(CHESTS_TRAPPED).add(Blocks.TRAPPED_CHEST);
            tagCustom(CHESTS_WOODEN).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
            tagCustom(CHESTS).addTags(CHESTS_ENDER, CHESTS_TRAPPED, CHESTS_WOODEN);
            tag(COBBLESTONE_NORMAL).add(Blocks.COBBLESTONE);
            tag(COBBLESTONE_INFESTED).add(Blocks.INFESTED_COBBLESTONE);
            tag(COBBLESTONE_MOSSY).add(Blocks.MOSSY_COBBLESTONE);
            tag(COBBLESTONE_DEEPSLATE).add(Blocks.COBBLED_DEEPSLATE);
            tagCustom(COBBLESTONE).addTags(COBBLESTONE_NORMAL, COBBLESTONE_INFESTED, COBBLESTONE_MOSSY, COBBLESTONE_DEEPSLATE);
            tag(DIRT).add(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.ROOTED_DIRT);
            tag(END_STONES).add(Blocks.END_STONE);
            tag(ENDERMAN_PLACE_ON_BLACKLIST);
            tag(FENCE_GATES).addTag(FENCE_GATES_WOODEN);
            tag(FENCE_GATES_WOODEN).add(Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE);
            tagCustom(FENCES).addTags(FENCES_NETHER_BRICK, FENCES_WOODEN);
            tag(FENCES_NETHER_BRICK).add(Blocks.NETHER_BRICK_FENCE);
            tag(FENCES_WOODEN).add(Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE, Blocks.JUNGLE_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE, Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE);
            tag(GLASS).addTag(GLASS_COLORLESS).addTag(STAINED_GLASS).addTag(GLASS_TINTED);
            tag(GLASS_COLORLESS).add(Blocks.GLASS);
            tag(GLASS_SILICA).add(Blocks.GLASS, Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS);
            tag(GLASS_TINTED).add(Blocks.TINTED_GLASS);
            addColored(tag(STAINED_GLASS)::add, GLASS, "{color}_stained_glass");
            tagCustom(GLASS_PANES).addTags(GLASS_PANES_COLORLESS, STAINED_GLASS_PANES);
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
            tagCustom(ORES).addTags(ORES_COAL, ORES_COPPER, ORES_DIAMOND, ORES_EMERALD, ORES_GOLD, ORES_IRON, ORES_LAPIS, ORES_REDSTONE, ORES_QUARTZ, ORES_NETHERITE_SCRAP);
            tag(ORES_COAL).add(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE);
            tag(ORES_COPPER).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE);
            tag(ORES_DIAMOND).add(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
            tag(ORES_EMERALD).add(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE);
            tag(ORES_GOLD).add(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE);
            tag(ORES_IRON).add(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE);
            tag(ORES_LAPIS).add(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);
            tag(ORES_QUARTZ).add(Blocks.NETHER_QUARTZ_ORE);
            tag(ORES_REDSTONE).add(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
            tag(ORES_NETHERITE_SCRAP).add(Blocks.ANCIENT_DEBRIS);
            tag(ORES_IN_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
            tag(ORES_IN_GROUND_NETHERRACK).add(Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE);
            tag(ORES_IN_GROUND_STONE).add(Blocks.COAL_ORE, Blocks.COPPER_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
            tagCustom(SAND).addTags(SAND_COLORLESS, SAND_RED);
            tag(SAND_COLORLESS).add(Blocks.SAND);
            tag(SAND_RED).add(Blocks.RED_SAND);
            tag(SANDSTONE).add(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
            tag(STONE).add(Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.INFESTED_STONE, Blocks.STONE, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.INFESTED_DEEPSLATE, Blocks.TUFF);
            tagCustom(STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_AMETHYST, STORAGE_BLOCKS_COAL, STORAGE_BLOCKS_COPPER, STORAGE_BLOCKS_DIAMOND, STORAGE_BLOCKS_EMERALD, STORAGE_BLOCKS_GOLD, STORAGE_BLOCKS_IRON, STORAGE_BLOCKS_LAPIS, STORAGE_BLOCKS_QUARTZ, STORAGE_BLOCKS_RAW_COPPER, STORAGE_BLOCKS_RAW_GOLD, STORAGE_BLOCKS_RAW_IRON, STORAGE_BLOCKS_REDSTONE, STORAGE_BLOCKS_NETHERITE);
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

        private void addColored(Consumer<Block> consumer, TagKey<Block> group, String pattern) {
            String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
            for (DyeColor color : DyeColor.values()) {
                ResourceLocation key = new ResourceLocation("minecraft", pattern.replace("{color}", color.getName()));
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

    private static class VBiomeTagsProvider extends TagsProvider<Biome> {
        private VBiomeTagsProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator, BuiltinRegistries.BIOME);
        }

        @Override
        protected void addTags() {
            this.tag(VTags.Biomes.HILLS)
                .add(Biomes.FLOWER_FOREST, Biomes.ICE_SPIKES, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.OLD_GROWTH_BIRCH_FOREST);
            this.tag(BiomeTags.IS_HILL).addTag(VTags.Biomes.HILLS);
            this.tag(VTags.Biomes.PLATEAUS)
                .add(Biomes.SAVANNA_PLATEAU, Biomes.WOODED_BADLANDS, Biomes.MEADOW);
            this.tag(VTags.Biomes.RARE)
                .add(Biomes.SPARSE_JUNGLE, Biomes.SAVANNA_PLATEAU, Biomes.SUNFLOWER_PLAINS, Biomes.WINDSWEPT_GRAVELLY_HILLS,
                    Biomes.FLOWER_FOREST, Biomes.ICE_SPIKES, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                    Biomes.WINDSWEPT_SAVANNA, Biomes.ERODED_BADLANDS, Biomes.BAMBOO_JUNGLE, Biomes.MUSHROOM_FIELDS);

            this.tag(VTags.Biomes.OCEANS)
                .addTag(VTags.Biomes.DEEP_OCEANS).addTag(VTags.Biomes.SHALLOW_OCEANS);
            this.tag(VTags.Biomes.DEEP_OCEANS)
                .add(Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN);
            this.tag(VTags.Biomes.SHALLOW_OCEANS)
                .add(Biomes.COLD_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.OCEAN, Biomes.WARM_OCEAN);
            this.tag(VTags.Biomes.RIVERS)
                .add(Biomes.RIVER, Biomes.FROZEN_RIVER);
            this.tag(VTags.Biomes.WATER)
                .addTag(VTags.Biomes.OCEANS).addTag(VTags.Biomes.RIVERS);
            this.tag(BiomeTags.IS_OCEAN).addTag(VTags.Biomes.SHALLOW_OCEANS);
            this.tag(BiomeTags.IS_DEEP_OCEAN).addTag(VTags.Biomes.DEEP_OCEANS);
            this.tag(BiomeTags.IS_RIVER).addTag(VTags.Biomes.RIVERS);


            this.tag(VTags.Biomes.BADLANDS).add(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS);
            this.tag(BiomeTags.IS_BADLANDS).addTag(VTags.Biomes.BADLANDS);
            this.tag(VTags.Biomes.BEACHES)
                .add(Biomes.BEACH, Biomes.SNOWY_BEACH, Biomes.STONY_SHORE);
            this.tag(BiomeTags.IS_BEACH).addTag(VTags.Biomes.BEACHES);
            this.tag(VTags.Biomes.DESERTS).add(Biomes.DESERT);
            this.tag(VTags.Biomes.FORESTS).addTag(VTags.Biomes.BIRCH_FORESTS).addTag(VTags.Biomes.DARK_FORESTS)
                .addTag(VTags.Biomes.JUNGLE_FORESTS).addTag(VTags.Biomes.NETHER_FORESTS)
                .addTag(VTags.Biomes.OAK_FORESTS).addTag(VTags.Biomes.TAIGA_FORESTS);
            this.tag(VTags.Biomes.BIRCH_FORESTS)
                .add(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
            this.tag(VTags.Biomes.DARK_FORESTS)
                .add(Biomes.DARK_FOREST);
            this.tag(VTags.Biomes.JUNGLE_FORESTS)
                .addTag(VTags.Biomes.BAMBOO_JUNGLE_FORESTS)
                .add(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE);
            this.tag(VTags.Biomes.BAMBOO_JUNGLE_FORESTS)
                .add(Biomes.BAMBOO_JUNGLE);
            this.tag(BiomeTags.IS_JUNGLE).addTag(VTags.Biomes.JUNGLE_FORESTS);
            this.tag(VTags.Biomes.NETHER_FORESTS)
                .add(Biomes.CRIMSON_FOREST, Biomes.WARPED_FOREST);
            this.tag(BiomeTags.IS_NETHER).addTag(VTags.Biomes.NETHER_FORESTS);
            this.tag(VTags.Biomes.OAK_FORESTS)
                .add(Biomes.FOREST, Biomes.FLOWER_FOREST);
            this.tag(VTags.Biomes.TAIGA_FORESTS)
                .add(Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.GROVE);
            this.tag(BiomeTags.IS_FOREST).addTag(VTags.Biomes.FORESTS);
            this.tag(VTags.Biomes.MUSHROOM)
                .add(Biomes.MUSHROOM_FIELDS);
            this.tag(VTags.Biomes.MOUNTAINS)
                .add(Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_SAVANNA);
            this.tag(BiomeTags.IS_MOUNTAIN).addTag(VTags.Biomes.MOUNTAINS);
            this.tag(VTags.Biomes.PLAINS)
                .add(Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW);
            this.tag(VTags.Biomes.GRASSLANDS)
                .addTag(VTags.Biomes.PLAINS).addTag(VTags.Biomes.SAVANNAS);
            this.tag(VTags.Biomes.SAVANNAS)
                .add(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA);
            this.tag(BiomeTags.IS_SAVANNA).addTag(VTags.Biomes.SAVANNAS);
            this.tag(VTags.Biomes.SNOWY)
                .add(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_RIVER,
                    Biomes.SNOWY_BEACH, Biomes.SNOWY_TAIGA, Biomes.SNOWY_PLAINS,
                    Biomes.GROVE, Biomes.SNOWY_SLOPES, Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS);
            this.tag(BiomeTags.ONLY_ALLOWS_SNOW_AND_GOLD_RABBITS).addTag(VTags.Biomes.SNOWY);
            this.tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).addTag(VTags.Biomes.SNOWY);
            this.tag(VTags.Biomes.SWAMPS)
                .add(Biomes.SWAMP);
            this.tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).addTag(VTags.Biomes.SWAMPS);
            this.tag(VTags.Biomes.SLOPES)
                .add(Biomes.MEADOW, Biomes.GROVE, Biomes.SNOWY_SLOPES);
            this.tag(VTags.Biomes.PEAKS)
                .add(Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS, Biomes.STONY_PEAKS);
            this.tag(VTags.Biomes.VOIDS)
                .add(Biomes.THE_VOID);

            this.tag(VTags.Biomes.OVERWORLD)
                .addTag(VTags.Biomes.OVERWORLD_SURFACE).addTag(VTags.Biomes.OVERWORLD_UNDERGROUND);
            this.tag(BiomeTags.IS_OVERWORLD).addTag(VTags.Biomes.OVERWORLD);
            this.tag(VTags.Biomes.OVERWORLD_SURFACE)
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
            this.tag(VTags.Biomes.OVERWORLD_UNDERGROUND)
                .add(Biomes.LUSH_CAVES, Biomes.DRIPSTONE_CAVES);
            this.tag(VTags.Biomes.NETHER)
                .addTag(VTags.Biomes.NETHER_FORESTS)
                .add(Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS);
            this.tag(BiomeTags.IS_NETHER).addTag(VTags.Biomes.NETHER);
            this.tag(VTags.Biomes.END)
                .add(Biomes.THE_END, Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS);
            this.tag(BiomeTags.IS_END).addTag(VTags.Biomes.END);
        }
    }

    private static class VItemTagsProvider extends CustomTagProviders.CustomItemTagProvider {
        private VItemTagsProvider(FabricDataGenerator dataGenerator, CustomTagProviders.CustomBlockTagProvider blockTagProvider) {
            super(dataGenerator, blockTagProvider);
        }

        @Override
        protected void generateTags() {
            copy(VTags.Blocks.BARRELS_WOODEN, VTags.Items.BARRELS_WOODEN);
            copy(VTags.Blocks.BARRELS, VTags.Items.BARRELS);
            tagCustom(VTags.Items.BONES).add(Items.BONE);
            tagCustom(VTags.Items.BOOKSHELVES).add(Items.BOOKSHELF);
            copy(VTags.Blocks.CHESTS, VTags.Items.CHESTS);
            copy(VTags.Blocks.CHESTS_ENDER, VTags.Items.CHESTS_ENDER);
            copy(VTags.Blocks.CHESTS_TRAPPED, VTags.Items.CHESTS_TRAPPED);
            copy(VTags.Blocks.CHESTS_WOODEN, VTags.Items.CHESTS_WOODEN);
            copy(VTags.Blocks.COBBLESTONE_NORMAL, VTags.Items.COBBLESTONE_NORMAL);
            copy(VTags.Blocks.COBBLESTONE_INFESTED, VTags.Items.COBBLESTONE_INFESTED);
            copy(VTags.Blocks.COBBLESTONE_MOSSY, VTags.Items.COBBLESTONE_MOSSY);
            copy(VTags.Blocks.COBBLESTONE_DEEPSLATE, VTags.Items.COBBLESTONE_DEEPSLATE);
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
            copy(VTags.Blocks.GLASS_COLORLESS, VTags.Items.GLASS_COLORLESS);
            copyColored(VTags.Blocks.GLASS, VTags.Items.GLASS);
            copy(VTags.Blocks.GLASS_PANES_COLORLESS, VTags.Items.GLASS_PANES_COLORLESS);
            copy(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
            copy(VTags.Blocks.STAINED_GLASS_PANES, VTags.Items.STAINED_GLASS_PANES);
            copyColored(VTags.Blocks.GLASS_PANES, VTags.Items.GLASS_PANES);
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

        private void addColored(Consumer<TagKey<Item>> consumer, TagKey<Item> group, String pattern) {
            String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
            for (DyeColor color : DyeColor.values()) {
                ResourceLocation key = new ResourceLocation("minecraft", pattern.replace("{color}", color.getName()));
                TagKey<Item> tag = getForgeItemTag(prefix + color.getName());
                Item item = Registry.ITEM.get(key);
                if (item == null || item == Items.AIR)
                    throw new IllegalStateException("Unknown vanilla item: " + key.toString());
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
//                copy(from, to);
            }
//            copy(getForgeBlockTag(blockPre + "colorless"), getForgeItemTag(itemPre + "colorless"));
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

            tag(VTags.EntityTypes.BIG_NOSES).addTag(VTags.EntityTypes.ILLAGERS).add(EntityType.WITCH, EntityType.VILLAGER, EntityType.ZOMBIE_VILLAGER);
            tag(VTags.EntityTypes.ILLAGERS).add(EntityType.PILLAGER, EntityType.ILLUSIONER, EntityType.VINDICATOR, EntityType.EVOKER);

            /*
             * Many of these mob types do not appear in vanilla Minecraft, and are thus left empty here
             */
            tagCustom(VTags.EntityTypes.ELEMENTAL).addTags(VTags.EntityTypes.ELEMENTAL_FIRE, VTags.EntityTypes.ELEMENTAL_ICE,
                VTags.EntityTypes.ELEMENTAL_METAL);
            tag(VTags.EntityTypes.ELEMENTAL_FIRE).add(EntityType.BLAZE);
            tag(VTags.EntityTypes.ELEMENTAL_ICE).add(EntityType.SNOW_GOLEM);
            tag(VTags.EntityTypes.ELEMENTAL_METAL).add(EntityType.IRON_GOLEM);

            tagCustom(VTags.EntityTypes.ELEMENTAL_ATTACKS).addTags(VTags.EntityTypes.ELEMENTAL_ATTACKS_ELECTRIC, VTags.EntityTypes.ELEMENTAL_ATTACKS_FIRE,
                VTags.EntityTypes.ELEMENTAL_ATTACKS_ICE);
            tag(VTags.EntityTypes.ELEMENTAL_ATTACKS_ELECTRIC).add(EntityType.LIGHTNING_BOLT);
            tag(VTags.EntityTypes.ELEMENTAL_ATTACKS_FIRE).add(EntityType.FIREBALL, EntityType.SMALL_FIREBALL);
            tag(VTags.EntityTypes.ELEMENTAL_ATTACKS_ICE).add(EntityType.SNOWBALL);

            tag(VTags.EntityTypes.ARTHROPODS).add(EntityType.BEE, EntityType.CAVE_SPIDER, EntityType.ENDERMITE, EntityType.SILVERFISH, EntityType.SPIDER);

            tag(VTags.EntityTypes.AVIANS).addTag(VTags.EntityTypes.AVIANS_FOWLS).add(EntityType.PARROT);
            tag(VTags.EntityTypes.AVIANS_FOWLS).add(EntityType.CHICKEN);

            tag(VTags.EntityTypes.AQUATIC).add(EntityType.AXOLOTL, EntityType.COD, EntityType.DOLPHIN, EntityType.ELDER_GUARDIAN,
                EntityType.GLOW_SQUID, EntityType.GUARDIAN, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.SQUID,
                EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.FROG, EntityType.TADPOLE);
            tag(VTags.EntityTypes.FISH).add(EntityType.COD, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.TROPICAL_FISH);
            tag(VTags.EntityTypes.CEPHALOPODS).add(EntityType.GLOW_SQUID, EntityType.SQUID);
            tag(VTags.EntityTypes.GUARDIANS).add(EntityType.ELDER_GUARDIAN, EntityType.GUARDIAN);

            tag(VTags.EntityTypes.REPTILES).add(EntityType.TURTLE);

            tagCustom(VTags.EntityTypes.MAMMALS)
                .addTags(VTags.EntityTypes.MAMMALS_BOVINES, VTags.EntityTypes.MAMMALS_CAMELIDS, VTags.EntityTypes.MAMMALS_CANIDS,
                    VTags.EntityTypes.MAMMALS_CAPRINES, VTags.EntityTypes.MAMMALS_EQUINES, VTags.EntityTypes.MAMMALS_FELINES,
                    VTags.EntityTypes.MAMMALS_SWINES, VTags.EntityTypes.MAMMALS_URSIDS
                ).add(EntityType.BAT, EntityType.RABBIT);
            tag(VTags.EntityTypes.MAMMALS_BOVINES).addTag(VTags.EntityTypes.MAMMALS_BOVINES_CATTLE).add(EntityType.SHEEP);
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

            tagCustom(VTags.EntityTypes.MILKABLE).addTags(VTags.EntityTypes.MAMMALS_BOVINES_CATTLE, VTags.EntityTypes.MAMMALS_CAPRINES);
            tag(VTags.EntityTypes.MUSHROOM_COWS).add(EntityType.MOOSHROOM);

            tag(VTags.EntityTypes.BLIND_MOBS);

            tagCustom(VTags.EntityTypes.FLYING).addTags(VTags.EntityTypes.BOSSES, VTags.EntityTypes.GHOSTS).add(EntityType.BAT, EntityType.BEE,
                EntityType.ENDER_DRAGON, EntityType.PARROT, EntityType.WITHER);
            tagCustom(VTags.EntityTypes.LAND).addTags(VTags.EntityTypes.CREEPERS, VTags.EntityTypes.SKELETONS, VTags.EntityTypes.ZOMBIES)
                .add(EntityType.BLAZE, EntityType.CAT, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.DONKEY, EntityType.ENDERMAN,
                    EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.GOAT, EntityType.HOGLIN, EntityType.HORSE, EntityType.ILLUSIONER,
                    EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MULE, EntityType.OCELOT, EntityType.PANDA, EntityType.PIG,
                    EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.PLAYER, EntityType.POLAR_BEAR, EntityType.RABBIT,
                    EntityType.RAVAGER, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SLIME, EntityType.SNOW_GOLEM, EntityType.SPIDER,
                    EntityType.TRADER_LLAMA, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WITCH, EntityType.WOLF
                );

            tag(VTags.EntityTypes.VOLCANIC).add(EntityType.STRIDER);
            tag(VTags.EntityTypes.HELL_MOBS).addTag(VTags.EntityTypes.VOLCANIC)
                .add(EntityType.MAGMA_CUBE, EntityType.GHAST, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE,
                    EntityType.BLAZE, EntityType.WITHER_SKELETON, EntityType.ZOMBIFIED_PIGLIN, EntityType.HOGLIN
                );

            tagCustom(VTags.EntityTypes.ENEMIES).addTags(VTags.EntityTypes.BOSSES, VTags.EntityTypes.CREEPERS/*, EntityTypeTags.RAIDERS*/)
                .add(EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDERMAN, EntityType.ENDERMITE,
                    EntityType.GHAST, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN,
                    EntityType.PIGLIN_BRUTE, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.STRAY,
                    EntityType.VEX, EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN
                );
            tag(VTags.EntityTypes.CREEPERS).add(EntityType.CREEPER);

            tagCustom(VTags.EntityTypes.UNDEAD).addTags(VTags.EntityTypes.SKELETONS, VTags.EntityTypes.ZOMBIES)
                .add(EntityType.PHANTOM, EntityType.WITHER);
            tag(VTags.EntityTypes.SKELETONS).add(EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.SKELETON_HORSE);
            tag(VTags.EntityTypes.ZOMBIES).add(EntityType.DROWNED, EntityType.HUSK, EntityType.ZOGLIN, EntityType.ZOMBIE,
                EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN);

            tag(VTags.EntityTypes.NPC).add(EntityType.PIGLIN, EntityType.VILLAGER, EntityType.WANDERING_TRADER);
        }
    }

    private static class VRecipeReplacementProvider extends FabricRecipesProvider {
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
            replace(Items.STICK,  VTags.Items.RODS_WOODEN);
            replace(Items.GOLD_INGOT,  VTags.Items.INGOTS_GOLD);
            replace(Items.IRON_INGOT,  VTags.Items.INGOTS_IRON);
            replace(Items.NETHERITE_INGOT,  VTags.Items.INGOTS_NETHERITE);
            replace(Items.DIAMOND,  VTags.Items.GEMS_DIAMOND);
            replace(Items.EMERALD,  VTags.Items.GEMS_EMERALD);
            replace(Items.CHEST,  VTags.Items.CHESTS_WOODEN);
            replace(Blocks.COBBLESTONE, VTags.Items.COBBLESTONE_NORMAL);
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
            List<Ingredient> ingredients = ((ShapelessRecipeResultAccessor)vanilla).getIngredients();
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
            Map<Character, Ingredient> ingredients = ((ShapedRecipeResultAccessor)vanilla).getKey();
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
            for (Value entry : ((IngredientAccessor)(Object)vanilla).getValues()) {
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

    private static class VDimensionTypeTagsProvider extends TagsProvider<DimensionType> {
        private VDimensionTypeTagsProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator, BuiltinRegistries.DIMENSION_TYPE);
        }

        @Override
        protected void addTags() {
            this.tag(VTags.DimensionTypes.END).add(BuiltinDimensionTypes.END);
            this.tag(VTags.DimensionTypes.NETHER).add(BuiltinDimensionTypes.NETHER);
            this.tag(VTags.DimensionTypes.OVERWORLD)
                .addTag(VTags.DimensionTypes.OVERWORLD_CAVES)
                .add(BuiltinDimensionTypes.OVERWORLD);
            this.tag(VTags.DimensionTypes.OVERWORLD_CAVES).add(BuiltinDimensionTypes.OVERWORLD_CAVES);
        }
    }
}
