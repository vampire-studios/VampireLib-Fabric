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

package io.github.vampirestudios.vampirelib.utils;

import org.jetbrains.annotations.Nullable;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;

import io.github.vampirestudios.vampirelib.init.VTags;

public class TagUtil {
	@Nullable
	public static DyeColor getColorFromStack(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof DyeItem dyeItem) {
			return dyeItem.getDyeColor();
		}

		for (DyeColor color : DyeColor.values()) {
			if (stack.is(color.getTag())) return color;
		}

		return null;
	}

	public static TagKey<Block> getTagFromVanillaTier(Tiers tier) {
		return switch (tier) {
			case WOOD -> VTags.Blocks.NEEDS_WOOD_TOOL;
			case GOLD -> VTags.Blocks.NEEDS_GOLD_TOOL;
			case STONE -> BlockTags.NEEDS_STONE_TOOL;
			case IRON -> BlockTags.NEEDS_IRON_TOOL;
			case DIAMOND -> BlockTags.NEEDS_DIAMOND_TOOL;
			case NETHERITE -> VTags.Blocks.NEEDS_NETHERITE_TOOL;
		};
	}

	public static TagKey<Block> getTagFromTier(Tier tier) {
		return tier.getTag();
	}
}
