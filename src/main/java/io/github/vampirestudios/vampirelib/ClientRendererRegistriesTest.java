/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

		ItemOverlayRendererRegistry.setCooldownOverlayInfo(RendererRegistriesTest.LONG_COOLDOWN, new FlashingCooldownOverlayInfo());
		ItemOverlayRendererRegistry.setCooldownOverlayInfo(RendererRegistriesTest.HIDDEN_COOLDOWN, new HiddenCooldownOverlayInfo());

		ItemOverlayRendererRegistry.setPreRenderer(RendererRegistriesTest.TUNISIAN_DIAMOND, new StackBorder(Formatting.GOLD));
		ItemOverlayRendererRegistry.setPreRenderer(RendererRegistriesTest.MYSTERIOUS_BOOK, new StackBorder(Formatting.DARK_PURPLE));
		ItemOverlayRendererRegistry.setPostRenderer(RendererRegistriesTest.MYSTERIOUS_BOOK, new WarningIcon());
	}
}