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

package io.github.vampirestudios.vampirelib.api.oreVeins;

import net.minecraft.world.level.levelgen.OreVeinifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class VOreVeinTypes {
	private static final Map<String, OreVeinifier.VeinType> NEW_VEIN_TYPES = new LinkedHashMap<>();

	public static void addVeinType(String id, OreVeinifier.VeinType veinType) {
		NEW_VEIN_TYPES.put(id, veinType);
	}

	public static OreVeinifier.VeinType getVeinType(String modId, String name) {
		return NEW_VEIN_TYPES.get(modId.toUpperCase() + name.toUpperCase());
	}
}
