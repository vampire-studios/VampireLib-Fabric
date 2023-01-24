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

package io.github.vampirestudios.vampirelib.api.callbacks;

import com.mojang.blaze3d.shaders.FogShape;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public final class FogEvents {
	public static final Event<SetDensity> SET_DENSITY = EventFactory.createArrayBacked(SetDensity.class,
			callbacks -> (info, density) -> {
				for (SetDensity callback : callbacks) {
					return callback.setDensity(info, density);
				}

				return density;
			});

	public static final Event<SetColor> SET_COLOR = EventFactory.createArrayBacked(SetColor.class,
			callbacks -> (data, partialTicks) -> {
				for (SetColor callback : callbacks) {
					callback.setColor(data, partialTicks);
				}
			});

	public static final Event<RenderFog> RENDER_FOG = EventFactory.createArrayBacked(RenderFog.class,
			callbacks -> (mode, type, camera, partialTick, renderDistance, nearDistance, farDistance, shape, fogData) -> {
				for (RenderFog callback : callbacks) {
					if (callback.onFogRender(mode, type, camera, partialTick, renderDistance, nearDistance, farDistance, shape, fogData)) {
						return true;
					}
				}

				return false;
			});

	private FogEvents() {
	}

	@FunctionalInterface
	public interface SetDensity {
		float setDensity(Camera activeRenderInfo, float density);
	}

	@FunctionalInterface
	public interface SetColor {
		void setColor(ColorData d, float partialTicks);
	}

	@FunctionalInterface
	public interface RenderFog {
		boolean onFogRender(FogRenderer.FogMode mode, FogType type, Camera camera, float partialTick, float renderDistance, float nearDistance, float farDistance, FogShape shape, FogData fogData);
	}

	public static class FogData {
		private float farPlaneDistance;
		private float nearPlaneDistance;
		private FogShape fogShape;

		public FogData(float nearPlaneDistance, float farPlaneDistance, FogShape fogShape) {
			this.setFarPlaneDistance(farPlaneDistance);
			this.setNearPlaneDistance(nearPlaneDistance);
			this.setFogShape(fogShape);
		}

		public float getFarPlaneDistance() {
			return this.farPlaneDistance;
		}

		public void setFarPlaneDistance(float distance) {
			this.farPlaneDistance = distance;
		}

		public float getNearPlaneDistance() {
			return this.nearPlaneDistance;
		}

		public void setNearPlaneDistance(float distance) {
			this.nearPlaneDistance = distance;
		}

		public FogShape getFogShape() {
			return this.fogShape;
		}

		public void setFogShape(FogShape shape) {
			this.fogShape = shape;
		}

		public void scaleFarPlaneDistance(float factor) {
			this.farPlaneDistance *= factor;
		}

		public void scaleNearPlaneDistance(float factor) {
			this.nearPlaneDistance *= factor;
		}
	}

	public static class ColorData {
		private final Camera camera;
		private float red;
		private float green;
		private float blue;

		public ColorData(Camera camera, float r, float g, float b) {
			this.camera = camera;
			this.red = r;
			this.green = g;
			this.blue = b;
		}

		public Camera getCamera() {
			return this.camera;
		}

		public float getRed() {
			return this.red;
		}

		public void setRed(float red) {
			this.red = red;
		}

		public float getGreen() {
			return this.green;
		}

		public void setGreen(float green) {
			this.green = green;
		}

		public float getBlue() {
			return this.blue;
		}

		public void setBlue(float blue) {
			this.blue = blue;
		}
	}
}
