package io.github.vampirestudios.vampirelib.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * This interface allows you to modify the durability bar that is displayed on item stacks in inventories.
 */
@Environment(EnvType.CLIENT)
public interface ItemDamageBarInfo {
	ItemDamageBarInfo DEFAULT = new ItemDamageBarInfo() {
		private float getDamageValue(ItemStack stack) {
			float f = (float)stack.getDamage();
			float g = (float)stack.getMaxDamage();
			return Math.max(0.0F, (g - f) / g);
		}

		@Override
		public boolean isVisible(ItemStack stack) {
			return stack.isDamaged();
		}

		@Override
		public float getFillFactor(ItemStack stack) {
			return getDamageValue(stack);
		}

		@Override
		public int getColor(ItemStack stack) {
			return MathHelper.hsvToRgb(getDamageValue(stack) / 3, 1, 1);
		}

		@Override
		public int getCount(ItemStack stack) {
			return 1;
		}

		@Override
		public boolean isVisible(ItemStack stack, int index) {
			return stack.isDamaged();
		}

		@Override
		public float getFillFactor(ItemStack stack, int index) {
			return getDamageValue(stack);
		}

		@Override
		public int getColor(ItemStack stack, int index) {
			return MathHelper.hsvToRgb(getDamageValue(stack) / 3, 1, 1);
		}
	};

	/**
	 * Checks if the durability bar is visible or not.
	 * @param stack stack to check
	 * @return {@code true} if bar is visible, {@code false} otherwise
	 */
	boolean isVisible(ItemStack stack);

	/**
	 * Gets how full the durability bar is.
	 * @param stack stack to check
	 * @return bar fill factor, between 0 and 1 (inclusive)
	 */
	float getFillFactor(ItemStack stack);

	/**
	 * Gets the color of the durability bar.
	 * @param stack stack to check
	 * @return bar color
	 */
	int getColor(ItemStack stack);

	int getCount(ItemStack stack);
	boolean isVisible(ItemStack stack, int index);
	float getFillFactor(ItemStack stack, int index);
	int getColor(ItemStack stack, int index);
}