/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vampirestudios.vampirelib.village;

import java.util.Set;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Registry of items that farmer villagers can plant on farmland.
 */
public interface VillagerPlantableRegistry {
	VillagerPlantableRegistry INSTANCE = new VillagerPlantableRegistryImpl();

	/**
	 * Registers a BlockItem to be plantable by farmer villagers.
	 * This will use the default state of the associated block.
	 * @param item the BlockItem to register
	 */
	void register(ItemLike item);

	/**
	 * Register an item with an associated to be plantable by farmer villagers.
	 * @param item       the seed item
	 * @param plantState the state that will be planted
	 */
	void register(ItemLike item, BlockState plantState);

	/**
	 * Tests if the item is a registered seed item.
	 * @param item the item to test
	 * @return true if the item is registered as a seed
	 */
	boolean contains(ItemLike item);

	/**
	 * Get the state that is associated with the provided seed item.
	 * @param item the seed item
	 * @return the state associated with the seed item
	 */
	BlockState getPlantState(ItemLike item);

	/**
	 * Get all currently registered seed items.
	 * @return all currently registered seed items.
	 */
	Set<Item> getItems();
}