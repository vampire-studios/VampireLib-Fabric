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

package io.github.vampirestudios.vampirelib.api.callbacks;

import net.minecraft.client.renderer.debug.DebugRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeature;

@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface DebugRendererRegistrationCallback {
	Event<DebugRendererRegistrationCallback> EVENT = EventFactory.createArrayBacked(DebugRendererRegistrationCallback.class, callbacks -> registrar -> {
		for (var callback : callbacks) {
			callback.registerDebugRenderers(registrar);
		}
	});

	void registerDebugRenderers(DebugRendererRegistrationCallback.DebugRendererRegistrar registrar);

	@FunctionalInterface
	interface DebugRendererRegistrar {
		void register(DebugFeature feature, DebugRenderer.SimpleDebugRenderer renderer);
	}
}
