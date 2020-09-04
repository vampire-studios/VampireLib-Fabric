package io.github.vampirestudios.vampirelib.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * This interface allows you to modify the cooldown overlay that is displayed on item stacks in inventories.
 */
@Environment(EnvType.CLIENT)
public interface ItemCooldownInfo {
	ItemCooldownInfo DEFAULT = new ItemCooldownInfo() {
		private float getCooldownAmount(ItemStack stack) {
			ClientPlayerEntity player = MinecraftClient.getInstance().player;
			return player == null ? 0.0F : player.getItemCooldownManager().getCooldownProgress(stack.getItem(), MinecraftClient.getInstance().getTickDelta());
		}

		@Override
		public boolean isVisible(ItemStack stack, MinecraftClient client) {
			return getCooldownAmount(stack) > 0;
		}

		@Override
		public float getFillFactor(ItemStack stack, MinecraftClient client) {
			return getCooldownAmount(stack);
		}

		@Override
		public int getColor(ItemStack stack, MinecraftClient client) {
			return 0x7FFFFFFF;
		}
	};

	/**
	 * Checks if the cooldown overlay is visible or not.
	 * @param stack stack to check
	 * @param client current {@link MinecraftClient} instance
	 * @return {@code true} if overlay is visible, {@code false} otherwise
	 */
	boolean isVisible(ItemStack stack, MinecraftClient client);

	/**
	 * Gets how full the cooldown overlay is.
	 * @param stack stack to check
	 * @param client current {@link MinecraftClient} instance
	 * @return overlay fill factor, between 0 and 1 (inclusive)
	 */
	float getFillFactor(ItemStack stack, MinecraftClient client);

	/**
	 * Gets the color of the cooldown overlay.
	 * @param stack stack to check
	 * @param client current {@link MinecraftClient} instance
	 * @return overlay color
	 */
	int getColor(ItemStack stack, MinecraftClient client);
}