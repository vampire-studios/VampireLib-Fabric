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

package io.github.vampirestudios.vampirelib.mixins;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(RegistrySynchronization.class)
public interface RegistrySynchronizationAccessor {
	@Accessor("NETWORKABLE_REGISTRIES")
	static Map<ResourceKey<? extends Registry<?>>, ?> v$getNetworkableRegistries() {
		throw new IllegalStateException("Mixin injection failed.");
	}

	@Accessor("NETWORKABLE_REGISTRIES")
	static void v$setNetworkableRegistries(Map<ResourceKey<? extends Registry<?>>, ?> syncedCodecs) {
		throw new IllegalStateException("Mixin injection failed.");
	}

	@Invoker("put")
	static <E> void v$put(ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, ?> builder, ResourceKey<? extends Registry<E>> registryKey, Codec<E> codec) {
		throw new IllegalStateException("Mixin injection failed.");
	}
}
