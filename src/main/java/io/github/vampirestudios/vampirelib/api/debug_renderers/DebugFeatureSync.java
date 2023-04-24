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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.api.FriendlyByteBufs;

public final class DebugFeatureSync {
	public static final ResourceLocation SYNC_MESSAGE_ID = VampireLib.INSTANCE.identifier("feature_sync");

	@Environment(EnvType.CLIENT)
	public static void syncFeaturesToServer() {
		var features = DebugFeaturesImpl.getFeatures();
		ClientPlayNetworking.send(SYNC_MESSAGE_ID, writeStatuses(features));
	}

	@Environment(EnvType.CLIENT)
	public static void syncFeaturesToServer(DebugFeature... features) {
		ClientPlayNetworking.send(SYNC_MESSAGE_ID, writeStatuses(List.of(features)));
	}

	public static void syncFeaturesToClient(ServerPlayer... players) {
		var features = DebugFeaturesImpl.getFeatures();
		for (ServerPlayer player : players) {
			ServerPlayNetworking.send(player, SYNC_MESSAGE_ID, writeStatuses(features));
		}
	}

	public static void syncFeaturesToClient(Collection<ServerPlayer> players, DebugFeature... features) {
		for (ServerPlayer player : players) {
			ServerPlayNetworking.send(player, SYNC_MESSAGE_ID, writeStatuses(List.of(features)));
		}
	}

	public static FriendlyByteBuf writeStatuses(Collection<DebugFeature> features) {
		var buf = FriendlyByteBufs.create();
		buf.writeVarInt(features.size());
		for (var feature : features) {
			buf.writeResourceLocation(feature.id());
			buf.writeBoolean(DebugFeaturesImpl.isEnabled(feature));
		}
		return buf;
	}

	public static Map<DebugFeature, Boolean> readStatuses(FriendlyByteBuf buf) {
		final int size = buf.readVarInt();
		var statuses = new HashMap<DebugFeature, Boolean>();
		for (int i = 0; i < size; i++) {
			var featureId = buf.readResourceLocation();
			var feature = DebugFeaturesImpl.get(featureId);
			if (feature == null) {
				VampireLib.INSTANCE.getLogger().warn("Received value for unknown debug feature {}", featureId);
				continue;
			}
			boolean enabled = buf.readBoolean();
			statuses.put(feature, enabled);
		}

		return statuses;
	}

	public static void init() {
		ServerPlayNetworking.registerGlobalReceiver(SYNC_MESSAGE_ID, (server, player, handler, buf, responseSender) -> {
			var statuses = readStatuses(buf);
			server.execute(() -> DebugFeaturesImpl.setEnabledForPlayer(player, statuses));
		});
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		ClientPlayNetworking.registerGlobalReceiver(SYNC_MESSAGE_ID, (client, handler, buf, responseSender) -> {
			var statuses = readStatuses(buf);
			client.execute(() -> {
				DebugFeaturesImpl.setEnabledOnServer(statuses);
				DebugFeatureSync.syncFeaturesToServer();
			});
		});
	}
}
