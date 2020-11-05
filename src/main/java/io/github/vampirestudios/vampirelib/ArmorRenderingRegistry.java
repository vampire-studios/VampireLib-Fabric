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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * A class for registering custom armor models and textures for {@link Item}, to be provided by a {@link ModelProvider} or {@link TextureProvider}.
 *
 * <p>This can be used to replace existing vanilla armor models and textures conditionally, however each {@link Item}
 * instance can only allow one {@link ModelProvider} or {@link TextureProvider} respectively, causing potential conflicts
 * with other mods if you replace the model or texture for vanilla items. Consider using a separate item instead.</p>
 *
 * <p>A custom model would probably also require a custom texture to go along it, the model will use the vanilla texture if it is undefined.</p>
 *
 * <p>Since armor textures identifier in vanilla is hardcoded to be in the {@code minecraft} namespace, this registry can also be
 * used to use a custom namespace if desired.</p>
 */
@Environment(EnvType.CLIENT)
public final class ArmorRenderingRegistry {
	private ArmorRenderingRegistry() {
	}

	/**
	 * Registers a provider for custom armor models for an item.
	 *
	 * @param provider the provider for the model
	 * @param items    the items to be registered for
	 */
	public static void registerModel(@Nullable ModelProvider provider, Item... items) {
		registerModel(provider, Arrays.asList(items));
	}

	/**
	 * Registers a provider for custom armor models for an item.
	 *
	 * @param provider the provider for the model
	 * @param items    the items to be registered for
	 */
	public static void registerModel(@Nullable ModelProvider provider, Iterable<Item> items) {
		ArmorRenderingRegistryImpl.registerModel(provider, items);
	}

	/**
	 * Registers a provider for custom texture models for an item.
	 *
	 * @param provider the provider for the texture
	 * @param items    the items to be registered for
	 */
	public static void registerTexture(@Nullable TextureProvider provider, Item... items) {
		registerTexture(provider, Arrays.asList(items));
	}

	/**
	 * Registers a provider for custom texture models for an item.
	 *
	 * @param provider the provider for the texture
	 * @param items    the items to be registered for
	 */
	public static void registerTexture(@Nullable TextureProvider provider, Iterable<Item> items) {
		ArmorRenderingRegistryImpl.registerTexture(provider, items);
	}

	/**
	 * Gets the model of the armor piece.
	 *
	 * @param entity       The entity equipping the armor
	 * @param stack        The item stack of the armor
	 * @param slot         The slot which the armor is in
	 * @param defaultModel The default model that vanilla provides
	 * @return The model of the armor piece. Should never be null.
	 */
	public static BipedEntityModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel<LivingEntity> defaultModel) {
		return ArmorRenderingRegistryImpl.getArmorModel(entity, stack, slot, defaultModel);
	}

	/**
	 * Gets the armor texture identifier in string, to be converted to {@link net.minecraft.util.Identifier}.
	 *
	 * @param entity         The entity equipping the armor
	 * @param stack          The item stack of the armor
	 * @param slot           The slot which the armor is in
	 * @param defaultTexture The default vanilla texture identifier
	 * @return the custom armor texture identifier, return null to use the vanilla ones. Defaulted to the item's registry id.
	 */
	/* @Nullable */
	public static String getArmorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot, String defaultTexture) {
		return ArmorRenderingRegistryImpl.getArmorTexture(entity, stack, slot, defaultTexture);
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface ModelProvider {
		/**
		 * Gets the model of the armor piece.
		 *
		 * @param entity       The entity equipping the armor
		 * @param stack        The item stack of the armor
		 * @param slot         The slot which the armor is in
		 * @param defaultModel The default vanilla armor model
		 * @return The model of the armor piece. Should never be null.
		 */
		@NotNull
		BipedEntityModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel<LivingEntity> defaultModel);
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface TextureProvider {
		/**
		 * Gets the armor texture identifier in string, to be converted to {@link net.minecraft.util.Identifier}.
		 *
		 * @param entity         The entity equipping the armor
		 * @param stack          The item stack of the armor
		 * @param slot           The slot which the armor is in
		 * @param defaultTexture The default vanilla texture identifier
		 * @return the custom armor texture identifier, return null to use the vanilla ones. Defaulted to the item's registry id.
		 */
		@Nullable
		String getArmorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot, String defaultTexture);
	}
}