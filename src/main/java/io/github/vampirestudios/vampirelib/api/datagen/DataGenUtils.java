package io.github.vampirestudios.vampirelib.api.datagen;

import static net.minecraft.client.data.models.BlockModelGenerators.createBooleanModelDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

import io.github.vampirestudios.vampirelib.api.BasicModClass;
import io.github.vampirestudios.vampirelib.utils.IdentifierUtils;

public class DataGenUtils {

	public DataGenUtils(BasicModClass instance) {
		IdentifierUtils.setModInstance(instance);
	}

	public static void generateSapling(String path, Block block, Block pottedBlock, BlockModelGenerators generators) {
		TextureMapping saplingMapping = TextureMapping.cross(new Material(IdentifierUtils.modId(path)));
		TextureMapping pottedSaplingMapping = TextureMapping.plant(new Material(IdentifierUtils.modId(path)));

		MultiVariant sapling = BlockModelGenerators.plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCross()
				.create(block, saplingMapping, generators.modelOutput));
		generators.blockStateOutput.accept(createSimpleBlock(block, sapling));
		ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(path))),
				generators.modelOutput);

		MultiVariant pottedSapling = BlockModelGenerators.plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCrossPot()
				.create(pottedBlock, pottedSaplingMapping, generators.modelOutput));
		generators.blockStateOutput.accept(createSimpleBlock(pottedBlock, pottedSapling));
	}

	public static void generateFlatModel(Item item, String path, ItemModelGenerators modelOutput) {
		ModelTemplates.FLAT_ITEM.create(
				ModelLocationUtils.getModelLocation(item),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(path))),
				modelOutput.modelOutput
		);
	}

	public static void generateFlatModel(Block item, String path, BlockModelGenerators modelOutput) {
		ModelTemplates.FLAT_ITEM.create(
				ModelLocationUtils.getModelLocation(item),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(path))),
				modelOutput.modelOutput
		);
	}

	public static void generateFlatHandheldModel(Item item, String path, ItemModelGenerators modelOutput) {
		ModelTemplates.FLAT_HANDHELD_ITEM.create(
				ModelLocationUtils.getModelLocation(item),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(path))),
				modelOutput.modelOutput
		);
	}

	public static void generateSimpleCubeModels(BlockModelGenerators blockStateModelGenerator, Identifier resourceLocation, Block block) {
		TextureMapping brimstoneCoalOreTextureMapping = TextureMapping.cube(new Material(resourceLocation));
		MultiVariant brimstoneCoalOreResourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ALL.create(block,
				brimstoneCoalOreTextureMapping,
				blockStateModelGenerator.modelOutput));
		blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(block, brimstoneCoalOreResourceLocation));
		blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	public static void generateConnectedCubeModels(
			BlockModelGenerators blockStateModelGenerator, Identifier resourceLocation,
			Identifier resourceLocation2, Block block, Property<Boolean> property
	) {
		Identifier notConnectedResourceLocation = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(new Material(resourceLocation)),
				blockStateModelGenerator.modelOutput);
		Identifier connectedResourceLocation = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(new Material(resourceLocation2)),
				blockStateModelGenerator.modelOutput);
//		blockStateModelGenerator.blockStateOutput.accept(
//				createConnectedBlock(block, property, connectedResourceLocation, notConnectedResourceLocation));
		blockStateModelGenerator.registerSimpleItemModel(block, notConnectedResourceLocation);
	}

	public static void generateConnectedTopBottomSideModels(
			BlockModelGenerators blockStateModelGenerator, Identifier notConnectedTexture,
			Triple<Identifier, Identifier, Identifier> connectedTextures, Block block,
			Property<Boolean> property
	) {
		TextureMapping connectedTextureMapping = new TextureMapping()
				.put(TextureSlot.TOP, new Material(connectedTextures.getLeft()))
				.put(TextureSlot.SIDE, new Material(connectedTextures.getMiddle()))
				.put(TextureSlot.BOTTOM, new Material(connectedTextures.getRight()));
		Identifier notConnectedResourceLocation = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(new Material(notConnectedTexture)),
				blockStateModelGenerator.modelOutput);
		Identifier connectedResourceLocation = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block,
				"_connected",
				connectedTextureMapping,
				blockStateModelGenerator.modelOutput);
//		blockStateModelGenerator.blockStateOutput.accept(
//				createConnectedBlock(block, property, connectedResourceLocation, notConnectedResourceLocation));
		blockStateModelGenerator.registerSimpleItemModel(block, notConnectedResourceLocation);
	}

	public static void generateConnectedEndSideModels(
			BlockModelGenerators blockStateModelGenerator, Identifier notConnectedTexture,
			Pair<Identifier, Identifier> connectedTextures, Block block,
			Property<Boolean> property
	) {
		TextureMapping connectedTextureMapping = new TextureMapping()
				.put(TextureSlot.END, new Material(connectedTextures.getLeft()))
				.put(TextureSlot.SIDE, new Material(connectedTextures.getRight()));
		Identifier notConnectedResourceLocation = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(new Material(notConnectedTexture)),
				blockStateModelGenerator.modelOutput);
		Identifier connectedResourceLocation = ModelTemplates.CUBE_COLUMN.create(block, connectedTextureMapping, blockStateModelGenerator.modelOutput);
//		blockStateModelGenerator.blockStateOutput.accept(createConnectedBlock(block, property, connectedResourceLocation, notConnectedResourceLocation));
		blockStateModelGenerator.registerSimpleItemModel(block, notConnectedResourceLocation);
	}

	public static void generateSimpleColumnModels(BlockModelGenerators blockStateModelGenerator, Identifier side, Identifier end, Block block) {
		TextureMapping textureMapping = new TextureMapping().put(TextureSlot.SIDE, new Material(side)).put(TextureSlot.END, new Material(end));
		MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(block, textureMapping, blockStateModelGenerator.modelOutput));
		blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(block, model));
		blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	public static void generateSimpleColumnModels(BlockModelGenerators blockStateModelGenerator, TextureMapping textureMapping, Block block) {
		MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(block, textureMapping, blockStateModelGenerator.modelOutput));
		blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(block, model));
		blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	public static void generateSimpleTopBottomModels(BlockModelGenerators blockStateModelGenerator, Identifier side, Identifier top, Identifier bottom, Block block) {
		TextureMapping textureMapping = new TextureMapping().put(TextureSlot.SIDE, new Material(side))
			.put(TextureSlot.TOP, new Material(top))
			.put(TextureSlot.BOTTOM, new Material(bottom));
		MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(block, textureMapping, blockStateModelGenerator.modelOutput));
		blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(block, model));
		blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	public static void createCarpet(BlockModelGenerators blockStateModelGenerator, Block block, Identifier texture) {
		blockStateModelGenerator.createTrivialBlock(block, TexturedModel.CARPET.updateTexture(
				textureMapping -> textureMapping.put(TextureSlot.WOOL, new Material(texture))));
		Identifier resourceLocation2 = ModelLocationUtils.getModelLocation(block);
		blockStateModelGenerator.registerSimpleItemModel(block, resourceLocation2);
	}

	public static void createCaveVines(Block vines, Block plant, BlockModelGenerators blockStateModelGenerator) {
		MultiVariant multiVariant = BlockModelGenerators.plainVariant(blockStateModelGenerator.createSuffixedVariant(vines, "", ModelTemplates.CROSS, TextureMapping::cross));
		MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(blockStateModelGenerator.createSuffixedVariant(vines, "_lit", ModelTemplates.CROSS, TextureMapping::cross));
		blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(vines).with(createBooleanModelDispatch(BlockStateProperties.BERRIES, multiVariant2, multiVariant)));
		MultiVariant multiVariant3 = BlockModelGenerators.plainVariant(blockStateModelGenerator.createSuffixedVariant(plant, "", ModelTemplates.CROSS, TextureMapping::cross));
		MultiVariant multiVariant4 = BlockModelGenerators.plainVariant(blockStateModelGenerator.createSuffixedVariant(plant, "_lit", ModelTemplates.CROSS, TextureMapping::cross));
		blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(plant).with(createBooleanModelDispatch(BlockStateProperties.BERRIES, multiVariant4, multiVariant3)));
	}

	/*public static MultiVariantGenerator createConnectedBlock(Block block, Property<Boolean> property, Identifier modelLocation, Identifier modelLocation2) {
		return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(property)
				.select(true, Variant.variant().with(VariantProperties.MODEL, modelLocation))
				.select(false, Variant.variant().with(VariantProperties.MODEL, modelLocation2))
		);
	}*/
}
