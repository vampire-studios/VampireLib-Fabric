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

package io.github.vampirestudios.vampirelib.api.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

/**
 * Provider of {@link BlockPathTypes}.
 */
public interface PathNodeTypeProvider {
	/**
	 * Gets the {@link BlockPathTypes} for the specified position.
	 *
	 * <p>Is possible to specify what to return if the block is a direct target of an entity path,
	 * or is a neighbor block that the entity will find in the path.
	 *
	 * <p>For example, for cactus you should specify DAMAGE_CACTUS if the block is a direct target (isNeighbor = false)
	 * to specify that an entity should not pass through or above the block because it will cause damage,
	 * and DANGER_CACTUS if the cactus will be found as a neighbor block in the entity path (isNeighbor = true)
	 * to specify that the entity should not get close to the block because here is danger.
	 *
	 * @param state      Current block state.
	 * @param world      Current world.
	 * @param pos        Current position.
	 * @param isNeighbor Specifies if the block is not a directly targeted block, but a neighbor block in the path.
	 *
	 * @return returns an instance of ${@link BlockPathTypes}
	 */
	BlockPathTypes getPathNodeType(BlockState state, BlockGetter world, BlockPos pos, boolean isNeighbor);
}
