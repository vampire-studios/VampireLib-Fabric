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

import io.github.vampirestudios.vampirelib.api.ElytraRegistry;
import io.github.vampirestudios.vampirelib.api.ShieldRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * Allows modded shields to receive damage.
	 * @return
	 */
	@Redirect(method = "damageShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;itemMatches(Lnet/minecraft/item/Item;)Z"))
	private boolean damageFabricShields(ItemStack itemStack, Item item) {
		return itemStack.itemMatches(Items.SHIELD) || item instanceof ShieldItem || ShieldRegistry.INSTANCE.isShield(item);
	}

	/**
	 * Allows modded shields to receive damage.
	 * @return
	 */
	@Redirect(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;itemMatches(Lnet/minecraft/item/Item;)Z"))
	private boolean checkFallFlyingFabric(ItemStack itemStack, Item item) {
		return itemStack.itemMatches(Items.ELYTRA) || item instanceof ElytraItem || ElytraRegistry.INSTANCE.isElytra(item);
	}

	/**
	 * Add cooldown for the modded shield instead of the vanilla one.
	 */
	@Redirect(method = "disableShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ItemCooldownManager;set(Lnet/minecraft/item/Item;I)V"))
	private void setCooldownForShields(ItemCooldownManager cooldownManager, Item item, int duration) {
		if (this.activeItemStack.getItem() instanceof ShieldItem) {
			cooldownManager.set(item, duration);
		} else if (ShieldRegistry.INSTANCE.isShield(this.activeItemStack.getItem())) {
			cooldownManager.set(item, 100);
		} else if (item instanceof ShieldItem) {
			cooldownManager.set(item, duration);
		} else if (ShieldRegistry.INSTANCE.isShield(item)) {
			cooldownManager.set(item, 100);
		}
	}
}