package io.github.vampirestudios.vampirelib.api.callbacks;

import net.minecraft.client.renderer.debug.DebugRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeature;

@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface DebugRendererRegistrationCallback {
	Event<DebugRendererRegistrationCallback> EVENT = EventFactory.createArrayBacked(DebugRendererRegistrationCallback.class, callbacks -> registrar -> {
		for (var callback : callbacks) {
			callback.registerDebugRenderers(registrar);
		}
	});

	void registerDebugRenderers(DebugRendererRegistrationCallback.DebugRendererRegistrar registrar);

	@FunctionalInterface
	interface DebugRendererRegistrar {
		void register(DebugFeature feature, DebugRenderer.SimpleDebugRenderer renderer);
	}
}
