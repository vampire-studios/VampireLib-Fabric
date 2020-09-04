package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import net.minecraft.item.ItemStack;

public class EnergyBarInfo implements ItemDamageBarInfo {
	@Override
	public boolean isVisible(ItemStack stack) {
		return true;
	}

	@Override
	public float getFillFactor(ItemStack stack) {
		return ((StorageItem) stack.getItem()).getFillLevel(stack);
	}

	@Override
	public int getColor(ItemStack stack) {
		// Let's go with green for an energy bar
		return 0xFF00;
	}

	@Override
	public int getCount(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean isVisible(ItemStack stack, int index) {
		return true;
	}

	@Override
	public float getFillFactor(ItemStack stack, int index) {
		return ((StorageItem) stack.getItem()).getFillLevel(stack);
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		// Let's go with green for an energy bar
		return 0xFF00;
	}
}