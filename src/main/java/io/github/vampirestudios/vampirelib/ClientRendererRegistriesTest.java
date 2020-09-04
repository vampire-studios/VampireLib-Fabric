package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.api.ItemOverlayRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Formatting;

public class ClientRendererRegistriesTest implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemOverlayRendererRegistry.setLabelInfo(RendererRegistriesTest.OBFUSCATED_COUNT, new ObfuscatedItemLabelInfo());

		ItemOverlayRendererRegistry.setDamageBarInfo(RendererRegistriesTest.ENERGY_STORAGE, new EnergyBarInfo());
		ItemOverlayRendererRegistry.setDamageBarInfo(RendererRegistriesTest.MANA_STORAGE, new ManaBarInfo());
		ItemOverlayRendererRegistry.setDamageBarInfo(RendererRegistriesTest.WATER_LAVA_BUCKET, new WaterLavaBarProperties());
		ItemOverlayRendererRegistry.setDamageBarInfo(RendererRegistriesTest.DISCO_BALL, new DiscoBarInfo());

		ItemOverlayRendererRegistry.setCooldownInfo(RendererRegistriesTest.LONG_COOLDOWN, new FlashingCooldownInfo());
		ItemOverlayRendererRegistry.setCooldownInfo(RendererRegistriesTest.HIDDEN_COOLDOWN, new HiddenCooldownInfo());

		ItemOverlayRendererRegistry.setPreRenderer(RendererRegistriesTest.TUNISIAN_DIAMOND, new StackBorder(Formatting.GOLD));
		ItemOverlayRendererRegistry.setPreRenderer(RendererRegistriesTest.MYSTERIOUS_BOOK, new StackBorder(Formatting.DARK_PURPLE));
		ItemOverlayRendererRegistry.setPostRenderer(RendererRegistriesTest.MYSTERIOUS_BOOK, new WarningIcon());
	}
}