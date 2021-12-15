package io.github.vampirestudios.vampirelib.api.boat.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
/*
  This class provides useful helpers for registering a {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType Terraform boat} on the client.
 */
public final class TerraformBoatClientHelper {
	private TerraformBoatClientHelper() {}

	/**
	 * Gets the identifier of a {@linkplain LayerDefinition model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.resources.ResourceLocation identifier} of the {@linkplain io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType boat}
	 */
	private static ResourceLocation getLayerId(ResourceLocation boatId) {
		return new ResourceLocation(boatId.getNamespace(), "boat/" + boatId.getPath());
	}

	/**
	 * Creates a {@linkplain LayerDefinition model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.resources.ResourceLocation identifier} of the {@linkplain io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType boat}
	 *
	 * <pre>{@code
	 *     EntityModelLayer layer = TerraformBoatClientHelper.getLayer(new Identifier("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static ModelLayerLocation getLayer(ResourceLocation boatId) {
		return new ModelLayerLocation(getLayerId(boatId), "main");
	}

	/**
	 * Registers a {@linkplain LayerDefinition model layer} for a boat of a given type.
	 * @param boatId the {@linkplain net.minecraft.resources.ResourceLocation identifier} of the {@linkplain io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType boat}
	 *
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayer(new Identifier("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static void registerModelLayer(ResourceLocation boatId) {
		EntityModelLayerRegistry.registerModelLayer(getLayer(boatId), BoatModel::createBodyModel);
	}
}
