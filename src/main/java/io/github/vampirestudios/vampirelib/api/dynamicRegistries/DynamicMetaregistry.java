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

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Allows registration of dynamic registries for use through {@link net.minecraft.core.RegistryAccess}.
 * <p>
 * Dynamic registries are only available in a world context. Entries must be added either:
 * <ul>
 * <li>as datapack files
 * <li>through the {@link net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback#EVENT dynamic registry setup event}
 * </ul>
 * <p>
 * This registry will be frozen at the same time as static registries.
 */
public final class DynamicMetaregistry {
	/**
	 * Registers a serverside dynamic registry.
	 * <p>
	 * Entries will be loaded from {@code "data/<namespace>/<registry_path>"} for every datapack {@code namespace},
	 * where {@code registry_path}'s value is {@code key.getLocation().getPath()}.
	 * The registry's own namespace is essentially ignored when loading values,
	 * meaning <strong>the registry {@code key}'s path must be unique by itself</strong>
	 * (e.g. {@code RegistryKey.ofRegistry(new Identifier("<mod_id>", "<mod_id>_<resource_name>))}).
	 *
	 * @param <E>        the type of elements in the dynamic registry
	 * @param key        a {@link ResourceKey#createRegistryKey(ResourceLocation) key for the new dynamic registry}
	 * @param entryCodec the codec used to deserialize entries from datapacks
	 * @throws IllegalStateException if this registry of registries already got frozen
	 */
	public static <E> void register(ResourceKey<? extends Registry<E>> key, Codec<E> entryCodec) {
		DynamicMetaregistryImpl.register(key, entryCodec);
	}

	/**
	 * Registers a dynamic registry which contents get synced between the server and connected clients.
	 * <p>
	 * Entries will be loaded from {@code "data/<namespace>/<registry_path>"} for every datapack {@code namespace},
	 * where {@code registry_path}'s value is {@code key.getLocation().getPath()}.
	 * The registry's own namespace is essentially ignored when loading values,
	 * meaning <strong>the registry {@code key}'s path must be unique by itself</strong>
	 * (e.g. {@code RegistryKey.ofRegistry(new Identifier("<mod_id>", "<mod_id>_<resource_name>))}).
	 *
	 * @param <E>        the type of elements in the dynamic registry
	 * @param key        a {@link ResourceKey#createRegistryKey(ResourceLocation) key for the new dynamic registry}
	 * @param entryCodec the codec used to both deserialize entries from datapacks and (de)serialize entries to and from packets
	 * @throws IllegalStateException if this registry of registries already got frozen
	 * @see #registerSynced(ResourceKey, Codec, Codec)
	 */
	public static <E> void registerSynced(ResourceKey<? extends Registry<E>> key, Codec<E> entryCodec) {
		DynamicMetaregistryImpl.registerSynced(key, entryCodec, entryCodec);
	}

	/**
	 * Registers a dynamic registry which contents get synced between the server and connected clients.
	 * <p>
	 * Entries will be loaded from {@code "data/<namespace>/<registry_path>"} for every datapack {@code namespace},
	 * where {@code registry_path}'s value is {@code key.getLocation().getPath()}.
	 * The registry's own namespace is essentially ignored when loading values,
	 * meaning <strong>the registry {@code key}'s path must be unique by itself</strong>
	 * (e.g. {@code RegistryKey.ofRegistry(new Identifier("<mod_id>", "<mod_id>_<resource_name>))}).
	 *
	 * @param <E>        the type of elements in the dynamic registry
	 * @param key        a {@link ResourceKey#createRegistryKey(ResourceLocation) key for the new dynamic registry}
	 * @param entryCodec the codec used to deserialize entries from datapacks
	 * @param syncCodec  the codec used to (de)serialize entries to and from packets - may be the same as {@code entryCodec}
	 * @throws IllegalStateException if this registry of registries already got frozen
	 * @see #registerSynced(ResourceKey, Codec)
	 */
	public static <E> void registerSynced(ResourceKey<? extends Registry<E>> key, Codec<E> entryCodec, Codec<E> syncCodec) {
		DynamicMetaregistryImpl.registerSynced(key, entryCodec, syncCodec);
	}
}
