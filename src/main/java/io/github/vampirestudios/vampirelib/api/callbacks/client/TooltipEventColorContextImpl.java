package io.github.vampirestudios.vampirelib.api.callbacks.client;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class TooltipEventColorContextImpl implements RenderTooltipCallback.ColorContext {
    private int backgroundColor;
    private int outlineGradientTopColor;
    private int outlineGradientBottomColor;
    
    public TooltipEventColorContextImpl reset() {
        this.backgroundColor = 0xf0100010;
        this.outlineGradientTopColor = 0x505000ff;
        this.outlineGradientBottomColor = 0x5028007f;
        
        return this;
    }
    
    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }
    
    @Override
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }
    
    @Override
    public int getOutlineGradientTopColor() {
        return outlineGradientTopColor;
    }
    
    @Override
    public void setOutlineGradientTopColor(int color) {
        this.outlineGradientTopColor = color;
    }
    
    @Override
    public int getOutlineGradientBottomColor() {
        return outlineGradientBottomColor;
    }
    
    @Override
    public void setOutlineGradientBottomColor(int color) {
        this.outlineGradientBottomColor = color;
    }
}