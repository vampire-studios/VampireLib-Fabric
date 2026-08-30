package io.github.vampirestudios.vampirelib.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import io.github.vampirestudios.vampirelib.modules.FeatureManager;

public class VRegistries {
	public static final Registry<FeatureManager> FEATURE_MANAGERS = FabricRegistryBuilder.create(
			FeatureManager.class, Identifier.fromNamespaceAndPath("vampirelib", "feature_managers")
	).buildAndRegister();
}
