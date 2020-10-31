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