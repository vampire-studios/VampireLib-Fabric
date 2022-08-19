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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface FOVModifierCallback {
	Event<FOVModifierCallback> EVENT = EventFactory.createArrayBacked(FOVModifierCallback.class,
			callbacks -> (player, fov) -> {
				for (FOVModifierCallback e : callbacks) {
					float newFov = e.getNewFOV(player, fov);
					if (newFov != fov) return newFov;
				}

				return fov;
			});

	Event<PartialFOV> PARTIAL_FOV = EventFactory.createArrayBacked(PartialFOV.class,
			callbacks -> (renderer, camera, partialTick, fov) -> {
				for (PartialFOV e : callbacks) {
					double newFov = e.getNewFOV(renderer, camera,
							partialTick,
							fov);
					if (newFov != fov) return newFov;
				}

				return fov;
			});

	float getNewFOV(Player entity, float fov);

	@FunctionalInterface
	interface PartialFOV {
		double getNewFOV(GameRenderer renderer, Camera camera, double partialTick, double fov);
	}
}
