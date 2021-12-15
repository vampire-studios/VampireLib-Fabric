package io.github.vampirestudios.vampirelib.impl.boat;

import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType;
import net.minecraft.world.item.Item;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final Item item;

	public TerraformBoatTypeImpl(Item item) {
		this.item = item;
	}

	@Override
	public Item getItem() {
		return this.item;
	}
}
