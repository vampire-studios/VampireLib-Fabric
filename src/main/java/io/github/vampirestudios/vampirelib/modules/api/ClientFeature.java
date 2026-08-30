package io.github.vampirestudios.vampirelib.modules.api;

import net.minecraft.resources.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public abstract class ClientFeature extends Feature {

	public ClientFeature(Identifier registryName, String name) {
		super(registryName, name);
	}

	@Environment(EnvType.CLIENT)
	public abstract void initClient();

}
