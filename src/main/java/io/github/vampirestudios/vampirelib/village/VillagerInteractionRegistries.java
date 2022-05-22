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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import io.github.vampirestudios.vampirelib.mixins.entity.FarmerWorkTaskAccessor;
import io.github.vampirestudios.vampirelib.mixins.entity.GiveGiftsToHeroTaskAccessor;
import io.github.vampirestudios.vampirelib.mixins.entity.VillagerEntityAccessor;

/**
 * Registries for modifying villager interactions that
 * villagers have with the world.
 * The registries are:
 *
 * @see VillagerPlantableRegistry for registering plants that farmers can plant
 */
public class VillagerInteractionRegistries {
	private static final Logger LOGGER = LoggerFactory.getLogger(VillagerInteractionRegistries.class);

	/**
	 * Registers an item to be collectable (picked up from item entity)
	 * by any profession villagers.
	 *
	 * @param item the item to register
	 */
	public static void registerCollectable(ItemLike item) {
		Objects.requireNonNull(item, "Item cannot be null!");
		getCollectableRegistry().add(item.asItem());
	}

	/**
	 * Registers an item to be use in a composter by farmer villagers.
	 * @param item the item to register
	 */
	public static void registerCompostable(ItemLike item) {
		Objects.requireNonNull(item, "Item cannot be null!");
		getCompostableRegistry().add(item.asItem());
	}

	/**
	 * Registers an item to be edible by villagers.
	 * @param item      the item to register
	 * @param foodValue the amount of breeding power the item has (1 = normal food item, 4 = bread)
	 */
	public static void registerFood(ItemLike item, int foodValue) {
		Objects.requireNonNull(item, "Item cannot be null!");
		Objects.requireNonNull(item, "Food value cannot be null!");
		Integer oldValue = getFoodRegistry().put(item.asItem(), foodValue);

		if (oldValue != null) {
			LOGGER.info("Overriding previous food value of {}, was: {}, now: {}", item.asItem().toString(), oldValue, foodValue);
		}
	}

	/**
	 * Registers a hero of the village gifts loot table to a profession.
	 * @param profession the profession to modify
	 * @param lootTable  the loot table to associate with the profession
	 */
	public static void registerGiftLootTable(VillagerProfession profession, ResourceLocation lootTable) {
		Objects.requireNonNull(profession, "Profession cannot be null!");
		ResourceLocation oldValue = GiveGiftsToHeroTaskAccessor.getGifts().put(profession, lootTable);

		if (oldValue != null) {
			LOGGER.info("Overriding previous gift loot table of {} profession, was: {}, now: {}", profession.getName(), oldValue, lootTable);
		}
	}

	private static Set<Item> getCollectableRegistry() {
		return ImmutableCollectionUtils.getAsMutableSet(VillagerEntityAccessor::getGatherableItems, VillagerEntityAccessor::setGatherableItems);
	}

	private static List<Item> getCompostableRegistry() {
		return ImmutableCollectionUtils.getAsMutableList(FarmerWorkTaskAccessor::getCompostables, FarmerWorkTaskAccessor::setCompostables);
	}

	private static Map<Item, Integer> getFoodRegistry() {
		return ImmutableCollectionUtils.getAsMutableMap(() -> Villager.FOOD_POINTS, VillagerEntityAccessor::setItemFoodValues);
	}
}