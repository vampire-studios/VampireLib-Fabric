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

import java.util.HashMap;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

/**
 * A registry to associate specific node types to blocks.
 * Specifying a node type for a block will change the way an entity recognizes the block when trying to pathfinding.
 * For example, you can specify that a block is dangerous and should be avoided by entities.
 * This works only for entities that move on air and land.
 */
public final class LandPathNodeTypesRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(LandPathNodeTypesRegistry.class);
	private static final HashMap<Block, PathNodeTypeProvider> NODE_TYPES = new HashMap<>();

	private LandPathNodeTypesRegistry() {
	}

	/**
	 * Registers a {@link BlockPathTypes} for the specified block.
	 * This will override the default block behaviour.
	 * For example, you can make a safe block as dangerous and vice-versa.
	 * Duplicated registrations for the same block will replace the previous registration.
	 *
	 * @param block              Block to register.
	 * @param nodeType           {@link BlockPathTypes} to associate to the block.
	 *                           (Set null to not specify a node type and use the default behaviour)
	 * @param nodeTypeAsNeighbor {@link BlockPathTypes} to associate to the block, if is a neighbor block in the path.
	 *                           (Set null to not specify a node type and use the default behaviour)
	 */
	public static void register(Block block, BlockPathTypes nodeType, BlockPathTypes nodeTypeAsNeighbor) {
		Objects.requireNonNull(block, "Block cannot be null!");

		//Registers a provider that always returns the specified node type.
		register(block, (state, world, pos, isNeighbor) -> isNeighbor ? nodeTypeAsNeighbor : nodeType);
	}

	/**
	 * Registers a {@link PathNodeTypeProvider} for the specified block.
	 * This will override the default block behaviour.
	 * For example, you can make a safe block as dangerous and vice-versa.
	 * Duplicated registrations for the same block will replace the previous registrations.
	 *
	 * @param block    Block to register.
	 * @param provider {@link PathNodeTypeProvider} to associate to the block.
	 */
	public static void register(Block block, PathNodeTypeProvider provider) {
		Objects.requireNonNull(block, "Block cannot be null!");
		Objects.requireNonNull(provider, "PathNodeTypeProvider cannot be null!");

		//Registers the provider.
		PathNodeTypeProvider old = NODE_TYPES.put(block, provider);

		if (old != null) {
			LOGGER.debug("Replaced PathNodeType provider for the block {}", block);
		}
	}

	/**
	 * Gets the {@link BlockPathTypes} for the specified position.
	 *
	 * @param state      Current block state.
	 * @param world      Current world.
	 * @param pos        Current position.
	 * @param isNeighbor Specifies if the block is not a directly targeted block, but a neighbor block in the path.
	 *
	 * @return returns null if the provider is not null else it gets the ${@link BlockPathTypes} from the provider
	 */
	@Nullable
	public static BlockPathTypes getPathNodeType(BlockState state, BlockGetter world, BlockPos pos, boolean isNeighbor) {
		Objects.requireNonNull(state, "BlockState cannot be null!");
		Objects.requireNonNull(world, "BlockView cannot be null!");
		Objects.requireNonNull(pos, "BlockPos cannot be null!");

		//Gets the node type for the block in the specified position.
		PathNodeTypeProvider provider = NODE_TYPES.get(state.getBlock());
		return provider != null ? provider.getPathNodeType(state, world, pos, isNeighbor) : null;
	}
}
