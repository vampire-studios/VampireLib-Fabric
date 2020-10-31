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

package io.github.vampirestudios.vampirelib.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.vampirelib.callbacks.FogColorCallback;
import io.github.vampirestudios.vampirelib.callbacks.FogDensityCallback;
import io.github.vampirestudios.vampirelib.callbacks.FogRenderCallback;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Shadow private static float red;
    @Shadow private static float green;
    @Shadow private static float blue;

    @Inject(method = "applyFog", at=@At("RETURN"))
    private static void onFogApplied(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo info) {
        ActionResult result = FogRenderCallback.EVENT.invoker().onRender(fogType, camera, viewDistance, viewDistance);
        if (result != ActionResult.PASS) {
            info.cancel();
        }
    }

    @Inject(method = "render", at=@At(value = "RETURN"))
    private static void idk(Camera camera, float tickDelta, ClientWorld world, int i, float f, CallbackInfo info) {
        Vector3f colors = FogColorCallback.EVENT.invoker().onRender(camera, tickDelta, red, green, blue);
        float red = colors.getX();
        float green = colors.getY();
        float blue = colors.getZ();
        RenderSystem.clearColor(red, green, blue, 0.0F);
    }

    @ModifyConstant(method = "method_23792", constant = @Constant())
    private static float idk(float old) {
        return FogDensityCallback.EVENT.invoker().onRender();
    }
}
