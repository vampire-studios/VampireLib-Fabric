package io.github.vampirestudios.vampirelib.utils;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

public record WoodMaterial(Identifier resourceLocation, WoodType woodType, Block leaves, Block log, boolean nether) {
	public WoodMaterial(String name, WoodType woodType, Block leaves, Block log, boolean nether) {
		this(Identifier.tryParse(name), woodType, leaves, log, nether);
	}

	public WoodMaterial(Identifier resourceLocation, WoodType woodType, Block leaves, Block log) {
		this(resourceLocation, woodType, leaves, log, false);
	}

	public WoodMaterial(Identifier resourceLocation, Block leaves, Block log) {
		this(resourceLocation, null, leaves, log, false);
	}

	public WoodMaterial(String name, WoodType woodType, Block leaves, Block log) {
		this(Identifier.tryParse(name), woodType, leaves, log, false);
	}
}
