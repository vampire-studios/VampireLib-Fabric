package io.github.vampirestudios.vampirelib.modules.api;

import net.minecraft.resources.Identifier;

public abstract class CommonFeature extends Feature {

	public CommonFeature(Identifier registryName, String name) {
		super(registryName, name);
	}

	public abstract void initCommon();

}
