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

package io.github.vampirestudios.vampirelib.api.callbacks;

import net.minecraft.core.RegistryAccess;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * This event is fired just before dynamic registries are immutablized,
 * giving modders a last chance to access and change any registry
 * while it's still mutable.
 *
 * @see RegistryAccess
 */
@FunctionalInterface
public interface DynamicRegistryFinalizeCallback {
	void onRegistryFinalize(RegistryAccess registryManager);

	Event<DynamicRegistryFinalizeCallback> EVENT = EventFactory.createArrayBacked(DynamicRegistryFinalizeCallback.class, callbacks -> registryManager -> {
		for (DynamicRegistryFinalizeCallback callback : callbacks) {
			callback.onRegistryFinalize(registryManager);
		}
	});
}
