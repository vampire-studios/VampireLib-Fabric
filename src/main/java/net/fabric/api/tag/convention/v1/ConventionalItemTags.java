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
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

public class ConventionalItemTags {

    public static final TagKey<Item> NORMAL_SANDSTONE_BLOCKS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("normal_sandstone_blocks");
    public static final TagKey<Item> RED_SANDSTONE_BLOCKS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("red_sandstone_blocks");
    public static final TagKey<Item> SANDSTONE_BLOCKS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("sandstone_blocks");
    public static final TagKey<Item> NORMAL_SANDSTONE_SLABS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("normal_sandstone_slabs");
    public static final TagKey<Item> RED_SANDSTONE_SLABS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("red_sandstone_slabs");
    public static final TagKey<Item> SANDSTONE_SLABS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("sandstone_slabs");
    public static final TagKey<Item> NORMAL_SANDSTONE_STAIRS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("normal_sandstone_stairs");
    public static final TagKey<Item> RED_SANDSTONE_STAIRS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("red_sandstone_stairs");
    public static final TagKey<Item> SANDSTONE_STAIRS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("sandstone_stairs");

    public static final TagKey<Item> VILLAGER_JOB_SITES = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("villager_job_sites");

    // Note: FISH is not here because it is covered by minecraft:item/fishes
    // Foods
    public static final TagKey<Item> MEAT = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("meat");
    public static final TagKey<Item> RAW_MEAT = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("raw_meat");
    public static final TagKey<Item> COOKED_MEAT = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("cooked_meat");
    public static final TagKey<Item> RAW_FISH = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("raw_fish");
    public static final TagKey<Item> COOKED_FISH = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("cooked_fish");
    public static final TagKey<Item> POISONOUS_FOOD = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("poisonous_food");

}
