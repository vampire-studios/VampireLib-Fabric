package io.github.vampirestudios.vampirelib;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

/**
 * Hides the cooldown overlay, even if there is a cooldown, as long as it has more than 20% remaining.
 */
public class HiddenCooldownInfo extends BaseCooldownInfo {
	@Override
	public boolean isVisible(ItemStack stack, MinecraftClient client) {
		return getCooldownAmount(stack, client) <= 0.2f;
	}
}