package team.hollow.abnormalib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class ContentBuilder {
	protected Identifier baseNameIdentifier;

	public abstract String getModId();

	public abstract void finish();

	public abstract Item newItem(String name, Item item);

	public abstract Block newBlock(String name, Block block);
	public abstract Block newBlock(String name, Block block, String textureName);
	public Block newBaseBlock(String name, Block block) {
		newBlock(name, block);
        asBaseBlock(block, new Identifier(getModId(), name));
		return block;
	}

	public void asBaseBlock(Block block) {
		asBaseBlock(block, Registry.BLOCK.getId(block));
	}

	public void asBaseBlock(Block block, Identifier name) {
		setBaseTexture(name);
		setBaseName(name);
		setBaseBlock(block);
		setBaseItem(block);
	}

	public Block newCompressedBlock(String name, Block block, ItemConvertible baseItem) {
		setBaseItem(baseItem);
		return newCompressedBlock(name, block);
	}
	public abstract Block newCompressedBlock(String name, Block block);

	public abstract void setBaseTexture(Identifier name);

	public void setBaseName(String name) {
		setBaseName(new Identifier(getModId(), name));
	}

	public void setBaseName(Identifier name) {
		baseNameIdentifier = name;
	}

	public abstract void setBaseBlock(Block block);

	public abstract void setBaseItem(ItemConvertible itemProvider);

	public abstract void setSecondaryItem(ItemConvertible itemProvider);

	public abstract Block slab();
	public abstract Block stairs();
	public abstract Block fence();
	public abstract Block fenceGate();
	public abstract Block door();
	public abstract Block trapDoor();
	public abstract Block grate();
	public abstract Block wall();
	public abstract Block button(boolean wooden);
	public abstract Block pressurePlate(PressurePlateBlock.ActivationRule type);
	public abstract Block post();
	public abstract Block siding();
	public abstract Block sign();
	public abstract Block chest();
	public abstract Block barrel();
	public abstract Block addPotted();
	public abstract Block addPotted(Identifier plantTexture);

	public abstract void runGameTask(Runnable runnable);

	public Identifier extendIdentifier(String suffix) {
		return extendIdentifier(baseNameIdentifier, suffix);
	}

	public Identifier extendIdentifier(Identifier identifier, String suffix) {
		return new Identifier(getModId(), identifier.getPath() + suffix);
	}
}
