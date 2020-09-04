package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import net.minecraft.item.ItemStack;

public class WaterLavaBarProperties implements ItemDamageBarInfo {

	@Override
	public int getCount(ItemStack stack) {
		return 2;
	}

	@Override
	public boolean isVisible(ItemStack stack, int index) {
		return true;
	}

	@Override
	public boolean isVisible(ItemStack stack) {
		return true;
	}

	@Override
	public float getFillFactor(ItemStack stack, int index) {
		if (index == 0) {
			return ((WaterLavaBucket) stack.getItem()).getWaterFillLevel(stack);
		} else {
			return ((WaterLavaBucket) stack.getItem()).getLavaFillLevel(stack);
		}
	}

	@Override
	public float getFillFactor(ItemStack stack) {
		return ((WaterLavaBucket) stack.getItem()).getWaterFillLevel(stack);
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		if (index == 0) {
			// Blue for water
			return 0xFF;
		} else {
			// Red for lava
			return 0xFF0000;
		}
	}

	@Override
	public int getColor(ItemStack stack) {
		return 0xFF;
	}
}
