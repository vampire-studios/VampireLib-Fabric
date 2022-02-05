package io.github.vampirestudios.vampirelib.callbacks.client;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class TooltipEventPositionContextImpl implements RenderTooltipCallback.PositionContext {
    private int tooltipX;
    private int tooltipY;
    
    public TooltipEventPositionContextImpl reset(int tooltipX, int tooltipY) {
        this.tooltipX = tooltipX;
        this.tooltipY = tooltipY;
        
        return this;
    }
    
    @Override
    public int getTooltipX() {
        return tooltipX;
    }
    
    @Override
    public void setTooltipX(int x) {
        this.tooltipX = x;
    }
    
    @Override
    public int getTooltipY() {
        return tooltipY;
    }
    
    @Override
    public void setTooltipY(int y) {
        this.tooltipY = y;
    }
}