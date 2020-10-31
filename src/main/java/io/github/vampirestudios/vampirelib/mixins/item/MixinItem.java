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

package io.github.vampirestudios.vampirelib.mixins.item;

import io.github.vampirestudios.vampirelib.ArmorProviderExtensions;
import io.github.vampirestudios.vampirelib.ArmorRenderingRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class MixinItem implements ArmorProviderExtensions {
	@Unique
	@Environment(EnvType.CLIENT)
	private ArmorRenderingRegistry.ModelProvider armorModelProvider;
	@Unique
	@Environment(EnvType.CLIENT)
	private ArmorRenderingRegistry.TextureProvider armorTextureProvider;

	@Override
	@Environment(EnvType.CLIENT)
	public ArmorRenderingRegistry.ModelProvider fabric_getArmorModelProvider() {
		return armorModelProvider;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ArmorRenderingRegistry.TextureProvider fabric_getArmorTextureProvider() {
		return armorTextureProvider;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void fabric_setArmorModelProvider(ArmorRenderingRegistry.ModelProvider provider) {
		armorModelProvider = provider;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void fabric_setArmorTextureProvider(ArmorRenderingRegistry.TextureProvider provider) {
		armorTextureProvider = provider;
	}
}