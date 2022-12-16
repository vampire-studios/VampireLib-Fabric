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

package io.github.vampirestudios.vampirelib.utils.registry;

import static net.minecraft.data.models.BlockModelGenerators.createDoor;
import static net.minecraft.data.models.BlockModelGenerators.createHorizontalFacingDispatch;
import static net.minecraft.data.models.BlockModelGenerators.createSimpleBlock;
import static net.minecraft.data.models.BlockModelGenerators.createTrapdoor;
import static net.minecraft.data.recipes.RecipeProvider.has;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.api.datagen.CustomTagProviders;
import io.github.vampirestudios.vampirelib.blocks.BaseBeehiveBlock;
import io.github.vampirestudios.vampirelib.blocks.CustomLadderBlock;
import io.github.vampirestudios.vampirelib.blocks.FlowerPotBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.FungusBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.SaplingBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.entity.IBlockEntityType;
import io.github.vampirestudios.vampirelib.client.VampireLibClient;
import io.github.vampirestudios.vampirelib.utils.Utils;

public class WoodRegistry {
	private final ResourceLocation name;
	private final AbstractTreeGrower saplingGenerator;
	private final ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator;
	private final Block baseFungusBlock;
	private final List<String> availableLeaves = new ArrayList<>();
	private final List<String> availableFloweryLeaves = new ArrayList<>();
	private final List<String> availableSaplings = new ArrayList<>();
	private final List<String> availablePottedSaplings = new ArrayList<>();
	public TagKey<Block> logsTag;
	public TagKey<Item> logsItemTag;
	private Block log;
	private Block wood;
	private Block strippedLog;
	private Block strippedWood;
	private Block stairs;
	private Block slab;
	private Block planks;
	private Block leaves;
	private Block floweryLeaves;
	private Block sapling;
	private Block pottedSapling;
	private Block fence;
	private Block fenceGate;
	private Block bookshelf;
	private Block chiseledBookshelf;
	private Block door;
	private Block trapdoor;
	private Block button;
	private Block pressurePlate;
	private Block sign;
	private Block wallSign;
	private Block hangingSign;
	private Block hangingWallSign;
	private Block ladder;
	private Block beehive;
	private Block mosaic;
	private Block mosaicStairs;
	private Block mosaicSlab;
	private Item signItem;
	private Item hangingSignItem;
	private Item boatItem;
	private Item chestBoatItem;
	private ResourceKey<TerraformBoatType> boatType;
	private boolean flammable = true;
	private boolean netherWoodLike = false;
	private boolean bambooLike = false;
	private boolean azaleaLike = false;
	private boolean spruceLike = false;

	private WoodRegistry(ResourceLocation name) {
		this(name, null, null);
	}

	private WoodRegistry(ResourceLocation name, AbstractTreeGrower saplingGenerator) {
		this(name, saplingGenerator, null, null);
	}

	private WoodRegistry(ResourceLocation name, ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator, Block baseFungusBlock) {
		this(name, null, fungusGenerator, baseFungusBlock);
	}

	private WoodRegistry(ResourceLocation name, AbstractTreeGrower saplingGenerator, ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator, Block baseFungusBlock) {
		this.name = name;
		this.saplingGenerator = saplingGenerator;
		this.fungusGenerator = fungusGenerator;
		this.baseFungusBlock = baseFungusBlock;
		this.logsTag = TagKey.create(Registries.BLOCK, Utils.appendToPath(name, "_logs"));
		this.logsItemTag = TagKey.create(Registries.ITEM, Utils.appendToPath(name, "_logs"));
	}

	public static WoodRegistry.Builder of(ResourceLocation name) {
		return new WoodRegistry.Builder().of(name);
	}

	public static WoodRegistry.Builder of(ResourceLocation name, Block planks) {
		return new WoodRegistry.Builder().of(name, planks);
	}

	public static WoodRegistry.Builder of(ResourceLocation name, AbstractTreeGrower saplingGenerator) {
		return new WoodRegistry.Builder().of(name, saplingGenerator);
	}

	public static WoodRegistry.Builder of(ResourceLocation name, ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator, Block baseFungusBlock) {
		return new WoodRegistry.Builder().of(name, fungusGenerator, baseFungusBlock);
	}

	public ResourceLocation name() {
		return name;
	}

	public List<String> availableLeaves() {
		return availableLeaves;
	}

	public List<String> availableFloweryLeaves() {
		return availableFloweryLeaves;
	}

	public List<String> availableSaplings() {
		return availableSaplings;
	}

	public Block log() {
		return log;
	}

	public Block wood() {
		return wood;
	}

	public Block strippedLog() {
		return strippedLog;
	}

	public Block strippedWood() {
		return strippedWood;
	}

	public Block stairs() {
		return stairs;
	}

	public Block slab() {
		return slab;
	}

	public Block planks() {
		return planks;
	}

	public Block leaves() {
		return leaves;
	}

	public Block floweryLeaves() {
		return floweryLeaves;
	}

	public Block sapling() {
		return sapling;
	}

	public Block pottedSapling() {
		return pottedSapling;
	}

	public Block fence() {
		return fence;
	}

	public Block fenceGate() {
		return fenceGate;
	}

	public Block bookshelf() {
		return bookshelf;
	}

	public Block chiseledBookshelf() {
		return chiseledBookshelf;
	}

	public Block door() {
		return door;
	}

	public Block trapdoor() {
		return trapdoor;
	}

	public Block button() {
		return button;
	}

	public Block pressurePlate() {
		return pressurePlate;
	}

	public Block ladder() {
		return ladder;
	}

	public Block beehive() {
		return beehive;
	}

	public Block sign() {
		return sign;
	}

	public Block wallSign() {
		return wallSign;
	}

	public Block hangingSign() {
		return hangingSign;
	}

	public Block hangingWallSign() {
		return hangingWallSign;
	}

	public AbstractTreeGrower saplingGenerator() {
		return saplingGenerator;
	}

	public ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator() {
		return fungusGenerator;
	}

	public TagKey<Block> logsTag() {
		return logsTag;
	}

	public TagKey<Item> logsItemTag() {
		return logsItemTag;
	}

	public Item signItem() {
		return signItem;
	}

	public Item hangingSignItem() {
		return hangingSignItem;
	}

	public Item boatItem() {
		return boatItem;
	}

	public Item chestBoatItem() {
		return chestBoatItem;
	}

	public void generateBlockTags(CustomTagProviders.CustomBlockTagProvider blockTags) {
		if (log != null) blockTags.tagCustom(logsTag).add(log);
		if (strippedLog != null) blockTags.tagCustom(logsTag).add(strippedLog);
		if (wood != null) blockTags.tagCustom(logsTag).add(wood);
		if (strippedWood != null) blockTags.tagCustom(logsTag).add(strippedWood);

		if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null)
			blockTags.tagCustom(BlockTags.LOGS).addTag(logsTag);

		if (planks != null) blockTags.tagCustom(BlockTags.PLANKS).add(planks);
		if (!availableLeaves.isEmpty() && !netherWoodLike) {
			availableLeaves.forEach(s -> {
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.LEAVES).add(block);
			});
		}
		if (!availableLeaves.isEmpty() && netherWoodLike) {
			availableLeaves.forEach(s -> {
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.WART_BLOCKS).add(block);
			});
		}
		if (leaves != null && !netherWoodLike) blockTags.tagCustom(BlockTags.LEAVES).add(leaves);
		if (leaves != null && netherWoodLike) blockTags.tagCustom(BlockTags.WART_BLOCKS).add(leaves);
		if (!availableFloweryLeaves.isEmpty() && !netherWoodLike) {
			availableFloweryLeaves.forEach(s -> {
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.LEAVES).add(block);
			});
		}
		if (!availableFloweryLeaves.isEmpty() && netherWoodLike) {
			availableFloweryLeaves.forEach(s -> {
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.WART_BLOCKS).add(block);
			});
		}
		if (floweryLeaves != null && !netherWoodLike) blockTags.tagCustom(BlockTags.LEAVES).add(floweryLeaves);
		if (floweryLeaves != null && netherWoodLike) blockTags.tagCustom(BlockTags.WART_BLOCKS).add(floweryLeaves);
		if (ladder != null) blockTags.tagCustom(BlockTags.CLIMBABLE).add(ladder);
		if (trapdoor != null) blockTags.tagCustom(BlockTags.WOODEN_TRAPDOORS).add(trapdoor);
		if (button != null) blockTags.tagCustom(BlockTags.WOODEN_BUTTONS).add(button);
		if (door != null) blockTags.tagCustom(BlockTags.WOODEN_DOORS).add(door);
		if (sapling != null) blockTags.tagCustom(BlockTags.SAPLINGS).add(sapling);
		if (sign != null) blockTags.tagCustom(BlockTags.STANDING_SIGNS).add(sign);
		if (wallSign != null) blockTags.tagCustom(BlockTags.WALL_SIGNS).add(wallSign);
		if (hangingSign != null) blockTags.tagCustom(BlockTags.CEILING_HANGING_SIGNS).add(hangingSign);
		if (hangingWallSign != null) blockTags.tagCustom(BlockTags.WALL_HANGING_SIGNS).add(hangingWallSign);
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.SAPLINGS).add(block);
			});
		}
		if (pottedSapling != null) blockTags.tagCustom(BlockTags.FLOWER_POTS).add(pottedSapling);
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.FLOWER_POTS).add(block);
			});
		}
		if (fence != null) blockTags.tagCustom(BlockTags.WOODEN_FENCES).add(fence);
		if (fenceGate != null) blockTags.tagCustom(BlockTags.FENCE_GATES).add(fenceGate);
		if (pressurePlate != null) blockTags.tagCustom(BlockTags.PRESSURE_PLATES).add(pressurePlate);
		if (slab != null) blockTags.tagCustom(BlockTags.WOODEN_SLABS).add(slab);
		if (stairs != null) blockTags.tagCustom(BlockTags.WOODEN_STAIRS).add(stairs);
		if (mosaicSlab != null) blockTags.tagCustom(BlockTags.WOODEN_SLABS).add(mosaicSlab);
		if (mosaicStairs != null) blockTags.tagCustom(BlockTags.WOODEN_STAIRS).add(mosaicStairs);
		if (flammable) {
			if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null)
				blockTags.tagCustom(BlockTags.LOGS_THAT_BURN).addTag(logsTag);
		}
	}

	public void generateItemTags(CustomTagProviders.CustomItemTagProvider itemsTag) {
		if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null &&
				logsItemTag != null) itemsTag.copy(logsTag, logsItemTag);
		itemsTag.copy(BlockTags.LOGS, ItemTags.LOGS);
		itemsTag.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		itemsTag.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		itemsTag.copy(BlockTags.LEAVES, ItemTags.LEAVES);
		itemsTag.copy(BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);
		itemsTag.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		itemsTag.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		itemsTag.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		itemsTag.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
		itemsTag.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		itemsTag.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		itemsTag.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		itemsTag.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
		if (boatItem != null) itemsTag.tagCustom(ItemTags.BOATS).add(boatItem);
		if (chestBoatItem != null) itemsTag.tagCustom(ItemTags.CHEST_BOATS).add(chestBoatItem);
	}

	@Environment(EnvType.CLIENT)
	public void generateModels(BlockModelGenerators blockStateModelGenerator) {
		this.generateModels(blockStateModelGenerator, false);
	}

	@Environment(EnvType.CLIENT)
	public void generateModels(BlockModelGenerators blockStateModelGenerator, boolean customPottedTexture) {
		TextureMapping logMapping = new TextureMapping()
				.put(TextureSlot.SIDE, new ResourceLocation(name.getNamespace(),
						String.format("wood_types/%s/%s", name.getPath(),
								netherWoodLike ? "stem" : "log")))
				.put(TextureSlot.END, new ResourceLocation(name.getNamespace(),
						String.format("wood_types/%s/%s_top", name.getPath(),
								netherWoodLike ? "stem" : "log")));
		TextureMapping strippedLogMapping = new TextureMapping()
				.put(TextureSlot.SIDE, new ResourceLocation(name.getNamespace(),
						String.format("wood_types/%s/stripped_%s", name.getPath(),
								netherWoodLike ? "stem" : "log")))
				.put(TextureSlot.END, new ResourceLocation(name.getNamespace(),
						String.format("wood_types/%s/stripped_%s_top", name.getPath(),
								netherWoodLike ? "stem" : "log")));

		TextureMapping leavesMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/%s", name.getPath(),
						netherWoodLike ? "wart_block" : "leaves")));

		TextureMapping floweryLeavesMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/%s", name.getPath(),
						"flowery_leaves")));

		TextureMapping saplingMapping = TextureMapping.cross(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/%s", name.getPath(),
						netherWoodLike ? "fungi" : "sapling")));
		TextureMapping saplingPlantMapping = TextureMapping.plant(new ResourceLocation(name.getNamespace(),
				customPottedTexture ? String.format(
						"wood_types/%s/potted_%s",
						name.getPath(),
						netherWoodLike ? "fungi" : "sapling") :
						String.format("wood_types/%s/%s",
								name.getPath(),
								netherWoodLike ? "fungi" : "sapling")));

		TextureMapping planksMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/planks", name.getPath())));

		TextureMapping mosaicMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/mosaic", name.getPath())));

		TextureMapping signMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/sign", name.getPath())));

		TextureMapping doorMapping = new TextureMapping()
				.put(TextureSlot.TOP,
						new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/door_top", name.getPath())))
				.put(TextureSlot.BOTTOM,
						new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/door_bottom", name.getPath())));

		TextureMapping trapdoorMapping = TextureMapping.defaultTexture(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/trapdoor", name.getPath())));

		TextureMapping bookshelfMapping = TextureMapping.column(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/bookshelf", name.getPath())),
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/planks", name.getPath()))
		);

		TextureMapping chiseledBookshelfMapping = TextureMapping.column(
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/chiseled_bookshelf", name.getPath())),
				new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/chiseled_bookshelf_top", name.getPath()))
		);

		if (log != null) {
			blockStateModelGenerator.new WoodProvider(logMapping).logWithHorizontal(log);
			blockStateModelGenerator.delegateItemModel(log, ModelLocationUtils.getModelLocation(log));
		}
		if (wood != null) {
			blockStateModelGenerator.new WoodProvider(logMapping).wood(wood);
			blockStateModelGenerator.delegateItemModel(wood, ModelLocationUtils.getModelLocation(wood));
		}
		if (strippedLog != null) {
			blockStateModelGenerator.new WoodProvider(strippedLogMapping).logWithHorizontal(strippedLog);
			blockStateModelGenerator.delegateItemModel(strippedLog, ModelLocationUtils.getModelLocation(strippedLog));
		}
		if (strippedWood != null) {
			blockStateModelGenerator.new WoodProvider(strippedLogMapping).wood(strippedWood);
			blockStateModelGenerator.delegateItemModel(strippedWood, ModelLocationUtils.getModelLocation(strippedWood));
		}
		if (!availableLeaves.isEmpty()) {
			availableLeaves.forEach(s -> {
				TextureMapping leaves2Mapping = TextureMapping.cube(
						new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/%s", name.getPath(), s)));
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockStateModelGenerator.createTrivialBlock(block, TexturedModel.createDefault(block1 -> leaves2Mapping,
						netherWoodLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
				blockStateModelGenerator.delegateItemModel(block, resourceLocation);
			});
		} else {
			if (leaves != null) {
				blockStateModelGenerator.createTrivialBlock(leaves, TexturedModel.createDefault(block -> leavesMapping,
						netherWoodLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(leaves);
				blockStateModelGenerator.delegateItemModel(leaves, resourceLocation);
			}
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				TextureMapping leaves2Mapping = TextureMapping.cube(
						new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/%s", name.getPath(), s)));
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockStateModelGenerator.createTrivialBlock(block, TexturedModel.createDefault(block1 -> leaves2Mapping,
						netherWoodLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
				blockStateModelGenerator.delegateItemModel(block, resourceLocation);
			});
		} else {
			if (floweryLeaves != null) {
				blockStateModelGenerator.createTrivialBlock(leaves,
						TexturedModel.createDefault(block -> floweryLeavesMapping,
								netherWoodLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(leaves);
				blockStateModelGenerator.delegateItemModel(leaves, resourceLocation);
			}
		}
		if (door != null) {
			ResourceLocation resourceLocation = ModelTemplates.DOOR_BOTTOM_LEFT.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation3 = ModelTemplates.DOOR_TOP_LEFT.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation4 = ModelTemplates.DOOR_TOP_LEFT_OPEN.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation5 = ModelTemplates.DOOR_BOTTOM_RIGHT.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation6 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation7 = ModelTemplates.DOOR_TOP_RIGHT.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation8 = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(door, doorMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					createDoor(door, resourceLocation, resourceLocation2, resourceLocation5, resourceLocation6,
							resourceLocation3, resourceLocation4, resourceLocation7, resourceLocation8));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(door.asItem()),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_types/%s/door", name.getPath())
					)), blockStateModelGenerator.modelOutput);
		}
		if (trapdoor != null) {
			ResourceLocation resourceLocation = ModelTemplates.TRAPDOOR_TOP.create(trapdoor, trapdoorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.TRAPDOOR_BOTTOM.create(trapdoor, trapdoorMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation3 = ModelTemplates.TRAPDOOR_OPEN.create(trapdoor, trapdoorMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					createTrapdoor(trapdoor, resourceLocation, resourceLocation2, resourceLocation3));
			blockStateModelGenerator.delegateItemModel(trapdoor, resourceLocation2);
		}
		if (planks != null) {
			blockStateModelGenerator.createTrivialBlock(planks, TexturedModel.createDefault(block -> planksMapping,
					ModelTemplates.CUBE_ALL));
			ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(planks);
			blockStateModelGenerator.delegateItemModel(planks, resourceLocation);
		}
		if (mosaic != null) {
			blockStateModelGenerator.createTrivialBlock(mosaic, TexturedModel.createDefault(block -> mosaicMapping,
					ModelTemplates.CUBE_ALL));
			ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(mosaic);
			blockStateModelGenerator.delegateItemModel(mosaic, resourceLocation);
		}
		if (ladder != null) {
			blockStateModelGenerator.blockStateOutput.accept(
					MultiVariantGenerator.multiVariant(ladder, net.minecraft.data.models.blockstates.Variant.variant()
									.with(
											VariantProperties.MODEL,
											ModelLocationUtils.getModelLocation(
													ladder)))
							.with(createHorizontalFacingDispatch()));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ladder.asItem()),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_types/%s/ladder", name.getPath())
					)), blockStateModelGenerator.modelOutput);
		}
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				TextureMapping sapling2Mapping = TextureMapping.cross(
						new ResourceLocation(name.getNamespace(), String.format("wood_types/%s/%s", name.getPath(),
								netherWoodLike ? "fungi" : "sapling")));
				ResourceLocation resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCross()
						.create(sapling,
								sapling2Mapping,
								blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(sapling, resourceLocation));
				ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(sapling.asItem()),
						TextureMapping.layer0(new ResourceLocation(
								name.getNamespace(),
								String.format("wood_types/%s/%s", name.getPath(),
										netherWoodLike ? "fungi" : "sapling")
						)), blockStateModelGenerator.modelOutput);
			});
		} else {
			if (sapling != null) {
				ResourceLocation resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCross()
						.create(sapling,
								saplingMapping,
								blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(sapling, resourceLocation));
				ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(sapling.asItem()),
						TextureMapping.layer0(new ResourceLocation(
								name.getNamespace(),
								String.format("wood_types/%s/%s", name.getPath(),
										netherWoodLike ? "fungi" : "sapling")
						)), blockStateModelGenerator.modelOutput);
			}
		}
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				TextureMapping sapling2PlantMapping = TextureMapping.plant(new ResourceLocation(name.getNamespace(),
						customPottedTexture ? String.format(
								"wood_types/%s/potted_%s",
								name.getPath(),
								netherWoodLike ? "fungi" : "sapling") :
								String.format(
										"wood_types/%s/%s",
										name.getPath(),
										netherWoodLike ? "fungi" : "sapling")));
				ResourceLocation resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCrossPot()
						.create(pottedSapling,
								sapling2PlantMapping,
								blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(pottedSapling, resourceLocation));
			});
		} else {
			if (pottedSapling != null) {
				ResourceLocation resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCrossPot()
						.create(pottedSapling,
								saplingPlantMapping,
								blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(pottedSapling, resourceLocation));
			}
		}

		if (bookshelf != null) {
			ResourceLocation resourceLocation = ModelTemplates.CUBE_COLUMN.create(bookshelf, bookshelfMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(bookshelf, resourceLocation));
			ResourceLocation resourceLocationItem = ModelLocationUtils.getModelLocation(bookshelf);
			blockStateModelGenerator.delegateItemModel(bookshelf, resourceLocationItem);
		}
		if (fence != null) {
			ResourceLocation resourceLocation = ModelTemplates.FENCE_POST.create(fence, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.FENCE_SIDE.create(fence, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createFence(fence, resourceLocation, resourceLocation2));
			ResourceLocation resourceLocation3 = ModelTemplates.FENCE_INVENTORY.create(fence, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.delegateItemModel(fence, resourceLocation3);
		}
		if (fenceGate != null) {
			ResourceLocation resourceLocation = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation3 = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation4 = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createFenceGate(fenceGate, resourceLocation, resourceLocation2, resourceLocation3,
							resourceLocation4, !bambooLike));
			blockStateModelGenerator.delegateItemModel(fenceGate, resourceLocation2);
		}
		if (pressurePlate != null) {
			ResourceLocation resourceLocation = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlate, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createPressurePlate(pressurePlate, resourceLocation, resourceLocation2));
			blockStateModelGenerator.delegateItemModel(pressurePlate, resourceLocation);
		}
		if (slab != null) {
			ResourceLocation resourceLocation = ModelTemplates.SLAB_TOP.create(slab, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.SLAB_BOTTOM.create(slab, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createSlab(slab, resourceLocation2, resourceLocation,
							ModelLocationUtils.getModelLocation(planks)));
			blockStateModelGenerator.delegateItemModel(slab, resourceLocation2);
		}
		if (mosaicSlab != null) {
			ResourceLocation resourceLocation = ModelTemplates.SLAB_TOP.create(mosaicSlab, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.SLAB_BOTTOM.create(mosaicSlab, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createSlab(mosaicSlab, resourceLocation2, resourceLocation,
							ModelLocationUtils.getModelLocation(mosaic)));
			blockStateModelGenerator.delegateItemModel(mosaicSlab, resourceLocation2);
		}
		if (sign != null && wallSign != null && signItem != null) {
			ResourceLocation resourceLocation = ModelTemplates.PARTICLE_ONLY.create(sign, signMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createSimpleBlock(sign, resourceLocation));
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createSimpleBlock(wallSign, resourceLocation));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(signItem),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_types/%s/sign_item", name.getPath())
					)), blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.skipAutoItemBlock(wallSign);
		}
		if (boatItem != null) {
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(boatItem),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_types/%s/boat_item", name.getPath())
					)), blockStateModelGenerator.modelOutput);
		}
		if (chestBoatItem != null) {
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(chestBoatItem),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_types/%s/chest_boat_item", name.getPath())
					)), blockStateModelGenerator.modelOutput);
		}
		if (stairs != null) {
			ResourceLocation resourceLocation = ModelTemplates.STAIRS_INNER.create(stairs, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.STAIRS_STRAIGHT.create(stairs, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation3 = ModelTemplates.STAIRS_OUTER.create(stairs, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createStairs(stairs, resourceLocation, resourceLocation2, resourceLocation3));
			blockStateModelGenerator.delegateItemModel(stairs, resourceLocation2);
		}
		if (mosaicStairs != null) {
			ResourceLocation resourceLocation = ModelTemplates.STAIRS_INNER.create(mosaicStairs, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.STAIRS_STRAIGHT.create(mosaicStairs, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation3 = ModelTemplates.STAIRS_OUTER.create(mosaicStairs, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createStairs(mosaicStairs, resourceLocation, resourceLocation2, resourceLocation3));
			blockStateModelGenerator.delegateItemModel(mosaicStairs, resourceLocation2);
		}
		if (button != null) {
			ResourceLocation resourceLocation = ModelTemplates.BUTTON.create(button, planksMapping,
					blockStateModelGenerator.modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.BUTTON_PRESSED.create(button, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createButton(button, resourceLocation, resourceLocation2));
			ResourceLocation resourceLocation3 = ModelTemplates.BUTTON_INVENTORY.create(button, planksMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.delegateItemModel(button, resourceLocation3);
		}
	}

	@Environment(EnvType.CLIENT)
	public void generateLang(FabricLanguageProvider.TranslationBuilder translationBuilder, Map<String, String> lang) {
		String translatedName = getTranslation(name.getPath(), lang);

		String logName = netherWoodLike ? getTranslation("stem", lang) : getTranslation("log", lang);
		String strippedLogName = netherWoodLike ? getTranslation("stripped_stem", lang) : getTranslation("stripped_log", lang);
		String woodName = netherWoodLike ? getTranslation("hyphae", lang) : getTranslation("wood", lang);
		String strippedWoodName = netherWoodLike ? getTranslation("stripped_hyphae", lang) : getTranslation("stripped_wood", lang);
		String saplingName = netherWoodLike ? getTranslation("fungus", lang) : getTranslation("sapling", lang);
		String pottedSaplingName = netherWoodLike ? getTranslation("potted_fungus", lang) : getTranslation("potted_sapling", lang);
		String foliageBlockName = netherWoodLike ? getTranslation("wart_block", lang) : getTranslation("leaves", lang);
		String floweryFoliageBlockName = netherWoodLike ? getTranslation("flowering_wart_block", lang) : getTranslation("flowering_leaves", lang);

		// Add translations for log, stripped log, wood, and stripped wood
		if (log != null) translationBuilder.add(log, String.format(logName, translatedName));
		if (strippedLog != null) translationBuilder.add(strippedLog, String.format(strippedLogName, translatedName));
		if (wood != null) translationBuilder.add(wood, String.format(woodName, translatedName));
		if (strippedWood != null) translationBuilder.add(strippedWood, String.format(strippedWoodName, translatedName));

		// Add translations for planks
		if (planks != null) translationBuilder.add(planks, String.format(getTranslation("planks", lang), translatedName));

		// Add translations for saplings and potted saplings
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				String registryName = netherWoodLike ? s + "_fungus" : s + "_sapling";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));
				translationBuilder.add(block, String.format(saplingName, translatedName));
			});
		} else {
			if (sapling != null) translationBuilder.add(sapling, String.format(saplingName, translatedName));
		}
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				String registryName = "potted_" + (netherWoodLike ? s + "_fungus" : s + "_sapling");
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));
				translationBuilder.add(block, String.format(pottedSaplingName, translate(s, lang)));
			});
		} else {
			if (pottedSapling != null)
				translationBuilder.add(pottedSapling, String.format(pottedSaplingName, translatedName));
		}

		// Add translations for trapdoor and door
		if (trapdoor != null) translationBuilder.add(trapdoor, String.format(getTranslation("trapdoor", lang), translatedName));
		if (door != null) translationBuilder.add(door, String.format(getTranslation("door", lang), translatedName));

		// Add translations for leaves and flowering leaves
		if (!availableLeaves.isEmpty()) {
			availableLeaves.forEach(s -> {
				String registryName = netherWoodLike ? s + "_wart_block" : s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));
				translationBuilder.add(block, String.format(foliageBlockName, translate(s, lang)));
			});
		} else {
			if (leaves != null) translationBuilder.add(leaves, String.format(foliageBlockName, translatedName));
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				String registryName = netherWoodLike ? "flowery_" + s + "_wart_block" : "flowery_" + s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));
				translationBuilder.add(block, String.format(floweryFoliageBlockName, translate(s, lang)));
			});
		} else {
			if (floweryLeaves != null) translationBuilder.add(leaves, String.format(floweryFoliageBlockName, translatedName));
		}

		// Add translations for fence, gate, pressure plate, and button
		if (fence != null) translationBuilder.add(fence, String.format(getTranslation("fence", lang), translatedName));
		if (fenceGate != null) translationBuilder.add(fenceGate, String.format(getTranslation("fence_gate", lang), translatedName));
		if (pressurePlate != null) translationBuilder.add(pressurePlate, String.format(getTranslation("pressure_plate", lang), translatedName));
		if (button != null) translationBuilder.add(button, String.format(getTranslation("button", lang), translatedName));
		if (ladder != null) translationBuilder.add(ladder, String.format(getTranslation("ladder", lang), translatedName));
		if (bookshelf != null) translationBuilder.add(bookshelf, String.format(getTranslation("bookshelf", lang), translatedName));
		if (chiseledBookshelf != null) translationBuilder.add(chiseledBookshelf, String.format(getTranslation("chiseled_bookshelf", lang), translatedName));
		if (mosaic != null) translationBuilder.add(mosaic, String.format(getTranslation("mosaic", lang), translatedName));
		if (mosaicSlab != null) translationBuilder.add(mosaicSlab, String.format(getTranslation("mosaic_slab", lang), translatedName));
		if (mosaicStairs != null) translationBuilder.add(mosaicStairs, String.format(getTranslation("mosaic_stairs", lang), translatedName));
		if (boatItem != null) translationBuilder.add(boatItem, String.format(getTranslation("boat", lang), translatedName));
		if (chestBoatItem != null) translationBuilder.add(chestBoatItem, String.format(getTranslation("chest_boat", lang), translatedName));
		if (sign != null) translationBuilder.add(sign, String.format(getTranslation("sign", lang), translatedName));
		if (wallSign != null) translationBuilder.add(wallSign, String.format(getTranslation("wall_sign", lang), translatedName));
		if (hangingSign != null) translationBuilder.add(hangingSign, String.format(getTranslation("hanging_sign", lang), translatedName));
		if (hangingWallSign != null) translationBuilder.add(hangingWallSign, String.format(getTranslation("wall_hanging_sign", lang), translatedName));
	}

	public void generateLoot(FabricBlockLootTableProvider lootTablesProvider) {
		if (log != null) lootTablesProvider.dropSelf(log);
		if (strippedLog != null) lootTablesProvider.dropSelf(strippedLog);
		if (wood != null) lootTablesProvider.dropSelf(wood);
		if (strippedWood != null) lootTablesProvider.dropSelf(strippedWood);
		if (planks != null) lootTablesProvider.dropSelf(planks);
		if (!availableLeaves.isEmpty() && !netherWoodLike) {
			availableLeaves.forEach(s -> {
				String registryName = netherWoodLike ? s + "_wart_block" : s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));

				String saplingName = netherWoodLike ? s + "_fungus" : s + "_sapling";
				if (!availableSaplings.isEmpty() && availableSaplings.contains(saplingName)) {
					Block sapling = BuiltInRegistries.BLOCK.get(
							new ResourceLocation(name.getNamespace(), saplingName));
					lootTablesProvider.add(block, block1 -> lootTablesProvider.createLeavesDrops(block1, sapling, 0.05F, 0.0625F,
							0.083333336F, 0.1F));
				} else {
					if (sapling != null) lootTablesProvider.add(block,
							block1 -> lootTablesProvider.createLeavesDrops(block1, sapling,
									0.05F, 0.0625F,
									0.083333336F,
									0.1F));
					else lootTablesProvider.dropSelf(block);
				}
			});
		} else {
			if (leaves != null && !netherWoodLike) {
				if (sapling != null) lootTablesProvider.add(leaves,
						block1 -> lootTablesProvider.createLeavesDrops(block1, sapling,
								0.05F, 0.0625F,
								0.083333336F, 0.1F));
				else lootTablesProvider.dropSelf(leaves);
			}
		}
		if (!availableLeaves.isEmpty() && netherWoodLike) {
			availableLeaves.forEach(s -> {
				String registryName = netherWoodLike ? s + "_wart_block" : s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));
				lootTablesProvider.dropSelf(block);
			});
		} else {
			if (leaves != null && netherWoodLike) lootTablesProvider.dropSelf(leaves);
		}
		if (!availableFloweryLeaves.isEmpty() && !netherWoodLike) {
			availableFloweryLeaves.forEach(s -> {
				String registryName = netherWoodLike ? "flowery_" + s + "_wart_block" : "flowery_" + s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));

				String saplingName = netherWoodLike ? s + "_fungus" : s + "_sapling";
				if (!availableSaplings.isEmpty() && availableSaplings.contains(saplingName)) {
					Block sapling = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), saplingName));
					lootTablesProvider.add(block, block1 -> lootTablesProvider.createLeavesDrops(block1, sapling, 0.05F, 0.0625F,
							0.083333336F, 0.1F));
				} else {
					if (sapling != null) lootTablesProvider.add(block,
							block1 -> lootTablesProvider.createLeavesDrops(block1, sapling,
									0.05F, 0.0625F,
									0.083333336F,
									0.1F));
					else lootTablesProvider.dropSelf(block);
				}
			});
		} else {
			if (floweryLeaves != null && !netherWoodLike) {
				if (sapling != null) lootTablesProvider.add(floweryLeaves,
						block1 -> lootTablesProvider.createLeavesDrops(block1, sapling,
								0.05F, 0.0625F,
								0.083333336F, 0.1F));
				else lootTablesProvider.dropSelf(floweryLeaves);
			}
		}
		if (!availableFloweryLeaves.isEmpty() && netherWoodLike) {
			availableFloweryLeaves.forEach(s -> {
				String registryName = netherWoodLike ? "flowery_" + s + "_wart_block" : "flowery_" + s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), registryName));
				lootTablesProvider.dropSelf(block);
			});
		} else {
			if (floweryLeaves != null && netherWoodLike) lootTablesProvider.dropSelf(floweryLeaves);
		}
		if (ladder != null) lootTablesProvider.dropSelf(ladder);
		if (trapdoor != null) lootTablesProvider.dropSelf(trapdoor);
		if (button != null) lootTablesProvider.dropSelf(button);
		if (door != null) lootTablesProvider.add(door, lootTablesProvider::createDoorTable);
		if (sapling != null) lootTablesProvider.dropSelf(sapling);
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				String saplingName = netherWoodLike ? s + "_fungus" : s + "_sapling";
				Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name.getNamespace(), saplingName));
				lootTablesProvider.dropSelf(block);
			});
		}
		if (fence != null) lootTablesProvider.dropSelf(fence);
		if (fenceGate != null) lootTablesProvider.dropSelf(fenceGate);
		if (pressurePlate != null) lootTablesProvider.dropSelf(pressurePlate);
		if (slab != null) lootTablesProvider.add(slab, lootTablesProvider::createSlabItemTable);
		if (stairs != null) lootTablesProvider.dropSelf(stairs);
		if (sign != null) lootTablesProvider.dropSelf(sign);
		if (bookshelf != null) lootTablesProvider.add(bookshelf,
				block -> lootTablesProvider.createSingleItemTableWithSilkTouch(block, Items.BOOK,
						ConstantValue.exactly(
								3.0F)));
	}

	public void generateRecipes(Consumer<FinishedRecipe> exporter) {
		if (planks != null && logsItemTag != null)
			RecipeProvider.planksFromLogs(exporter, planks, logsItemTag, bambooLike ? 2 : 4);
		if (wood != null && log != null) RecipeProvider.woodFromLogs(exporter, wood, log);
		if (strippedWood != null && strippedLog != null)
			RecipeProvider.woodFromLogs(exporter, strippedWood, strippedLog);
		if (trapdoor != null && planks != null) RecipeProvider.trapdoorBuilder(trapdoor, Ingredient.of(planks));
		if (door != null && planks != null) RecipeProvider.doorBuilder(door, Ingredient.of(planks));
		if (fence != null && planks != null) RecipeProvider.fenceBuilder(fence, Ingredient.of(planks));
		if (fenceGate != null && planks != null) RecipeProvider.fenceGateBuilder(fenceGate, Ingredient.of(planks));
		if (slab != null && planks != null) RecipeProvider.slab(exporter, RecipeCategory.BUILDING_BLOCKS, slab, planks);
		if (stairs != null && planks != null) RecipeProvider.stairBuilder(stairs, Ingredient.of(planks));
		if (pressurePlate != null && planks != null) RecipeProvider.pressurePlate(exporter, pressurePlate, planks);
		if (button != null && planks != null) RecipeProvider.buttonBuilder(button, Ingredient.of(planks));
		if (boatItem != null && planks != null) RecipeProvider.woodenBoat(exporter, boatItem, planks);
		if (chestBoatItem != null && boatItem != null) RecipeProvider.chestBoat(exporter, chestBoatItem, planks);
		if (signItem != null && planks != null) RecipeProvider.signBuilder(signItem, Ingredient.of(planks));
		if (bookshelf != null && planks != null)
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf)
					.define('#', planks)
					.define('X', Items.BOOK)
					.pattern("###")
					.pattern("XXX")
					.pattern("###")
					.unlockedBy("has_book", has(Items.BOOK))
					.save(exporter);
	}

	public static class Builder {
		public ResourceLocation name;
		private WoodRegistry woodRegistry;
		private RegistryHelper registryHelper;
		private boolean preRegisteredPlanks = false;

		public Builder of(ResourceLocation name) {
			this.name = name;
			woodRegistry = new WoodRegistry(name);
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
			return this;
		}

		public Builder of(ResourceLocation name, AbstractTreeGrower saplingGenerator) {
			this.name = name;
			woodRegistry = new WoodRegistry(name, saplingGenerator);
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
			return this;
		}

		public Builder of(ResourceLocation name, ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator, Block baseFungusBlock) {
			this.name = name;
			woodRegistry = new WoodRegistry(name, fungusGenerator, baseFungusBlock);
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
			return this;
		}

		public Builder of(ResourceLocation name, Block planks) {
			this.name = name;
			woodRegistry = new WoodRegistry(name);
			woodRegistry.planks = planks;
			preRegisteredPlanks = true;
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());

			return this;
		}

		public Builder of(ResourceLocation name, Block planks, AbstractTreeGrower saplingGenerator) {
			this.name = name;
			woodRegistry = new WoodRegistry(name, saplingGenerator);
			woodRegistry.planks = planks;
			preRegisteredPlanks = true;
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());

			return this;
		}

		public Builder of(ResourceLocation name, Block planks, ResourceKey<ConfiguredFeature<?, ?>> fungusGenerator, Block baseFungusBlock) {
			this.name = name;
			woodRegistry = new WoodRegistry(name, fungusGenerator, baseFungusBlock);
			woodRegistry.planks = planks;
			preRegisteredPlanks = true;
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());

			return this;
		}

		public Builder log() {
			String logName = woodRegistry.netherWoodLike ? name.getPath() + "_stem" : woodRegistry.bambooLike ? this.name.getPath() + "_block" : this.name.getPath() + "_log";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_STEM : woodRegistry.bambooLike ? Blocks.BAMBOO_BLOCK : Blocks.MANGROVE_LOG;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.log = registryHelper.blocks().registerBlock(
					new RotatedPillarBlock(blockSettings),
					logName,
					CreativeModeTabs.BUILDING_BLOCKS, block
			);
			ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(block, woodRegistry.log));
			return this;
		}

		public Builder wood() {
			String woodName = woodRegistry.netherWoodLike ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_HYPHAE : Blocks.DARK_OAK_WOOD;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.wood = registryHelper.blocks().registerBlock(
					new RotatedPillarBlock(blockSettings),
					woodName,
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.log
			);
			return this;
		}

		public Builder strippedLog() {
			String logName = woodRegistry.netherWoodLike ? name.getPath() + "_stem" : woodRegistry.bambooLike ? this.name.getPath() + "_block" : this.name.getPath() + "_log";
			Block block = woodRegistry.netherWoodLike ? Blocks.STRIPPED_WARPED_STEM : woodRegistry.bambooLike ? Blocks.STRIPPED_BAMBOO_BLOCK : Blocks.STRIPPED_MANGROVE_LOG;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.strippedLog = registryHelper.blocks().registerBlock(
					new RotatedPillarBlock(blockSettings),
					"stripped_" + logName,
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.wood
			);
			return this;
		}

		public Builder strippedWood() {
			String woodName = woodRegistry.netherWoodLike ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
			Block block = woodRegistry.netherWoodLike ? Blocks.STRIPPED_WARPED_HYPHAE : Blocks.STRIPPED_DARK_OAK_WOOD;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.strippedWood = registryHelper.blocks().registerBlock(
					new RotatedPillarBlock(blockSettings),
					"stripped_" + woodName,
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.strippedLog
			);
			return this;
		}

		public Builder stairs() {
			woodRegistry.stairs = registryHelper.blocks().registerBlock(
					new StairBlock(
							woodRegistry.planks.defaultBlockState(),
							FabricBlockSettings.copy(woodRegistry.planks)
					), name.getPath() + "_stairs",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.planks
			);
			return this;
		}

		public Builder slab() {
			woodRegistry.slab = registryHelper.blocks().registerBlock(
					new SlabBlock(FabricBlockSettings.copy(woodRegistry.planks)),
					name.getPath() + "_slab",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.stairs
			);
			return this;
		}

		public Builder planks() {
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_PLANKS : woodRegistry.bambooLike ? Blocks.BAMBOO_PLANKS : Blocks.MANGROVE_PLANKS;
			woodRegistry.planks = registryHelper.blocks().registerBlock(
					new Block(FabricBlockSettings.copyOf(block)),
					name.getPath() + "_planks",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.strippedWood
			);
			return this;
		}

		public Builder leaves() {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
					name.getPath() + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, block
			);
			return this;
		}

		public Builder leaves(String nameIn) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
					nameIn + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, block
			);
			return this;
		}

		public Builder leaves(String... nameIn) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			for (String name : nameIn) {
				woodRegistry.leaves = registryHelper.blocks().registerBlock(
						woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
						name + leavesName,
						CreativeModeTabs.NATURAL_BLOCKS, block
				);
				woodRegistry.availableLeaves.add(name);
			}
			return this;
		}

		public Builder coloredLeaves() {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
					name.getPath() + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, block
			);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true));
			return this;
		}

		public Builder coloredLeaves(int color) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
					name.getPath() + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, block
			);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
			return this;
		}

		public Builder coloredLeaves(String nameIn) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
					nameIn + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, block
			);
			VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves,
					true);
			VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
			return this;
		}

		public Builder coloredLeaves(String... nameIn) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			for (String name : nameIn) {
				woodRegistry.leaves = registryHelper.blocks().registerBlock(
						woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
						name + leavesName,
						CreativeModeTabs.NATURAL_BLOCKS, block
				);
				VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true));
				woodRegistry.availableLeaves.add(name);
			}
			return this;
		}

		public Builder coloredLeaves(String nameIn, int color) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
					nameIn + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, block
			);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
			return this;
		}

		public Builder coloredLeaves(int color, String... nameIn) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			for (String name : nameIn) {
				woodRegistry.leaves = registryHelper.blocks().registerBlock(
						woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
						name + leavesName,
						CreativeModeTabs.NATURAL_BLOCKS, block
				);
				VampireLibClient.COLORED_LEAVES.add(
						new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
				woodRegistry.availableLeaves.add(name);
			}
			return this;
		}

		public Builder coloredLeaves(ColoredBlock... coloredLeavesBlocks) {
			String leavesName = woodRegistry.netherWoodLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			CreativeModeTab creativeModeTab = woodRegistry.netherWoodLike ? CreativeModeTabs.BUILDING_BLOCKS : CreativeModeTabs.NATURAL_BLOCKS;
			BlockBehaviour.Properties properties = FabricBlockSettings.copyOf(block);
			for (ColoredBlock coloredLeavesBlock : coloredLeavesBlocks) {
				woodRegistry.leaves = registryHelper.blocks().registerBlock(
						woodRegistry.netherWoodLike ? new Block(properties) : new LeavesBlock(properties),
						coloredLeavesBlock.name + leavesName,
						creativeModeTab, block
				);
				VampireLibClient.COLORED_LEAVES.add(
						new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, coloredLeavesBlock.color));
				woodRegistry.availableLeaves.add(coloredLeavesBlock.name);
			}
			return this;
		}

		public Builder sapling() {
			if (!woodRegistry.netherWoodLike) woodRegistry.sapling = registryHelper.blocks().registerBlock(
					new SaplingBaseBlock(woodRegistry.saplingGenerator),
					name.getPath() + "_sapling",
					CreativeModeTabs.NATURAL_BLOCKS, Blocks.MANGROVE_PROPAGULE
			);
			else woodRegistry.sapling = registryHelper.blocks().registerBlock(
					new FungusBaseBlock(woodRegistry.fungusGenerator, woodRegistry.baseFungusBlock),
					name.getPath() + "_fungus",
					CreativeModeTabs.NATURAL_BLOCKS, Blocks.WARPED_FUNGUS
			);
			return this;
		}

		public Builder pottedSapling() {
			String name = "potted_" + this.name.getPath() + (!woodRegistry.netherWoodLike ?
					"_sapling" : "_fungus");
			woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
					name,
					new FlowerPotBaseBlock(woodRegistry.sapling)
			);
			return this;
		}

		public Builder sapling(String nameIn) {
			if (!woodRegistry.netherWoodLike) {
				woodRegistry.sapling = registryHelper.blocks().registerBlock(
						new SaplingBaseBlock(woodRegistry.saplingGenerator),
						nameIn + "_sapling",
						CreativeModeTabs.NATURAL_BLOCKS, Blocks.MANGROVE_PROPAGULE
				);
			} else {
				woodRegistry.sapling = registryHelper.blocks().registerBlock(
						new FungusBaseBlock(woodRegistry.fungusGenerator, woodRegistry.baseFungusBlock),
						nameIn + "_fungus",
						CreativeModeTabs.NATURAL_BLOCKS, Blocks.WARPED_FUNGUS
				);
			}
			woodRegistry.availableSaplings.add(nameIn);
			return this;
		}

		public Builder pottedSapling(String nameIn) {
			String name = "potted_" + nameIn + (!woodRegistry.netherWoodLike ?
					"_sapling" : "_fungus");
			woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
					name,
					new FlowerPotBaseBlock(woodRegistry.sapling)
			);
			return this;
		}

		public Builder saplings(String... names) {
			for (String saplingName : names) {
				if (!woodRegistry.netherWoodLike) {
					woodRegistry.sapling = registryHelper.blocks().registerBlock(
							new SaplingBaseBlock(woodRegistry.saplingGenerator),
							saplingName + "_sapling",
							CreativeModeTabs.NATURAL_BLOCKS, Blocks.MANGROVE_PROPAGULE
					);
				} else {
					woodRegistry.sapling = registryHelper.blocks().registerBlock(
							new FungusBaseBlock(woodRegistry.fungusGenerator, woodRegistry.baseFungusBlock),
							saplingName + "_fungus",
							CreativeModeTabs.NATURAL_BLOCKS, Blocks.WARPED_FUNGUS
					);
				}
				woodRegistry.availableSaplings.add(saplingName);
			}
			return this;
		}

		public Builder pottedSapling(String... names) {
			for (String saplingName : names) {
				String name = "potted_" + saplingName + (!woodRegistry.netherWoodLike ?
						"_sapling" : "_fungus");
				woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
						name,
						new FlowerPotBaseBlock(woodRegistry.sapling));
			}
			return this;
		}

		public Builder fence() {
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_FENCE : woodRegistry.bambooLike ? Blocks.BAMBOO_FENCE : Blocks.DARK_OAK_FENCE;
			woodRegistry.fence = registryHelper.blocks().registerBlock(
					new FenceBlock(BlockBehaviour.Properties.copy(block)),
					name.getPath() + "_fence",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.slab
			);
			return this;
		}

		public Builder fenceGate() {
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_FENCE_GATE : woodRegistry.bambooLike ? Blocks.BAMBOO_FENCE_GATE : Blocks.DARK_OAK_FENCE_GATE;
			SoundEvent openSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN : SoundEvents.FENCE_GATE_OPEN;
			SoundEvent closeSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE : SoundEvents.FENCE_GATE_CLOSE;
			woodRegistry.fenceGate = registryHelper.blocks().registerBlock(
					new FenceGateBlock(BlockBehaviour.Properties.copy(block), openSound, closeSound),
					name.getPath() + "_fence_gate",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.fence
			);
			return this;
		}

		public Builder bookshelf() {
			woodRegistry.bookshelf = registryHelper.blocks().registerBlock(
					new Block(BlockBehaviour.Properties.copy(woodRegistry.planks)),
					name.getPath() + "_bookshelf",
					CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.BOOKSHELF
			);
			return this;
		}

		public Builder chiseledBookshelf() {
			woodRegistry.chiseledBookshelf = registryHelper.blocks().registerBlock(
					new ChiseledBookShelfBlock(BlockBehaviour.Properties.copy(woodRegistry.planks)),
					"chiseled_" + name.getPath() + "_bookshelf",
					CreativeModeTabs.FUNCTIONAL_BLOCKS, Blocks.CHISELED_BOOKSHELF
			);
			((IBlockEntityType) BlockEntityType.CHISELED_BOOKSHELF).vlAddBlocks(woodRegistry.chiseledBookshelf);
			return this;
		}

		public Builder door() {
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_DOOR : woodRegistry.bambooLike ? Blocks.BAMBOO_DOOR : Blocks.DARK_OAK_DOOR;
			SoundEvent openSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_DOOR_OPEN : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_DOOR_OPEN : SoundEvents.WOODEN_DOOR_OPEN;
			SoundEvent closeSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_DOOR_CLOSE : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_DOOR_CLOSE : SoundEvents.WOODEN_DOOR_CLOSE;
			woodRegistry.door = registryHelper.blocks().registerDoubleBlock(
					new DoorBlock(BlockBehaviour.Properties.copy(block), openSound, closeSound),
					name.getPath() + "_door",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.fenceGate
			);
			return this;
		}

		public Builder trapdoor() {
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_TRAPDOOR : woodRegistry.bambooLike ? Blocks.BAMBOO_TRAPDOOR : Blocks.MANGROVE_TRAPDOOR;
			SoundEvent openSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN : SoundEvents.WOODEN_TRAPDOOR_OPEN;
			SoundEvent closeSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE : SoundEvents.WOODEN_TRAPDOOR_CLOSE;
			woodRegistry.trapdoor = registryHelper.blocks().registerBlock(
					new TrapDoorBlock(BlockBehaviour.Properties.copy(block), openSound, closeSound),
					name.getPath() + "_trapdoor",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.door
			);
			return this;
		}

		public Builder pressurePlate(PressurePlateBlock.Sensitivity type) {
			Block block = woodRegistry.netherWoodLike ? Blocks.WARPED_PRESSURE_PLATE : woodRegistry.bambooLike ? Blocks.BAMBOO_PRESSURE_PLATE : Blocks.DARK_OAK_PRESSURE_PLATE;
			SoundEvent depressSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF : SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF;
			SoundEvent pressSound = woodRegistry.netherWoodLike ? SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON : woodRegistry.bambooLike ? SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON : SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON;
			woodRegistry.pressurePlate = registryHelper.blocks().registerBlock(
					new PressurePlateBlock(type, BlockBehaviour.Properties.copy(block), depressSound, pressSound),
					name.getPath() + "_pressure_plate",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.trapdoor
			);
			return this;
		}

		public Builder button() {
			Block netherWoodButton = woodenButton(SoundType.NETHER_WOOD, SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON);
			Block bambooButton = woodenButton(SoundType.BAMBOO_WOOD, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON);
			Block block = woodRegistry.netherWoodLike ? netherWoodButton : woodRegistry.bambooLike ? bambooButton : woodenButton();
			woodRegistry.button = registryHelper.blocks().registerBlock(
					block,
					name.getPath() + "_button",
					CreativeModeTabs.BUILDING_BLOCKS, woodRegistry.pressurePlate
			);
			return this;
		}

		private static ButtonBlock woodenButton() {
			return woodenButton(SoundType.WOOD, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
		}

		private static ButtonBlock woodenButton(SoundType soundGroup, SoundEvent offSound, SoundEvent onSound) {
			return new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(soundGroup), 30, true, offSound, onSound);
		}

		public Builder ladder() {
			woodRegistry.ladder = registryHelper.blocks().registerBlock(
					new CustomLadderBlock(),
					name.getPath() + "_ladder",
					CreativeModeTabs.NATURAL_BLOCKS, Blocks.LADDER
			);
			return this;
		}

		public Builder beehive() {
			woodRegistry.beehive = registryHelper.blocks().registerBlock(
					new BaseBeehiveBlock(BlockBehaviour.Properties.copy(Blocks.BEEHIVE)),
					name.getPath() + "_beehive",
					CreativeModeTabs.NATURAL_BLOCKS, Blocks.BEEHIVE
			);
			((IBlockEntityType) BlockEntityType.BEEHIVE).vlAddBlocks(woodRegistry.beehive);
			return this;
		}

		public Builder sign() {
			Item baseHangingSignItem = this.woodRegistry.netherWoodLike ? Items.WARPED_HANGING_SIGN :
					this.woodRegistry.bambooLike ? Items.BAMBOO_HANGING_SIGN : Items.MANGROVE_HANGING_SIGN;

			Block baseSignBlock = this.woodRegistry.netherWoodLike ? Blocks.WARPED_SIGN :
					this.woodRegistry.bambooLike ? Blocks.BAMBOO_SIGN : Blocks.MANGROVE_SIGN;
			ResourceLocation signTexture = new ResourceLocation(this.name.getNamespace(),
					"wood_types/" + this.name.getPath() + "/sign");
			this.woodRegistry.sign = this.registryHelper.blocks().registerBlockWithoutItem(
					this.name.getPath() + "_sign",
					new TerraformSignBlock(signTexture, FabricBlockSettings.copyOf(baseSignBlock))
			);
			this.woodRegistry.wallSign = this.registryHelper.blocks().registerBlockWithoutItem(
					this.name.getPath() + "_wall_sign",
					new TerraformWallSignBlock(signTexture, FabricBlockSettings.copyOf(baseSignBlock))
			);
			this.woodRegistry.signItem = this.registryHelper.items().registerItem(
					this.name.getPath() + "_sign",
					new SignItem(
							new Item.Properties().stacksTo(16), this.woodRegistry.sign,
							this.woodRegistry.wallSign
					),
					CreativeModeTabs.FUNCTIONAL_BLOCKS, baseHangingSignItem
			);
			((IBlockEntityType) BlockEntityType.SIGN).vlAddBlocks(this.woodRegistry.sign, this.woodRegistry.wallSign);
			return this;
		}

		public Builder hangingSign() {
			Block baseHangingSignBlock = this.woodRegistry.netherWoodLike ? Blocks.WARPED_HANGING_SIGN :
					this.woodRegistry.bambooLike ? Blocks.BAMBOO_HANGING_SIGN : Blocks.MANGROVE_HANGING_SIGN;
			ResourceLocation hangingSignTexture = new ResourceLocation(this.name.getNamespace(),
					"wood_types/" + this.name.getPath() + "/hanging_sign");
			ResourceLocation hangingSignGuiTexture = new ResourceLocation(this.name.getNamespace(),
					"gui/hanging_signs/" + this.name.getPath());
			this.woodRegistry.hangingSign = this.registryHelper.blocks().registerBlockWithoutItem(
					this.name.getPath() + "_hanging_sign",
					new TerraformHangingSignBlock(hangingSignTexture, hangingSignGuiTexture, FabricBlockSettings.copyOf(baseHangingSignBlock))
			);
			this.woodRegistry.hangingWallSign = this.registryHelper.blocks().registerBlockWithoutItem(
					this.name.getPath() + "_wall_hanging_sign",
					new TerraformWallHangingSignBlock(hangingSignTexture, hangingSignGuiTexture, FabricBlockSettings.copyOf(baseHangingSignBlock))
			);
			this.woodRegistry.hangingSignItem = this.registryHelper.items().registerItem(
					this.name.getPath() + "_hanging_sign",
					new HangingSignItem(
							this.woodRegistry.hangingSign, this.woodRegistry.hangingWallSign,
							new Item.Properties().stacksTo(16)
					),
					CreativeModeTabs.FUNCTIONAL_BLOCKS, woodRegistry.signItem
			);
			((IBlockEntityType) BlockEntityType.HANGING_SIGN).vlAddBlocks(this.woodRegistry.hangingSign, this.woodRegistry.hangingWallSign);
			return this;
		}

		public Builder boat() {
			String name = woodRegistry.bambooLike ? "_raft" : "_boat";
			woodRegistry.boatType = TerraformBoatTypeRegistry.createKey(Utils.appendToPath(this.name, name));

			woodRegistry.boatItem = TerraformBoatItemHelper.registerBoatItem(Utils.appendToPath(this.name, name),
					woodRegistry.boatType, false);
			woodRegistry.chestBoatItem = TerraformBoatItemHelper.registerBoatItem(
					Utils.appendToPath(this.name, "_chest" + name), woodRegistry.boatType, true);
			TerraformBoatType.Builder builder = new TerraformBoatType.Builder()
					.item(woodRegistry.boatItem)
					.chestItem(woodRegistry.chestBoatItem)
					.planks(woodRegistry.planks.asItem());
			if (this.woodRegistry.bambooLike) builder.raft();
			Registry.register(TerraformBoatTypeRegistry.INSTANCE, Utils.appendToPath(this.name, name), builder.build());
			if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
				TerraformBoatClientHelper.registerModelLayers(this.name, woodRegistry.bambooLike);
			}
			Item baseBoatItem = this.woodRegistry.netherWoodLike ? Items.BAMBOO_CHEST_RAFT :
					this.woodRegistry.bambooLike ? Items.BAMBOO_CHEST_RAFT : Items.MANGROVE_CHEST_BOAT;
			ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
				entries.addAfter(baseBoatItem, woodRegistry.boatItem);
				entries.addAfter(woodRegistry.boatItem, woodRegistry.chestBoatItem);
			});
			return this;
		}

		public Builder nonFlammable() {
			woodRegistry.flammable = false;
			return this;
		}

		public Builder mushroomLike() {
			woodRegistry.netherWoodLike = true;
			return this;
		}

		public Builder azaleaLike() {
			woodRegistry.azaleaLike = true;
			return this;
		}

		public Builder spruceLike() {
			woodRegistry.spruceLike = true;
			return this;
		}

		public Builder bambooLike() {
			woodRegistry.bambooLike = true;
			return this;
		}

		public Builder defaultLogsAndWoods() {
			return this.log().wood().strippedLog().strippedWood();
		}

		public Builder defaultBlocks() {
			return this.defaultLogsAndWoods().planks().leaves()
					.sapling().pottedSapling().fence().fenceGate()
					.stairs().slab().door().trapdoor()
					.pressurePlate(PressurePlateBlock.Sensitivity.EVERYTHING)
					.button().sign().hangingSign().boat();
		}

		public Builder defaultExtras() {
			return this.bookshelf().chiseledBookshelf().beehive()
					.ladder();
		}

		public Builder defaultBlocksColoredLeaves() {
			return this.defaultLogsAndWoods().planks().coloredLeaves().sapling().pottedSapling();
		}

		public Builder defaultBlocksColoredLeaves(int color) {
			return this.defaultLogsAndWoods().planks().coloredLeaves(color).sapling().pottedSapling();
		}

		public WoodRegistry build() {
			if (woodRegistry.leaves != null && !woodRegistry.netherWoodLike)
				ComposterBlock.COMPOSTABLES.put(woodRegistry.leaves, 0.3F);
			if (woodRegistry.flammable) {
				// flammable blocks
				int baseBurnChance = 5;
				int largeBurnChance = baseBurnChance * 6;

				int baseSpreadChance = 20;
				int smallSpreadChance = baseSpreadChance / 4;
				int largeSpreadChance = baseSpreadChance * 3;

				FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
				if (woodRegistry.planks != null && !preRegisteredPlanks)
					flammableBlockRegistry.add(woodRegistry.planks, baseBurnChance, baseSpreadChance);
				if (woodRegistry.slab != null)
					flammableBlockRegistry.add(woodRegistry.slab, baseBurnChance, baseSpreadChance);
				if (woodRegistry.fenceGate != null)
					flammableBlockRegistry.add(woodRegistry.fenceGate, baseBurnChance, baseSpreadChance);
				if (woodRegistry.fence != null)
					flammableBlockRegistry.add(woodRegistry.fence, baseBurnChance, baseSpreadChance);
				if (woodRegistry.stairs != null)
					flammableBlockRegistry.add(woodRegistry.stairs, baseBurnChance, baseSpreadChance);
				if (woodRegistry.log != null)
					flammableBlockRegistry.add(woodRegistry.log, baseBurnChance, smallSpreadChance);
				if (woodRegistry.strippedLog != null)
					flammableBlockRegistry.add(woodRegistry.strippedLog, baseBurnChance, smallSpreadChance);
				if (woodRegistry.strippedWood != null)
					flammableBlockRegistry.add(woodRegistry.strippedWood, baseBurnChance, smallSpreadChance);
				if (woodRegistry.wood != null)
					flammableBlockRegistry.add(woodRegistry.wood, baseBurnChance, smallSpreadChance);
				if (woodRegistry.leaves != null)
					flammableBlockRegistry.add(woodRegistry.leaves, largeBurnChance, largeSpreadChance);

				// fuel registering
				int fenceFuelTime = 300;

				FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
				if (woodRegistry.fence != null)
					fuelRegistry.add(woodRegistry.fence, fenceFuelTime);
				if (woodRegistry.fenceGate != null)
					fuelRegistry.add(woodRegistry.fenceGate, fenceFuelTime);
			}

			if (woodRegistry.log != null && woodRegistry.wood != null && woodRegistry.strippedLog != null &&
					woodRegistry.strippedWood != null) {
				StrippableBlockRegistry.register(woodRegistry.log, woodRegistry.strippedLog);
				StrippableBlockRegistry.register(woodRegistry.wood, woodRegistry.strippedWood);
			}

			if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
				if (woodRegistry.leaves != null) {
					BlockRenderLayerMap.INSTANCE.putBlock(woodRegistry.leaves, RenderType.cutoutMipped());
				}
				if (woodRegistry.sapling != null) {
					BlockRenderLayerMap.INSTANCE.putBlock(woodRegistry.sapling, RenderType.cutoutMipped());
				}
				if (woodRegistry.door != null) {
					BlockRenderLayerMap.INSTANCE.putBlock(woodRegistry.door, RenderType.cutoutMipped());
				}
				if (woodRegistry.trapdoor != null) {
					BlockRenderLayerMap.INSTANCE.putBlock(woodRegistry.trapdoor, RenderType.cutoutMipped());
				}
			}

			return woodRegistry;
		}
	}

	public record ColoredBlock(String name, int color) {
	}

	private String translate(String name, Map<String, String> lang) {
		return getTranslation(name, lang);
	}

	public String getTranslation(String key, Map<String, String> lang) {
		if(lang.containsKey(key)) {
			return lang.get(key);
		}
		return key.replace("_", " ");
	}

}
