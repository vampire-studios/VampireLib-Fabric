package net.fabric.api.tag.convention.v1;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

public class ConventionalItemTags extends net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags {

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

}
