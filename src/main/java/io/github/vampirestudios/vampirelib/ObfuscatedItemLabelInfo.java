package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemLabelInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ObfuscatedItemLabelInfo implements ItemLabelInfo {
	@Override
	public boolean isVisible(ItemStack stack, String override) {
		return true;
	}

	@Override
	public Text getContents(ItemStack stack, String override) {
		return new LiteralText(override == null ? Integer.toString(stack.getCount()) : override)
				.styled(style -> style.withFormatting(Formatting.OBFUSCATED));
	}

	@Override
	public int getColor(ItemStack stack, String override) {
		return 0xFFFFFF;
	}
}