package io.github.vampirestudios.vampirelib.impl.boat;

import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType;
import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public final class TerraformBoatTrackedData {
	private TerraformBoatTrackedData() {
		return;
	}

	public static final EntityDataSerializer<TerraformBoatType> HANDLER = new EntityDataSerializer<>() {
		public void write(FriendlyByteBuf buf, TerraformBoatType boat) {
			buf.writeResourceLocation(TerraformBoatTypeRegistry.INSTANCE.getKey(boat));
		}

		public TerraformBoatType read(FriendlyByteBuf buf) {
			return TerraformBoatTypeRegistry.INSTANCE.get(buf.readResourceLocation());
		}

		public TerraformBoatType copy(TerraformBoatType boat) {
			return boat;
		}
	};

	protected static void register() {
		EntityDataSerializers.registerSerializer(HANDLER);
	}
}
