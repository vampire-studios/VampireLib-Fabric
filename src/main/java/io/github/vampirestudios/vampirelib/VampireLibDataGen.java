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

import static io.github.vampirestudios.vampirelib.VampireLib.BLOCK_WITH_CUSTOM_MODEL;
import static io.github.vampirestudios.vampirelib.VampireLib.BLOCK_WITH_EMPTY_MODEL;
import static io.github.vampirestudios.vampirelib.VampireLib.TEST_CONTENT_ENABLED;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabric.api.tag.convention.v1.ConventionalItemTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import io.github.vampirestudios.vampirelib.api.datagen.BlockModelBuilder;
import io.github.vampirestudios.vampirelib.api.datagen.CustomTagProviders;
import io.github.vampirestudios.vampirelib.api.datagen.DisplayBuilder;
import io.github.vampirestudios.vampirelib.api.datagen.FabricSoundProvider;
import io.github.vampirestudios.vampirelib.api.datagen.SoundBuilder;
import io.github.vampirestudios.vampirelib.init.VTags;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;

public class VampireLibDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		FabricDataGenerator.Pack pack = dataGenerator.createPack();
		if (TEST_CONTENT_ENABLED) {
			pack.addProvider(WoodTypeBlockStateDefinitionProvider::new);
			pack.addProvider((FabricDataGenerator.Pack.Factory<DataProvider>) output -> new WoodTypeTranslationProvider(output, "en_us"));
			pack.addProvider((FabricDataGenerator.Pack.Factory<DataProvider>) output -> new WoodTypeTranslationProvider(output, "fr_fr"));
			pack.addProvider((FabricDataGenerator.Pack.Factory<DataProvider>) output -> new WoodTypeTranslationProvider(output, "no_no"));
			pack.addProvider(WoodTypeRecipeProvider::new);
			WoodTypeBlockTagProvider blockTagsProvider = pack.addProvider(WoodTypeBlockTagProvider::new);
			pack.addProvider((output, registriesFuture) -> new WoodTypeItemTagProvider(output, blockTagsProvider, registriesFuture));
		}
//		VBlockTagsProvider blockTagsProvider = pack.addProvider(VBlockTagsProvider::new);
//		pack.addProvider((output, registriesFuture) -> new VItemTagsProvider(output, blockTagsProvider, registriesFuture));
//		pack.addProvider(VBiomeTagsProvider::new);
	}

	private static class TestSoundProvider extends FabricSoundProvider {
		private TestSoundProvider(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generateSounds(SoundGenerator soundGenerator) {
			soundGenerator.add(SoundEvents.METAL_BREAK, true,
					SoundBuilder.sound(VampireLib.INSTANCE.identifier("replacement_sound_1")),
					SoundBuilder.sound(VampireLib.INSTANCE.identifier("replacement_sound_2")).setVolume(0.5f).setPitch(0.5f),
					SoundBuilder.event(VampireLib.INSTANCE.identifier("replacement_event")).setWeight(2));
			soundGenerator.add(SoundEvents.DEEPSLATE_BREAK, true,
					SoundBuilder.event(VampireLib.INSTANCE.identifier("replacement_event")));
		}
	}

	//Wood Type Test Generation
	private static class WoodTypeBlockStateDefinitionProvider extends FabricModelProvider {
		private WoodTypeBlockStateDefinitionProvider(FabricDataOutput generator) {
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

			// TODO: needs more simplification
			TextureSlot texture1 = TextureSlot.create("texture1");
			TextureSlot texture2 = TextureSlot.create("texture2");
			ModelTemplate customModel = BlockModelBuilder.createNew(VampireLib.INSTANCE.identifier("custom"))
					.addTextureKey(texture1)
					.addTextureKey(texture2)
					.addDisplay(DisplayBuilder.Position.FIXED, new DisplayBuilder()
							.rotate(45, 45, 45)
							.scale(2))
					.noAmbientOcclusion()
					.build();
			TextureMapping customTextureMap = new TextureMapping()
					.put(texture1, VampireLib.INSTANCE.identifier("block_with_custom_model_1"))
					.put(texture2, VampireLib.INSTANCE.identifier("block_with_custom_model_2"));
			blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BLOCK_WITH_CUSTOM_MODEL,
					Variant.variant().with(VariantProperties.MODEL, customModel.create(BLOCK_WITH_CUSTOM_MODEL, customTextureMap, blockStateModelGenerator.modelOutput))));

			blockStateModelGenerator.registerEmptyModel(BLOCK_WITH_EMPTY_MODEL);
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerator) {

		}

		private void generateWoodTypeAssets(BlockModelGenerators blockStateModelGenerator, WoodRegistry woodRegistry) {
			woodRegistry.generateModels(blockStateModelGenerator);
		}
	}

	private static class WoodTypeTranslationProvider extends FabricLanguageProvider {
		private Map<String, String> lang;

		private WoodTypeTranslationProvider(FabricDataOutput dataGenerator, String langCode) {
			super(dataGenerator, langCode);
			File file = new File("translations/" + langCode + ".json");
			try(Reader reader = Files.newBufferedReader(Paths.get(file.toURI()))) {
				lang = VampireLib.GSON.fromJson(reader, Map.class);
			} catch(Exception ignored) {
				lang = new HashMap<>();
			}
		}

		@Override
		public void generateTranslations(TranslationBuilder translationBuilder) {
			addWoodTypeLang(VampireLib.TEST_WOOD, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD1, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD2, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD3, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD4, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD5, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD6, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD7, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD8, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD9, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD10, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD11, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD12, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD13, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD14, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD15, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_WOOD16, translationBuilder);

			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD1, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD2, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD3, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD4, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD5, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD6, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD7, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD8, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD9, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD10, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD11, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD12, translationBuilder);
			addWoodTypeLang(VampireLib.TEST_NETHER_WOOD13, translationBuilder);
		}

		private void addWoodTypeLang(WoodRegistry woodRegistry, FabricLanguageProvider.TranslationBuilder translationBuilder) {
			woodRegistry.generateLang(translationBuilder, lang);
		}
	}

	private static class WoodTypeRecipeProvider extends FabricRecipeProvider {
		private WoodTypeRecipeProvider(FabricDataOutput dataGenerator) {
			super(dataGenerator);
		}

		@Override
		public void buildRecipes(Consumer<FinishedRecipe> exporter) {
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
		private WoodTypeBlockTagProvider(FabricDataOutput dataGenerator, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(dataGenerator, completableFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
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
		private WoodTypeItemTagProvider(FabricDataOutput dataGenerator, CustomBlockTagProvider blockTagProvider, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(dataGenerator, registriesFuture, blockTagProvider);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
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
		private VBlockTagsProvider(FabricDataOutput dataGenerator, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(dataGenerator, completableFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
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
	}

	private static class VBiomeTagsProvider extends BiomeTagsProvider {
		private VBiomeTagsProvider(FabricDataOutput dataGenerator, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(dataGenerator, completableFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
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
			/*TagAppender<Biome> withMonsterSpawns = this.tag(VTags.Biomes.WITH_DEFAULT_MONSTER_SPAWNS);
			MultiNoiseBiomeSource.Preset.OVERWORLD.possibleBiomes(arg.asGetterLookup()).forEach((biome) -> {
				if (biome != Biomes.MUSHROOM_FIELDS && biome != Biomes.DEEP_DARK)
					withMonsterSpawns.add(biome);
			});*/
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
		private VItemTagsProvider(FabricDataOutput dataGenerator, CustomTagProviders.CustomBlockTagProvider blockTagProvider, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(dataGenerator, completableFuture, blockTagProvider);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			copyCobblestoneTags();

			generateFoodTags();
			generateVillageJobSitesTag();
			generateSandstoneTags();
		}

		private void copyCobblestoneTags() {
			getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS)
					.add(Items.ANDESITE, Items.DIORITE, Items.GRANITE);
			getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS)
					.addTag(ItemTags.STONE_CRAFTING_MATERIALS);
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
			getOrCreateTagBuilder(ConventionalItemTags.VILLAGER_JOB_SITES)
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
	}

}
