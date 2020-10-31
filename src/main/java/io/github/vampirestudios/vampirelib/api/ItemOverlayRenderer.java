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

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class ItemOverlayRenderer {
	private ItemOverlayRenderer() { }

	/**
	 * This interface allows you to override Minecraft's GUI item overlay rendering (damage bar, item label
	 * and cooldown overlay).
	 *
	 * <p>With this interface, you could customize this overlay to your heart's content.
	 *
	 * <p>Example:<pre>
	 * {@link ItemOverlayRendererRegistry#setPreRenderer(Item, Pre) ItemOverlayRendererRegistry.setPreRenderer}(Items.DIAMOND, (matrixStack, renderer, stack, x, y, countLabel) -> {
	 * 	renderer.drawWithShadow(matrixStack, "?", x + 17 - renderer.getWidth("?"), y + 9, 0xFFFFFF);
	 * 	return true;
	 * });
	 * </pre>
	 */
	@Environment(EnvType.CLIENT)
	@FunctionalInterface
	public interface Pre {
		/**
		 * Called before Vanilla's overlay rendering. Note that overlay rendering occurs <em>after</em> the
		 * enchanted glint is rendered.
		 * @return {@code true} to cancel Vanilla's overlay rendering.
		 */
		boolean renderOverlay(MatrixStack matrixStack, TextRenderer renderer, ItemStack stack, int x, int y, String countLabel);
	}

	/**
	 * This interface allows you to append to Minecraft's GUI item overlay rendering (durability bars, item counts
	 * and cooldown overlays).
	 */
	@Environment(EnvType.CLIENT)
	@FunctionalInterface
	public interface Post {
		/**
		 * Called after Vanilla's overlay rendering. Note that overlay rendering occurs <em>after</em> the
		 * enchanted glint is rendered.
		 */
		void renderOverlay(MatrixStack matrixStack, TextRenderer renderer, ItemStack stack, int x, int y, String countLabel);
	}
}