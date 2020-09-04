package io.github.vampirestudios.vampirelib.mixins;

import io.github.vampirestudios.vampirelib.api.*;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public abstract class MixinItem implements ItemOverlayExtensions {
	@Unique private ItemLabelInfo clp = ItemLabelInfo.DEFAULT;
	@Unique private ItemDamageBarInfo dbp = ItemDamageBarInfo.DEFAULT;
	@Unique private ItemCooldownInfo cop = ItemCooldownInfo.DEFAULT;
	@Unique private ItemOverlayRenderer.Pre cior;
	@Unique private ItemOverlayRenderer.Post cio3;

	@Override
	public ItemLabelInfo fabric_getCountLabelProperties() {
		return clp;
	}

	@Override
	public void fabric_setCountLabelProperties(ItemLabelInfo clp) {
		this.clp = clp;
	}

	@Override
	public ItemDamageBarInfo fabric_getDurabilityBarProperties() {
		return dbp;
	}

	@Override
	public void fabric_setDurabilityBarProperties(ItemDamageBarInfo dbp) {
		this.dbp = dbp;
	}

	@Override
	public ItemCooldownInfo fabric_getCooldownOverlayProperties() {
		return cop;
	}

	@Override
	public void fabric_setCooldownOverlayProperties(ItemCooldownInfo cop) {
		this.cop = cop;
	}

	@Override
	public ItemOverlayRenderer.Pre fabric_getCustomItemOverlayRendererPre() {
		return cior;
	}

	@Override
	public void fabric_setCustomOverlayRendererPre(ItemOverlayRenderer.Pre cior) {
		this.cior = cior;
	}

	@Override
	public ItemOverlayRenderer.Post fabric_getCustomItemOverlayRendererPost() {
		return cio3;
	}

	@Override
	public void fabric_setCustomOverlayRendererPost(ItemOverlayRenderer.Post cio3) {
		this.cio3 = cio3;
	}
}