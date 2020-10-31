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

import io.github.vampirestudios.vampirelib.callbacks.TextRendererDrawLayerClass5348Callback;
import io.github.vampirestudios.vampirelib.callbacks.TextRendererDrawLayerStringCallback;
import io.github.vampirestudios.vampirelib.callbacks.TextRendererInitCallback;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    @Inject(at = @At("RETURN"), method = "<init>*")
    public void onInit(CallbackInfo ci) {
        TextRendererInitCallback.EVENT.invoker().onInit((TextRenderer)(Object)this);
    }

    @Inject(at = @At("HEAD"), method = "drawLayer(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F", cancellable = true)
    public void onDrawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> cir) {
        TextRendererDrawLayerStringCallback.EVENT.invoker().onDrawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, (Boolean cancel, Float returnValue) -> {
            if (cancel) {
                cir.cancel();
                cir.setReturnValue(returnValue);
            }
        });
    }

    @Inject(at = @At("HEAD"), method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", cancellable = true)
    public void onDrawLayer(OrderedText arg, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> cir) {
        TextRendererDrawLayerClass5348Callback.EVENT.invoker().onDrawLayer(arg, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, (Boolean cancel, Float returnValue) -> {
            if (cancel) {
                cir.cancel();
                cir.setReturnValue(returnValue);
            }
        });
    }




}