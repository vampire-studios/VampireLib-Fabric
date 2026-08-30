package io.github.vampirestudios.vampirelib.item;

import java.util.List;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

public record BundledTooltipComponentImpl(List<ClientTooltipComponent> list) implements ClientTooltipComponent {
	@Override
	public int getHeight(Font font) {
		int h = 0;

		for (ClientTooltipComponent component : list) {
			h += component.getHeight(font);
		}

		return h;
	}

	@Override
	public int getWidth(Font textRenderer) {
		int w = 0;

		for (ClientTooltipComponent component : list) {
			w = Math.max(w, component.getWidth(textRenderer));
		}

		return w;
	}

	@Override
	public void extractText(GuiGraphicsExtractor graphics, Font font, int x, int y) {
		int y1 = y;

		for (ClientTooltipComponent component : list) {
			component.extractText(graphics, font, x, y1);
			y1 += component.getHeight(font);
		}
	}

	@Override
	public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
		int y1 = y;

		for (ClientTooltipComponent component : list) {
			component.extractImage(font, x, y, w, h, graphics);
			y1 += component.getHeight(font);
		}
	}
}
