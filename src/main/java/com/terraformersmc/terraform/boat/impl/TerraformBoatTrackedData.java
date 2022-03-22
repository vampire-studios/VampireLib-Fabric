package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public final class TerraformBoatTrackedData {
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

	static void register() {
		EntityDataSerializers.registerSerializer(HANDLER);
	}
}