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

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback called when the GameRenderer sets up the Camera.
 */
public interface CameraSetupCallback {
	Event<CameraSetupCallback> EVENT = EventFactory.createArrayBacked(CameraSetupCallback.class,
			callbacks -> info -> {
				for (CameraSetupCallback e : callbacks) {
					if (e.onCameraSetup(info)) {
						return true;
					}
				}

				return false;
			});

	/**
	 * @param info holds the camera data. Modify its fields.
	 *
	 * @return true to cancel further processing
	 */
	boolean onCameraSetup(CameraInfo info);

	class CameraInfo {
		public final GameRenderer renderer;
		public final Camera camera;
		public final double partialTicks;
		public float yaw; // yRot
		public float pitch; // xRot
		public float roll; // zRot

		public CameraInfo(GameRenderer renderer, Camera camera, double partialTicks, float yaw, float pitch, float roll) {
			this.renderer = renderer;
			this.camera = camera;
			this.partialTicks = partialTicks;
			this.yaw = yaw;
			this.pitch = pitch;
			this.roll = roll;
		}
	}
}
