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

package io.github.vampirestudios.vampirelib.utils.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WoodType {
	public static final WoodType SPRUCE = WoodTypeRegistry.registerVanilla(
			new WoodType("spruce", Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG));
	public static final WoodType OAK = WoodTypeRegistry.registerVanilla(
			new WoodType("oak", Blocks.OAK_LEAVES, Blocks.OAK_LOG));
	public static final WoodType BIRCH = WoodTypeRegistry.registerVanilla(
			new WoodType("birch", Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
	public static final WoodType JUNGLE = WoodTypeRegistry.registerVanilla(
			new WoodType("jungle", Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
	public static final WoodType ACACIA = WoodTypeRegistry.registerVanilla(
			new WoodType("acacia", Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
	public static final WoodType DARK_OAK = WoodTypeRegistry.registerVanilla(
			new WoodType("dark_oak", Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
	public static final WoodType CRIMSON = WoodTypeRegistry.registerVanilla(
			new WoodType("crimson", Blocks.NETHER_WART_BLOCK, Blocks.CRIMSON_STEM));
	public static final WoodType WARPED = WoodTypeRegistry.registerVanilla(
			new WoodType("warped", Blocks.WARPED_WART_BLOCK, Blocks.WARPED_STEM));
	public static final WoodType[] VANILLA = new WoodType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, CRIMSON, WARPED};
	public final Block leaves;
	public final Block log;
	public final ResourceLocation identifier;

	public WoodType(String name, Block leaves, Block log) {
		this(new ResourceLocation(name), leaves, log);
	}

	public WoodType(ResourceLocation identifier, Block leaves, Block log) {
		this.identifier = identifier;
		this.leaves = leaves;
		this.log = log;
	}

}
