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