package io.github.vampirestudios.vampirelib.api;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

/**
 * This class is used to hold the link between two different blocks (See for example {@link OxidizableBlocksRegistry#registerOxidizableBlockPair(Block, Block)} and
 * {@link OxidizableBlocksRegistry#registerWaxableBlockPair(Block, Block)})
 **/
public class ConvertibleBlockPair {
	private final Block original;
	private final Block converted;
	private final ConversionItem conversionItem;
	private final ConversionItem reversingItem;

	/**
	 * @param original        - The original block which will be converted
	 * @param converted       - The block that the original one will be converted to
	 * @param conversionItems - The item that is used to convert the original block into the converted block
	 **/
	public ConvertibleBlockPair(Block original, Block converted, ConversionItem conversionItems) {
		this(original, converted, conversionItems, null);
	}

	/**
	 * @param original        - The original block which will be converted
	 * @param converted       - The block that the original one will be converted to
	 * @param conversionItem - The item that is used to convert the original block into the converted block
	 * @param reversingItem  - The item that is used to reverse the converted block into the original block
	 **/
	public ConvertibleBlockPair(Block original, Block converted, ConversionItem conversionItem, ConversionItem reversingItem) {
		this.original = original;
		this.converted = converted;
		this.conversionItem = conversionItem;
		this.reversingItem = reversingItem;
	}

	public Block getOriginal() {
		return original;
	}

	public Block getConverted() {
		return converted;
	}

	public ConversionItem getConversionItem() {
		return conversionItem;
	}

	public ConversionItem getReversingItem() {
		return reversingItem;
	}

	public static class ConversionItem {
		private final Tag<Item> tag;
		private final Item item;

		private ConversionItem(Tag<Item> tag, Item item) {
			if((tag == null) == (item == null)) {
				throw new IllegalArgumentException("Only one of the fields must be non-null");
			}
			this.tag = tag;
			this.item = item;
		}

		public static ConversionItem of(Tag<Item> tag) {
			return new ConversionItem(tag, null);
		}
		public static ConversionItem of(Item item) {
			return new ConversionItem(null, item);
		}

		// Call this by parsing the stack in hand
		public boolean matches(ItemStack stack) {
			if(this.tag != null) {
				return stack.is(this.tag);
			} else {
				return stack.is(this.item);
			}
		}
	}

}