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

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public final class ArmorRenderingRegistryImpl {
	private ArmorRenderingRegistryImpl() {
	}

	public static void registerModel(ArmorRenderingRegistry.ModelProvider provider, Iterable<Item> items) {
		Objects.requireNonNull(items);

		for (Item item : items) {
			Objects.requireNonNull(item);

			((ArmorProviderExtensions) item).fabric_setArmorModelProvider(provider);
		}
	}

	public static void registerTexture(ArmorRenderingRegistry.TextureProvider provider, Iterable<Item> items) {
		Objects.requireNonNull(items);

		for (Item item : items) {
			Objects.requireNonNull(item);

			((ArmorProviderExtensions) item).fabric_setArmorTextureProvider(provider);
		}
	}

	public static BipedEntityModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel<LivingEntity> defaultModel) {
		if (!stack.isEmpty()) {
			ArmorRenderingRegistry.ModelProvider provider = ((ArmorProviderExtensions) stack.getItem()).fabric_getArmorModelProvider();

			if (provider != null) {
				return provider.getArmorModel(entity, stack, slot, defaultModel);
			}
		}

		return defaultModel;
	}

	public static String getArmorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot, String defaultTexture) {
		if (!stack.isEmpty()) {
			ArmorRenderingRegistry.TextureProvider provider = ((ArmorProviderExtensions) stack.getItem()).fabric_getArmorTextureProvider();

			if (provider != null) {
				return provider.getArmorTexture(entity, stack, slot, defaultTexture);
			}
		}

		return null;
	}
}