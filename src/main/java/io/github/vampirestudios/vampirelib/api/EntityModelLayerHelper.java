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