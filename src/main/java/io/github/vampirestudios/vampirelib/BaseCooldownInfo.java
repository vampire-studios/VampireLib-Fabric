package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemCooldownInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class BaseCooldownInfo implements ItemCooldownInfo {
	protected float getCooldownAmount(ItemStack stack, MinecraftClient client) {
		// copied from ItemRenderer.renderGuiItemOverlay, lines 355-356 (player was local "clientPlayerEntity", client was MinecraftClient.getInstance())
		ClientPlayerEntity player = client.player;
		return player == null ? 0.0F : player.getItemCooldownManager().getCooldownProgress(stack.getItem(), client.getTickDelta());
	}

	@Override
	public boolean isVisible(ItemStack stack, MinecraftClient client) {
		// copied from ItemRenderer.renderGuiItemOverlay, line 357 (getCooldownAmount call was local "k")
		return getCooldownAmount(stack, client) > 0;
	}

	@Override
	public float getFillFactor(ItemStack stack, MinecraftClient client) {
		return getCooldownAmount(stack, client);
	}

	@Override
	public int getColor(ItemStack stack, MinecraftClient client) {
		return 0x7FFFFFFF;
	}
}