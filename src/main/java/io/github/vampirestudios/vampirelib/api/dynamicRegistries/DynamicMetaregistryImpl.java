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

package io.github.vampirestudios.vampirelib.api.dynamicRegistries;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import io.github.vampirestudios.vampirelib.mixins.RegistrySynchronizationAccessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

public class DynamicMetaregistryImpl {
	private static boolean frozen;

	public static <E> void register(ResourceKey<? extends Registry<E>> ref, Codec<E> entryCodec) {
		if (frozen) throw new IllegalStateException("Registry is already frozen");
		RegistryDataLoader.WORLDGEN_REGISTRIES.add(new RegistryDataLoader.RegistryData<>(ref, entryCodec));
	}

	public static <E> void registerSynced(ResourceKey<? extends Registry<E>> ref, Codec<E> entryCodec, Codec<E> syncCodec) {
		register(ref, entryCodec);
		var builder = ImmutableMap.<ResourceKey<? extends Registry<?>>, Object>builder().putAll(RegistrySynchronizationAccessor.v$getNetworkableRegistries());
		RegistrySynchronizationAccessor.v$put(builder, ref, syncCodec);
		RegistrySynchronizationAccessor.v$setNetworkableRegistries(builder.build());
	}

	public static void freeze() {
		frozen = true;
	}
}
