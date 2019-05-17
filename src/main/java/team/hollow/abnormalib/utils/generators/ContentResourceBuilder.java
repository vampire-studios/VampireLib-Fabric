package team.hollow.abnormalib.utils.generators;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.hollow.abnormalib.blocks.CakeBaseBlock;
import team.hollow.abnormalib.blocks.CustomLadderBlock;
import team.hollow.abnormalib.blocks.SaplingBaseBlock;
import team.hollow.abnormalib.utils.ContentBuilder;
import team.hollow.abnormalib.utils.generators.blockmodel.BottomTopBlockModel;
import team.hollow.abnormalib.utils.generators.blockmodel.ColumnBlockModel;

public class ContentResourceBuilder extends ContentBuilder {
	protected Identifier baseTextureIdentifier;
	protected String modId;
	protected ResourceGenerator resourceGenerator;

	public ContentResourceBuilder(String modId) {
		this.modId = modId;
		this.resourceGenerator = new ResourceGenerator();
	}

	@Override
	public String getModId() {
		return modId;
	}

	@Override
	public void finish() {
		resourceGenerator.finish();
	}

	@Override
	public Item newItem(String name, Item item) {
		Identifier identifier = new Identifier(getModId(), name);
        resourceGenerator.genItemModel(identifier, identifier);
		return null;
	}

	@Override
	public Block newBlock(String name, Block block) {
		return newBlock(name, block, name);
	}

	@Override
	public Block newBlock(String name, Block block, String textureName) {
		Identifier identifier = new Identifier(getModId(), name);
		Identifier textureIdentifier = new Identifier(getModId(), textureName);

		if(block instanceof PillarBlock) {
			resourceGenerator.genPillarBlock(identifier, extendIdentifier(textureIdentifier, "_top"), textureIdentifier);
		} else if(block instanceof HorizontalFacingBlock) {
			resourceGenerator.genOrientedBlock(identifier, extendIdentifier(textureIdentifier, "_top"), extendIdentifier(textureIdentifier, "_front"), extendIdentifier(textureIdentifier, "_sides"));
		} else if(block instanceof ColumnBlockModel) {
			resourceGenerator.genSimpleBlockstates(identifier);
			resourceGenerator.genPillarBlockModel(identifier, baseTextureIdentifier, textureIdentifier);
			resourceGenerator.genSimpleBlockItemModel(identifier);
		} else if(block instanceof SaplingBaseBlock) {
            resourceGenerator.genPlant(identifier, textureIdentifier);
		} else if(block instanceof BottomTopBlockModel) {
			resourceGenerator.genSimpleBlockstates(identifier);
			resourceGenerator.genBottomTopBlockModel(identifier, extendIdentifier(textureIdentifier, "_bottom"), extendIdentifier(textureIdentifier, "_top"), textureIdentifier);
			resourceGenerator.genSimpleBlockItemModel(identifier);
		} else if(block instanceof CustomLadderBlock) {
			resourceGenerator.genOrientedBlockStates(identifier);
			resourceGenerator.genLadderModel(identifier, textureIdentifier);
			resourceGenerator.genFlatBlockItemModel(identifier, textureIdentifier);
		} else if(block instanceof SweetBerryBushBlock) {
			resourceGenerator.genBush(identifier, textureIdentifier);
		} else if(block instanceof CakeBaseBlock) {
			resourceGenerator.genCake(((CakeBaseBlock) block).getSlices(), identifier, extendIdentifier(textureIdentifier, "_bottom"), extendIdentifier(textureIdentifier, "_side"), extendIdentifier(textureIdentifier, "_inner"), extendIdentifier(textureIdentifier, "_top"));
		} else {
			resourceGenerator.genSimpleBlock(identifier, textureIdentifier);
		}

		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public void asBaseBlock(Block block, Identifier name) {
		super.asBaseBlock(block, name);
		baseTextureIdentifier = name;
	}

	@Override
	public Block newCompressedBlock(String name, Block block) {
		newBlock(name, block);
		return null;
	}

	@Override
	public void setBaseTexture(Identifier name) {
		baseTextureIdentifier = name;
	}

	@Override
	public void setBaseName(Identifier name) {
		baseNameIdentifier = name;
	}

	@Override
	public void setBaseBlock(Block block) {

	}

	@Override
	public void setBaseItem(ItemConvertible itemConvertible) {

	}

	@Override
	public void setSecondaryItem(ItemConvertible itemConvertible) {

	}

	@Override
	public Block slab() {
		Identifier identifier = extendIdentifier("_slab");
		resourceGenerator.genSlab(identifier, baseTextureIdentifier, baseTextureIdentifier, baseTextureIdentifier, baseTextureIdentifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block stairs() {
		Identifier identifier = extendIdentifier("_stairs");
		resourceGenerator.genStair(identifier, baseTextureIdentifier, baseTextureIdentifier, baseTextureIdentifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block fence() {
		Identifier identifier = extendIdentifier("_fence");
		resourceGenerator.genFence(identifier, baseTextureIdentifier, false);
        resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block fenceGate() {
        Identifier identifier = extendIdentifier("_fence_gate");
        resourceGenerator.genFenceGate(identifier, baseTextureIdentifier);
        resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block door() {
		Identifier identifier = extendIdentifier("_door");
		resourceGenerator.genDoor(identifier, extendIdentifier(identifier, "_bottom"), extendIdentifier(identifier, "_top"));
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block trapDoor() {
		Identifier identifier = extendIdentifier("_trapdoor");
		resourceGenerator.genTrapdoor(identifier, identifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block grate() {
		Identifier identifier = extendIdentifier("_grate");
		resourceGenerator.genTrapdoor(identifier, identifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block wall() {
        Identifier identifier = extendIdentifier("_wall");
        resourceGenerator.genFence(identifier, baseTextureIdentifier, true);
        resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block button(boolean wooden) {
        Identifier identifier = extendIdentifier("_button");
        resourceGenerator.genButton(identifier, baseTextureIdentifier);
        resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block pressurePlate(PressurePlateBlock.ActivationRule type) {
		Identifier identifier = extendIdentifier("_pressure_plate");
        resourceGenerator.genPressurePlate(identifier, baseTextureIdentifier);
        resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block post() {
		// TODO
		return null;
	}

	@Override
	public Block sign() {
		Identifier identifier = extendIdentifier("_sign");
		resourceGenerator.genSign(identifier, extendIdentifier("_wall_sign"), baseTextureIdentifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block siding() {
        Identifier identifier = extendIdentifier("_siding");
        resourceGenerator.genSiding(identifier, baseTextureIdentifier);
        resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block chest() {
		Identifier identifier = extendIdentifier("_chest");
		resourceGenerator.genChest(identifier, baseTextureIdentifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block barrel() {
		Identifier identifier = extendIdentifier("_barrel");
		resourceGenerator.genBarrel(identifier, identifier);
		resourceGenerator.genSimpleLootTable(identifier, identifier);
		return null;
	}

	@Override
	public Block addPotted() {
		addPotted(new Identifier(baseTextureIdentifier.getNamespace(), "blocks/" + baseTextureIdentifier.getPath()));
		return null;
	}

	@Override
	public Block addPotted(Identifier plantTexture) {
		Identifier identifier = new Identifier(getModId(), "potted_" + baseNameIdentifier.getPath());
		resourceGenerator.genPottedBlock(identifier, plantTexture);
        resourceGenerator.genSimpleLootTable(identifier, Registry.ITEM.getId(Item.BLOCK_ITEM_MAP.get(Registry.BLOCK.get(baseNameIdentifier))), Registry.ITEM.getId(Items.FLOWER_POT));
		return null;
	}

	@Override
	public void runGameTask(Runnable runnable) {

	}
}
