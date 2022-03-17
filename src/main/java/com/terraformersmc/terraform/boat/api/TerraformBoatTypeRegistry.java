package com.terraformersmc.terraform.boat.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

/**
 * @see TerraformBoatTypeRegistry#INSTANCE
 */
public class TerraformBoatTypeRegistry {
	private static final ResourceLocation REGISTRY_ID = new ResourceLocation("terraform", "boat");

	/**
	 * The registry for {@linkplain TerraformBoatType Terraform boats}.
	 * 
	 * 
	 * <p>To register a boat type:
	 * 
	 * <pre>{@code
	 *     Registry.register(TerraformBoatType.REGISTRY, new Identifier("examplemod", "mahogany"), boat);
	 * }</pre>
	 * 
	 * @see com.terraformersmc.terraform.boat.api.TerraformBoatType.Builder The builder for boat types
	 * @see com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper Helpers for registering the boat on the client
	 */
	public static final Registry<TerraformBoatType> INSTANCE = FabricRegistryBuilder.createSimple(TerraformBoatType.class, REGISTRY_ID).buildAndRegister();
}
