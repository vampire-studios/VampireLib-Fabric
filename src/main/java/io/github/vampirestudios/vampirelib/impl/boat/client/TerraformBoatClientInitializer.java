package io.github.vampirestudios.vampirelib.impl.boat.client;

import io.github.vampirestudios.vampirelib.impl.boat.TerraformBoatInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public final class TerraformBoatClientInitializer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(TerraformBoatInitializer.BOAT, TerraformBoatEntityRenderer::new);
	}
}
