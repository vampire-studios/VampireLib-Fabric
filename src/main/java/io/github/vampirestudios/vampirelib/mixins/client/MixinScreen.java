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

import io.github.vampirestudios.vampirelib.callbacks.client.RenderTooltipCallback;
import io.github.vampirestudios.vampirelib.callbacks.client.TooltipEventColorContextImpl;
import io.github.vampirestudios.vampirelib.callbacks.client.TooltipEventPositionContextImpl;

@Mixin(Screen.class)
public abstract class MixinScreen {
    @Unique
    private static final ThreadLocal<TooltipEventPositionContextImpl> tooltipPositionContext = ThreadLocal.withInitial(TooltipEventPositionContextImpl::new);
    @Unique
    private static final ThreadLocal<TooltipEventColorContextImpl> tooltipColorContext = ThreadLocal.withInitial(TooltipEventColorContextImpl::new);

    @Inject(method = "renderTooltipInternal", at = @At("HEAD"), cancellable = true)
    private void renderTooltip(PoseStack poseStack, List<? extends ClientTooltipComponent> list, int x, int y, CallbackInfo ci) {
        if (!list.isEmpty()) {
            var colorContext = tooltipColorContext.get();
            colorContext.reset();
            var positionContext = tooltipPositionContext.get();
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
    private int modifyTooltipX(int original) {
        return tooltipPositionContext.get().getTooltipX();
    }
    
    @ModifyVariable(method = "renderTooltipInternal",
            at = @At(value = "HEAD"), ordinal = 1, argsOnly = true)
    private int modifyTooltipY(int original) {
        return tooltipPositionContext.get().getTooltipY();
    }
    
    @ModifyConstant(method = "renderTooltipInternal", constant = @Constant(intValue = 0xf0100010))
    private int modifyTooltipBackgroundColor(int original) {
        return tooltipColorContext.get().getBackgroundColor();
    }
    
    @ModifyConstant(method = "renderTooltipInternal", constant = @Constant(intValue = 0x505000ff))
    private int modifyTooltipOutlineGradientTopColor(int original) {
        return tooltipColorContext.get().getOutlineGradientTopColor();
    }
    
    @ModifyConstant(method = "renderTooltipInternal", constant = @Constant(intValue = 0x5028007f))
    private int modifyTooltipOutlineGradientBottomColor(int original) {
        return tooltipColorContext.get().getOutlineGradientBottomColor();
    }

}