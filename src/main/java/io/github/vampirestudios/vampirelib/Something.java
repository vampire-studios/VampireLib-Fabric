package io.github.vampirestudios.vampirelib;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import io.github.vampirestudios.vampirelib.modules.FeatureManager;

public class Something {
    public static final Registry<FeatureManager> FEATURE_MANAGERS = FabricRegistryBuilder.createSimple(FeatureManager.class, new ResourceLocation("vampirelib:feature_managers")).buildAndRegister();
}
