/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * This interface allows you to modify the damage bar that is displayed on item stacks in inventories.
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
	 * Checks if the damage bar is visible or not.
	 * @param stack stack to check
	 * @return {@code true} if bar is visible, {@code false} otherwise
	 */
	boolean isVisible(ItemStack stack);

	/**
	 * Gets how full the damage bar is.
	 * @param stack stack to check
	 * @return bar fill factor, between 0 and 1 (inclusive)
	 */
	float getFillFactor(ItemStack stack);

	/**
	 * Gets the color of the damage bar.
	 * @param stack stack to check
	 * @return bar color
	 */
	int getColor(ItemStack stack);

	int getCount(ItemStack stack);
	boolean isVisible(ItemStack stack, int index);
	float getFillFactor(ItemStack stack, int index);
	int getColor(ItemStack stack, int index);
}