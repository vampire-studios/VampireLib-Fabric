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

package io.github.vampirestudios.vampirelib.api;

import io.github.vampirestudios.vampirelib.impl.ItemOverlayMaps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public final class ItemOverlayRendererRegistry {
	private ItemOverlayRendererRegistry() { }

	public static void setLabelInfo(Item item, ItemLabelInfo info) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(info);
		ItemOverlayMaps.LABEL_INFO_MAP.put(item, info);
	}

	public static void setDamageBarInfo(Item item, ItemDamageBarInfo info) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(info);
		ItemOverlayMaps.DAMAGE_BAR_INFO_MAP.put(item, info);
	}

	public static void setCooldownOverlayInfo(Item item, ItemCooldownOverlayInfo info) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(info);
		ItemOverlayMaps.COOLDOWN_OVERLAY_INFO_MAP.put(item, info);
	}

	public static void setPreRenderer(Item item, ItemOverlayRenderer.Pre renderer) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(renderer);
		ItemOverlayMaps.PRE_RENDERER_MAP.put(item, renderer);
	}

	public static void setPostRenderer(Item item, ItemOverlayRenderer.Post renderer) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(renderer);
		ItemOverlayMaps.POST_RENDERER_MAP.put(item, renderer);
	}

	public static void setLabelInfo(ItemConvertible itemConvertible, ItemLabelInfo info) {
		Objects.requireNonNull(itemConvertible);
		setLabelInfo(itemConvertible.asItem(), info);
	}

	public static void setDamageBarInfo(ItemConvertible itemConvertible, ItemDamageBarInfo info) {
		Objects.requireNonNull(itemConvertible);
		setDamageBarInfo(itemConvertible.asItem(), info);
	}

	public static void setCooldownOverlayInfo(ItemConvertible itemConvertible, ItemCooldownOverlayInfo info) {
		Objects.requireNonNull(itemConvertible);
		setCooldownOverlayInfo(itemConvertible.asItem(), info);
	}

	public static void setPreRenderer(ItemConvertible itemConvertible, ItemOverlayRenderer.Pre renderer) {
		Objects.requireNonNull(itemConvertible);
		setPreRenderer(itemConvertible.asItem(), renderer);
	}

	public static void setPostRenderer(ItemConvertible itemConvertible, ItemOverlayRenderer.Post renderer) {
		Objects.requireNonNull(itemConvertible);
		setPostRenderer(itemConvertible.asItem(), renderer);
	}

	public static void setDefaultLabelInfo(ItemLabelInfo info) {
		Objects.requireNonNull(info);
		ItemOverlayMaps.LABEL_INFO_MAP.defaultReturnValue(info);
	}

	public static void setDefaultDamageBarInfo(ItemDamageBarInfo info) {
		Objects.requireNonNull(info);
		ItemOverlayMaps.DAMAGE_BAR_INFO_MAP.defaultReturnValue(info);
	}

	public static void setDefaultCooldownOverlayInfo(ItemCooldownOverlayInfo info) {
		Objects.requireNonNull(info);
		ItemOverlayMaps.COOLDOWN_OVERLAY_INFO_MAP.defaultReturnValue(info);
	}

	public static void setDefaultPreRenderer(ItemOverlayRenderer.Pre renderer) {
		Objects.requireNonNull(renderer);
		ItemOverlayMaps.PRE_RENDERER_MAP.defaultReturnValue(renderer);
	}

	public static void setDefaultPostRenderer(ItemOverlayRenderer.Post renderer) {
		Objects.requireNonNull(renderer);
		ItemOverlayMaps.POST_RENDERER_MAP.defaultReturnValue(renderer);
	}
}