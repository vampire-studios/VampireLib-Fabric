package io.github.vampirestudios.vampirelib;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.vampirelib.api.ItemOverlayRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class WarningIcon extends DrawableHelper implements ItemOverlayRenderer.Post {
	private static final Identifier WARNING_TEX = new Identifier(VampireLib.MOD_ID, "textures/gui/warning.png");

	@SuppressWarnings("deprecation")
	@Override
	public void renderOverlay(MatrixStack matrixStack, TextRenderer renderer, ItemStack stack, int x, int y, String countLabel) {
		RenderSystem.disableDepthTest();
		RenderSystem.enableTexture();
		RenderSystem.disableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		MinecraftClient.getInstance().getTextureManager().bindTexture(WARNING_TEX);
		drawTexture(matrixStack, x - 1, y - 1, 18, 18, 18, 18, 18, 18);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
	}
}