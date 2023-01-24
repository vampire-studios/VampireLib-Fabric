/*
 * Copyright (c) 2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.debug_renderers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;

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

	public static void setEnabledNotifyClients(DebugFeature feature, boolean value, MinecraftServer server) {
		setEnabled(feature, value);
		DebugFeatureSync.syncFeaturesToClient(PlayerLookup.all(server), feature);
	}

	@Environment(EnvType.CLIENT)
	public static void setEnabledNotifyServer(DebugFeature feature, boolean value) {
		setEnabled(feature, value);
		DebugFeatureSync.syncFeaturesToServer(feature);
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
