package net.fabric.api.tag.convention.v1;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

public class ConventionalBlockTags extends net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags {

    public static final TagKey<Block> NORMAL_SANDSTONE_BLOCKS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("normal_sandstone_blocks");
    public static final TagKey<Block> RED_SANDSTONE_BLOCKS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("red_sandstone_blocks");
    public static final TagKey<Block> SANDSTONE_BLOCKS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("sandstone_blocks");
    public static final TagKey<Block> NORMAL_SANDSTONE_SLABS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("normal_sandstone_slabs");
    public static final TagKey<Block> RED_SANDSTONE_SLABS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("red_sandstone_slabs");
    public static final TagKey<Block> SANDSTONE_SLABS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("sandstone_slabs");
    public static final TagKey<Block> NORMAL_SANDSTONE_STAIRS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("normal_sandstone_stairs");
    public static final TagKey<Block> RED_SANDSTONE_STAIRS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("red_sandstone_stairs");
    public static final TagKey<Block> SANDSTONE_STAIRS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("sandstone_stairs");

    public static final TagKey<Block> VILLAGER_JOB_SITES = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("villager_job_sites");

}
