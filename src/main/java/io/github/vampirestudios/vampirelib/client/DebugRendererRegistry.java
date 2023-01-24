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

package io.github.vampirestudios.vampirelib.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.renderer.debug.DebugRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeature;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeaturesImpl;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public final class DebugRendererRegistry {
	private static final Map<DebugFeature, DebugRenderer.SimpleDebugRenderer> RENDERERS = new HashMap<>();

	DebugRendererRegistry() {}

	public static void register(DebugFeature feature, DebugRenderer.SimpleDebugRenderer renderer) {
		RENDERERS.put(feature, renderer);
	}

	public static Collection<DebugRenderer.SimpleDebugRenderer> getEnabledRenderers() {
		return DebugFeaturesImpl.getEnabledFeatures().stream()
				.filter(DebugFeature::shouldRender)
				.map(RENDERERS::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
