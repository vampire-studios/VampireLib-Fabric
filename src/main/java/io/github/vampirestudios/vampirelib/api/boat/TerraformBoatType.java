package io.github.vampirestudios.vampirelib.api.boat;

import io.github.vampirestudios.vampirelib.impl.boat.TerraformBoatEntity;
import io.github.vampirestudios.vampirelib.impl.boat.TerraformBoatTypeImpl;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;

/**
 * An interface representing a Terraform boat.
 */
public interface TerraformBoatType {
	/**
	 * {@return the {@linkplain Boat#getPickResult()} () pick stack} and {@linkplain Item item} dropped when the {@linkplain TerraformBoatEntity boat entity} is broken}
	 */
	Item getItem();

	/**
	 * A builder for {@linkplain TerraformBoatType Terraform boat types}.
	 *
	 * <p>To build a Terraform boat type:
	 *
	 * <pre>{@code
	 *     TerraformBoatType boat = new TerraformBoatType.Builder()
	 *         .item(ExampleModItems.MAHOGANY_BOAT)
	 *         .build();
	 * }</pre>
	 */
	public static class Builder {
		private Item item;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.item);
		}

		/**
		 * @see TerraformBoatType#getItem
		 */
		public Builder item(Item item) {
			this.item = item;
			return this;
		}
	}
}
