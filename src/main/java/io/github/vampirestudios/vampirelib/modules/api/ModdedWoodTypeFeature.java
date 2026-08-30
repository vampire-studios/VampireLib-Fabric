package io.github.vampirestudios.vampirelib.modules.api;

import net.minecraft.resources.Identifier;

import io.github.vampirestudios.vampirelib.utils.registry.WoodMaterialRegistry;

public abstract class ModdedWoodTypeFeature extends CommonFeature implements WoodMaterialRegistry.ModdedTypeListener {

	public ModdedWoodTypeFeature(Identifier path, String name) {
		super(path, name);
		WoodMaterialRegistry.registerModdedTypeListener(this);
	}

}
