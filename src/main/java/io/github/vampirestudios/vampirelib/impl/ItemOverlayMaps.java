package io.github.vampirestudios.vampirelib.impl;

import io.github.vampirestudios.vampirelib.api.ItemCooldownInfo;
import io.github.vampirestudios.vampirelib.api.ItemDamageBarInfo;
import io.github.vampirestudios.vampirelib.api.ItemLabelInfo;
import io.github.vampirestudios.vampirelib.api.ItemOverlayRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;

import java.util.IdentityHashMap;

@Environment(EnvType.CLIENT)
public final class ItemOverlayMaps {
	private ItemOverlayMaps() { }

	// The FabricMC Group is not responsible for any damages caused by directly mutating the following maps.
	// (use ItemOverlayRendererRegistry)

	public static final IdentityHashMap<Item, ItemLabelInfo> LABEL_INFO_MAP = new IdentityHashMap<>();
	public static final IdentityHashMap<Item, ItemDamageBarInfo> DAMAGE_BAR_INFO_MAP = new IdentityHashMap<>();
	public static final IdentityHashMap<Item, ItemCooldownInfo> COOLDOWN_INFO_MAP = new IdentityHashMap<>();
	public static final IdentityHashMap<Item, ItemOverlayRenderer.Pre> PRE_RENDERER_MAP = new IdentityHashMap<>();
	public static final IdentityHashMap<Item, ItemOverlayRenderer.Post> POST_RENDERER_MAP = new IdentityHashMap<>();
}