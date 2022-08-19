/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.api.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

/**
 * A consumer used by {@link FabricLanguageProvider#generateLanguages(LanguageConsumer)}.
 */
@FunctionalInterface
public interface LanguageConsumer {
	/**
	 * Adds a language entry.
	 *
	 * @param languageKey The key of the language entry.
	 * @param value       The value of the entry.
	 */
	void addLanguage(String languageKey, String value);

	/**
	 * Adds a language entry for an {@link Item}.
	 *
	 * @param item  The {@link Item} to get the language entry key from.
	 * @param value The value of the entry.
	 */
	default void addLanguage(Item item, String value) {
		this.addLanguage(item.getDescriptionId(), value);
	}

	/**
	 * Adds a language entry for a {@link Block}.
	 *
	 * @param block The {@link Block} to get the language entry key from.
	 * @param value The value of the entry.
	 */
	default void addLanguage(Block block, String value) {
		this.addLanguage(block.getDescriptionId(), value);
	}

	/**
	 * Adds a language entry for an {@link CreativeModeTab}.
	 *
	 * @param group The {@link CreativeModeTab} to get the language entry key from.
	 * @param value The value of the entry.
	 */
	default void addLanguage(CreativeModeTab group, String value) {
		this.addLanguage("itemGroup." + group.getRecipeFolderName(), value);
	}

	/**
	 * Adds a language entry for an {@link EntityType}.
	 *
	 * @param entityType The {@link EntityType} to get the language entry key from.
	 * @param value      The value of the entry.
	 */
	default void addLanguage(EntityType<?> entityType, String value) {
		this.addLanguage(entityType.getDescriptionId(), value);
	}

	/**
	 * Adds a language entry for an {@link Enchantment}.
	 *
	 * @param enchantment The {@link Enchantment} to get the language entry key from.
	 * @param value       The value of the entry.
	 */
	default void addLanguage(Enchantment enchantment, String value) {
		this.addLanguage(enchantment.getDescriptionId(), value);
	}

	/**
	 * Adds a language entry for an {@link Attribute}.
	 *
	 * @param entityAttribute The {@link Attribute} to get the language entry key from.
	 * @param value           The value of the entry.
	 */
	default void addLanguage(Attribute entityAttribute, String value) {
		this.addLanguage(entityAttribute.getDescriptionId(), value);
	}

	/**
	 * Adds a language entry for a {@link StatType}.
	 *
	 * @param statType The {@link StatType} to get the language entry key from.
	 * @param value    The value of the entry.
	 */
	default void addLanguage(StatType<?> statType, String value) {
		this.addLanguage(statType.getTranslationKey(), value);
	}

	/**
	 * Adds a language entry for a {@link MobEffect}.
	 *
	 * @param statusEffect The {@link MobEffect} to get the language entry key from.
	 * @param value        The value of the entry.
	 */
	default void addLanguage(MobEffect statusEffect, String value) {
		this.addLanguage(statusEffect.getDescriptionId(), value);
	}

	/**
	 * Adds a language entry for an {@link ResourceLocation}.
	 *
	 * @param identifier The {@link ResourceLocation} to get the language entry key from.
	 * @param value      The value of the entry.
	 */
	default void addLanguage(ResourceLocation identifier, String value) {
		this.addLanguage(identifier.toLanguageKey(), value);
	}

	/**
	 * Merges an existing language file into the data generated language file.
	 *
	 * @param existingLanguageFile The path to the existing language file.
	 *
	 * @throws IOException If the path is invalid, an IOException is thrown.
	 */
	default void addLanguage(Path existingLanguageFile) throws IOException {
		Gson gson = new Gson();

		JsonObject langEntryJson = gson.fromJson(Files.readString(existingLanguageFile), JsonObject.class);

		for (Map.Entry<String, JsonElement> stringJsonElementEntry : langEntryJson.entrySet()) {
			this.addLanguage(stringJsonElementEntry.getKey(), stringJsonElementEntry.getValue().getAsString());
		}
	}
}
