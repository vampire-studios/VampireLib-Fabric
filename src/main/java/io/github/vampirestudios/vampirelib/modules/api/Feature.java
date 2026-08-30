package io.github.vampirestudios.vampirelib.modules.api;

import net.minecraft.resources.Identifier;

public abstract class Feature {

	private final Identifier registryName;
	private final String name;
	private boolean enabled = true;

	public Feature(Identifier registryName, String name) {
		this.registryName = registryName;
		this.name = name;
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Identifier getRegistryName() {
		return registryName;
	}

	public String getName() {
		return name;
	}

}
