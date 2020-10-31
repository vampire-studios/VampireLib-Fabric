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

package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class DiscoBarInfo implements ItemDamageBarInfo {
	@Override
	public boolean isVisible(ItemStack stack) {
		return true;
	}

	@Override
	public float getFillFactor(ItemStack stack) {
		return 1;
	}

	@Override
	public int getColor(ItemStack stack) {
		// This doesn't need to be pretty, but it shows that
		// one can get fancy with durability bars by taking
		// the current time into account when calculating fill factor
		// or color.
		float c = (Util.getMeasuringTimeMs() % 360) / 360f;
		return MathHelper.hsvToRgb(c, 1.0f, 1.0f);
	}

	@Override
	public int getCount(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean isVisible(ItemStack stack, int index) {
		return true;
	}

	@Override
	public float getFillFactor(ItemStack stack, int index) {
		return 1;
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		// This doesn't need to be pretty, but it shows that
		// one can get fancy with durability bars by taking
		// the current time into account when calculating fill factor
		// or color.
		float c = (Util.getMeasuringTimeMs() % 360) / 360f;
		return MathHelper.hsvToRgb(c, 1.0f, 1.0f);
	}
}