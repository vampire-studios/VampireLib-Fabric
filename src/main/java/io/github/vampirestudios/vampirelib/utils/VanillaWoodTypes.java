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

import net.minecraft.world.level.block.Blocks;

import io.github.vampirestudios.vampirelib.utils.registry.WoodMaterialRegistry;
import net.minecraft.world.level.block.state.properties.WoodType;

public class VanillaWoodTypes {
	public static final WoodMaterial SPRUCE = WoodMaterialRegistry.registerVanilla(new WoodMaterial("spruce", WoodType.SPRUCE, Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG));
	public static final WoodMaterial OAK = WoodMaterialRegistry.registerVanilla(new WoodMaterial("oak", WoodType.OAK, Blocks.OAK_LEAVES, Blocks.OAK_LOG));
	public static final WoodMaterial BIRCH = WoodMaterialRegistry.registerVanilla(new WoodMaterial("birch", WoodType.BIRCH, Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
	public static final WoodMaterial JUNGLE = WoodMaterialRegistry.registerVanilla(new WoodMaterial("jungle", WoodType.JUNGLE, Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
	public static final WoodMaterial ACACIA = WoodMaterialRegistry.registerVanilla(new WoodMaterial("acacia", WoodType.ACACIA, Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
	public static final WoodMaterial DARK_OAK = WoodMaterialRegistry.registerVanilla(new WoodMaterial("dark_oak", WoodType.DARK_OAK, Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
	public static final WoodMaterial MANGROVE = WoodMaterialRegistry.registerVanilla(new WoodMaterial("mangrove", WoodType.MANGROVE, Blocks.MANGROVE_LEAVES, Blocks.MANGROVE_LOG));
	public static final WoodMaterial CHERRY = WoodMaterialRegistry.registerVanilla(new WoodMaterial("cherry", WoodType.CHERRY, Blocks.CHERRY_LEAVES, Blocks.CHERRY_LOG));
	public static final WoodMaterial BAMBOO = WoodMaterialRegistry.registerVanilla(new WoodMaterial("bamboo", WoodType.BAMBOO, null, Blocks.BAMBOO_BLOCK));

	public static final WoodMaterial CRIMSON = WoodMaterialRegistry.registerVanilla(new WoodMaterial("crimson", WoodType.CRIMSON, Blocks.NETHER_WART_BLOCK, Blocks.CRIMSON_STEM, true));
	public static final WoodMaterial WARPED = WoodMaterialRegistry.registerVanilla(new WoodMaterial("warped", WoodType.WARPED, Blocks.WARPED_WART_BLOCK, Blocks.WARPED_STEM, true));

	public static final WoodMaterial[] OVERWORLD_NON_OAK = new WoodMaterial[] {
			SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, MANGROVE, BAMBOO, CHERRY
	};

	public static final WoodMaterial[] OVERWORLD = new WoodMaterial[] {
			OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, MANGROVE, BAMBOO, CHERRY
	};

	public static final WoodMaterial[] NETHER = new WoodMaterial[] {
			CRIMSON, WARPED
	};

	public static final WoodMaterial[] ALL = new WoodMaterial[] {
			OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, CRIMSON, WARPED, MANGROVE, BAMBOO, CHERRY
	};

	public static final WoodMaterial[] NON_OAK = new WoodMaterial[] {
			SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, CRIMSON, WARPED, MANGROVE, BAMBOO, CHERRY
	};
}
