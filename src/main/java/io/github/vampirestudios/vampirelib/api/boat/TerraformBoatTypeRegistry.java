package io.github.vampirestudios.vampirelib.api.boat;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * @see TerraformBoatTypeRegistry#INSTANCE
 */
public class TerraformBoatTypeRegistry {
	private static final ResourceLocation REGISTRY_ID = new ResourceLocation("terraform", "boat");
	private static final ResourceKey<Registry<TerraformBoatType>> REGISTRY_KEY = ResourceKey.createRegistryKey(REGISTRY_ID);

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
	 * @see TerraformBoatType.Builder The builder for boat types
	 * @see io.github.vampirestudios.vampirelib.api.boat.client.TerraformBoatClientHelper Helpers for registering the boat on the client
	 */
	public static final Registry<TerraformBoatType> INSTANCE = new MappedRegistry<>(REGISTRY_KEY, Lifecycle.stable());
}
