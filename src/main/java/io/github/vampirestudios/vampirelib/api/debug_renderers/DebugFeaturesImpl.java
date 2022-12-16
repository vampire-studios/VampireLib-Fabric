package io.github.vampirestudios.vampirelib.api.debug_renderers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@ApiStatus.Internal
public class DebugFeaturesImpl {
	private static final Map<ResourceLocation, DebugFeature> DEBUG_FEATURES = new HashMap<>();
	private static final Set<DebugFeature> ENABLED_FEATURES = new HashSet<>();
	private static final WeakHashMap<ServerPlayer, Set<ResourceLocation>> ENABLED_FEATURES_PER_PLAYER = new WeakHashMap<>();
	@Environment(EnvType.CLIENT)
	private static final Set<ResourceLocation> ENABLED_FEATURES_ON_SERVER = new HashSet<>();

	public static DebugFeature register(DebugFeature feature) {
		DEBUG_FEATURES.put(feature.id(), feature);
		return feature;
	}

	public static @Nullable DebugFeature get(ResourceLocation id) {
		return DEBUG_FEATURES.get(id);
	}

	public static Set<DebugFeature> getFeatures() {
		return new HashSet<>(DEBUG_FEATURES.values());
	}

	public static boolean isEnabled(DebugFeature feature) {
		return ENABLED_FEATURES.contains(feature);
	}

	public static void setEnabled(DebugFeature feature, boolean value) {
		if (value) {
			ENABLED_FEATURES.add(feature);
		} else {
			ENABLED_FEATURES.remove(feature);
		}
	}

	public static Set<DebugFeature> getEnabledFeatures() {
		return new HashSet<>(ENABLED_FEATURES);
	}

	public static boolean isEnabledForPlayer(ServerPlayer player, DebugFeature feature) {
		return ENABLED_FEATURES_PER_PLAYER.getOrDefault(player, Set.of()).contains(feature.id());
	}

	public static void setEnabledForPlayer(ServerPlayer player, DebugFeature feature, boolean value) {
		var set = ENABLED_FEATURES_PER_PLAYER.getOrDefault(player, new HashSet<>());
		if (value) {
			set.add(feature.id());
		} else {
			set.remove(feature.id());
		}
		ENABLED_FEATURES_PER_PLAYER.put(player, set);
	}

	public static void setEnabledForPlayer(ServerPlayer player, Map<DebugFeature, Boolean> statuses) {
		var set = ENABLED_FEATURES_PER_PLAYER.getOrDefault(player, new HashSet<>());
		for (var entry : statuses.entrySet()) {
			if (entry.getValue()) {
				set.add(entry.getKey().id());
			} else {
				set.remove(entry.getKey().id());
			}
		}
		ENABLED_FEATURES_PER_PLAYER.put(player, set);
	}

	@Environment(EnvType.CLIENT)
	public static boolean isEnabledOnServer(DebugFeature feature) {
		return ENABLED_FEATURES_ON_SERVER.contains(feature.id());
	}

	@Environment(EnvType.CLIENT)
	public static void setEnabledOnServer(DebugFeature feature, boolean value) {
		if (value) {
			ENABLED_FEATURES_ON_SERVER.add(feature.id());
		} else {
			ENABLED_FEATURES_ON_SERVER.remove(feature.id());
		}
	}

	@Environment(EnvType.CLIENT)
	public static void setEnabledOnServer(Map<DebugFeature, Boolean> statuses) {
		for (var entry : statuses.entrySet()) {
			if (entry.getValue()) {
				ENABLED_FEATURES_ON_SERVER.add(entry.getKey().id());
			} else {
				ENABLED_FEATURES_ON_SERVER.remove(entry.getKey().id());
			}
		}
	}
}
