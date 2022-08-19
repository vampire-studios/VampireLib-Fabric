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

package io.github.vampirestudios.vampirelib.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.VertexFormat;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

public class ShaderRegistry {

	private static final Map<ResourceLocation, VertexFormat> REGISTERED_SHADERS = new ConcurrentHashMap<>();
	private static final Map<ResourceLocation, ShaderInstance> SHADERS = new HashMap<>();

	@ApiStatus.Internal
	public static void loadShader(ResourceLocation shader, ShaderInstance instance) {
		SHADERS.put(shader, instance);
	}

	public static void register(ResourceLocation shader, VertexFormat format) {
		REGISTERED_SHADERS.put(shader, format);
	}

	public static Supplier<ShaderInstance> getShader(ResourceLocation shader) {
		return () -> SHADERS.get(shader);
	}

	public static Set<Map.Entry<ResourceLocation, VertexFormat>> getRegisteredShaders() {
		return REGISTERED_SHADERS.entrySet();
	}
}
