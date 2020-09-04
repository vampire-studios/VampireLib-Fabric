package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import net.minecraft.item.ItemStack;

public abstract class SingleDurabilityBarProperties implements ItemDamageBarInfo {
	public abstract boolean isVisible(ItemStack stack);
	public abstract float getFillFactor(ItemStack stack);
	public abstract int getColor(ItemStack stack);

	@Override
	public int getCount(ItemStack stack) {
		return isVisible(stack) ? 1 : 0;
	}

	@Override
	public boolean isVisible(ItemStack stack, int index) {
		return isVisible(stack);
	}

	@Override
	public float getFillFactor(ItemStack stack, int index) {
		return getFillFactor(stack);
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		return getColor(stack);
	}
}