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

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.vampirelib.api.ItemOverlayRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class StackBorder extends DrawableHelper implements ItemOverlayRenderer.Pre {
	protected static final Identifier BORDER_TEX = new Identifier(VampireLib.MOD_ID, "textures/misc/border.png");
	protected final int color;

	public StackBorder(int color) {
		this.color = color;
	}

	public StackBorder(Formatting formatting) {
		this(getColorFromFormatting(formatting));
	}

	public static int getColorFromFormatting(Formatting formatting) {
		if (!formatting.isColor()) {
			throw new IllegalArgumentException("Formatting must be color!");
		}

		Integer colorRaw = formatting.getColorValue();
		assert colorRaw != null : "Something is *seriously* wrong with this Minecraft client...";
		return colorRaw;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean renderOverlay(MatrixStack matrixStack, TextRenderer renderer, ItemStack stack, int x, int y, String countLabel) {
		RenderSystem.enableDepthTest();
		RenderSystem.enableTexture();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		x -= 1;
		y -= 1;
		Matrix4f matrix4f = matrixStack.peek().getModel();
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = color & 255;
		MinecraftClient.getInstance().getTextureManager().bindTexture(BORDER_TEX);
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
		bufferBuilder.vertex(matrix4f, x, y + 18, 0).color(r, g, b, 255).texture(0, 1).next();
		bufferBuilder.vertex(matrix4f, x + 18, y + 18, 0).color(r, g, b, 255).texture(1, 1).next();
		bufferBuilder.vertex(matrix4f, x + 18, y, 0).color(r, g, b, 255).texture(1, 0).next();
		bufferBuilder.vertex(matrix4f, x, y, 0).color(r, g, b, 255).texture(0, 0).next();
		bufferBuilder.end();
		RenderSystem.enableAlphaTest();
		BufferRenderer.draw(bufferBuilder);
		return false;
	}
}