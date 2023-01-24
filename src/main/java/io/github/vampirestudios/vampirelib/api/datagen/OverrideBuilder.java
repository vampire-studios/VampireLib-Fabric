/*
 * Copyright (c) 2023 OliviaTheVampire
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

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;

import net.minecraft.resources.ResourceLocation;

/**
 * Instantiate this class in order to provide an optional set of <code>overrides</code> for a given item model JSON.
 */
public class OverrideBuilder {
	private final ResourceLocation model;
	private final Object2FloatMap<ResourceLocation> predicates = new Object2FloatLinkedOpenHashMap<>();

	/**
	 * Create a new override builder with a given model ID to switch to for this item.
	 *
	 * @param overrideModel The ID of the model to be overridden by.
	 */
	public OverrideBuilder(ResourceLocation overrideModel) {
		this.model = overrideModel;
	}

	/**
	 * Adds a new predicate set to dictate when to switch to the provided item model.
	 *
	 * @param key The ID of an item property to check for.
	 * @param value The value of the property for which the override should be carried out.
	 * @return The current newly-modified {@link OverrideBuilder} instance.
	 */
	public OverrideBuilder predicate(ResourceLocation key, float value) {
		this.predicates.putIfAbsent(key, value);
		return this;
	}

	public JsonObject build() {
		JsonObject override = new JsonObject();

		JsonObject predicates = new JsonObject();
		this.predicates.forEach((key, val) -> predicates.addProperty(key.toString(), val));
		override.add("predicate", predicates);

		override.addProperty("model", this.model.toString());
		return override;
	}
}
