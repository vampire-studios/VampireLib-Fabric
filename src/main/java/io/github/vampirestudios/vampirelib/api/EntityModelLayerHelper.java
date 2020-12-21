/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vampirestudios.vampirelib.api;

import io.github.vampirestudios.vampirelib.mixins.client.EntityModelLayersAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.Objects;

/**
 * A helpers for registering entity model layers and providers for the layer's textured model data.
 *
 * @deprecated Experimental feature, may be removed or changed without further notice: Snapshot feature.
 */
@Deprecated
@Environment(EnvType.CLIENT)
public final class EntityModelLayerHelper {
	/**
	 * Registers an entity model layer and registers a provider for a {@linkplain TexturedModelData}.
	 *
	 * @param modelLayer the entity model layer
	 * @param provider the provider for the textured model data
	 */
	public static void registerModelLayer(EntityModelLayer modelLayer, TexturedModelDataProvider provider) {
		Objects.requireNonNull(modelLayer, "EntityModelLayer cannot be null");
		Objects.requireNonNull(provider, "TexturedModelDataProvider cannot be null");

		if (EntityModelLayerImpl.PROVIDERS.putIfAbsent(modelLayer, provider) != null) {
			throw new IllegalArgumentException(String.format("Cannot replace registration for entity model layer \"%s\"", modelLayer));
		}

		EntityModelLayersAccessor.getLayers().add(modelLayer);
	}

	private EntityModelLayerHelper() {
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface TexturedModelDataProvider {
		TexturedModelData createModelData();
	}
}