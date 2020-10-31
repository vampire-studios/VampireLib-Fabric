/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * An example item for something that has two storage tanks (one for water, one for lava in this example),
 * 1000 units each.
 *
 * <p>Right-clicking with the item in hand will cycle through the water level, while holding shift, it will cycle
 * through the lava level.
 */
public class WaterLavaBucket extends Item {
	private static final String TAG_WATER = "waterUnits";
	private static final String TAG_LAVA = "lavaUnits";
	private static final int CAPACITY = 1000;

	public WaterLavaBucket(Settings settings) {
		super(settings);
	}

	public float getWaterFillLevel(ItemStack stack) {
		CompoundTag tag = stack.getTag();

		if (tag == null) {
			return 0;
		}

		return tag.getInt(TAG_WATER) / (float) CAPACITY;
	}

	public float getLavaFillLevel(ItemStack stack) {
		CompoundTag tag = stack.getTag();

		if (tag == null) {
			return 0;
		}

		return tag.getInt(TAG_LAVA) / (float) CAPACITY;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		String propName = user.isInSneakingPose() ? TAG_LAVA : TAG_WATER;

		ItemStack item = user.getStackInHand(hand).copy();

		CompoundTag tag = item.getOrCreateTag();
		int current = tag.getInt(propName);

		if (current >= CAPACITY) {
			current = 0; // cycle back to 0
		} else {
			current = Math.min(CAPACITY, current + CAPACITY / 5);
		}

		tag.putInt(propName, current);

		return TypedActionResult.success(item);
	}
}