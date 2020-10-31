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

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

/**
 * Shows a full red, flashing overlay if there's more than 80% of the cooldown still remaining.
 */
public class FlashingCooldownOverlayInfo extends BaseCooldownOverlayInfo {
	@Override
	public float getFillFactor(ItemStack stack, MinecraftClient client) {
		if (getCooldownAmount(stack, client) > 0.8f) {
			return 1.0f;
		}

		return super.getFillFactor(stack, client);
	}

	@Override
	public int getColor(ItemStack stack, MinecraftClient client) {
		// Between 1 and 0.5 color it red, otherwise use the default color
		if (getCooldownAmount(stack, client) > 0.8f) {
			float a = 0.1f + 0.7f * (float) Math.sin((Util.getMeasuringTimeMs() % 250) / 250.0f * (float) Math.PI);
			a = MathHelper.clamp(a, 0f, 1f);

			return new Color(1.0f, 0.0f, 0.0f, a).getRGB();
		}

		return super.getColor(stack, client);
	}
}