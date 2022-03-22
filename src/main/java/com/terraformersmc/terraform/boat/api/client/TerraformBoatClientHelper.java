package com.terraformersmc.terraform.boat.api.client;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;

@Environment(EnvType.CLIENT)
@SuppressWarnings("deprecation")
/*
 * This class provides useful helpers for registering a {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType Terraform boat} on the client.
 */
public final class TerraformBoatClientHelper {
	/**
	 * Gets the identifier of a {@linkplain ModelLayerLocation model layer} for a boat of a given type.
	 * @param boatId the {@linkplain ResourceLocation identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 */
	private static ResourceLocation getLayerId(ResourceLocation boatId) {
		return new ResourceLocation(boatId.getNamespace(), "boat/" + boatId.getPath());
	}

	/**
	 * Creates a {@linkplain ModelLayerLocation model layer} for a boat of a given type.
	 * @param boatId the {@linkplain ResourceLocation identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * 
	 * <pre>{@code
	 *     EntityModelLayer layer = TerraformBoatClientHelper.getLayer(new ResourceLocation("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static ModelLayerLocation getLayer(ResourceLocation boatId) {
		return new ModelLayerLocation(getLayerId(boatId), "main");
	}

	/**
	 * Registers a {@linkplain ModelLayerLocation model layer} for a boat of a given type.
	 * @param boatId the {@linkplain ResourceLocation identifier} of the {@linkplain com.terraformersmc.terraform.boat.api.TerraformBoatType boat}
	 * 
	 * <pre>{@code
	 *     TerraformBoatClientHelper.registerModelLayer(new Identifier("examplemod", "mahogany"));
	 * }</pre>
	 */
	public static void registerModelLayer(ResourceLocation boatId) {
		EntityModelLayerRegistry.registerModelLayer(getLayer(boatId), BoatModel::createBodyModel);
	}
}