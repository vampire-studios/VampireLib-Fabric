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

package io.github.vampirestudios.vampirelib.client.callbacks;

import io.github.vampirestudios.vampirelib.client.TooltipRenderer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;

import java.util.List;

public interface RenderTooltipCallback {

    interface Pre {
        Event<RenderTooltipCallback.Pre> EVENT = EventFactory.createArrayBacked(RenderTooltipCallback.Pre.class, (callbacks) -> {
            return (tooltipRenderer) -> {
                for(RenderTooltipCallback.Pre callback : callbacks) {
                    callback.preRender(tooltipRenderer);
                }
            };
        });

        void preRender(TooltipRenderer.Pre tooltipRenderer);
    }

    interface PostText {
        Event<RenderTooltipCallback.PostText> EVENT = EventFactory.createArrayBacked(RenderTooltipCallback.PostText.class, (callbacks) -> {
            return (postText) -> {
                for(RenderTooltipCallback.PostText callback : callbacks) {
                    callback.postText(postText);
                }
            };
        });

        void postText(TooltipRenderer.PostText postText);
    }

    interface PostBackground {
        Event<RenderTooltipCallback.PostBackground> EVENT = EventFactory.createArrayBacked(RenderTooltipCallback.PostBackground.class, (callbacks) -> {
            return (postBackground) -> {
                for(RenderTooltipCallback.PostBackground callback : callbacks) {
                    callback.postBackground(postBackground);
                }
            };
        });

        void postBackground(TooltipRenderer.PostBackground postBackground);
    }

    interface Color {
        Event<RenderTooltipCallback.Color> EVENT = EventFactory.createArrayBacked(RenderTooltipCallback.Color.class, (callbacks) -> {
            return (stack, lines, matrixStack, x, y, textRenderer, background, borderStart, borderEnd) -> {
                for(RenderTooltipCallback.Color callback : callbacks) {
                    return callback.color(stack, lines, matrixStack, x, y, textRenderer, background, borderStart, borderEnd);
                }
                return new TooltipRenderer.Color(stack, lines, matrixStack, x, y, textRenderer, background, borderStart, borderEnd);
            };
        });

        TooltipRenderer.Color color(ItemStack stack, List<? extends OrderedText> textLines, MatrixStack matrixStack, int x, int y, TextRenderer fr, int background, int borderStart, int borderEnd);
    }

}
