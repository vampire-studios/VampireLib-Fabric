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
import java.util.List;
import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;

public final class DebugFeature {
	private final ResourceLocation id;
	private final boolean needsServer;

	private DebugFeature(ResourceLocation id, boolean needsServer) {
		this.id = id;
		this.needsServer = needsServer;
	}

	public boolean isEnabled() {
		return DebugFeaturesImpl.isEnabled(this);
	}

	public Collection<ServerPlayer> getPlayersWithFeatureEnabled(MinecraftServer server) {
		return DebugFeaturesImpl.isEnabled(this) ?
				PlayerLookup.all(server).stream().filter(p -> DebugFeaturesImpl.isEnabledForPlayer(p, this)).toList() :
				List.of();
	}

	public boolean isEnabledOnServerAndClient(ServerPlayer player) {
		return DebugFeaturesImpl.isEnabled(this) && DebugFeaturesImpl.isEnabledForPlayer(player, this);
	}

	@Environment(EnvType.CLIENT)
	public boolean isEnabledOnServerAndClient() {
		return DebugFeaturesImpl.isEnabledOnServer(this) && DebugFeaturesImpl.isEnabledOnServer(this);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRender() {
		return this.isEnabled() && !this.needsServer() || this.isEnabledOnServerAndClient();
	}

	public static DebugFeature register(ResourceLocation id, boolean needsServer) {
		var existingFeature = DebugFeaturesImpl.get(id);
		if (existingFeature != null) {
			throw new IllegalArgumentException("A debug feature with the id %s already exists!".formatted(id));
		}
		var newFeature = new DebugFeature(id, needsServer);
		return DebugFeaturesImpl.register(newFeature);
	}

	public ResourceLocation id() {
		return this.id;
	}

	public boolean needsServer() {
		return this.needsServer;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (DebugFeature) obj;
		return Objects.equals(this.id, that.id) &&
				this.needsServer == that.needsServer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.needsServer);
	}

	@Override
	public String toString() {
		return "DebugFeature[" +
				"id=" + this.id + ", " +
				"needsServer=" + this.needsServer + ']';
	}

}
