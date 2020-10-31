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

package io.github.vampirestudios.vampirelib.mixins.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.vampirelib.api.*;
import io.github.vampirestudios.vampirelib.impl.ItemOverlayMaps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Shadow public float zOffset;
    @Shadow
    protected abstract void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha);

    @Unique
    protected void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int color) {
        renderGuiQuad(buffer, x, y, width, height, color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255);
    }
    @Unique private final MatrixStack matrixStack = new MatrixStack();

    /**
     * @reason Implement custom item overlay API
     * @author ADudeCalledLeo
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel) {
        if (!stack.isEmpty()) {
            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, this.zOffset + 200.0F);

            if (ItemOverlayMaps.PRE_RENDERER_MAP.get(stack.getItem()).renderOverlay(matrixStack, renderer, stack, x, y, countLabel)) {
                return;
            }

            ItemLabelInfo countProps = ItemOverlayMaps.LABEL_INFO_MAP.get(stack.getItem());

            if (countProps.isVisible(stack, countLabel)) {
                Text string = countProps.getContents(stack, countLabel);
                int color = countProps.getColor(stack, countLabel);
                VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
                renderer.draw(string, x + 17 - renderer.getWidth(string), y + 9, color, true, matrixStack.peek().getModel(), immediate, false, 0, 0xF000F0);
                immediate.draw();
            }

            ItemDamageBarInfo barProps = ItemOverlayMaps.DAMAGE_BAR_INFO_MAP.get(stack.getItem());

            final int barCount = barProps.getCount(stack);

            if (barCount > 0) {
                int barY = 13 - (barCount - 1) * 2;
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableAlphaTest();
                RenderSystem.disableBlend();

                for (int i = 0; i < barCount; i++) {
                    if (barProps.isVisible(stack, i)) {
                        Tessellator tessellator = Tessellator.getInstance();
                        BufferBuilder bufferBuilder = tessellator.getBuffer();
                        int width = Math.round(barProps.getFillFactor(stack, i) * 13.0F);
                        int color = barProps.getColor(stack, i);
                        this.renderGuiQuad(bufferBuilder, x + 2, y + barY, 13, 2, 0xFF000000);
                        this.renderGuiQuad(bufferBuilder, x + 2, y + barY, width, 1, color);
                        barY += 2;
                    }
                }

                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            ItemCooldownOverlayInfo coolProps = ItemOverlayMaps.COOLDOWN_OVERLAY_INFO_MAP.get(stack.getItem());

            if (coolProps.isVisible(stack, MinecraftClient.getInstance())) {
                float fillFactor = coolProps.getFillFactor(stack, MinecraftClient.getInstance());
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                Tessellator tessellator2 = Tessellator.getInstance();
                BufferBuilder bufferBuilder2 = tessellator2.getBuffer();
                this.renderGuiQuad(bufferBuilder2, x, y + MathHelper.floor(16.0F * (1.0F - fillFactor)), 16, MathHelper.ceil(16.0F * fillFactor), coolProps.getColor(stack, MinecraftClient.getInstance()));
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            ItemOverlayMaps.POST_RENDERER_MAP.get(stack.getItem()).renderOverlay(matrixStack, renderer, stack, x, y, countLabel);
            matrixStack.pop();
        }
    }

}
