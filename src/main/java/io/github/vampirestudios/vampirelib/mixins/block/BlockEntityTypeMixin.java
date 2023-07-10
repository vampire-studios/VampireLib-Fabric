/*
 * Copyright (c) 2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.mixins.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import io.github.vampirestudios.vampirelib.blocks.entity.IBlockEntityType;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements IBlockEntityType {
	@Shadow
	@Final
	@Mutable
	private Set<Block> validBlocks;

	@Override
	public void vlAddBlocks(Block... newBlocks) {
		ArrayList<Block> tempList = new ArrayList<>(newBlocks.length + validBlocks.size());
		tempList.addAll(Arrays.asList(newBlocks));
		tempList.addAll(validBlocks);
		validBlocks = ImmutableSet.copyOf(tempList);
	}
}
