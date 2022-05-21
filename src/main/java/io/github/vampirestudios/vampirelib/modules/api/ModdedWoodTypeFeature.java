package io.github.vampirestudios.vampirelib.modules.api;

import net.minecraft.resources.ResourceLocation;

import io.github.vampirestudios.vampirelib.utils.registry.WoodTypeRegistry;

public abstract class ModdedWoodTypeFeature extends CommonFeature implements WoodTypeRegistry.ModdedTypeListener {

	public ModdedWoodTypeFeature(ResourceLocation path, String name) {
		super(path, name);
		WoodTypeRegistry.registerModdedTypeListener(this);
	}

}