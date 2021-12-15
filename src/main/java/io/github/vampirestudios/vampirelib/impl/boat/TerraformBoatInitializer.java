package io.github.vampirestudios.vampirelib.impl.boat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class TerraformBoatInitializer implements ModInitializer {
	private static final ResourceLocation BOAT_ID = new ResourceLocation("vampirelib", "boat");
	public static final EntityType<TerraformBoatEntity> BOAT = FabricEntityTypeBuilder.<TerraformBoatEntity>create(MobCategory.MISC, TerraformBoatEntity::new)
		.dimensions(EntityDimensions.fixed(1.375f, 0.5625f))
		.build();

	@Override
	public void onInitialize() {
		TerraformBoatTrackedData.register();
		Registry.register(Registry.ENTITY_TYPE, BOAT_ID, BOAT);
	}
}