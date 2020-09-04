package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import net.minecraft.item.ItemStack;

public class ManaBarInfo implements ItemDamageBarInfo {
	@Override
	public boolean isVisible(ItemStack stack) {
		// Let's make this only visible if anything is stored
		return getFillFactor(stack) > 0;
	}

	@Override
	public float getFillFactor(ItemStack stack) {
		return ((StorageItem) stack.getItem()).getFillLevel(stack);
	}

	@Override
	public int getColor(ItemStack stack) {
		// Purple mana!
		return 0x660099;
	}

	@Override
	public int getCount(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean isVisible(ItemStack stack, int index) {
		// Let's make this only visible if anything is stored
		return getFillFactor(stack) > 0;
	}

	@Override
	public float getFillFactor(ItemStack stack, int index) {
		return ((StorageItem) stack.getItem()).getFillLevel(stack);
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		// Purple mana!
		return 0x660099;
	}
}