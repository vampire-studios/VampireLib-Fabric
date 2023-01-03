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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.api.datagen.CustomTagProviders;
import io.github.vampirestudios.vampirelib.blocks.*;
import io.github.vampirestudios.vampirelib.blocks.entity.IBlockEntityType;
import io.github.vampirestudios.vampirelib.client.VampireLibClient;
import io.github.vampirestudios.vampirelib.utils.Utils;

public class WoodRegistry {
	private final ResourceLocation name;
	private final AbstractTreeGrower saplingGenerator;
	private final ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator;
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
	private Block door;
	private Block trapdoor;
	private Block button;
	private Block pressurePlate;
	private Block sign;
	private Block wallSign;
	private Block ladder;
	private Block beehive;
	private Item signItem;
	private Item boatItem;
	private Item chestBoatItem;
	private TerraformBoatType boatType;
	private boolean flammable = true;
	private boolean mushroomLike = false;
	private boolean azaleaLike = false;
	private boolean spruceLike = false;

	private WoodRegistry(ResourceLocation name) {
		this(name, null, null);
	}

	private WoodRegistry(ResourceLocation name, AbstractTreeGrower saplingGenerator) {
		this(name, saplingGenerator, null);
	}

	private WoodRegistry(ResourceLocation name, ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator) {
		this(name, null, fungusGenerator);
	}

	private WoodRegistry(ResourceLocation name, AbstractTreeGrower saplingGenerator, ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator) {
		this.name = name;
		this.saplingGenerator = saplingGenerator;
		this.fungusGenerator = fungusGenerator;
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

	public static WoodRegistry.Builder of(ResourceLocation name, ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator) {
		return new WoodRegistry.Builder().of(name, fungusGenerator);
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

	public AbstractTreeGrower saplingGenerator() {
		return saplingGenerator;
	}

	public ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator() {
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

	public Item boatItem() {
		return boatItem;
	}

	public Item chestBoatItem() {
		return chestBoatItem;
	}

	public void generateBlockTags(CustomTagProviders.CustomBlockTagProvider blockTags) {
		/*if (log != null) blockTags.tagCustom(logsTag).add(log);
		if (strippedLog != null) blockTags.tagCustom(logsTag).add(strippedLog);
		if (wood != null) blockTags.tagCustom(logsTag).add(wood);
		if (strippedWood != null) blockTags.tagCustom(logsTag).add(strippedWood);

		if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null)
			blockTags.tagCustom(BlockTags.LOGS).addTag(logsTag);

		if (planks != null) blockTags.tagCustom(BlockTags.PLANKS).add(planks);
		if (!availableLeaves.isEmpty() && !mushroomLike) {
			availableLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.LEAVES).add(block);
			});
		}
		if (!availableLeaves.isEmpty() && mushroomLike) {
			availableLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.WART_BLOCKS).add(block);
			});
		}
		if (leaves != null && !mushroomLike) blockTags.tagCustom(BlockTags.LEAVES).add(leaves);
		if (leaves != null && mushroomLike) blockTags.tagCustom(BlockTags.WART_BLOCKS).add(leaves);
		if (!availableFloweryLeaves.isEmpty() && !mushroomLike) {
			availableFloweryLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.LEAVES).add(block);
			});
		}
		if (!availableFloweryLeaves.isEmpty() && mushroomLike) {
			availableFloweryLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.WART_BLOCKS).add(block);
			});
		}
		if (floweryLeaves != null && !mushroomLike) blockTags.tagCustom(BlockTags.LEAVES).add(floweryLeaves);
		if (floweryLeaves != null && mushroomLike) blockTags.tagCustom(BlockTags.WART_BLOCKS).add(floweryLeaves);
		if (ladder != null) blockTags.tagCustom(BlockTags.CLIMBABLE).add(ladder);
		if (trapdoor != null) blockTags.tagCustom(BlockTags.WOODEN_TRAPDOORS).add(trapdoor);
		if (button != null) blockTags.tagCustom(BlockTags.WOODEN_BUTTONS).add(button);
		if (door != null) blockTags.tagCustom(BlockTags.WOODEN_DOORS).add(door);
		if (sapling != null) blockTags.tagCustom(BlockTags.SAPLINGS).add(sapling);
		if (sign != null) blockTags.tagCustom(BlockTags.STANDING_SIGNS).add(sign);
		if (wallSign != null) blockTags.tagCustom(BlockTags.WALL_SIGNS).add(wallSign);
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.SAPLINGS).add(block);
			});
		}
		if (pottedSapling != null) blockTags.tagCustom(BlockTags.FLOWER_POTS).add(pottedSapling);
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockTags.tagCustom(BlockTags.FLOWER_POTS).add(block);
			});
		}
		if (fence != null) blockTags.tagCustom(BlockTags.WOODEN_FENCES).add(fence);
		if (fenceGate != null) blockTags.tagCustom(BlockTags.FENCE_GATES).add(fenceGate);
		if (pressurePlate != null) blockTags.tagCustom(BlockTags.PRESSURE_PLATES).add(pressurePlate);
		if (slab != null) blockTags.tagCustom(BlockTags.WOODEN_SLABS).add(slab);
		if (stairs != null) blockTags.tagCustom(BlockTags.WOODEN_STAIRS).add(stairs);
		if (flammable) {
			if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null)
				blockTags.tagCustom(BlockTags.LOGS_THAT_BURN).addTag(logsTag);
		} else {
			CustomTagProviders.CustomFabricTagBuilder<Block> nonFlammableWoodTag = blockTags.tagCustom(
					BlockTags.NON_FLAMMABLE_WOOD);
			if (ladder != null) nonFlammableWoodTag.add(ladder);
			if (trapdoor != null) nonFlammableWoodTag.add(trapdoor);
			if (button != null) nonFlammableWoodTag.add(button);
			if (door != null) nonFlammableWoodTag.add(door);
			if (fence != null) nonFlammableWoodTag.add(fence);
			if (fenceGate != null) nonFlammableWoodTag.add(fenceGate);
			if (pressurePlate != null) nonFlammableWoodTag.add(pressurePlate);
			if (slab != null) nonFlammableWoodTag.add(slab);
			if (stairs != null) nonFlammableWoodTag.add(stairs);
			if (bookshelf != null) nonFlammableWoodTag.add(bookshelf);
		}*/
	}

	public void generateItemTags(CustomTagProviders.CustomItemTagProvider itemsTag) {
		/*if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null &&
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
		itemsTag.copy(BlockTags.NON_FLAMMABLE_WOOD, ItemTags.NON_FLAMMABLE_WOOD);
		itemsTag.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
		if (boatItem != null) itemsTag.tagCustom(ItemTags.BOATS).add(boatItem);
		if (chestBoatItem != null) itemsTag.tagCustom(ItemTags.CHEST_BOATS).add(chestBoatItem);*/
	}

	@Environment(EnvType.CLIENT)
	public void generateModels(BlockModelGenerators blockStateModelGenerator) {
		this.generateModels(blockStateModelGenerator, false);
	}

	@Environment(EnvType.CLIENT)
	public void generateModels(BlockModelGenerators blockStateModelGenerator, boolean customPottedTexture) {
		/*TextureMapping logMapping = new TextureMapping()
				.put(TextureSlot.SIDE, new ResourceLocation(name.getNamespace(),
						String.format("wood_sets/%s/%s", name.getPath(),
								mushroomLike ? "stem" : "log")))
				.put(TextureSlot.END, new ResourceLocation(name.getNamespace(),
						String.format("wood_sets/%s/%s_top", name.getPath(),
								mushroomLike ? "stem" : "log")));
		TextureMapping strippedLogMapping = new TextureMapping()
				.put(TextureSlot.SIDE, new ResourceLocation(name.getNamespace(),
						String.format("wood_sets/%s/stripped_%s", name.getPath(),
								mushroomLike ? "stem" : "log")))
				.put(TextureSlot.END, new ResourceLocation(name.getNamespace(),
						String.format("wood_sets/%s/stripped_%s_top", name.getPath(),
								mushroomLike ? "stem" : "log")));

		TextureMapping leavesMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/%s", name.getPath(),
						mushroomLike ? "wart_block" : "leaves")));

		TextureMapping floweryLeavesMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/%s", name.getPath(),
						"flowery_leaves")));

		TextureMapping saplingMapping = TextureMapping.cross(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/%s", name.getPath(),
						mushroomLike ? "fungi" : "sapling")));
		TextureMapping saplingPlantMapping = TextureMapping.plant(new ResourceLocation(name.getNamespace(),
				customPottedTexture ? String.format(
						"wood_sets/%s/potted_%s",
						name.getPath(),
						mushroomLike ? "fungi" : "sapling") :
						String.format("wood_sets/%s/%s",
								name.getPath(),
								mushroomLike ? "fungi" : "sapling")));

		TextureMapping planksMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/planks", name.getPath())));

		TextureMapping signMapping = TextureMapping.cube(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/sign", name.getPath())));

		TextureMapping doorMapping = new TextureMapping()
				.put(TextureSlot.TOP,
						new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/door_top", name.getPath())))
				.put(TextureSlot.BOTTOM,
						new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/door_bottom", name.getPath())));

		TextureMapping trapdoorMapping = TextureMapping.defaultTexture(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/trapdoor", name.getPath())));

		TextureMapping bookshelfMapping = TextureMapping.column(
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/bookshelf", name.getPath())),
				new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/planks", name.getPath()))
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
						new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/%s", name.getPath(), s)));
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockStateModelGenerator.createTrivialBlock(block, TexturedModel.createDefault(block1 -> leaves2Mapping,
						mushroomLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
				blockStateModelGenerator.delegateItemModel(block, resourceLocation);
			});
		} else {
			if (leaves != null) {
				blockStateModelGenerator.createTrivialBlock(leaves, TexturedModel.createDefault(block -> leavesMapping,
						mushroomLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(leaves);
				blockStateModelGenerator.delegateItemModel(leaves, resourceLocation);
			}
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				TextureMapping leaves2Mapping = TextureMapping.cube(
						new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/%s", name.getPath(), s)));
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				blockStateModelGenerator.createTrivialBlock(block, TexturedModel.createDefault(block1 -> leaves2Mapping,
						mushroomLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
				blockStateModelGenerator.delegateItemModel(block, resourceLocation);
			});
		} else {
			if (floweryLeaves != null) {
				blockStateModelGenerator.createTrivialBlock(leaves,
						TexturedModel.createDefault(block -> floweryLeavesMapping,
								mushroomLike ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
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
							String.format("wood_sets/%s/door", name.getPath())
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
							String.format("wood_sets/%s/ladder", name.getPath())
					)), blockStateModelGenerator.modelOutput);
		}
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				TextureMapping sapling2Mapping = TextureMapping.cross(
						new ResourceLocation(name.getNamespace(), String.format("wood_sets/%s/%s", name.getPath(),
								mushroomLike ? "fungi" : "sapling")));
				ResourceLocation resourceLocation = BlockModelGenerators.TintState.NOT_TINTED.getCross()
						.create(sapling,
								sapling2Mapping,
								blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(sapling, resourceLocation));
				ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(sapling.asItem()),
						TextureMapping.layer0(new ResourceLocation(
								name.getNamespace(),
								String.format("wood_sets/%s/%s", name.getPath(),
										mushroomLike ? "fungi" : "sapling")
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
								String.format("wood_sets/%s/%s", name.getPath(),
										mushroomLike ? "fungi" : "sapling")
						)), blockStateModelGenerator.modelOutput);
			}
		}
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				TextureMapping sapling2PlantMapping = TextureMapping.plant(new ResourceLocation(name.getNamespace(),
						customPottedTexture ? String.format(
								"wood_sets/%s/potted_%s",
								name.getPath(),
								mushroomLike ? "fungi" : "sapling") :
								String.format(
										"wood_sets/%s/%s",
										name.getPath(),
										mushroomLike ? "fungi" : "sapling")));
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
							resourceLocation4));
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
		if (sign != null && wallSign != null && signItem != null) {
			ResourceLocation resourceLocation = ModelTemplates.PARTICLE_ONLY.create(sign, signMapping,
					blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createSimpleBlock(sign, resourceLocation));
			blockStateModelGenerator.blockStateOutput.accept(
					BlockModelGenerators.createSimpleBlock(wallSign, resourceLocation));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(sign.asItem()),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_sets/%s/sign_item", name.getPath())
					)), blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.skipAutoItemBlock(wallSign);
		}
		if (boatItem != null) {
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(boatItem),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_sets/%s/boat_item", name.getPath())
					)), blockStateModelGenerator.modelOutput);
		}
		if (chestBoatItem != null) {
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(chestBoatItem),
					TextureMapping.layer0(new ResourceLocation(
							name.getNamespace(),
							String.format("wood_sets/%s/chest_boat_item", name.getPath())
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
		}*/
	}

	@Environment(EnvType.CLIENT)
	public void generateLang(FabricLanguageProvider.TranslationBuilder languageProvider) {
		/*String translatedName = WordUtils.capitalizeFully(name.getPath().replace("_", " "));
		if (log != null) languageProvider.addLanguage(log, translatedName + (mushroomLike ? " Stem" : " Log"));
		if (strippedLog != null)
			languageProvider.addLanguage(strippedLog, "Stripped " + translatedName + (mushroomLike ? " Stem" : " Log"));
		if (wood != null) languageProvider.addLanguage(wood, translatedName + (mushroomLike ? " Hyphae" : " Wood"));
		if (strippedWood != null) languageProvider.addLanguage(strippedWood, "Stripped " + translatedName +
				(mushroomLike ? " Hyphae" : " Wood"));
		if (planks != null) languageProvider.addLanguage(planks, translatedName + " Planks");
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				String translatedName1 = WordUtils.capitalizeFully(s.replace("_", " "));
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				languageProvider.addLanguage(block, translatedName1 + (mushroomLike ? " Fungi" : " Sapling"));
			});
		} else {
			if (sapling != null)
				languageProvider.addLanguage(sapling, translatedName + (mushroomLike ? " Fungi" : " Sapling"));
		}
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				String translatedName1 = WordUtils.capitalizeFully(s.replace("_", " "));
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				languageProvider.addLanguage(block,
						"Potted " + translatedName1 + (mushroomLike ? " Fungi" : " Sapling"));
			});
		} else {
			if (pottedSapling != null) languageProvider.addLanguage(pottedSapling, "Potted " + translatedName +
					(mushroomLike ? " Fungi" : " Sapling"));
		}
		if (trapdoor != null) languageProvider.addLanguage(trapdoor, translatedName + " Trapdoor");
		if (door != null) languageProvider.addLanguage(door, translatedName + " Door");
		if (!availableLeaves.isEmpty()) {
			availableLeaves.forEach(s -> {
				String translatedName1 = WordUtils.capitalizeFully(s.replace("_", " "));
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				languageProvider.addLanguage(block, translatedName1);
			});
		} else {
			if (leaves != null)
				languageProvider.addLanguage(leaves, translatedName + (mushroomLike ? " Wart Block" : " Leaves"));
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				String translatedName1 = WordUtils.capitalizeFully(s.replace("_", " "));
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				languageProvider.addLanguage(block, translatedName1);
			});
		} else {
			if (floweryLeaves != null) languageProvider.addLanguage(leaves, "Flowering " + translatedName + " Leaves");
		}
		if (fence != null) languageProvider.addLanguage(fence, translatedName + " Fence");
		if (fenceGate != null) languageProvider.addLanguage(fenceGate, translatedName + " Fence Gate");
		if (button != null) languageProvider.addLanguage(button, translatedName + " Button");
		if (pressurePlate != null) languageProvider.addLanguage(pressurePlate, translatedName + " Pressure Plate");
		if (slab != null) languageProvider.addLanguage(slab, translatedName + " Slab");
		if (stairs != null) languageProvider.addLanguage(stairs, translatedName + " Stairs");
		if (bookshelf != null) languageProvider.addLanguage(bookshelf, translatedName + " Bookshelf");
		if (signItem != null) languageProvider.addLanguage(sign, translatedName + " Sign");
		if (boatItem != null) languageProvider.addLanguage(boatItem, translatedName + " Boat");
		if (chestBoatItem != null) languageProvider.addLanguage(chestBoatItem, translatedName + " Boat with Chest");*/
	}

	public void generateLoot(FabricBlockLootTableProvider lootTablesProvider) {
		/*if (log != null) lootTablesProvider.dropSelf(log);
		if (strippedLog != null) lootTablesProvider.dropSelf(strippedLog);
		if (wood != null) lootTablesProvider.dropSelf(wood);
		if (strippedWood != null) lootTablesProvider.dropSelf(strippedWood);
		if (planks != null) lootTablesProvider.dropSelf(planks);
		if (!availableLeaves.isEmpty() && !mushroomLike) {
			availableLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				if (!availableSaplings.isEmpty() && availableSaplings.contains(s.replace("_leaves", "_sapling"))) {
					Block sapling = Registry.BLOCK.get(
							new ResourceLocation(name.getNamespace(), s.replace("_leaves", "_sapling")));
					lootTablesProvider.add(block, block1 -> BlockLoot.createLeavesDrops(block1, sapling, 0.05F, 0.0625F,
							0.083333336F, 0.1F));
				} else {
					if (sapling != null) lootTablesProvider.add(block,
							block1 -> BlockLoot.createLeavesDrops(block1, sapling,
									0.05F, 0.0625F,
									0.083333336F,
									0.1F));
					else lootTablesProvider.dropSelf(block);
				}
			});
		} else {
			if (leaves != null && !mushroomLike) {
				if (sapling != null) lootTablesProvider.add(leaves,
						block1 -> BlockLoot.createLeavesDrops(block1, sapling,
								0.05F, 0.0625F,
								0.083333336F, 0.1F));
				else lootTablesProvider.dropSelf(leaves);
			}
		}
		if (!availableLeaves.isEmpty() && mushroomLike) {
			availableLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				lootTablesProvider.dropSelf(block);
			});
		} else {
			if (leaves != null && mushroomLike) lootTablesProvider.dropSelf(leaves);
		}
		if (!availableFloweryLeaves.isEmpty() && !mushroomLike) {
			availableFloweryLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				if (!availableSaplings.isEmpty() &&
						availableSaplings.contains(s.replace("_leaves", "_sapling").replace("flowery_", ""))) {
					Block sapling = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(),
							s.replace("_leaves", "_sapling")
									.replace("flowery_", "")));
					lootTablesProvider.add(block, block1 -> BlockLoot.createLeavesDrops(block1, sapling, 0.05F, 0.0625F,
							0.083333336F, 0.1F));
				} else {
					if (sapling != null) lootTablesProvider.add(block,
							block1 -> BlockLoot.createLeavesDrops(block1, sapling,
									0.05F, 0.0625F,
									0.083333336F,
									0.1F));
					else lootTablesProvider.dropSelf(block);
				}
			});
		} else {
			if (floweryLeaves != null && !mushroomLike) {
				if (sapling != null) lootTablesProvider.add(floweryLeaves,
						block1 -> BlockLoot.createLeavesDrops(block1, sapling,
								0.05F, 0.0625F,
								0.083333336F, 0.1F));
				else lootTablesProvider.dropSelf(floweryLeaves);
			}
		}
		if (!availableFloweryLeaves.isEmpty() && mushroomLike) {
			availableFloweryLeaves.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				lootTablesProvider.dropSelf(block);
			});
		} else {
			if (floweryLeaves != null && mushroomLike) lootTablesProvider.dropSelf(floweryLeaves);
		}
		if (ladder != null) lootTablesProvider.dropSelf(ladder);
		if (trapdoor != null) lootTablesProvider.dropSelf(trapdoor);
		if (button != null) lootTablesProvider.dropSelf(button);
		if (door != null) lootTablesProvider.add(door, BlockLoot::createDoorTable);
		if (sapling != null) lootTablesProvider.dropSelf(sapling);
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				Block block = Registry.BLOCK.get(new ResourceLocation(name.getNamespace(), s));
				lootTablesProvider.dropSelf(block);
			});
		}
		if (fence != null) lootTablesProvider.dropSelf(fence);
		if (fenceGate != null) lootTablesProvider.dropSelf(fenceGate);
		if (pressurePlate != null) lootTablesProvider.dropSelf(pressurePlate);
		if (slab != null) lootTablesProvider.add(slab, BlockLoot::createSlabItemTable);
		if (stairs != null) lootTablesProvider.dropSelf(stairs);
		if (sign != null) lootTablesProvider.dropSelf(sign);
		if (bookshelf != null) lootTablesProvider.add(bookshelf,
				block -> createSingleItemTableWithSilkTouch(block, Items.BOOK,
						ConstantValue.exactly(
								3.0F)));*/
	}

	public void generateRecipes(Consumer<FinishedRecipe> exporter) {
		/*if (planks != null && logsItemTag != null) RecipeProvider.planksFromLogs(exporter, planks, logsItemTag);
		if (wood != null && log != null) RecipeProvider.woodFromLogs(exporter, wood, log);
		if (strippedWood != null && strippedLog != null)
			RecipeProvider.woodFromLogs(exporter, strippedWood, strippedLog);
		if (trapdoor != null && planks != null) RecipeProvider.trapdoorBuilder(trapdoor, Ingredient.of(planks));
		if (door != null && planks != null) RecipeProvider.doorBuilder(door, Ingredient.of(planks));
		if (fence != null && planks != null) RecipeProvider.fenceBuilder(fence, Ingredient.of(planks));
		if (fenceGate != null && planks != null) RecipeProvider.fenceGateBuilder(fenceGate, Ingredient.of(planks));
		if (slab != null && planks != null) RecipeProvider.slab(exporter, slab, planks);
		if (stairs != null && planks != null) RecipeProvider.stairBuilder(stairs, Ingredient.of(planks));
		if (pressurePlate != null && planks != null) RecipeProvider.pressurePlate(exporter, pressurePlate, planks);
		if (button != null && planks != null) RecipeProvider.buttonBuilder(button, Ingredient.of(planks));
		if (boatItem != null && planks != null) RecipeProvider.woodenBoat(exporter, boatItem, planks);
		if (chestBoatItem != null && boatItem != null) ShapelessRecipeBuilder.shapeless(chestBoatItem)
				.requires(Blocks.CHEST)
				.requires(boatItem)
				.unlockedBy("has_boat",
						has(ItemTags.BOATS))
				.save(exporter);
		if (signItem != null && planks != null) RecipeProvider.signBuilder(signItem, Ingredient.of(planks));
		if (bookshelf != null && planks != null)
			ShapedRecipeBuilder.shaped(bookshelf).define('#', planks).define('X', Items.BOOK).pattern("###")
					.pattern("XXX").pattern("###").unlockedBy("has_book", has(Items.BOOK)).save(exporter);*/
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

		public Builder of(ResourceLocation name, ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator) {
			this.name = name;
			woodRegistry = new WoodRegistry(name, fungusGenerator);
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

		public Builder of(ResourceLocation name, Block planks, ConfiguredFeature<HugeFungusConfiguration, ?> fungusGenerator) {
			this.name = name;
			woodRegistry = new WoodRegistry(name, fungusGenerator);
			woodRegistry.planks = planks;
			preRegisteredPlanks = true;
			registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());

			return this;
		}

		public Builder log() {
			String logName = woodRegistry.mushroomLike ? name.getPath() + "_stem" : this.name.getPath() + "_log";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_STEM : Blocks.DARK_OAK_LOG;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.log = registryHelper.blocks().registerBlock(new RotatedPillarBlock(blockSettings),
					logName, CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS);
			return this;
		}

		public Builder wood() {
			String woodName = woodRegistry.mushroomLike ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_HYPHAE : Blocks.DARK_OAK_WOOD;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.wood = registryHelper.blocks().registerBlock(new RotatedPillarBlock(blockSettings),
					woodName, CreativeModeTabs.BUILDING_BLOCKS);
			return this;
		}

		public Builder strippedLog() {
			String logName = woodRegistry.mushroomLike ? name.getPath() + "_stem" : name.getPath() + "_log";
			Block block = woodRegistry.mushroomLike ? Blocks.STRIPPED_WARPED_STEM : Blocks.STRIPPED_DARK_OAK_LOG;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.strippedLog = registryHelper.blocks().registerBlock(new RotatedPillarBlock(blockSettings),
							"stripped_" + logName, CreativeModeTabs.BUILDING_BLOCKS);
			return this;
		}

		public Builder strippedWood() {
			String woodName = woodRegistry.mushroomLike ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
			Block block = woodRegistry.mushroomLike ? Blocks.STRIPPED_WARPED_HYPHAE : Blocks.STRIPPED_DARK_OAK_WOOD;
			BlockBehaviour.Properties blockSettings = FabricBlockSettings.copyOf(block);
			woodRegistry.strippedWood = registryHelper.blocks().registerBlock(new RotatedPillarBlock(blockSettings),
							"stripped_" + woodName, CreativeModeTabs.BUILDING_BLOCKS);
			return this;
		}

		public Builder stairs() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_STAIRS : Blocks.DARK_OAK_STAIRS;
			woodRegistry.stairs = registryHelper.blocks().registerBlock(new StairBlock(woodRegistry.planks.defaultBlockState(),
							FabricBlockSettings.copyOf(block)), name.getPath() + "_stairs");
			return this;
		}

		public Builder slab() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_SLAB : Blocks.DARK_OAK_SLAB;
			woodRegistry.slab = registryHelper.blocks().registerBlock(
					new SlabBlock(FabricBlockSettings.copy(block)),
					name.getPath() + "_slab", CreativeModeTabs.BUILDING_BLOCKS);
			return this;
		}

		public Builder planks() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_PLANKS : Blocks.DARK_OAK_PLANKS;
			woodRegistry.planks = registryHelper.blocks().registerBlock(
					new Block(FabricBlockSettings.copyOf(block)),
					name.getPath() + "_planks", CreativeModeTabs.BUILDING_BLOCKS);
			return this;
		}

		public Builder leaves() {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			woodRegistry.leaves = registryHelper.blocks().registerBlock(
					woodRegistry.mushroomLike ? new Block(FabricBlockSettings.copyOf(block)) :
							new LeavesBlock(FabricBlockSettings.copyOf(block)), name.getPath() + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS);
			return this;
		}

		public Builder leaves(String nameIn) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			woodRegistry.leaves = registryHelper.blocks().registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), nameIn + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS);
			return this;
		}

		public Builder leaves(String... nameIn) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			for (String name : nameIn) {
				woodRegistry.leaves = registryHelper.blocks()
						.registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), name + leavesName,
								CreativeModeTabs.NATURAL_BLOCKS);
				woodRegistry.availableLeaves.add(name + leavesName);
			}
			return this;
		}

		public Builder coloredLeaves() {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			woodRegistry.leaves = registryHelper.blocks()
					.registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), name.getPath() + leavesName,
							CreativeModeTabs.NATURAL_BLOCKS);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true));
			return this;
		}

		public Builder coloredLeaves(int color) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			woodRegistry.leaves = registryHelper.blocks()
					.registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), name.getPath() + leavesName,
							CreativeModeTabs.NATURAL_BLOCKS);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
			return this;
		}

		public Builder coloredLeaves(String nameIn) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			woodRegistry.leaves = registryHelper.blocks().registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), nameIn + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS);
			VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves,
					true);
			VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
			return this;
		}

		public Builder coloredLeaves(String... nameIn) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			for (String name : nameIn) {
				woodRegistry.leaves = registryHelper.blocks()
						.registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), name + leavesName,
								CreativeModeTabs.NATURAL_BLOCKS);
				VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true));
				woodRegistry.availableLeaves.add(name + leavesName);
			}
			return this;
		}

		public Builder coloredLeaves(String nameIn, int color) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			woodRegistry.leaves = registryHelper.blocks().registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), nameIn + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
			return this;
		}

		public Builder coloredLeaves(int color, String... nameIn) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			for (String name : nameIn) {
				woodRegistry.leaves = registryHelper.blocks()
						.registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)), name + leavesName,
								CreativeModeTabs.NATURAL_BLOCKS);
				VampireLibClient.COLORED_LEAVES.add(
						new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
				woodRegistry.availableLeaves.add(name + leavesName);
			}
			return this;
		}

		public Builder coloredLeaves(ColoredBlock... coloredLeavesBlocks) {
			String leavesName = woodRegistry.mushroomLike ? "_wart_block" : "_leaves";
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			for (ColoredBlock coloredLeavesBlock : coloredLeavesBlocks) {
				woodRegistry.leaves = registryHelper.blocks().registerBlock(new LeavesBlock(FabricBlockSettings.copyOf(block)),
						coloredLeavesBlock.name + leavesName,
						CreativeModeTabs.NATURAL_BLOCKS);
				VampireLibClient.COLORED_LEAVES.add(
						new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, coloredLeavesBlock.color));
				woodRegistry.availableLeaves.add(coloredLeavesBlock.name + leavesName);
			}
			return this;
		}

		public Builder sapling() {
			if (!woodRegistry.mushroomLike) woodRegistry.sapling = registryHelper.blocks().registerBlock(
					new SaplingBaseBlock(woodRegistry.saplingGenerator),
					name.getPath() + "_sapling", CreativeModeTabs.NATURAL_BLOCKS);
			else woodRegistry.sapling = registryHelper.blocks()
					.registerBlock(new FungusBaseBlock(woodRegistry.fungusGenerator),
							name.getPath() + "_fungus",
							CreativeModeTabs.NATURAL_BLOCKS);
			return this;
		}

		public Builder pottedSapling() {
			woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
					"potted_" + name.getPath() + (!woodRegistry.mushroomLike ?
							"_sapling" : "_fungus"), new FlowerPotBaseBlock(woodRegistry.sapling));
			return this;
		}

		public Builder sapling(String nameIn) {
			if (!woodRegistry.mushroomLike) woodRegistry.sapling = registryHelper.blocks().registerBlock(
					new SaplingBaseBlock(woodRegistry.saplingGenerator),
					nameIn + "_sapling", CreativeModeTabs.NATURAL_BLOCKS);
			else woodRegistry.sapling = registryHelper.blocks()
					.registerBlock(new FungusBaseBlock(woodRegistry.fungusGenerator),
							nameIn + "_fungus",
							CreativeModeTabs.NATURAL_BLOCKS);
			return this;
		}

		public Builder pottedSapling(String nameIn) {
			woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
					"potted_" + nameIn + (!woodRegistry.mushroomLike ?
							"_sapling" : "_fungus"), new FlowerPotBaseBlock(woodRegistry.sapling));
			return this;
		}

		public Builder saplings(String... names) {
			for (String saplingName : names) {
				if (!woodRegistry.mushroomLike) {
					woodRegistry.sapling = registryHelper.blocks().registerBlock(
							new SaplingBaseBlock(woodRegistry.saplingGenerator),
							saplingName + "_sapling", CreativeModeTabs.NATURAL_BLOCKS);
					woodRegistry.availableSaplings.add(saplingName + "_sapling");
				} else {
					woodRegistry.sapling = registryHelper.blocks().registerBlock(
							new FungusBaseBlock(woodRegistry.fungusGenerator),
							saplingName + "_fungus", CreativeModeTabs.NATURAL_BLOCKS);
					woodRegistry.availableSaplings.add(saplingName + "_fungus");
				}
			}
			return this;
		}

		public Builder pottedSapling(String... names) {
			for (String saplingName : names) {
				woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
						"potted_" + saplingName + (!woodRegistry.mushroomLike ? "_sapling" : "_fungus"),
						new FlowerPotBaseBlock(woodRegistry.sapling));
				woodRegistry.availablePottedSaplings.add(
						saplingName + (!woodRegistry.mushroomLike ? "_sapling" : "_fungus"));
			}
			return this;
		}

		public Builder fence() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_FENCE : Blocks.DARK_OAK_FENCE;
			woodRegistry.fence = registryHelper.blocks().registerBlock(
					new FenceBlock(BlockBehaviour.Properties.copy(block)),
					name.getPath() + "_fence", CreativeModeTabs.FUNCTIONAL_BLOCKS);
			return this;
		}

		public Builder fenceGate() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_FENCE_GATE : Blocks.DARK_OAK_FENCE_GATE;
			SoundEvent offSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE
					: SoundEvents.FENCE_GATE_CLOSE;
			SoundEvent onSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN
					: SoundEvents.FENCE_GATE_OPEN;
			woodRegistry.fenceGate = registryHelper.blocks().registerBlock(
					new FenceGateBlock(BlockBehaviour.Properties.copy(block), offSound, onSound),
					name.getPath() + "_fence_gate", CreativeModeTabs.REDSTONE_BLOCKS);
			return this;
		}

		public Builder bookshelf() {
			woodRegistry.bookshelf = registryHelper.blocks().registerBlock(new Block(
					BlockBehaviour.Properties.copy(woodRegistry.planks)),
					name.getPath() + "_bookshelf");
			return this;
		}

		public Builder door() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_DOOR : Blocks.DARK_OAK_DOOR;
			SoundEvent offSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_DOOR_CLOSE
					: SoundEvents.WOODEN_DOOR_CLOSE;
			SoundEvent onSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_DOOR_OPEN
					: SoundEvents.WOODEN_DOOR_OPEN;
			woodRegistry.door = registryHelper.blocks().registerBlock(
					new DoorBaseBlock(BlockBehaviour.Properties.copy(block), offSound, onSound),
					name.getPath() + "_door", CreativeModeTabs.REDSTONE_BLOCKS);
			return this;
		}

		public Builder trapdoor() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_TRAPDOOR : Blocks.DARK_OAK_TRAPDOOR;
			SoundEvent offSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_DOOR_CLOSE
					: SoundEvents.WOODEN_DOOR_CLOSE;
			SoundEvent onSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_DOOR_OPEN
					: SoundEvents.WOODEN_DOOR_OPEN;
			woodRegistry.trapdoor = registryHelper.blocks().registerBlock(
					new TrapDoorBlock(BlockBehaviour.Properties.copy(block), offSound, onSound),
					name.getPath() + "_trapdoor", CreativeModeTabs.REDSTONE_BLOCKS);
			return this;
		}

		public Builder button() {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_BUTTON : Blocks.DARK_OAK_BUTTON;
			SoundEvent offSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF
					: SoundEvents.WOODEN_BUTTON_CLICK_OFF;
			SoundEvent onSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON
					: SoundEvents.WOODEN_BUTTON_CLICK_ON;
			woodRegistry.button = registryHelper.blocks().registerBlock(new ButtonBlock(
					BlockBehaviour.Properties.copy(block), 15,
							true, offSound, onSound),
					name.getPath() + "_button", CreativeModeTabs.REDSTONE_BLOCKS);
			return this;
		}

		public Builder pressurePlate(PressurePlateBlock.Sensitivity type) {
			Block block = woodRegistry.mushroomLike ? Blocks.WARPED_PRESSURE_PLATE : Blocks.DARK_OAK_PRESSURE_PLATE;
			SoundEvent offSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF
					: SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF;
			SoundEvent onSound = woodRegistry.mushroomLike ? SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON
					: SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON;
			woodRegistry.pressurePlate = registryHelper.blocks().registerBlock(
					new PressurePlateBlock(type, BlockBehaviour.Properties.copy(block), offSound, onSound),
					name.getPath() + "_pressure_plate", CreativeModeTabs.REDSTONE_BLOCKS);
			return this;
		}

		public Builder ladder() {
			woodRegistry.ladder = registryHelper.blocks().registerBlock(new CustomLadderBlock(),
					name.getPath() + "_ladder", CreativeModeTabs.NATURAL_BLOCKS);
			return this;
		}

		public Builder beehive() {
			woodRegistry.beehive = registryHelper.blocks().registerBlock(
					new BeehiveBlock(BlockBehaviour.Properties.copy(Blocks.BEEHIVE)),
					name.getPath() + "_beehive", CreativeModeTabs.FUNCTIONAL_BLOCKS);
			((IBlockEntityType) BlockEntityType.BEEHIVE).vlAddBlocks(woodRegistry.beehive);
			return this;
		}

		public Builder sign() {
			Block baseBlock = this.woodRegistry.mushroomLike ? Blocks.WARPED_SIGN : Blocks.DARK_OAK_SIGN;

			ResourceLocation signTexture = new ResourceLocation(this.name.getNamespace(),
					"wood_types/" + this.name.getPath() + "/sign");
			this.woodRegistry.sign = this.registryHelper.blocks().registerBlockWithoutItem(this.name.getPath() + "_sign",
					new TerraformSignBlock(signTexture, FabricBlockSettings.copyOf(Blocks.OAK_SIGN)));
			this.woodRegistry.wallSign = this.registryHelper.blocks().registerBlockWithoutItem(this.name.getPath() + "_wall_sign",
					new TerraformWallSignBlock(signTexture, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN)));
			this.woodRegistry.signItem = this.registryHelper.items().registerItem(this.name.getPath() + "_sign", new SignItem(
					new Item.Properties().durability(16), this.woodRegistry.sign,
					this.woodRegistry.wallSign));
			((IBlockEntityType) BlockEntityType.SIGN).vlAddBlocks(this.woodRegistry.sign, this.woodRegistry.wallSign);
			return this;
		}

		public Builder boat() {
//			this.woodRegistry.boatItem = TerraformBoatItemHelper.registerBoatItem(Utils.appendToPath(this.name, "_boat"),
//					() -> this.woodRegistry.boatType, false);
//			woodRegistry.chestBoatItem = TerraformBoatItemHelper.registerBoatItem(
//					Utils.appendToPath(this.name, "_chest_boat"), () -> this.woodRegistry.boatType, true);
//			woodRegistry.boatType = Registry.register(TerraformBoatTypeRegistry.INSTANCE, name,
//					new TerraformBoatType.Builder().item(woodRegistry.boatItem)
//							.chestItem(
//									woodRegistry.chestBoatItem)
//							.build());
//			if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
//				TerraformBoatClientHelper.registerModelLayers(name);
//			}
			return this;
		}

		public Builder nonFlammable() {
			woodRegistry.flammable = false;
			return this;
		}

		public Builder mushroomLike() {
			woodRegistry.mushroomLike = true;
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

		public Builder defaultLogsAndWoods() {
			return this.log().strippedLog().wood().strippedWood();
		}

		public Builder defaultBlocks() {
			return this.defaultLogsAndWoods().planks().leaves().sapling().pottedSapling();
		}

		public Builder defaultExtras() {
			return this.fence().fenceGate().stairs().slab().door().trapdoor().button()
					.pressurePlate(PressurePlateBlock.Sensitivity.EVERYTHING).sign().boat()
					.bookshelf();
		}

		public Builder defaultBlocksColoredLeaves() {
			return this.defaultLogsAndWoods().planks().coloredLeaves().sapling().pottedSapling();
		}

		public Builder defaultBlocksColoredLeaves(int color) {
			return this.defaultLogsAndWoods().planks().coloredLeaves(color).sapling().pottedSapling();
		}

		public WoodRegistry build() {
			if (woodRegistry.leaves != null && !woodRegistry.mushroomLike)
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
					BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.leaves, RenderType.cutoutMipped());
				}
				if (woodRegistry.sapling != null) {
					BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.sapling, RenderType.cutoutMipped());
				}
				if (woodRegistry.door != null) {
					BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.door, RenderType.cutoutMipped());
				}
				if (woodRegistry.trapdoor != null) {
					BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.trapdoor, RenderType.cutoutMipped());
				}
			}

			return woodRegistry;
		}
	}

	public record ColoredBlock(String name, int color) {
	}

}
