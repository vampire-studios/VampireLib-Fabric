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

package io.github.vampirestudios.vampirelib.impl;

import io.github.vampirestudios.vampirelib.api.ItemCooldownOverlayInfo;
import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import io.github.vampirestudios.vampirelib.api.ItemLabelInfo;
import io.github.vampirestudios.vampirelib.api.ItemOverlayRenderer;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;

@Environment(EnvType.CLIENT)
public final class ItemOverlayMaps {
	private ItemOverlayMaps() { }

	// The FabricMC Group is not responsible for any damages caused by directly mutating the following maps.
	// (use ItemOverlayRendererRegistry)

	public static final Reference2ObjectOpenHashMap<Item, ItemLabelInfo> LABEL_INFO_MAP = new Reference2ObjectOpenHashMap<>();
	public static final Reference2ObjectOpenHashMap<Item, ItemDamageBarInfo> DAMAGE_BAR_INFO_MAP = new Reference2ObjectOpenHashMap<>();
	public static final Reference2ObjectOpenHashMap<Item, ItemCooldownOverlayInfo> COOLDOWN_OVERLAY_INFO_MAP = new Reference2ObjectOpenHashMap<>();
	public static final Reference2ObjectOpenHashMap<Item, ItemOverlayRenderer.Pre> PRE_RENDERER_MAP = new Reference2ObjectOpenHashMap<>();
	public static final Reference2ObjectOpenHashMap<Item, ItemOverlayRenderer.Post> POST_RENDERER_MAP = new Reference2ObjectOpenHashMap<>();
}