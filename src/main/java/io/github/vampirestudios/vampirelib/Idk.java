package io.github.vampirestudios.vampirelib;

import net.minecraft.util.Mth;

public class Idk {

	public static void main(String[] args) {
		System.out.println(Integer.toHexString(13421772));
	}

	public static int calculateSkyColor(float temperature) {
		float f = temperature / 3.0F;
		f = Mth.clamp(f, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
	}

}
