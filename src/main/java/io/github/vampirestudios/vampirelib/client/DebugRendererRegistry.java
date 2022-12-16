package io.github.vampirestudios.vampirelib.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.renderer.debug.DebugRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeature;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeaturesImpl;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public final class DebugRendererRegistry {
	private static final Map<DebugFeature, DebugRenderer.SimpleDebugRenderer> RENDERERS = new HashMap<>();

	DebugRendererRegistry() {}

	public static void register(DebugFeature feature, DebugRenderer.SimpleDebugRenderer renderer) {
		RENDERERS.put(feature, renderer);
	}

	public static Collection<DebugRenderer.SimpleDebugRenderer> getEnabledRenderers() {
		return DebugFeaturesImpl.getEnabledFeatures().stream()
				.filter(DebugFeature::shouldRender)
				.map(RENDERERS::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
