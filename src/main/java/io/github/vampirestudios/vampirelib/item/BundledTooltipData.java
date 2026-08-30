package io.github.vampirestudios.vampirelib.item;

import java.util.List;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

@ApiStatus.Internal
public record BundledTooltipData(List<TooltipComponent> list) implements TooltipComponent {
}
