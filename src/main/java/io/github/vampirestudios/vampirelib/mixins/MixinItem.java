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

package io.github.vampirestudios.vampirelib.mixins;

import io.github.vampirestudios.vampirelib.api.*;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public abstract class MixinItem implements ItemOverlayExtensions {
	@Unique private ItemLabelInfo labelInfo;
	@Unique private ItemDamageBarInfo damageBarInfo = ItemDamageBarInfo.DEFAULT;
	@Unique private ItemCooldownOverlayInfo cooldownOverlayInfo = ItemCooldownOverlayInfo.DEFAULT;
	@Unique private ItemOverlayRenderer.Pre pre;
	@Unique private ItemOverlayRenderer.Post post;

	@Override
	public ItemLabelInfo fabric_getCountLabelProperties() {
		return labelInfo;
	}

	@Override
	public void fabric_setCountLabelProperties(ItemLabelInfo clp) {
		this.labelInfo = clp;
	}

	@Override
	public ItemDamageBarInfo fabric_getDurabilityBarProperties() {
		return damageBarInfo;
	}

	@Override
	public void fabric_setDurabilityBarProperties(ItemDamageBarInfo dbp) {
		this.damageBarInfo = dbp;
	}

	@Override
	public ItemCooldownOverlayInfo fabric_getCooldownOverlayProperties() {
		return cooldownOverlayInfo;
	}

	@Override
	public void fabric_setCooldownOverlayProperties(ItemCooldownOverlayInfo cop) {
		this.cooldownOverlayInfo = cop;
	}

	@Override
	public ItemOverlayRenderer.Pre fabric_getCustomItemOverlayRendererPre() {
		return pre;
	}

	@Override
	public void fabric_setCustomOverlayRendererPre(ItemOverlayRenderer.Pre cior) {
		this.pre = cior;
	}

	@Override
	public ItemOverlayRenderer.Post fabric_getCustomItemOverlayRendererPost() {
		return post;
	}

	@Override
	public void fabric_setCustomOverlayRendererPost(ItemOverlayRenderer.Post cio3) {
		this.post = cio3;
	}
}