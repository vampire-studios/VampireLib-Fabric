package io.github.vampirestudios.vampirelib.mixins.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.InteractionResult;

import io.github.vampirestudios.vampirelib.api.callbacks.client.RenderTooltipCallback;
import io.github.vampirestudios.vampirelib.api.callbacks.client.TooltipEventColorContextImpl;
import io.github.vampirestudios.vampirelib.api.callbacks.client.TooltipEventPositionContextImpl;

@Mixin(value = Screen.class, priority = 1100)
public abstract class MixinScreen {
    @Unique
    private static final ThreadLocal<TooltipEventPositionContextImpl> vl_tooltipPositionContext = ThreadLocal.withInitial(TooltipEventPositionContextImpl::new);
    @Unique
    private static final ThreadLocal<TooltipEventColorContextImpl> vl_tooltipColorContext = ThreadLocal.withInitial(TooltipEventColorContextImpl::new);

    @Inject(method = "renderTooltipInternal", at = @At("HEAD"), cancellable = true)
    private void vl_renderTooltip(PoseStack poseStack, List<? extends ClientTooltipComponent> list, int x, int y, CallbackInfo ci) {
        if (!list.isEmpty()) {
            var colorContext = vl_tooltipColorContext.get();
            colorContext.reset();
            var positionContext = vl_tooltipPositionContext.get();
            positionContext.reset(x, y);
            InteractionResult result = RenderTooltipCallback.RENDER_PRE.invoker().renderTooltip(poseStack, list, x, y);
            if (result == InteractionResult.FAIL) {
                ci.cancel();
            } else {
                RenderTooltipCallback.RENDER_MODIFY_COLOR.invoker().renderTooltip(poseStack, x, y, colorContext);
                RenderTooltipCallback.RENDER_MODIFY_POSITION.invoker().renderTooltip(poseStack, positionContext);
            }
        }
    }
    
    @ModifyVariable(method = "renderTooltipInternal",
            at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    private int vl_modifyTooltipX(int original) {
        return vl_tooltipPositionContext.get().getTooltipX();
    }
    
    @ModifyVariable(method = "renderTooltipInternal",
            at = @At(value = "HEAD"), ordinal = 1, argsOnly = true)
    private int vl_modifyTooltipY(int original) {
        return vl_tooltipPositionContext.get().getTooltipY();
    }
    
    @ModifyConstant(method = "renderTooltipInternal", constant = @Constant(intValue = 0xf0100010))
    private int vl_modifyTooltipBackgroundColor(int original) {
        return vl_tooltipColorContext.get().getBackgroundColor();
    }
    
    @ModifyConstant(method = "renderTooltipInternal", constant = @Constant(intValue = 0x505000ff))
    private int vl_modifyTooltipOutlineGradientTopColor(int original) {
        return vl_tooltipColorContext.get().getOutlineGradientTopColor();
    }
    
    @ModifyConstant(method = "renderTooltipInternal", constant = @Constant(intValue = 0x5028007f))
    private int vl_modifyTooltipOutlineGradientBottomColor(int original) {
        return vl_tooltipColorContext.get().getOutlineGradientBottomColor();
    }

}