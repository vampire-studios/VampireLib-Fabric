package io.github.vampirestudios.vampirelib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * An item that causes a cooldown when it is used.
 */
public class CooldownItem extends Item {
	// Cooldown duration in ticks when this item is being used
	private final int duration;

	public CooldownItem(Settings settings, int duration) {
		super(settings);
		this.duration = duration;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.getItemCooldownManager().set(this, duration);
		return TypedActionResult.success(user.getStackInHand(hand));
	}
}