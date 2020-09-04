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

	public static void setCooldownInfo(Item item, ItemCooldownInfo info) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(info);
		ItemOverlayMaps.COOLDOWN_INFO_MAP.put(item, info);
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

	public static void setCooldownInfo(ItemConvertible itemConvertible, ItemCooldownInfo info) {
		Objects.requireNonNull(itemConvertible);
		setCooldownInfo(itemConvertible.asItem(), info);
	}

	public static void setPreRenderer(ItemConvertible itemConvertible, ItemOverlayRenderer.Pre renderer) {
		Objects.requireNonNull(itemConvertible);
		setPreRenderer(itemConvertible.asItem(), renderer);
	}

	public static void setPostRenderer(ItemConvertible itemConvertible, ItemOverlayRenderer.Post renderer) {
		Objects.requireNonNull(itemConvertible);
		setPostRenderer(itemConvertible.asItem(), renderer);
	}
}