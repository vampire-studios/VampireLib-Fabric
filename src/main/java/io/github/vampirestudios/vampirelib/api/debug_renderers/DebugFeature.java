package io.github.vampirestudios.vampirelib.api.debug_renderers;

import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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
