package io.github.vampirestudios.vampirelib.api.callbacks;

import com.mojang.blaze3d.shaders.FogShape;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public interface FogEvents {
	Event<SetDensity> SET_DENSITY = EventFactory.createArrayBacked(SetDensity.class, callbacks -> (info, density) -> {
		for (SetDensity callback : callbacks) {
			return callback.setDensity(info, density);
		}
		return density;
	});

	Event<SetColor> SET_COLOR = EventFactory.createArrayBacked(SetColor.class, callbacks -> (data, partialTicks) -> {
		for (SetColor callback : callbacks) {
			callback.setColor(data, partialTicks);
		}
	});

	Event<RenderFog> RENDER_FOG = EventFactory.createArrayBacked(RenderFog.class, callbacks -> (type, info, partialTicks, distance) -> {
		for (RenderFog callback : callbacks) {
			callback.onFogRender(type, info, partialTicks, distance);
		}
	});

	Event<ActualRenderFog> ACTUAL_RENDER_FOG = EventFactory.createArrayBacked(ActualRenderFog.class, callbacks -> (type, info, fogData) -> {
		for (ActualRenderFog callback : callbacks) {
			if (callback.onFogRender(type, info, fogData))
				return true;
		}
		return false;
	});

	@FunctionalInterface
	interface SetDensity {
		float setDensity(Camera activeRenderInfo, float density);
	}

	@FunctionalInterface
	interface SetColor {
		void setColor(ColorData d, float partialTicks);
	}

	@FunctionalInterface
	interface RenderFog {
		void onFogRender(FogRenderer.FogMode type, Camera info, float partial, float distance);
	}

	@FunctionalInterface
	interface ActualRenderFog {
		boolean onFogRender(FogRenderer.FogMode type, Camera info, FogData fogData);
	}

	class FogData {
		private float farPlaneDistance;
		private float nearPlaneDistance;
		private FogShape fogShape;

		public FogData(float nearPlaneDistance, float farPlaneDistance, FogShape fogShape) {
			setFarPlaneDistance(farPlaneDistance);
			setNearPlaneDistance(nearPlaneDistance);
			setFogShape(fogShape);
		}

		public float getFarPlaneDistance() {
			return farPlaneDistance;
		}

		public float getNearPlaneDistance() {
			return nearPlaneDistance;
		}

		public FogShape getFogShape() {
			return fogShape;
		}

		public void setFarPlaneDistance(float distance) {
			farPlaneDistance = distance;
		}

		public void setNearPlaneDistance(float distance) {
			nearPlaneDistance = distance;
		}

		public void setFogShape(FogShape shape) {
			fogShape = shape;
		}

		public void scaleFarPlaneDistance(float factor) {
			farPlaneDistance *= factor;
		}

		public void scaleNearPlaneDistance(float factor) {
			nearPlaneDistance *= factor;
		}
	}

	class ColorData {
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
			return camera;
		}

		public float getRed() {
			return red;
		}

		public float getGreen() {
			return green;
		}

		public float getBlue() {
			return blue;
		}

		public void setRed(float red) {
			this.red = red;
		}

		public void setGreen(float green) {
			this.green = green;
		}

		public void setBlue(float blue) {
			this.blue = blue;
		}
	}
}