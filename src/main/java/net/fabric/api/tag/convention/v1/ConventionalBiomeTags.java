/*
 * Copyright (c) 2022 OliviaTheVampire
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

package net.fabric.api.tag.convention.v1;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

public class ConventionalBiomeTags {
	/**
	 * For water biomes where ice naturally spawns.
	 * For biomes where snow alone spawns, see {@link net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#SNOWY}.
	 */
	public static final TagKey<Biome> AQUATIC_ICY = TagRegistration.BIOME_TAG_REGISTRATION.registerCommon("aquatic_icy");
}
