package io.github.vampirestudios.vampirelib.utils.registry;

import static net.minecraft.client.data.models.BlockModelGenerators.createDoor;
import static net.minecraft.client.data.models.BlockModelGenerators.createEmptyOrFullDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import static net.minecraft.client.data.models.BlockModelGenerators.createTrapdoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.block.dispatch.multipart.CombinedCondition;
import net.minecraft.client.renderer.block.dispatch.multipart.Condition;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

import io.github.vampirestudios.vampirelib.blocks.CustomLadderBlock;
import io.github.vampirestudios.vampirelib.blocks.FlowerPotBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.FungusBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.SaplingBaseBlock;
import io.github.vampirestudios.vampirelib.client.VampireLibClient;
import io.github.vampirestudios.vampirelib.utils.IdentifierUtils;
import io.github.vampirestudios.vampirelib.utils.Utils;
import io.github.vampirestudios.vampirelib.utils.WoodMaterial;

public class WoodRegistry {
	private static final Map<BookSlotModelCacheKey, Identifier> CHISELED_BOOKSHELF_SLOT_MODEL_CACHE = new HashMap<>();
	private final List<String> availableLeaves = new ArrayList<>();
	private final List<String> availableFloweryLeaves = new ArrayList<>();
	private final List<String> availableSaplings = new ArrayList<>();
	private final List<String> availablePottedSaplings = new ArrayList<>();
	public TagKey<Block> logsTag;
	public TagKey<Item> logsItemTag;
	public WoodPropertyType woodPropertyType = WoodPropertyType.OVERWORLD;
	public boolean tintedLeavesParticle = false;
	public float fallenLeavesAmount = 0.01F;
	public int tintedLeavesColor = -9399763;
	public boolean preRegisteredPlanks = false;
	public boolean preRegisteredLog = false;
	public boolean preRegisteredLeaves = false;
	private Identifier name;
	private TreeGrower saplingGenerator;
	private ResourceKey<Feature> fungusGenerator;
	private Block baseFungusBlock;
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
	private boolean flammable = true;

	public WoodRegistry() {
	}

	private WoodRegistry(Identifier name) {
		this(name, null, null);
	}

	private WoodRegistry(Identifier name, TreeGrower saplingGenerator) {
		this(name, saplingGenerator, null, null);
	}

	private WoodRegistry(Identifier name, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
		this(name, null, fungusGenerator, baseFungusBlock);
	}

	private WoodRegistry(Identifier name, TreeGrower saplingGenerator, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
		setName(name);
		setSaplingGenerator(saplingGenerator);
		setFungusGenerator(fungusGenerator);
		setBaseFungusBlock(baseFungusBlock);
		this.logsTag = TagKey.create(Registries.BLOCK, Utils.appendToPath(name, "_logs"));
		this.logsItemTag = TagKey.create(Registries.ITEM, Utils.appendToPath(name, "_logs"));
		IdentifierUtils.setModId(name.getNamespace());
	}

	public static Builder create() {
		return new WoodRegistry.Builder();
	}

	public static WoodRegistry.Builder of(Identifier name) {
		return create().of(name);
	}

	public static WoodRegistry.Builder of(Identifier name, Block planks) {
		return create().of(name, planks);
	}

	public static WoodRegistry.Builder of(Identifier name, TreeGrower saplingGenerator) {
		return create().of(name, saplingGenerator);
	}

	public static WoodRegistry.Builder of(Identifier name, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
		return create().of(name, fungusGenerator, baseFungusBlock);
	}

	public static WoodRegistry.Builder of(WoodMaterial woodMaterial) {
		return create().of(woodMaterial);
	}

	public static WoodRegistry.Builder of(WoodMaterial woodMaterial, TreeGrower saplingGenerator) {
		return create().of(woodMaterial, saplingGenerator);
	}

	public static WoodRegistry.Builder of(WoodMaterial woodMaterial, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
		return create().of(woodMaterial, fungusGenerator, baseFungusBlock);
	}

	private void setSaplingGenerator(TreeGrower abstractTreeGrower) {
		this.saplingGenerator = abstractTreeGrower;
	}

	private void setName(Identifier name) {
		this.name = name;
	}

	private void setFungusGenerator(ResourceKey<Feature> fungusGenerator) {
		this.fungusGenerator = fungusGenerator;
	}

	private void setBaseFungusBlock(Block baseFungusBlock) {
		this.baseFungusBlock = baseFungusBlock;
	}

	public Identifier name() {
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

	public TreeGrower saplingGenerator() {
		return saplingGenerator;
	}

	public ResourceKey<Feature> fungusGenerator() {
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

	public void generateBlockTags(FabricTagsProvider.BlockTagsProvider blockTags) {
//		if (log != null) blockTags.tag(logsTag).add(log);
//		if (strippedLog != null) blockTags.tag(logsTag).add(strippedLog);
//		if (wood != null) blockTags.tag(logsTag).add(wood);
//		if (strippedWood != null) blockTags.tag(logsTag).add(strippedWood);
//
//		if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null)
//			blockTags.tag(BlockTags.LOGS).addTag(logsTag);
//
//		if (planks != null) blockTags.tag(BlockTags.PLANKS).add(planks);
//		if (!availableLeaves.isEmpty() && !isNetherWood()) {
//			availableLeaves.forEach(s -> {
//				Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocationUtils.modId(s));
//				blockTags.tag(BlockTags.LEAVES).add(block);
//			});
//		}
//		if (!availableLeaves.isEmpty() && isNetherWood()) {
//			availableLeaves.forEach(s -> {
//				Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocationUtils.modId(s));
//				blockTags.tag(BlockTags.WART_BLOCKS).add(block);
//			});
//		}
//		if (leaves != null && !isNetherWood()) blockTags.tag(BlockTags.LEAVES).add(leaves);
//		if (leaves != null && isNetherWood()) blockTags.tag(BlockTags.WART_BLOCKS).add(leaves);
//		if (!availableFloweryLeaves.isEmpty() && !isNetherWood()) {
//			availableFloweryLeaves.forEach(s -> {
//				Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocationUtils.modId(s));
//				blockTags.tag(BlockTags.LEAVES).add(block);
//			});
//		}
//		if (!availableFloweryLeaves.isEmpty() && isNetherWood()) {
//			availableFloweryLeaves.forEach(s -> {
//				Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocationUtils.modId(s));
//				blockTags.tag(BlockTags.WART_BLOCKS).add(block);
//			});
//		}
//		if (floweryLeaves != null && !isNetherWood()) blockTags.tag(BlockTags.LEAVES).add(floweryLeaves);
//		if (floweryLeaves != null && isNetherWood()) blockTags.tag(BlockTags.WART_BLOCKS).add(floweryLeaves);
//		if (ladder != null) blockTags.tag(BlockTags.CLIMBABLE).add(ladder);
//		if (trapdoor != null) blockTags.tag(BlockTags.WOODEN_TRAPDOORS).add(trapdoor);
//		if (button != null) blockTags.tag(BlockTags.WOODEN_BUTTONS).add(button);
//		if (door != null) blockTags.tag(BlockTags.WOODEN_DOORS).add(door);
//		if (sapling != null) blockTags.tag(BlockTags.SAPLINGS).add(sapling);
//		if (sign != null) blockTags.tag(BlockTags.STANDING_SIGNS).add(sign);
//		if (wallSign != null) blockTags.tag(BlockTags.WALL_SIGNS).add(wallSign);
//		if (hangingSign != null) blockTags.tag(BlockTags.CEILING_HANGING_SIGNS).add(hangingSign);
//		if (hangingWallSign != null) blockTags.tag(BlockTags.WALL_HANGING_SIGNS).add(hangingWallSign);
//		if (!availableSaplings.isEmpty()) {
//			availableSaplings.forEach(s -> {
//				Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocationUtils.modId(s));
//				blockTags.tag(BlockTags.SAPLINGS).add(block);
//			});
//		}
//		if (pottedSapling != null) blockTags.tag(BlockTags.FLOWER_POTS).add(pottedSapling);
//		if (!availablePottedSaplings.isEmpty()) {
//			availablePottedSaplings.forEach(s -> {
//				Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocationUtils.modId(s));
//				blockTags.tag(BlockTags.FLOWER_POTS).add(block);
//			});
//		}
//		if (fence != null) blockTags.tag(BlockTags.WOODEN_FENCES).add(fence);
//		if (fenceGate != null) blockTags.tag(BlockTags.FENCE_GATES).add(fenceGate);
//		if (pressurePlate != null) blockTags.tag(BlockTags.PRESSURE_PLATES).add(pressurePlate);
//		if (slab != null) blockTags.tag(BlockTags.WOODEN_SLABS).add(slab);
//		if (stairs != null) blockTags.tag(BlockTags.WOODEN_STAIRS).add(stairs);
//		if (mosaicSlab != null) blockTags.tag(BlockTags.WOODEN_SLABS).add(mosaicSlab);
//		if (mosaicStairs != null) blockTags.tag(BlockTags.WOODEN_STAIRS).add(mosaicStairs);
//		if (flammable) {
//			if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null)
//				blockTags.tag(BlockTags.LOGS_THAT_BURN).addTag(logsTag);
//		}
	}

	public void generateItemTags(FabricTagsProvider.ItemTagsProvider itemsTag) {
		if ((log != null || strippedLog != null || wood != null || strippedWood != null) && logsTag != null &&
			logsItemTag != null) itemsTag.copy(logsTag, logsItemTag);
		itemsTag.copy(BlockTags.LOGS, ItemTags.LOGS);
		itemsTag.copy(BlockItemTags.LOGS_THAT_BURN.block(), ItemTags.LOGS_THAT_BURN);
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
	}

	@Environment(EnvType.CLIENT)
	public void generateModels(BlockModelGenerators blockStateModelGenerator) {
		this.generateModels(blockStateModelGenerator, false);
	}

	@Environment(EnvType.CLIENT)
	public void generateModels(BlockModelGenerators blockStateModelGenerator, boolean customPottedTexture) {
		TextureMapping logMapping = new TextureMapping()
			.put(TextureSlot.SIDE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/%s", name.getPath(),
				isNetherWood() ? "stem" : "log"))
			))
			.put(TextureSlot.END, new Material(IdentifierUtils.modId(String.format("wood_types/%s/%s_top", name.getPath(),
				isNetherWood() ? "stem" : "log"))
			));
		TextureMapping strippedLogMapping = new TextureMapping()
			.put(TextureSlot.SIDE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/stripped_%s", name.getPath(),
				isNetherWood() ? "stem" : "log"))
			))
			.put(TextureSlot.END, new Material(IdentifierUtils.modId(String.format("wood_types/%s/stripped_%s_top", name.getPath(),
				isNetherWood() ? "stem" : "log"))
			));

		TextureMapping leavesMapping = TextureMapping.cube(new Material(
			IdentifierUtils.modId(String.format("wood_types/%s/%s", name.getPath(), isNetherWood() ? "wart_block" : "leaves"))
		));

		TextureMapping floweryLeavesMapping = TextureMapping.cube(new Material(
			IdentifierUtils.modId(String.format("wood_types/%s/%s", name.getPath(), "flowery_leaves"))
		));

		TextureMapping saplingMapping = TextureMapping.cross(new Material(
			IdentifierUtils.modId(
				String.format("wood_types/%s/%s", name.getPath(), isNetherWood() ? "fungi" : "sapling")
			)
		));
		TextureMapping saplingPlantMapping = TextureMapping.plant(new Material(
			IdentifierUtils.modId(
				customPottedTexture ? String.format(
					"wood_types/%s/potted_%s",
					name.getPath(),
					isNetherWood()
						? "fungi" : "sapling"
				) : String.format("wood_types/%s/%s", name.getPath(), isNetherWood() ? "fungi" : "sapling")
			)
		));

		TextureMapping planksMapping = TextureMapping.cube(new Material(IdentifierUtils.modId(String.format("wood_types/%s/planks", name.getPath()))));

		TextureMapping mosaicMapping = TextureMapping.cube(new Material(IdentifierUtils.modId(String.format("wood_types/%s/mosaic", name.getPath()))));

		TextureMapping signMapping = TextureMapping.particle(new Material(IdentifierUtils.modId(String.format("wood_types/%s/sign", name.getPath()))));

		TextureMapping hangingSignMapping = TextureMapping.particle(new Material(IdentifierUtils.modId(String.format("wood_types/%s/hanging_sign", name.getPath()))));

		TextureMapping doorMapping = new TextureMapping()
			.put(TextureSlot.TOP, new Material(IdentifierUtils.modId(String.format("wood_types/%s/door_top", name.getPath()))))
			.put(TextureSlot.BOTTOM, new Material(IdentifierUtils.modId(String.format("wood_types/%s/door_bottom", name.getPath()))));

		TextureMapping trapdoorMapping = TextureMapping.defaultTexture(new Material(IdentifierUtils.modId(String.format("wood_types/%s/trapdoor", name.getPath()))));

		TextureMapping bookshelfMapping = TextureMapping.column(
			new Material(IdentifierUtils.modId(String.format("wood_types/%s/bookshelf", name.getPath()))),
			new Material(IdentifierUtils.modId(String.format("wood_types/%s/planks", name.getPath())))
		);
		TextureMapping occupiedTextureMapping = new TextureMapping()
			.put(TextureSlot.TEXTURE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf", name.getPath()))))
			.put(TextureSlot.TOP, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_top", name.getPath()))))
			.put(TextureSlot.BOTTOM, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_top", name.getPath()))))
			.put(TextureSlot.SIDE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_side", name.getPath()))));
		TextureMapping emptyTextureMapping = new TextureMapping()
			.put(TextureSlot.TEXTURE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_empty", name.getPath()))))
			.put(TextureSlot.TOP, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_top", name.getPath()))))
			.put(TextureSlot.BOTTOM, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_top", name.getPath()))))
			.put(TextureSlot.SIDE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/chiseled_bookshelf_side", name.getPath()))));

		TextureMapping beehiveTextureMapping = new TextureMapping()
			.put(TextureSlot.SIDE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/beehive_side", name.getPath()))))
			.put(TextureSlot.FRONT, new Material(IdentifierUtils.modId(String.format("wood_types/%s/beehive_front", name.getPath()))))
			.put(TextureSlot.END, new Material(IdentifierUtils.modId(String.format("wood_types/%s/beehive_end", name.getPath()))));
		TextureMapping beehiveHoneyTextureMapping = new TextureMapping()
			.put(TextureSlot.SIDE, new Material(IdentifierUtils.modId(String.format("wood_types/%s/beehive_side", name.getPath()))))
			.put(TextureSlot.FRONT, new Material(IdentifierUtils.modId(String.format("wood_types/%s/beehive_front_honey", name.getPath()))))
			.put(TextureSlot.END, new Material(IdentifierUtils.modId(String.format("wood_types/%s/beehive_end", name.getPath()))));

		if (log != null && !preRegisteredLog) {
			blockStateModelGenerator.new WoodProvider(logMapping).logWithHorizontal(log);
			blockStateModelGenerator.registerSimpleItemModel(log, ModelLocationUtils.getModelLocation(log));
		}
		if (wood != null) {
			blockStateModelGenerator.new WoodProvider(logMapping).wood(wood);
			blockStateModelGenerator.registerSimpleItemModel(wood, ModelLocationUtils.getModelLocation(wood));
		}
		if (strippedLog != null) {
			blockStateModelGenerator.new WoodProvider(strippedLogMapping).logWithHorizontal(strippedLog);
			blockStateModelGenerator.registerSimpleItemModel(strippedLog, ModelLocationUtils.getModelLocation(strippedLog));
		}
		if (strippedWood != null) {
			blockStateModelGenerator.new WoodProvider(strippedLogMapping).wood(strippedWood);
			blockStateModelGenerator.registerSimpleItemModel(strippedWood, ModelLocationUtils.getModelLocation(strippedWood));
		}
		if (!preRegisteredLog) {
			if (!availableLeaves.isEmpty()) {
				availableLeaves.forEach(s -> {
					TextureMapping leaves2Mapping = TextureMapping.cube(new Material(IdentifierUtils.modId(String.format("wood_types/%s/%s", name.getPath(), s))));
					Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(s));
					blockStateModelGenerator.createTrivialBlock(block, TexturedModel.createDefault(block1 -> leaves2Mapping,
						isNetherWood() ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
//				Identifier resourceLocation = ModelLocationUtils.getModelLocation(block);
//				blockStateModelGenerator.registerSimpleItemModel(block, resourceLocation);
				});
			} else {
				if (leaves != null) {
					blockStateModelGenerator.createTrivialBlock(leaves, TexturedModel.createDefault(block -> leavesMapping,
						isNetherWood() ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
					Identifier resourceLocation = ModelLocationUtils.getModelLocation(leaves);
					blockStateModelGenerator.registerSimpleItemModel(leaves, resourceLocation);
				}
			}
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				TextureMapping leaves2Mapping = TextureMapping.cube(new Material(IdentifierUtils.modId(String.format("wood_types/%s/%s", name.getPath(), s))));
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(s));
				blockStateModelGenerator.createTrivialBlock(block, TexturedModel.createDefault(block1 -> leaves2Mapping,
					isNetherWood() ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				Identifier resourceLocation = ModelLocationUtils.getModelLocation(block);
				blockStateModelGenerator.registerSimpleItemModel(block, resourceLocation);
			});
		} else {
			if (floweryLeaves != null) {
				blockStateModelGenerator.createTrivialBlock(leaves,
					TexturedModel.createDefault(block -> floweryLeavesMapping,
						isNetherWood() ? ModelTemplates.CUBE_ALL : ModelTemplates.LEAVES));
				Identifier resourceLocation = ModelLocationUtils.getModelLocation(leaves);
				blockStateModelGenerator.registerSimpleItemModel(leaves, resourceLocation);
			}
		}
		if (door != null) {
			MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant3 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant4 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant5 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant6 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant7 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant8 = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(door, doorMapping, blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				createDoor(door, multiVariant, multiVariant2, multiVariant5, multiVariant6,
					multiVariant3, multiVariant4, multiVariant7, multiVariant8));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(door.asItem()),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(String.format("wood_types/%s/door", name.getPath())))),
				blockStateModelGenerator.modelOutput
			);
		}
		if (trapdoor != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.TRAPDOOR_TOP.create(trapdoor, trapdoorMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation2 = BlockModelGenerators.plainVariant(ModelTemplates.TRAPDOOR_BOTTOM.create(trapdoor, trapdoorMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation3 = BlockModelGenerators.plainVariant(ModelTemplates.TRAPDOOR_OPEN.create(trapdoor, trapdoorMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				createTrapdoor(trapdoor, resourceLocation, resourceLocation2, resourceLocation3));
			blockStateModelGenerator.registerSimpleItemModel(trapdoor, ModelTemplates.TRAPDOOR_BOTTOM.create(trapdoor, trapdoorMapping,
				blockStateModelGenerator.modelOutput));
		}
		if (planks != null && !preRegisteredPlanks) {
			blockStateModelGenerator.createTrivialBlock(planks, TexturedModel.createDefault(block -> planksMapping,
				ModelTemplates.CUBE_ALL));
			Identifier resourceLocation = ModelLocationUtils.getModelLocation(planks);
			blockStateModelGenerator.registerSimpleItemModel(planks, resourceLocation);
		}
		if (mosaic != null) {
			blockStateModelGenerator.createTrivialBlock(mosaic, TexturedModel.createDefault(block -> mosaicMapping,
				ModelTemplates.CUBE_ALL));
			Identifier resourceLocation = ModelLocationUtils.getModelLocation(mosaic);
			blockStateModelGenerator.registerSimpleItemModel(mosaic, resourceLocation);
		}
		if (ladder != null) {
			blockStateModelGenerator.createNonTemplateHorizontalBlock(ladder);
			blockStateModelGenerator.registerSimpleFlatItemModel(ladder);
		}
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				TextureMapping sapling2Mapping = TextureMapping.cross(new Material(
					IdentifierUtils.modId(String.format("wood_types/%s/%s", name.getPath(), isNetherWood() ? "fungi" : "sapling"))
				));
				MultiVariant resourceLocation = BlockModelGenerators.plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCross()
					.create(sapling,
						sapling2Mapping,
						blockStateModelGenerator.modelOutput));
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(sapling, resourceLocation));
				ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(sapling.asItem()),
					TextureMapping.layer0(new Material(IdentifierUtils.modId(
						String.format("wood_types/%s/%s", name.getPath(), isNetherWood() ? "fungi" : "sapling")
					))),
					blockStateModelGenerator.modelOutput
				);
			});
		} else {
			if (sapling != null) {
				MultiVariant resourceLocation = BlockModelGenerators.plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCross()
					.create(sapling,
						saplingMapping,
						blockStateModelGenerator.modelOutput)
				);
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(sapling, resourceLocation));
				ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(sapling.asItem()),
					TextureMapping.layer0(new Material(IdentifierUtils.modId(
						String.format("wood_types/%s/%s", name.getPath(), isNetherWood() ? "fungi" : "sapling")
					))),
					blockStateModelGenerator.modelOutput
				);
			}
		}
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				TextureMapping sapling2PlantMapping = TextureMapping.plant(new Material(IdentifierUtils.modId(customPottedTexture ? String.format(
						"wood_types/%s/potted_%s",
						name.getPath(),
						isNetherWood()
							? "fungi" : "sapling")
							: String.format(
								"wood_types/%s/%s",
								name.getPath(),
								isNetherWood() ? "fungi" : "sapling"
							)
				)));
				MultiVariant resourceLocation = BlockModelGenerators.plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCrossPot()
					.create(pottedSapling, sapling2PlantMapping, blockStateModelGenerator.modelOutput));
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(pottedSapling, resourceLocation));
			});
		} else {
			if (pottedSapling != null) {
				MultiVariant resourceLocation = BlockModelGenerators.plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCrossPot()
					.create(pottedSapling,
						saplingPlantMapping,
						blockStateModelGenerator.modelOutput));
				blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(pottedSapling, resourceLocation));
			}
		}

		if (bookshelf != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(bookshelf, bookshelfMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(bookshelf, resourceLocation));
			Identifier resourceLocationItem = ModelLocationUtils.getModelLocation(bookshelf);
			blockStateModelGenerator.registerSimpleItemModel(bookshelf, resourceLocationItem);
		}
		if (chiseledBookshelf != null) {
			createChiseledBookshelf(blockStateModelGenerator, chiseledBookshelf, occupiedTextureMapping, emptyTextureMapping);
		}
		if (fence != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(fence, planksMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation2 = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(fence, planksMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createFence(fence, resourceLocation, resourceLocation2));
			Identifier resourceLocation3 = ModelTemplates.FENCE_INVENTORY.create(fence, planksMapping,
				blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.registerSimpleItemModel(fence, resourceLocation3);
		}
		if (fenceGate != null) {
			MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, planksMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, planksMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant3 = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, planksMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant4 = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, planksMapping, blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createFenceGate(fenceGate, multiVariant, multiVariant2, multiVariant3,
					multiVariant4, !isBambooWood()));
			blockStateModelGenerator.registerSimpleItemModel(fenceGate, ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, planksMapping, blockStateModelGenerator.modelOutput));
		}
		if (pressurePlate != null) {
			MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, planksMapping, blockStateModelGenerator.modelOutput));
			MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlate, planksMapping, blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createPressurePlate(pressurePlate, multiVariant, multiVariant2));
			blockStateModelGenerator.registerSimpleItemModel(pressurePlate, ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, planksMapping, blockStateModelGenerator.modelOutput));
		}
		if (slab != null) { // TODO
//			BlockModelGenerators.BlockFamilyProvider family = blockStateModelGenerator.family(planks);
//			Identifier resourceLocation = family.getOrCreateModel(ModelTemplates.SLAB_BOTTOM, slab);
//			MultiVariant multiVariant = BlockModelGenerators.plainVariant(family.getOrCreateModel(ModelTemplates.SLAB_TOP, slab));
//			blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, BlockModelGenerators.plainVariant(resourceLocation), multiVariant, BlockModelGenerators.variant(fullBlock)));
//			blockStateModelGenerator.registerSimpleItemModel(slab, resourceLocation);
		}
		if (mosaicSlab != null) { // TODO
//			BlockModelGenerators.BlockFamilyProvider family = blockStateModelGenerator.family(mosaic);
//			Identifier resourceLocation = family.getOrCreateModel(ModelTemplates.SLAB_BOTTOM, mosaicSlab);
//			MultiVariant multiVariant = BlockModelGenerators.plainVariant(family.getOrCreateModel(ModelTemplates.SLAB_TOP, mosaicSlab));
//			blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSlab(mosaicSlab, BlockModelGenerators.plainVariant(resourceLocation), multiVariant, BlockModelGenerators.variant(fullBlock)));
//			blockStateModelGenerator.registerSimpleItemModel(mosaicSlab, resourceLocation);
		}
		if (sign != null && wallSign != null && signItem != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(sign, signMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(sign, resourceLocation));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(wallSign, resourceLocation));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(signItem),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(
					String.format("wood_types/%s/sign_item", name.getPath())
				))), blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.registerSimpleFlatItemModel(wallSign);
		}
		if (hangingSign != null && hangingWallSign != null && hangingSignItem != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(hangingSign, hangingSignMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(hangingSign, resourceLocation));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(hangingWallSign, resourceLocation));
			ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(hangingSignItem),
				TextureMapping.layer0(new Material(IdentifierUtils.modId(
					String.format("wood_types/%s/hanging_sign_item", name.getPath())
				))), blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.registerSimpleFlatItemModel(hangingWallSign);
		}
		if (stairs != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, planksMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation2 = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, planksMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation3 = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, planksMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createStairs(stairs, resourceLocation, resourceLocation2, resourceLocation3));
			blockStateModelGenerator.registerSimpleItemModel(stairs, ModelTemplates.STAIRS_STRAIGHT.create(stairs, planksMapping,
				blockStateModelGenerator.modelOutput));
		}
		if (mosaicStairs != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_INNER.create(mosaicStairs, planksMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation2 = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(mosaicStairs, planksMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation3 = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_OUTER.create(mosaicStairs, planksMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createStairs(mosaicStairs, resourceLocation, resourceLocation2, resourceLocation3));
			blockStateModelGenerator.registerSimpleItemModel(mosaicStairs, ModelTemplates.STAIRS_STRAIGHT.create(mosaicStairs, planksMapping,
				blockStateModelGenerator.modelOutput));
		}
		if (button != null) {
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(button, planksMapping,
				blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation2 = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(button, planksMapping,
				blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				BlockModelGenerators.createButton(button, resourceLocation, resourceLocation2));
			Identifier resourceLocation3 = ModelTemplates.BUTTON_INVENTORY.create(button, planksMapping,
				blockStateModelGenerator.modelOutput);
			blockStateModelGenerator.registerSimpleItemModel(button, resourceLocation3);
		}
		if (beehive != null) {
			TextureMapping textureMapping = beehiveTextureMapping.copyForced(TextureSlot.SIDE, TextureSlot.PARTICLE);
			TextureMapping textureMapping2 = beehiveHoneyTextureMapping.copyForced(TextureSlot.SIDE, TextureSlot.PARTICLE);
			MultiVariant resourceLocation = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create(beehive, textureMapping, blockStateModelGenerator.modelOutput));
			MultiVariant resourceLocation2 = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.createWithSuffix(beehive, "_honey", textureMapping2, blockStateModelGenerator.modelOutput));
			blockStateModelGenerator.blockStateOutput.accept(
				MultiVariantGenerator.dispatch(beehive)
					.with(createEmptyOrFullDispatch(BeehiveBlock.HONEY_LEVEL, 5, resourceLocation2, resourceLocation))
					.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
		}
	}

	private void createChiseledBookshelf(BlockModelGenerators blockModelGenerators, Block block, TextureMapping occupiedTextureMapping, TextureMapping emptyTextureMapping) {
		MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(block);
		List.of(Pair.of(Direction.NORTH, BlockModelGenerators.NOP), Pair.of(Direction.EAST, BlockModelGenerators.Y_ROT_90), Pair.of(Direction.SOUTH, BlockModelGenerators.Y_ROT_180), Pair.of(Direction.WEST, BlockModelGenerators.Y_ROT_270)).forEach((pair) -> {
			Direction direction = pair.getFirst();
			VariantMutator variantMutator = pair.getSecond();
			Condition condition = BlockModelGenerators.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).build();
			multiPartGenerator.with(condition, multiVariant.with(variantMutator).with(BlockModelGenerators.UV_LOCK));
			this.addSlotStateAndRotationVariants(blockModelGenerators, multiPartGenerator, condition, variantMutator);
		});
		blockModelGenerators.blockStateOutput.accept(multiPartGenerator);
		blockModelGenerators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block, "_inventory"));
		CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.clear();
	}

	public final void addSlotStateAndRotationVariants(BlockModelGenerators blockModelGenerators, MultiPartGenerator multiPartGenerator, Condition condition, VariantMutator variantMutator) {
		List.of(
			Pair.of(BlockStateProperties.SLOT_0_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_LEFT),
			Pair.of(BlockStateProperties.SLOT_1_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_MID),
			Pair.of(BlockStateProperties.SLOT_2_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_RIGHT),
			Pair.of(BlockStateProperties.SLOT_3_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT),
			Pair.of(BlockStateProperties.SLOT_4_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_MID),
			Pair.of(BlockStateProperties.SLOT_5_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT)
		).forEach((pair) -> {
			BooleanProperty booleanProperty = pair.getFirst();
			ModelTemplate modelTemplate = pair.getSecond();
			this.addBookSlotModel(blockModelGenerators, multiPartGenerator, condition, variantMutator, booleanProperty, modelTemplate, true);
			this.addBookSlotModel(blockModelGenerators, multiPartGenerator, condition, variantMutator, booleanProperty, modelTemplate, false);
		});
	}

	public final void addBookSlotModel(BlockModelGenerators blockModelGenerators, MultiPartGenerator multiPartGenerator, Condition condition, VariantMutator variantMutator, BooleanProperty booleanProperty, ModelTemplate modelTemplate, boolean bl) {
		String string = bl ? "_occupied" : "_empty";
		TextureMapping textureMapping = (new TextureMapping()).put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(Blocks.CHISELED_BOOKSHELF, string));
		BookSlotModelCacheKey bookSlotModelCacheKey = new BookSlotModelCacheKey(modelTemplate, string);
		MultiVariant multiVariant = BlockModelGenerators.plainVariant(CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.computeIfAbsent(bookSlotModelCacheKey, (bookSlotModelCacheKeyx) -> modelTemplate.createWithSuffix(Blocks.CHISELED_BOOKSHELF, string, textureMapping, blockModelGenerators.modelOutput)));
		multiPartGenerator.with(new CombinedCondition(CombinedCondition.Operation.AND, List.of(condition, BlockModelGenerators.condition().term(booleanProperty, bl).build())), multiVariant.with(variantMutator));
	}

	@Environment(EnvType.CLIENT)
	public void generateLang(FabricLanguageProvider.TranslationBuilder translationBuilder, Map<String, String> lang) {
		String translatedName = getTranslation(name.getPath(), lang);

		String logName = isNetherWood() ? getTranslation("stem", lang) : isBambooWood() ? getTranslation("block", lang) : getTranslation("log", lang);
		String strippedLogName = isNetherWood() ? getTranslation("stripped_stem", lang) : isBambooWood() ? getTranslation("stripped_block", lang) : getTranslation("stripped_log", lang);
		String woodName = isNetherWood() ? getTranslation("hyphae", lang) : getTranslation("wood", lang);
		String strippedWoodName = isNetherWood() ? getTranslation("stripped_hyphae", lang) : getTranslation("stripped_wood", lang);
		String saplingName = isNetherWood() ? getTranslation("fungus", lang) : getTranslation("sapling", lang);
		String pottedSaplingName = isNetherWood() ? getTranslation("potted_fungus", lang) : getTranslation("potted_sapling", lang);
		String foliageBlockName = isNetherWood() ? getTranslation("wart_block", lang) : getTranslation("leaves", lang);
		String floweryFoliageBlockName = isNetherWood() ? getTranslation("flowering_wart_block", lang) : getTranslation("flowering_leaves", lang);

		// Add translations for log, stripped log, wood, and stripped wood
		if (log != null) translationBuilder.add(log, String.format(logName, translatedName));
		if (strippedLog != null) translationBuilder.add(strippedLog, String.format(strippedLogName, translatedName));
		if (wood != null) translationBuilder.add(wood, String.format(woodName, translatedName));
		if (strippedWood != null) translationBuilder.add(strippedWood, String.format(strippedWoodName, translatedName));

		// Add translations for planks
		if (planks != null)
			translationBuilder.add(planks, String.format(getTranslation("planks", lang), translatedName));

		// Add translations for saplings and potted saplings
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				String registryName = isNetherWood() ? s + "_fungus" : s + "_sapling";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));
				translationBuilder.add(block, String.format(saplingName, translatedName));
			});
		} else {
			if (sapling != null) translationBuilder.add(sapling, String.format(saplingName, translatedName));
		}
		if (!availablePottedSaplings.isEmpty()) {
			availablePottedSaplings.forEach(s -> {
				String registryName = "potted_" + (isNetherWood() ? s + "_fungus" : s + "_sapling");
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));
				translationBuilder.add(block, String.format(pottedSaplingName, translate(s, lang)));
			});
		} else {
			if (pottedSapling != null)
				translationBuilder.add(pottedSapling, String.format(pottedSaplingName, translatedName));
		}

		// Add translations for trapdoor and door
		if (trapdoor != null)
			translationBuilder.add(trapdoor, String.format(getTranslation("trapdoor", lang), translatedName));
		if (door != null) translationBuilder.add(door, String.format(getTranslation("door", lang), translatedName));

		// Add translations for leaves and flowering leaves
		if (!availableLeaves.isEmpty()) {
			availableLeaves.forEach(s -> {
				String registryName = isNetherWood() ? s + "_wart_block" : s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));
				translationBuilder.add(block, String.format(foliageBlockName, translate(s, lang)));
			});
		} else {
			if (leaves != null) translationBuilder.add(leaves, String.format(foliageBlockName, translatedName));
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				String registryName = isNetherWood() ? "flowery_" + s + "_wart_block" : "flowery_" + s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));
				translationBuilder.add(block, String.format(floweryFoliageBlockName, translate(s, lang)));
			});
		} else {
			if (floweryLeaves != null)
				translationBuilder.add(leaves, String.format(floweryFoliageBlockName, translatedName));
		}

		// Add translations for fence, gate, pressure plate, and button
		if (fence != null) translationBuilder.add(fence, String.format(getTranslation("fence", lang), translatedName));
		if (fenceGate != null)
			translationBuilder.add(fenceGate, String.format(getTranslation("fence_gate", lang), translatedName));
		if (pressurePlate != null)
			translationBuilder.add(pressurePlate, String.format(getTranslation("pressure_plate", lang), translatedName));
		if (button != null)
			translationBuilder.add(button, String.format(getTranslation("button", lang), translatedName));
		if (ladder != null)
			translationBuilder.add(ladder, String.format(getTranslation("ladder", lang), translatedName));
		if (bookshelf != null)
			translationBuilder.add(bookshelf, String.format(getTranslation("bookshelf", lang), translatedName));
		if (chiseledBookshelf != null)
			translationBuilder.add(chiseledBookshelf, String.format(getTranslation("chiseled_bookshelf", lang), translatedName));
		if (mosaic != null)
			translationBuilder.add(mosaic, String.format(getTranslation("mosaic", lang), translatedName));
		if (mosaicSlab != null)
			translationBuilder.add(mosaicSlab, String.format(getTranslation("mosaic_slab", lang), translatedName));
		if (mosaicStairs != null)
			translationBuilder.add(mosaicStairs, String.format(getTranslation("mosaic_stairs", lang), translatedName));
		if (slab != null) translationBuilder.add(slab, String.format(getTranslation("slab", lang), translatedName));
		if (stairs != null)
			translationBuilder.add(stairs, String.format(getTranslation("stairs", lang), translatedName));
		if (sign != null) translationBuilder.add(sign, String.format(getTranslation("sign", lang), translatedName));
		if (hangingSign != null)
			translationBuilder.add(hangingSign, String.format(getTranslation("hanging_sign", lang), translatedName));
	}

	public void generateLoot(FabricBlockLootSubProvider lootTablesProvider) {
		if (log != null) lootTablesProvider.dropSelf(log);
		if (strippedLog != null) lootTablesProvider.dropSelf(strippedLog);
		if (wood != null) lootTablesProvider.dropSelf(wood);
		if (strippedWood != null) lootTablesProvider.dropSelf(strippedWood);
		if (planks != null) lootTablesProvider.dropSelf(planks);
		if (mosaic != null) lootTablesProvider.dropSelf(mosaic);
		if (!availableLeaves.isEmpty() && !isNetherWood()) {
			availableLeaves.forEach(s -> {
				String registryName = isNetherWood() ? s + "_wart_block" : s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));

				String saplingName = isNetherWood() ? s + "_fungus" : s + "_sapling";
				if (!availableSaplings.isEmpty() && availableSaplings.contains(saplingName)) {
					Block sapling = BuiltInRegistries.BLOCK.getValue(
						IdentifierUtils.modId(saplingName));
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
			if (leaves != null && !isNetherWood()) {
				if (sapling != null) lootTablesProvider.add(leaves,
					block1 -> lootTablesProvider.createLeavesDrops(block1, sapling,
						0.05F, 0.0625F,
						0.083333336F, 0.1F));
				else lootTablesProvider.dropSelf(leaves);
			}
		}
		if (!availableLeaves.isEmpty()) {
			availableLeaves.forEach(s -> {
				String registryName = isNetherWood() ? s + "_wart_block" : s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));
				lootTablesProvider.dropSelf(block);
			});
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				String registryName = isNetherWood() ? "flowery_" + s + "_wart_block" : "flowery_" + s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));

				String saplingName = isNetherWood() ? s + "_fungus" : s + "_sapling";
				if (!availableSaplings.isEmpty() && availableSaplings.contains(saplingName)) {
					Block sapling = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(saplingName));
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
		}
		if (!availableFloweryLeaves.isEmpty()) {
			availableFloweryLeaves.forEach(s -> {
				String registryName = isNetherWood() ? "flowery_" + s + "_wart_block" : "flowery_" + s + "_leaves";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(registryName));
				lootTablesProvider.dropSelf(block);
			});
		}
		if (ladder != null) lootTablesProvider.dropSelf(ladder);
		if (trapdoor != null) lootTablesProvider.dropSelf(trapdoor);
		if (button != null) lootTablesProvider.dropSelf(button);
		if (door != null) lootTablesProvider.add(door, lootTablesProvider::createDoorTable);
		if (sapling != null) lootTablesProvider.dropSelf(sapling);
		if (!availableSaplings.isEmpty()) {
			availableSaplings.forEach(s -> {
				String saplingName = isNetherWood() ? s + "_fungus" : s + "_sapling";
				Block block = BuiltInRegistries.BLOCK.getValue(IdentifierUtils.modId(saplingName));
				lootTablesProvider.dropSelf(block);
			});
		}
		if (fence != null) lootTablesProvider.dropSelf(fence);
		if (fenceGate != null) lootTablesProvider.dropSelf(fenceGate);
		if (pressurePlate != null) lootTablesProvider.dropSelf(pressurePlate);
		if (slab != null) lootTablesProvider.add(slab, lootTablesProvider::createSlabItemTable);
		if (mosaicSlab != null) lootTablesProvider.add(mosaicSlab, lootTablesProvider::createSlabItemTable);
		if (stairs != null) lootTablesProvider.dropSelf(stairs);
		if (mosaicStairs != null) lootTablesProvider.dropSelf(mosaicStairs);
		if (sign != null) lootTablesProvider.dropSelf(sign);
		if (hangingSign != null) lootTablesProvider.dropSelf(hangingSign);
		if (bookshelf != null) lootTablesProvider.add(bookshelf,
			block -> lootTablesProvider.createSingleItemTableWithSilkTouch(block, Items.BOOK,
				ConstantValue.exactly(3.0F)));
		if (chiseledBookshelf != null) lootTablesProvider.dropWhenSilkTouch(chiseledBookshelf);
	}

	public void generateRecipes(RecipeProvider recipeProvider, RecipeOutput exporter) {
		if (planks != null && logsItemTag != null)
			recipeProvider.planksFromLogs(planks, logsItemTag, isBambooWood() ? 2 : 4);
		if (mosaic != null && slab != null)
			recipeProvider.mosaicBuilder(RecipeCategory.DECORATIONS, mosaic, slab);
		if (wood != null && log != null) recipeProvider.woodFromLogs(wood, log);
		if (strippedWood != null && strippedLog != null)
			recipeProvider.woodFromLogs(strippedWood, strippedLog);
		if (trapdoor != null && planks != null) recipeProvider.trapdoorBuilder(trapdoor, Ingredient.of(planks));
		if (door != null && planks != null) recipeProvider.doorBuilder(door, Ingredient.of(planks));
		if (fence != null && planks != null) recipeProvider.fenceBuilder(fence, Ingredient.of(planks));
		if (fenceGate != null && planks != null) recipeProvider.fenceGateBuilder(fenceGate, Ingredient.of(planks));
		if (slab != null && planks != null) recipeProvider.slab(RecipeCategory.BUILDING_BLOCKS, slab, planks);
		if (stairs != null && planks != null) recipeProvider.stairBuilder(stairs, Ingredient.of(planks));
		if (mosaicSlab != null && mosaic != null)
			recipeProvider.slab(RecipeCategory.BUILDING_BLOCKS, mosaicSlab, mosaic);
		if (mosaicStairs != null && mosaic != null) recipeProvider.stairBuilder(mosaicStairs, Ingredient.of(mosaic));
		if (pressurePlate != null && planks != null) recipeProvider.pressurePlate(pressurePlate, planks);
		if (button != null && planks != null) recipeProvider.buttonBuilder(button, Ingredient.of(planks));
		if (signItem != null && planks != null) recipeProvider.signBuilder(signItem, Ingredient.of(planks));
		if (hangingSignItem != null && strippedLog != null)
			recipeProvider.hangingSignBuilder(hangingSignItem, Ingredient.of(strippedLog));
		if (bookshelf != null && planks != null)
			recipeProvider.shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf)
				.define('#', planks)
				.define('X', Items.BOOK)
				.pattern("###")
				.pattern("XXX")
				.pattern("###")
				.unlockedBy("has_book", recipeProvider.has(Items.BOOK))
				.save(exporter);
		if (chiseledBookshelf != null && planks != null && slab != null)
			recipeProvider.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledBookshelf)
				.define('#', planks)
				.define('X', slab)
				.pattern("###")
				.pattern("XXX")
				.pattern("###")
				.unlockedBy("has_book", recipeProvider.has(Items.BOOK))
				.save(exporter);

	}

	protected boolean isNetherWood() {
		return woodPropertyType == WoodPropertyType.NETHER;
	}

	protected boolean isBambooWood() {
		return woodPropertyType == WoodPropertyType.BAMBOO;
	}

	protected boolean isCherryWood() {
		return woodPropertyType == WoodPropertyType.CHERRY;
	}

	private String translate(String name, Map<String, String> lang) {
		return getTranslation(name, lang);
	}

	public String getTranslation(String key, Map<String, String> lang) {
		if (lang.containsKey(key)) {
			return lang.get(key);
		}
		return key.replace("_", " ");
	}

	public enum WoodPropertyType {
		OVERWORLD,
		NETHER,
		CHERRY,
		BAMBOO,
		AZALEA
	}

	record BookSlotModelCacheKey(ModelTemplate template, String modelSuffix) {
	}

	public static class Builder {
		public Identifier name;
		private WoodRegistry woodRegistry;
		private RegistryHelper registryHelper;
		private WoodType woodType;

		private static ButtonBlock woodenButton(BlockSetType blockSetType) {
			return new ButtonBlock(blockSetType, 30, BlockBehaviour.Properties.of().noCollision().strength(0.5F));
		}

		public Builder setSaplingGenerator(TreeGrower abstractTreeGrower) {
			woodRegistry.setSaplingGenerator(abstractTreeGrower);
			return this;
		}

		public Builder setName(Identifier name) {
			woodRegistry.setName(name);
			this.name = name;
			return this;
		}

		public Builder setFungusGenerator(ResourceKey<Feature> fungusGenerator) {
			woodRegistry.setFungusGenerator(fungusGenerator);
			return this;
		}

		public Builder setBaseFungusBlock(Block block) {
			woodRegistry.setBaseFungusBlock(block);
			return this;
		}

		public Builder setPlanks(Block planks) {
			woodRegistry.planks = planks;
			woodRegistry.preRegisteredPlanks = true;
			return this;
		}

		public Builder setLog(Block log) {
			woodRegistry.log = log;
			woodRegistry.preRegisteredLog = true;
			return this;
		}

		public Builder setLeaves(Block leaves) {
			woodRegistry.leaves = leaves;
			woodRegistry.preRegisteredLeaves = true;
			return this;
		}

		public Builder setWoodType(WoodType woodType) {
			this.woodType = woodType;
			return this;
		}

		public Builder of(Identifier name) {
			woodRegistry = new WoodRegistry(name);
			this.name = name;
			registryHelper = RegistryHelper.createRegistryHelper(this.name.getNamespace());
			return this;
		}

		public Builder of(Identifier name, TreeGrower saplingGenerator) {
			return of(name).setSaplingGenerator(saplingGenerator);
		}

		public Builder of(Identifier name, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
			return of(name).setFungusGenerator(fungusGenerator).setBaseFungusBlock(baseFungusBlock);
		}

		public Builder of(Identifier name, Block planks) {
			return of(name).setPlanks(planks);
		}

		public Builder of(WoodMaterial woodMaterial) {
			return of(name).setWoodType(woodMaterial.woodType()).setLog(woodMaterial.log())
				.setLeaves(woodMaterial.leaves());
		}

		public Builder of(WoodMaterial woodMaterial, TreeGrower saplingGenerator) {
			return of(woodMaterial).setSaplingGenerator(saplingGenerator);
		}

		public Builder of(WoodMaterial woodMaterial, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
			return of(woodMaterial).setFungusGenerator(fungusGenerator).setBaseFungusBlock(baseFungusBlock);
		}

		public Builder of(WoodMaterial woodMaterial, Block planks, TreeGrower saplingGenerator) {
			return of(woodMaterial, saplingGenerator).setPlanks(planks);
		}

		public Builder of(WoodMaterial woodMaterial, Block planks, ResourceKey<Feature> fungusGenerator, Block baseFungusBlock) {
			return of(woodMaterial, fungusGenerator, baseFungusBlock).setPlanks(planks);
		}

		public Builder log() {
			String logName = woodRegistry.isNetherWood() ? name.getPath() + "_stem" : woodRegistry.isBambooWood() ? name.getPath() + "_block" : name.getPath() + "_log";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_STEM : woodRegistry.isBambooWood() ? Blocks.BAMBOO_BLOCK : woodRegistry.isCherryWood() ? Blocks.CHERRY_LOG : Blocks.DARK_OAK_LOG;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.STONE : woodRegistry.isBambooWood() ? Blocks.CRIMSON_STEM : Blocks.BAMBOO_BLOCK;

			woodRegistry.log = registryHelper.blocks().registerBlockWood(
				new RotatedPillarBlock(properties(block, logName)),
				logName,
				creativeTabBlock,
				CreativeModeTabs.BUILDING_BLOCKS
			);

			Block finalBlock = woodRegistry.isNetherWood() ? Blocks.OAK_LEAVES : Blocks.MUSHROOM_STEM;

			CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.insertBefore(finalBlock, woodRegistry.log));

			woodType = woodRegistry.isNetherWood() ? WoodType.CRIMSON : woodRegistry.isBambooWood() ? WoodType.BAMBOO : woodRegistry.isCherryWood() ? WoodType.CHERRY : WoodType.ACACIA;
			return this;
		}

		public Builder wood() {
			String woodName = woodRegistry.isNetherWood() ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_HYPHAE : woodRegistry.isCherryWood() ? Blocks.CHERRY_WOOD : Blocks.DARK_OAK_WOOD;

			woodRegistry.wood = registryHelper.blocks().registerBlock(
				new RotatedPillarBlock(properties(block, woodName)),
				woodName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.log
			);

			return this;
		}

		public Builder strippedLog() {
			String logName = woodRegistry.isNetherWood() ? name.getPath() + "_stem" : woodRegistry.isBambooWood() ? name.getPath() + "_block" : name.getPath() + "_log";
			String registryName = "stripped_" + logName;

			Block block = woodRegistry.isNetherWood() ? Blocks.STRIPPED_WARPED_STEM : woodRegistry.isBambooWood() ? Blocks.STRIPPED_BAMBOO_BLOCK : Blocks.STRIPPED_DARK_OAK_LOG;

			woodRegistry.strippedLog = registryHelper.blocks().registerBlock(
				new RotatedPillarBlock(properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.wood
			);

			return this;
		}

		public Builder strippedWood() {
			String woodName = woodRegistry.isNetherWood() ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
			String registryName = "stripped_" + woodName;

			Block block = woodRegistry.isNetherWood() ? Blocks.STRIPPED_WARPED_HYPHAE : woodRegistry.isCherryWood() ? Blocks.STRIPPED_CHERRY_WOOD : Blocks.STRIPPED_DARK_OAK_WOOD;

			woodRegistry.strippedWood = registryHelper.blocks().registerBlock(
				new RotatedPillarBlock(properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.strippedLog
			);

			return this;
		}

		public Builder stairs() {
			String registryName = name.getPath() + "_stairs";

			woodRegistry.stairs = registryHelper.blocks().registerBlock(
				new StairBlock(woodRegistry.planks.defaultBlockState(), properties(woodRegistry.planks, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.planks != null ? woodRegistry.planks : woodRegistry.strippedWood
			);

			return this;
		}

		public Builder mosaicStairs() {
			String registryName = name.getPath() + "_mosaic_stairs";

			woodRegistry.mosaicStairs = registryHelper.blocks().registerBlock(
				new StairBlock(woodRegistry.mosaic.defaultBlockState(), properties(woodRegistry.mosaic, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.stairs
			);

			return this;
		}

		public Builder slab() {
			String registryName = name.getPath() + "_slab";

			woodRegistry.slab = registryHelper.blocks().registerBlock(
				new SlabBlock(properties(woodRegistry.planks, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.mosaicStairs != null ? woodRegistry.mosaicStairs : woodRegistry.stairs
			);

			return this;
		}

		public Builder mosaicSlab() {
			String registryName = name.getPath() + "_mosaic_slab";

			woodRegistry.mosaicSlab = registryHelper.blocks().registerBlock(
				new SlabBlock(properties(woodRegistry.mosaic, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.slab
			);

			return this;
		}

		public Builder planks() {
			String registryName = name.getPath() + "_planks";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_PLANKS : woodRegistry.isBambooWood() ? Blocks.BAMBOO_PLANKS : Blocks.MANGROVE_PLANKS;

			woodRegistry.planks = registryHelper.blocks().registerBlock(
				new Block(properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.strippedWood
			);

			return this;
		}

		public Builder mosaic() {
			String registryName = name.getPath() + "_mosaic";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_PLANKS : woodRegistry.isBambooWood() ? Blocks.BAMBOO_PLANKS : Blocks.MANGROVE_PLANKS;

			woodRegistry.mosaic = registryHelper.blocks().registerBlock(
				new Block(properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.planks
			);

			return this;
		}

		public Builder leaves() {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			String registryName = name.getPath() + leavesName;

			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;

			BlockBehaviour.Properties properties = properties(block, registryName);

			woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
				woodRegistry.isNetherWood()
					? new Block(properties)
					: woodRegistry.tintedLeavesParticle
					  ? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
					  : new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
				registryName,
				CreativeModeTabs.NATURAL_BLOCKS,
				creativeTabBlock
			);

			return this;
		}

		public Builder leaves(String nameIn) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), nameIn + leavesName)));
			woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
				woodRegistry.isNetherWood()
					? new Block(properties)
					: woodRegistry.tintedLeavesParticle
					? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
					: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
				nameIn + leavesName,
				CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
			);
			return this;
		}

		public Builder leaves(String... nameIn) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			for (String name : nameIn) {
				BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), name + leavesName)));
				woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
					woodRegistry.isNetherWood()
						? new Block(properties)
						: woodRegistry.tintedLeavesParticle
						? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
						: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
					name + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
				);
				woodRegistry.availableLeaves.add(name);
			}
			return this;
		}

		public Builder coloredLeaves() {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), name.getPath() + leavesName)));
			woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
				woodRegistry.isNetherWood()
					? new Block(properties)
					: woodRegistry.tintedLeavesParticle
					? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
					: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
				name.getPath() + leavesName,
				CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
			);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true));
			return this;
		}

		public Builder coloredLeaves(int color) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), name.getPath() + leavesName)));
			woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
				woodRegistry.isNetherWood()
					? new Block(properties)
					: woodRegistry.tintedLeavesParticle
					? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
					: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
				name.getPath() + leavesName,
				CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
			);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
			return this;
		}

		public Builder coloredLeaves(String nameIn) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), nameIn + leavesName)));
			woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
				woodRegistry.isNetherWood()
					? new Block(properties)
					: woodRegistry.tintedLeavesParticle
					? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
					: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
				nameIn + leavesName,
				CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
			);
			VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves,
				true);
			VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
			return this;
		}

		public Builder coloredLeaves(String... nameIn) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			for (String name : nameIn) {
				BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), name + leavesName)));
				woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
					woodRegistry.isNetherWood()
						? new Block(properties)
						: woodRegistry.tintedLeavesParticle
						? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
						: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
					name + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
				);
				VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true));
				woodRegistry.availableLeaves.add(name);
			}
			return this;
		}

		public Builder coloredLeaves(String nameIn, int color) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), nameIn + leavesName)));
			woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
				woodRegistry.isNetherWood()
					? new Block(properties)
					: woodRegistry.tintedLeavesParticle
					? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
					: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
				nameIn + leavesName,
				CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
			);
			VampireLibClient.COLORED_LEAVES.add(new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
			return this;
		}

		public Builder coloredLeaves(int color, String... nameIn) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			for (String name : nameIn) {
				BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), name + leavesName)));
				woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
					woodRegistry.isNetherWood()
						? new Block(properties)
						: woodRegistry.tintedLeavesParticle
						? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
						: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
					name + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
				);
				VampireLibClient.COLORED_LEAVES.add(
					new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color));
				woodRegistry.availableLeaves.add(name);
			}
			return this;
		}

		public Builder coloredLeaves(ColoredBlock... coloredLeavesBlocks) {
			String leavesName = woodRegistry.isNetherWood() ? "_wart_block" : "_leaves";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_WART_BLOCK : Blocks.FLOWERING_AZALEA_LEAVES;
			Block creativeTabBlock = woodRegistry.isNetherWood() ? Blocks.SHROOMLIGHT : Blocks.BROWN_MUSHROOM_BLOCK;
			for (ColoredBlock coloredLeavesBlock : coloredLeavesBlocks) {
				BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofLegacyCopy(block).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(registryHelper.modId(), coloredLeavesBlock.name + leavesName)));
				woodRegistry.leaves = registryHelper.blocks().registerBlockWood(
					woodRegistry.isNetherWood()
						? new Block(properties)
						: woodRegistry.tintedLeavesParticle
						? new TintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, properties)
						: new UntintedParticleLeavesBlock(woodRegistry.fallenLeavesAmount, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, woodRegistry.tintedLeavesColor), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
					coloredLeavesBlock.name + leavesName,
					CreativeModeTabs.NATURAL_BLOCKS, creativeTabBlock
				);
				VampireLibClient.COLORED_LEAVES.add(
					new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, coloredLeavesBlock.color));
				woodRegistry.availableLeaves.add(coloredLeavesBlock.name);
			}
			return this;
		}

		public Builder sapling() {
			String registryName = name.getPath() + (woodRegistry.isNetherWood() ? "_fungus" : "_sapling");

			if (!woodRegistry.isNetherWood()) {
				woodRegistry.sapling = registryHelper.blocks().registerBlockWood(
					new SaplingBaseBlock(woodRegistry.saplingGenerator, saplingProperties(registryName)),
					registryName,
					CreativeModeTabs.NATURAL_BLOCKS,
					Blocks.BROWN_MUSHROOM
				);
			} else {
				woodRegistry.sapling = registryHelper.blocks().registerBlockWood(
					new FungusBaseBlock(woodRegistry.fungusGenerator, woodRegistry.baseFungusBlock, BlockTags.SUPPORTS_CRIMSON_FUNGUS, fungusProperties(registryName)),
					registryName,
					CreativeModeTabs.NATURAL_BLOCKS,
					Blocks.SHORT_GRASS
				);
			}

			return this;
		}

		public Builder pottedSapling() {
			String registryName = "potted_" + name.getPath() + (woodRegistry.isNetherWood() ? "_fungus" : "_sapling");

			woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
				registryName,
				new FlowerPotBaseBlock(woodRegistry.sapling, pottedSaplingProperties(registryName))
			);

			return this;
		}

		public Builder sapling(String nameIn) {
			String registryName = nameIn + (woodRegistry.isNetherWood() ? "_fungus" : "_sapling");

			if (!woodRegistry.isNetherWood()) {
				woodRegistry.sapling = registryHelper.blocks().registerBlockWood(
					new SaplingBaseBlock(woodRegistry.saplingGenerator, saplingProperties(registryName)),
					registryName,
					CreativeModeTabs.NATURAL_BLOCKS,
					Blocks.BROWN_MUSHROOM
				);
			} else {
				woodRegistry.sapling = registryHelper.blocks().registerBlockWood(
					new FungusBaseBlock(woodRegistry.fungusGenerator, woodRegistry.baseFungusBlock, BlockTags.SUPPORTS_CRIMSON_FUNGUS, fungusProperties(registryName)),
					registryName,
					CreativeModeTabs.NATURAL_BLOCKS,
					Blocks.SHORT_GRASS
				);
			}

			woodRegistry.availableSaplings.add(nameIn);

			return this;
		}

		public Builder pottedSapling(String nameIn) {
			String registryName = "potted_" + nameIn + (woodRegistry.isNetherWood() ? "_fungus" : "_sapling");

			woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
				registryName,
				new FlowerPotBaseBlock(woodRegistry.sapling, pottedSaplingProperties(registryName))
			);

			return this;
		}

		public Builder saplings(String... names) {
			for (String saplingName : names) {
				String registryName = saplingName + (woodRegistry.isNetherWood() ? "_fungus" : "_sapling");

				if (!woodRegistry.isNetherWood()) {
					woodRegistry.sapling = registryHelper.blocks().registerBlockWood(
						new SaplingBaseBlock(woodRegistry.saplingGenerator, saplingProperties(registryName)),
						registryName,
						CreativeModeTabs.NATURAL_BLOCKS,
						Blocks.BROWN_MUSHROOM
					);
				} else {
					woodRegistry.sapling = registryHelper.blocks().registerBlockWood(
						new FungusBaseBlock(woodRegistry.fungusGenerator, woodRegistry.baseFungusBlock, BlockTags.SUPPORTS_CRIMSON_FUNGUS, fungusProperties(registryName)),
						registryName,
						CreativeModeTabs.NATURAL_BLOCKS,
						Blocks.SHORT_GRASS
					);
				}

				woodRegistry.availableSaplings.add(saplingName);
			}

			return this;
		}

		public Builder pottedSapling(String... names) {
			for (String saplingName : names) {
				String registryName = "potted_" + saplingName + (woodRegistry.isNetherWood() ? "_fungus" : "_sapling");

				woodRegistry.pottedSapling = registryHelper.blocks().registerBlockWithoutItem(
					registryName,
					new FlowerPotBaseBlock(woodRegistry.sapling, pottedSaplingProperties(registryName))
				);

				woodRegistry.availablePottedSaplings.add(saplingName);
			}

			return this;
		}

		public Builder fence() {
			String registryName = name.getPath() + "_fence";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_FENCE : woodRegistry.isBambooWood() ? Blocks.BAMBOO_FENCE : Blocks.DARK_OAK_FENCE;

			woodRegistry.fence = registryHelper.blocks().registerBlock(
				new FenceBlock(properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.mosaicSlab != null ? woodRegistry.mosaicSlab : woodRegistry.slab
			);

			return this;
		}

		public Builder fenceGate() {
			String registryName = name.getPath() + "_fence_gate";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_FENCE_GATE : woodRegistry.isBambooWood() ? Blocks.BAMBOO_FENCE_GATE : Blocks.DARK_OAK_FENCE_GATE;

			woodRegistry.fenceGate = registryHelper.blocks().registerBlock(
				new FenceGateBlock(woodType, properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.fence
			);

			return this;
		}

		public Builder bookshelf() {
			String registryName = name.getPath() + "_bookshelf";

			woodRegistry.bookshelf = registryHelper.blocks().registerBlockWood(
				new Block(properties(woodRegistry.planks, registryName)),
				registryName,
				CreativeModeTabs.FUNCTIONAL_BLOCKS,
				Blocks.CHISELED_BOOKSHELF
			);

			return this;
		}

		public Builder chiseledBookshelf() {
			String registryName = "chiseled_" + name.getPath() + "_bookshelf";

			woodRegistry.chiseledBookshelf = registryHelper.blocks().registerBlockWood(
				new ChiseledBookShelfBlock(properties(woodRegistry.planks, registryName)),
				registryName,
				CreativeModeTabs.FUNCTIONAL_BLOCKS,
				Blocks.LECTERN
			);

			BlockEntityTypes.CHISELED_BOOKSHELF.addValidBlock(woodRegistry.chiseledBookshelf);

			return this;
		}

		public Builder door() {
			String registryName = name.getPath() + "_door";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_DOOR : woodRegistry.isBambooWood() ? Blocks.BAMBOO_DOOR : Blocks.DARK_OAK_DOOR;

			woodRegistry.door = registryHelper.blocks().registerDoubleBlock(
				new DoorBlock(woodType.setType(), properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.fenceGate
			);

			return this;
		}

		public Builder trapdoor() {
			String registryName = name.getPath() + "_trapdoor";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_TRAPDOOR : woodRegistry.isBambooWood() ? Blocks.BAMBOO_TRAPDOOR : Blocks.MANGROVE_TRAPDOOR;

			woodRegistry.trapdoor = registryHelper.blocks().registerBlock(
				new TrapDoorBlock(woodType.setType(), properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.door
			);

			return this;
		}

		public Builder pressurePlate() {
			String registryName = name.getPath() + "_pressure_plate";
			Block block = woodRegistry.isNetherWood() ? Blocks.WARPED_PRESSURE_PLATE : woodRegistry.isBambooWood() ? Blocks.BAMBOO_PRESSURE_PLATE : Blocks.DARK_OAK_PRESSURE_PLATE;

			woodRegistry.pressurePlate = registryHelper.blocks().registerBlock(
				new PressurePlateBlock(woodType.setType(), properties(block, registryName)),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.trapdoor
			);

			return this;
		}

		private ButtonBlock woodenButton(BlockSetType blockSetType, String registryName) {
			return new ButtonBlock(blockSetType, 30, properties(registryName).noCollision().strength(0.5F));
		}

		public Builder button() {
			String registryName = name.getPath() + "_button";

			woodRegistry.button = registryHelper.blocks().registerBlock(
				woodenButton(woodType.setType(), registryName),
				registryName,
				CreativeModeTabs.BUILDING_BLOCKS,
				woodRegistry.pressurePlate
			);

			return this;
		}

		public Builder beehive() {
			String registryName = name.getPath() + "_beehive";

			woodRegistry.beehive = registryHelper.blocks().registerBlockWood(
				new BeehiveBlock(properties(Blocks.BEEHIVE, registryName)),
				registryName,
				CreativeModeTabs.FUNCTIONAL_BLOCKS,
				Blocks.SUSPICIOUS_SAND
			);

			BlockEntityTypes.BEEHIVE.addValidBlock(woodRegistry.beehive);

			return this;
		}

		public Builder ladder() {
			String registryName = name.getPath() + "_ladder";

			woodRegistry.ladder = registryHelper.blocks().registerBlockWood(
				new CustomLadderBlock(properties(Blocks.LADDER, registryName)),
				registryName,
				CreativeModeTabs.FUNCTIONAL_BLOCKS,
				Blocks.SCAFFOLDING
			);

			return this;
		}

		public Builder sign() {
			/*Item baseHangingSignItem = this.woodRegistry.isNetherWood() ? Items.CHEST :
				this.woodRegistry.isBambooWood() ? Items.CRIMSON_SIGN : Items.BAMBOO_SIGN;
			Block baseSignBlock = this.woodRegistry.isNetherWood() ? Blocks.WARPED_SIGN :
				this.woodRegistry.isBambooWood() ? Blocks.BAMBOO_SIGN : Blocks.MANGROVE_SIGN;
			Identifier signTexture = ResourceLocationUtils.modId(
				"wood_types/%s/sign".formatted(this.name.getPath()));
			this.woodRegistry.sign = this.registryHelper.blocks().registerBlockWithoutItem(
				"%s_sign".formatted(this.name.getPath()),
				new TerraformSignBlock(signTexture, BlockBehaviour.Properties.ofLegacyCopy(baseSignBlock))
			);
			this.woodRegistry.wallSign = this.registryHelper.blocks().registerBlockWithoutItem(
				"%s_wall_sign".formatted(this.name.getPath()),
				new TerraformWallSignBlock(signTexture, BlockBehaviour.Properties.ofLegacyCopy(baseSignBlock))
			);
			this.woodRegistry.signItem = this.registryHelper.items().registerItemWood(
				"%s_sign".formatted(this.name.getPath()),
				new SignItem(
					this.woodRegistry.sign,
					this.woodRegistry.wallSign,
					new Item.Properties().stacksTo(16)
				),
				CreativeModeTabs.FUNCTIONAL_BLOCKS, baseHangingSignItem
			);
			((IBlockEntityType) BlockEntityType.SIGN).vlAddBlocks(this.woodRegistry.sign, this.woodRegistry.wallSign);*/
			return this;
		}

		public Builder hangingSign() {
			/*Block baseHangingSignBlock = this.woodRegistry.isNetherWood() ? Blocks.WARPED_HANGING_SIGN :
				this.woodRegistry.isBambooWood() ? Blocks.BAMBOO_HANGING_SIGN : Blocks.MANGROVE_HANGING_SIGN;
			Identifier hangingSignTexture = ResourceLocationUtils.modId(
				"wood_types/" + this.name.getPath() + "/hanging_sign");
			Identifier hangingSignGuiTexture = ResourceLocationUtils.modId(
				"gui/hanging_signs/" + this.name.getPath());
			this.woodRegistry.hangingSign = this.registryHelper.blocks().registerBlockWithoutItem(
				this.name.getPath() + "_hanging_sign",
				new TerraformHangingSignBlock(hangingSignTexture, hangingSignGuiTexture, BlockBehaviour.Properties.ofLegacyCopy(baseHangingSignBlock))
			);
			this.woodRegistry.hangingWallSign = this.registryHelper.blocks().registerBlockWithoutItem(
				this.name.getPath() + "_wall_hanging_sign",
				new TerraformWallHangingSignBlock(hangingSignTexture, hangingSignGuiTexture, BlockBehaviour.Properties.ofLegacyCopy(baseHangingSignBlock))
			);
			this.woodRegistry.hangingSignItem = this.registryHelper.items().registerItem(
				this.name.getPath() + "_hanging_sign",
				new HangingSignItem(
					this.woodRegistry.hangingSign, this.woodRegistry.hangingWallSign,
					new Item.Properties().stacksTo(16)
				),
				CreativeModeTabs.FUNCTIONAL_BLOCKS, woodRegistry.signItem
			);
			((IBlockEntityType) BlockEntityType.HANGING_SIGN).vlAddBlocks(this.woodRegistry.hangingSign, this.woodRegistry.hangingWallSign);*/
			return this;
		}

		public Builder nonFlammable() {
			woodRegistry.flammable = false;
			return this;
		}

		public Builder woodPropertyType(WoodPropertyType woodPropertyType) {
			woodRegistry.woodPropertyType = woodPropertyType;
			if (woodType == null) switch (woodPropertyType) {
				case OVERWORLD, AZALEA -> woodType = WoodType.OAK;
				case NETHER -> woodType = WoodType.CRIMSON;
				case CHERRY -> woodType = WoodType.CHERRY;
				case BAMBOO -> woodType = WoodType.BAMBOO;
			}
			return this;
		}

		public Builder defaultLogsAndWoods() {
			return this.log().wood().strippedLog().strippedWood();
		}

		public Builder defaultBlocks(WoodPropertyType woodPropertyType) {
			return this.woodPropertyType(woodPropertyType).defaultLogsAndWoods()
				.planks().leaves().sapling().pottedSapling().stairs().slab()
				.fence().fenceGate().door().trapdoor()
				.pressurePlate()
				.button().sign().hangingSign();
		}

		public Builder defaultBlocks() {
			return this.defaultBlocks(WoodPropertyType.OVERWORLD);
		}

		public Builder defaultExtras() {
			return this.bookshelf().chiseledBookshelf().beehive().ladder();
		}

		public Builder defaultBlocksColoredLeaves(WoodPropertyType woodPropertyType) {
			return this.woodPropertyType(woodPropertyType).defaultLogsAndWoods()
				.planks().coloredLeaves().sapling().pottedSapling().stairs()
				.slab().fence().fenceGate().door().trapdoor()
				.pressurePlate()
				.button().sign().hangingSign();
		}

		public Builder defaultBlocksColoredLeaves() {
			return this.defaultBlocksColoredLeaves(WoodPropertyType.OVERWORLD);
		}

		public Builder defaultBlocksColoredLeaves(WoodPropertyType woodPropertyType, int color) {
			return this.woodPropertyType(woodPropertyType).defaultLogsAndWoods().planks()
				.coloredLeaves(color).sapling().pottedSapling().stairs().slab()
				.fence().fenceGate().door().trapdoor()
				.pressurePlate()
				.button().sign().hangingSign();
		}

		public Builder defaultBlocksColoredLeaves(int color) {
			return this.defaultBlocksColoredLeaves(WoodPropertyType.OVERWORLD, color);
		}

		private ResourceKey<Block> blockKey(String name) {
			return ResourceKey.create(
				Registries.BLOCK,
				Identifier.fromNamespaceAndPath(registryHelper.modId(), name)
			);
		}

		private BlockBehaviour.Properties properties(String name) {
			return BlockBehaviour.Properties.of().setId(blockKey(name));
		}

		private BlockBehaviour.Properties properties(Block source, String name) {
			return BlockBehaviour.Properties.ofLegacyCopy(source).setId(blockKey(name));
		}

		private BlockBehaviour.Properties fullProperties(Block source, String name) {
			return BlockBehaviour.Properties.ofFullCopy(source).setId(blockKey(name));
		}

		private BlockBehaviour.Properties saplingProperties(String name) {
			return properties(name).noCollision().randomTicks().instabreak().sound(SoundType.GRASS);
		}

		private BlockBehaviour.Properties pottedSaplingProperties(String name) {
			return fullProperties(Blocks.POTTED_ACACIA_SAPLING, name);
		}

		private BlockBehaviour.Properties fungusProperties(String name) {
			return fullProperties(Blocks.CRIMSON_FUNGUS, name);
		}

		public WoodRegistry build() {
			if (woodRegistry.leaves != null && !woodRegistry.isNetherWood())
				DefaultItemComponentEvents.MODIFY.register(context -> {
					var compostable = new Compostable(NumberProviders.COMPOSTABLE_LOW);
					context.modify(woodRegistry.fence.asItem(), builder -> builder.set(DataComponents.COMPOSTABLE, compostable));
				});
			if (woodRegistry.flammable) {
				// flammable blocks
				int baseBurnChance = 5;
				int largeBurnChance = baseBurnChance * 6;

				int baseSpreadChance = 20;
				int smallSpreadChance = baseSpreadChance / 4;
				int largeSpreadChance = baseSpreadChance * 3;

				FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
				if (woodRegistry.planks != null && !woodRegistry.preRegisteredPlanks)
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

				DefaultItemComponentEvents.MODIFY.register(context -> {
					var fuel = new CookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS, NumberProviders.COOKING_DEFAULT_SPEED_MULTIPLIER);
					if (woodRegistry.fence != null)
						context.modify(woodRegistry.fence.asItem(), builder -> builder.set(DataComponents.COOKING_FUEL, fuel));
					if (woodRegistry.fenceGate != null)
						context.modify(woodRegistry.fenceGate.asItem(), builder -> builder.set(DataComponents.COOKING_FUEL, fuel));
				});
			}

			if (woodRegistry.log != null && woodRegistry.wood != null && woodRegistry.strippedLog != null &&
				woodRegistry.strippedWood != null) {
				BlockTransformerHelper.registerStripping(woodRegistry.log, woodRegistry.strippedLog);
				BlockTransformerHelper.registerStripping(woodRegistry.wood, woodRegistry.strippedWood);
			}

			return woodRegistry;
		}
	}

	public record ColoredBlock(String name, int color) {
	}

}
