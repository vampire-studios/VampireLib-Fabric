package io.github.vampirestudios.vampirelib.modules.api;

import net.minecraft.resources.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public abstract class ServerFeature extends Feature {

	public ServerFeature(Identifier registryName, String name) {
		super(registryName, name);
	}

	@Environment(EnvType.SERVER)
	public abstract void initServer();

}
