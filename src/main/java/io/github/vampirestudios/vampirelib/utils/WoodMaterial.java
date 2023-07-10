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

package io.github.vampirestudios.vampirelib.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

public record WoodMaterial(ResourceLocation resourceLocation, WoodType woodType, Block leaves, Block log, boolean nether) {
	public WoodMaterial(String name, WoodType woodType, Block leaves, Block log, boolean nether) {
		this(ResourceLocation.tryParse(name), woodType, leaves, log, nether);
	}

	public WoodMaterial(ResourceLocation resourceLocation, WoodType woodType, Block leaves, Block log) {
		this(resourceLocation, woodType, leaves, log, false);
	}

	public WoodMaterial(ResourceLocation resourceLocation, Block leaves, Block log) {
		this(resourceLocation, null, leaves, log, false);
	}

	public WoodMaterial(String name, WoodType woodType, Block leaves, Block log) {
		this(ResourceLocation.tryParse(name), woodType, leaves, log, false);
	}
}
