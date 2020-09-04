package io.github.vampirestudios.vampirelib;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

/**
 * Shows a full red, flashing overlay if there's more than 80% of the cooldown still remaining.
 */
public class FlashingCooldownInfo extends BaseCooldownInfo {
	@Override
	public float getFillFactor(ItemStack stack, MinecraftClient client) {
		if (getCooldownAmount(stack, client) > 0.8f) {
			return 1.0f;
		}

		return super.getFillFactor(stack, client);
	}

	@Override
	public int getColor(ItemStack stack, MinecraftClient client) {
		// Between 1 and 0.5 color it red, otherwise use the default color
		if (getCooldownAmount(stack, client) > 0.8f) {
			float a = 0.1f + 0.7f * (float) Math.sin((Util.getMeasuringTimeMs() % 250) / 250.0f * (float) Math.PI);
			a = MathHelper.clamp(a, 0f, 1f);

			return new Color(1.0f, 0.0f, 0.0f, a).getRGB();
		}

		return super.getColor(stack, client);
	}
}