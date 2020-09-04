package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class DiscoBarInfo implements ItemDamageBarInfo {
	@Override
	public boolean isVisible(ItemStack stack) {
		return true;
	}

	@Override
	public float getFillFactor(ItemStack stack) {
		return 1;
	}

	@Override
	public int getColor(ItemStack stack) {
		// This doesn't need to be pretty, but it shows that
		// one can get fancy with durability bars by taking
		// the current time into account when calculating fill factor
		// or color.
		float c = (Util.getMeasuringTimeMs() % 360) / 360f;
		return MathHelper.hsvToRgb(c, 1.0f, 1.0f);
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
		return 1;
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		// This doesn't need to be pretty, but it shows that
		// one can get fancy with durability bars by taking
		// the current time into account when calculating fill factor
		// or color.
		float c = (Util.getMeasuringTimeMs() % 360) / 360f;
		return MathHelper.hsvToRgb(c, 1.0f, 1.0f);
	}
}