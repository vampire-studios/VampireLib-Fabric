package io.github.vampirestudios.vampirelib.api;

import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * This interface allows you to modify the count label that is displayed on item stacks in inventories.
 */
@Environment(EnvType.CLIENT)
public interface ItemLabelInfo {
	ItemLabelInfo DEFAULT = new ItemLabelInfo() {
		@Override
		public boolean isVisible(ItemStack stack, String override) {
			return override != null && stack.getCount() != 1;
		}

		@Override
		public Text getContents(ItemStack stack, String override) {
			return new LiteralText(override == null ? Integer.toString(stack.getCount()) : override);
		}

		@Override
		public int getColor(ItemStack stack, String override) {
			return 0xFFFFFF;
		}
	};

	/**
	 * Checks if the count label is visible or not.
	 * @param stack stack to check
	 * @param override label contents override
	 * @return {@code true} if label is visible, {@code false} otherwise
	 */
	boolean isVisible(ItemStack stack, String override);

	/**
	 * Gets the contents of the count label.
	 * @param stack stack to check
	 * @param override label contents override
	 * @return label contents
	 */
	Text getContents(ItemStack stack, String override);

	/**
	 * Gets the color of the count label.
	 * @param stack stack to check
	 * @param override label contents override
	 * @return label color
	 */
	int getColor(ItemStack stack, String override);
}