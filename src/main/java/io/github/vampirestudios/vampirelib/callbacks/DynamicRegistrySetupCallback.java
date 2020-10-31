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

package io.github.vampirestudios.vampirelib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.registry.DynamicRegistryManager;

/**
 * This event gets triggered when a new {@link DynamicRegistryManager} gets created, but before it gets filled.
 * Therefore, this is the ideal place to register callbacks to dynamic registries.
 * For example, the following code is used to register a callback that gets triggered for any registered Biome, both JSON and code defined.
 *
 * <pre>
 * {@code
 * DynamicRegistrySetupCallback.EVENT.register(registryManager -> {
 *     Registry<Biome> biomes = registryManager.get(Registry.BIOME_KEY);
 *     RegistryEntryAddedCallback.event(biomes).register((rawId, id, object) -> {
 *         // Do something
 *     });
 * });
 * }
 * </pre>
 */
@FunctionalInterface
public interface DynamicRegistrySetupCallback {
	void onSetupRegistry(DynamicRegistryManager registryManager);

	Event<DynamicRegistrySetupCallback> EVENT = EventFactory.createArrayBacked(
			DynamicRegistrySetupCallback.class,
			callbacks -> registryManager -> {
				for (DynamicRegistrySetupCallback callback : callbacks) {
					callback.onSetupRegistry(registryManager);
				}
			}
	);
}