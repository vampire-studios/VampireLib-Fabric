/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public interface RegisterShadersCallback {
	Event<RegisterShadersCallback> EVENT = EventFactory.createArrayBacked(RegisterShadersCallback.class,
			callbacks -> (resourceManager, registry) -> {
				for (RegisterShadersCallback event : callbacks) {
					event.onShaderReload(resourceManager,
							registry);
				}
			});

	void onShaderReload(ResourceManager resourceManager, ShaderRegistry registry) throws IOException;

	record ShaderRegistry(List<Pair<ShaderInstance, Consumer<ShaderInstance>>> shaderList) {
		public void registerShader(ShaderInstance shaderInstance, Consumer<ShaderInstance> onLoaded) {
			this.shaderList.add(Pair.of(shaderInstance, onLoaded));
		}
	}
}
