package io.github.vampirestudios.vampirelib.api;

public interface ItemOverlayExtensions {
	ItemLabelInfo fabric_getCountLabelProperties();
	void fabric_setCountLabelProperties(ItemLabelInfo clp);

	ItemDamageBarInfo fabric_getDurabilityBarProperties();
	void fabric_setDurabilityBarProperties(ItemDamageBarInfo dbp);

	ItemCooldownInfo fabric_getCooldownOverlayProperties();
	void fabric_setCooldownOverlayProperties(ItemCooldownInfo cop);

	ItemOverlayRenderer.Pre fabric_getCustomItemOverlayRendererPre();
	void fabric_setCustomOverlayRendererPre(ItemOverlayRenderer.Pre cior);

	ItemOverlayRenderer.Post fabric_getCustomItemOverlayRendererPost();
	void fabric_setCustomOverlayRendererPost(ItemOverlayRenderer.Post cior);
}