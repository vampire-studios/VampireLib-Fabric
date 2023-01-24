/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

public class ConventionalBlockTags {

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
