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

package io.github.vampirestudios.vampirelib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

import io.github.vampirestudios.vampirelib.mixins.RegistryLoaderAccessor;

/**
 * Methods for registering dynamically loadable registries. These registries are loaded
 * after static registries are frozen and have full access to them, alongside any other
 * dynamic registry loaded before them.
 *
 * <p>NOTE: These registries are not reloadable.
 *
 * <p><b>Experimental feature</b>, dynamic registries are a pretty new feature, and Mojang
 * can make any changes to these registries during snapshots. Since we cannot guarantee this
 * will work in the next snapshots, we reserve the right to remove or change it without further notice.
 * <p>
 * The path used for {@code key} is used as path for resource search as well,
 * so {@code "fabric-api:dynamic_data"} would be searched in {@code "resources/data/modid/dynamic_data/"},
 * {@code modid} being mod id of any mod, it is recommended to use your mod id in
 * registry path to avoid path clashes, like {@code "fabric-api:fabric-api/dynamic_data"}.
 */
@ApiStatus.Experimental
public class DynamicRegistryRegistry {
	/**
	 * Registers a dynamically loaded registry.
	 *
	 * @param key Registry key for the registry
	 * @param codec The codec used for deserialization
	 * @throws IllegalStateException if key path clashes with an already registered entry
	 */
	public static <T> RegistryDataLoader.RegistryData<T> register(ResourceKey<? extends Registry<T>> key, Codec<T> codec) {
		Objects.requireNonNull(key, "Registry key cannot be null!");
		Objects.requireNonNull(codec, "Codec cannot be null!");
		return register(new RegistryDataLoader.RegistryData<>(key, codec));
	}

	/**
	 * Registers a dynamically loaded registry.
	 *
	 * @param entry The entry
	 * @throws IllegalStateException if key path clashes with an already registered entry
	 */
	public static <T> RegistryDataLoader.RegistryData<T> register(RegistryDataLoader.RegistryData<T> entry) {
		Objects.requireNonNull(entry, "Entry cannot be null!");
		validateKey(entry.key());
		MUTABLE_REGISTRIES.add(entry);
		RegistryLoaderAccessor.setDynamicRegistries(Collections.unmodifiableList(DynamicRegistryRegistry.MUTABLE_REGISTRIES));
		return entry;
	}

	/**
	 * Registers a dynamically loaded registry before a specific entry.
	 * If there is no matching entry with key, entry is added to the end of the list.
	 *
	 * @param before Entry key to register before.
	 * @param key Registry key for the registry
	 * @param codec The codec used for deserialization
	 * @throws IllegalStateException if key path clashes with an already registered entry
	 */
	public static <T> RegistryDataLoader.RegistryData<T> registerBefore(ResourceKey<? extends Registry<?>> before, ResourceKey<? extends Registry<T>> key, Codec<T> codec) {
		Objects.requireNonNull(before, "Before key cannot be null!");
		Objects.requireNonNull(key, "Registry key cannot be null!");
		Objects.requireNonNull(codec, "Codec cannot be null!");
		return registerBefore(before, new RegistryDataLoader.RegistryData<>(key, codec));
	}

	/**
	 * Registers a dynamically loaded registry before a specific entry.
	 * If there is no matching entry with key, entry is added to the end of the list.
	 *
	 * @param before Entry key to register before.
	 * @param entry The entry
	 * @throws IllegalStateException if key path clashes with an already registered entry
	 */
	public static <T> RegistryDataLoader.RegistryData<T> registerBefore(ResourceKey<? extends Registry<?>> before, RegistryDataLoader.RegistryData<T> entry) {
		Objects.requireNonNull(before, "Before key cannot be null!");
		Objects.requireNonNull(entry, "Entry cannot be null!");
		validateKey(entry.key());
		MUTABLE_REGISTRIES.add(findIndex(before, false), entry);
		RegistryLoaderAccessor.setDynamicRegistries(Collections.unmodifiableList(DynamicRegistryRegistry.MUTABLE_REGISTRIES));
		return entry;
	}

	/**
	 * Registers a dynamically loaded registry after a specific entry.
	 * If there is no matching entry with key, entry is added to the end of the list.
	 *
	 * @param after Entry key to register after.
	 * @param key Registry key for the registry
	 * @param codec The codec used for deserialization
	 * @throws IllegalStateException if key path clashes with an already registered entry
	 */
	public static <T> RegistryDataLoader.RegistryData<T> registerAfter(ResourceKey<? extends Registry<?>> after, ResourceKey<? extends Registry<T>> key, Codec<T> codec) {
		Objects.requireNonNull(after, "After key cannot be null!");
		Objects.requireNonNull(key, "Registry key cannot be null!");
		Objects.requireNonNull(codec, "Codec cannot be null!");
		return registerAfter(after, new RegistryDataLoader.RegistryData<>(key, codec));
	}

	/**
	 * Registers a dynamically loaded registry after a specific entry.
	 * If there is no matching entry with key, entry is added to the end of the list.
	 *
	 * @param after Entry key to register after.
	 * @param entry The entry
	 * @throws IllegalStateException if key path clashes with an already registered entry
	 */
	public static <T> RegistryDataLoader.RegistryData<T> registerAfter(ResourceKey<? extends Registry<?>> after, RegistryDataLoader.RegistryData<T> entry) {
		Objects.requireNonNull(after, "After key cannot be null!");
		Objects.requireNonNull(entry, "Entry cannot be null!");
		validateKey(entry.key());
		MUTABLE_REGISTRIES.add(findIndex(after, true), entry);
		RegistryLoaderAccessor.setDynamicRegistries(Collections.unmodifiableList(DynamicRegistryRegistry.MUTABLE_REGISTRIES));
		return entry;
	}

	// private

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRegistryRegistry.class);
	private static final List<RegistryDataLoader.RegistryData<?>> MUTABLE_REGISTRIES = new ArrayList<>(RegistryDataLoader.WORLDGEN_REGISTRIES);

	private static void validateKey(ResourceKey<?> key) {
		String path = key.location().getPath();

		for (RegistryDataLoader.RegistryData<?> entry : MUTABLE_REGISTRIES) {
			if (entry.key().location().getPath().equals(path)) {
				throw new IllegalStateException("Dynamic registry path clash between " + entry.key() + " and " + key);
			}
		}
	}

	private static int findIndex(ResourceKey<? extends Registry<?>> key, boolean after) {
		for (int i = 0; i < MUTABLE_REGISTRIES.size(); ++i) {
			if (keysEqual(MUTABLE_REGISTRIES.get(i).key(), key)) {
				return after ? i + 1 : i;
			}
		}

		LOGGER.warn("No matching entry for key: " + key);
		return MUTABLE_REGISTRIES.size();
	}

	private static boolean keysEqual(ResourceKey<?> first, ResourceKey<?> second) {
		return first.registry().equals(second.registry()) && first.location().equals(second.location());
	}

	private DynamicRegistryRegistry() { }
}
