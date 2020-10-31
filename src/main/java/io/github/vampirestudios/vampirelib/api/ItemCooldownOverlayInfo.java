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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * This interface allows you to modify the cooldown overlay that is displayed on item stacks in inventories.
 */
@Environment(EnvType.CLIENT)
public interface ItemCooldownOverlayInfo {
	ItemCooldownOverlayInfo DEFAULT = new ItemCooldownOverlayInfo() {
		private float getCooldownAmount(ItemStack stack) {
			ClientPlayerEntity player = MinecraftClient.getInstance().player;
			return player == null ? 0.0F : player.getItemCooldownManager().getCooldownProgress(stack.getItem(), MinecraftClient.getInstance().getTickDelta());
		}

		@Override
		public boolean isVisible(ItemStack stack, MinecraftClient client) {
			return getCooldownAmount(stack) > 0;
		}

		@Override
		public float getFillFactor(ItemStack stack, MinecraftClient client) {
			return getCooldownAmount(stack);
		}

		@Override
		public int getColor(ItemStack stack, MinecraftClient client) {
			return 0x7FFFFFFF;
		}
	};

	/**
	 * Checks if the cooldown overlay is visible or not.
	 * @param stack stack to check
	 * @param client current {@link MinecraftClient} instance
	 * @return {@code true} if overlay is visible, {@code false} otherwise
	 */
	boolean isVisible(ItemStack stack, MinecraftClient client);

	/**
	 * Gets how full the cooldown overlay is.
	 * @param stack stack to check
	 * @param client current {@link MinecraftClient} instance
	 * @return overlay fill factor, between 0 and 1 (inclusive)
	 */
	float getFillFactor(ItemStack stack, MinecraftClient client);

	/**
	 * Gets the color of the cooldown overlay.
	 * @param stack stack to check
	 * @param client current {@link MinecraftClient} instance
	 * @return overlay color
	 */
	int getColor(ItemStack stack, MinecraftClient client);
}