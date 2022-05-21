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

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VillagerPlantableRegistryImpl implements VillagerPlantableRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(VillagerPlantableRegistryImpl.class);

	private final HashMap<Item, BlockState> plantables = new HashMap<>();

	public VillagerPlantableRegistryImpl() {
		register(Items.WHEAT_SEEDS);
		register(Items.POTATO);
		register(Items.CARROT);
		register(Items.BEETROOT_SEEDS);
	}

	@Override
	public void register(ItemLike item) {
		if (!(item.asItem() instanceof BlockItem)) {
			throw new IllegalArgumentException("item is not a BlockItem");
		}

		this.register(item, ((BlockItem) item.asItem()).getBlock().defaultBlockState());
	}

	@Override
	public void register(ItemLike item, BlockState plantState) {
		this.plantables.put(item.asItem(), plantState);

		if (!(plantState.getBlock() instanceof CropBlock)) {
			LOGGER.info("Registered a block ({}) that does not extend CropBlock, this block will not be villager harvestable by default.", Registry.BLOCK.getKey(plantState.getBlock()));
		}
	}

	@Override
	public boolean contains(ItemLike item) {
		return this.plantables.containsKey(item.asItem());
	}

	@Override
	public BlockState getPlantState(ItemLike item) {
		return this.plantables.get(item.asItem());
	}

	@Override
	public Set<Item> getItems() {
		return this.plantables.keySet();
	}
}