package io.github.vampirestudios.vampirelib.modules;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import io.github.vampirestudios.vampirelib.init.VRegistries;
import io.github.vampirestudios.vampirelib.modules.api.ClientFeature;
import io.github.vampirestudios.vampirelib.modules.api.CommonFeature;
import io.github.vampirestudios.vampirelib.modules.api.Feature;
import io.github.vampirestudios.vampirelib.modules.api.ServerFeature;
import io.github.vampirestudios.vampirelib.modules.utils.ConsoleUtils;

public final class FeatureManager {

	public static final Registry<CommonFeature> COMMON_FEATURES = FabricRegistryBuilder.create(
		CommonFeature.class,
		Identifier.parse("vampirelib:common_features")
	).buildAndRegister();

	public static final Registry<ClientFeature> CLIENT_FEATURES = FabricRegistryBuilder.create(
		ClientFeature.class,
		Identifier.parse("vampirelib:client_features")
	).buildAndRegister();

	public static final Registry<ServerFeature> SERVER_FEATURES = FabricRegistryBuilder.create(
		ServerFeature.class,
		Identifier.parse("vampirelib:server_features")
	).buildAndRegister();

	private final Identifier modIdentifier;

	private final Set<Identifier> initializedCommonFeatures = new HashSet<>();
	private final Set<Identifier> initializedClientFeatures = new HashSet<>();
	private final Set<Identifier> initializedServerFeatures = new HashSet<>();

	private FeatureManager(Identifier modIdentifier) {
		this.modIdentifier = modIdentifier;
	}

	public static FeatureManager createFeatureManager(Identifier modIdentifier) {
		Objects.requireNonNull(modIdentifier, "modIdentifier");

		FeatureManager manager = new FeatureManager(modIdentifier);
		return Registry.register(VRegistries.FEATURE_MANAGERS, modIdentifier, manager);
	}

	public static FeatureManager getFeatureManager(Identifier modIdentifier) {
		FeatureManager manager = VRegistries.FEATURE_MANAGERS.getValue(modIdentifier);

		if (manager == null) {
			throw new IllegalArgumentException("No feature manager registered for " + modIdentifier);
		}

		return manager;
	}

	public Identifier getModIdentifier() {
		return this.modIdentifier;
	}

	public String getNamespace() {
		return this.modIdentifier.getNamespace();
	}

	public void registerCommonFeature(CommonFeature feature) {
		registerFeature(COMMON_FEATURES, feature);
	}

	@Environment(EnvType.CLIENT)
	public void registerClientFeature(ClientFeature feature) {
		registerFeature(CLIENT_FEATURES, feature);
	}

	public void registerServerFeature(ServerFeature feature) {
		registerFeature(SERVER_FEATURES, feature);
	}

	public void initCommonFeature(CommonFeature feature) {
		if (!belongsToManager(feature)) {
			throw createWrongNamespaceException(feature);
		}

		Identifier id = feature.getRegistryName();

		if (this.initializedCommonFeatures.add(id)) {
			feature.initCommon();
		}
	}

	public void initCommonFeature(CommonFeature... features) {
		for (CommonFeature feature : features) {
			initCommonFeature(feature);
		}
	}

	public void initCommon() {
		for (CommonFeature feature : COMMON_FEATURES) {
			if (belongsToManager(feature)) {
				initCommonFeature(feature);
			}
		}

		ConsoleUtils.logCommonFeatures();
	}

	@Environment(EnvType.CLIENT)
	public void initClientFeature(ClientFeature feature) {
		if (!belongsToManager(feature)) {
			throw createWrongNamespaceException(feature);
		}

		Identifier id = feature.getRegistryName();

		if (this.initializedClientFeatures.add(id)) {
			feature.initClient();
		}
	}

	@Environment(EnvType.CLIENT)
	public void initClientFeature(ClientFeature... features) {
		for (ClientFeature feature : features) {
			initClientFeature(feature);
		}
	}

	@Environment(EnvType.CLIENT)
	public void initClient() {
		for (ClientFeature feature : CLIENT_FEATURES) {
			if (belongsToManager(feature)) {
				initClientFeature(feature);
			}
		}

		ConsoleUtils.logClientFeatures();
	}

	public void initServerFeature(ServerFeature feature) {
		if (!belongsToManager(feature)) {
			throw createWrongNamespaceException(feature);
		}

		Identifier id = feature.getRegistryName();

		if (this.initializedServerFeatures.add(id)) {
			feature.initServer();
		}
	}

	public void initServerFeature(ServerFeature... features) {
		for (ServerFeature feature : features) {
			initServerFeature(feature);
		}
	}

	public void initServer() {
		for (ServerFeature feature : SERVER_FEATURES) {
			if (belongsToManager(feature)) {
				initServerFeature(feature);
			}
		}

		ConsoleUtils.logServerFeatures();
	}

	public boolean doesCommonFeatureExist(Identifier id) {
		return COMMON_FEATURES.containsKey(id);
	}

	public boolean doesClientFeatureExist(Identifier id) {
		return CLIENT_FEATURES.containsKey(id);
	}

	public boolean doesServerFeatureExist(Identifier id) {
		return SERVER_FEATURES.containsKey(id);
	}

	public boolean doesCommonFeatureExist(CommonFeature feature) {
		return doesCommonFeatureExist(feature.getRegistryName());
	}

	public boolean doesClientFeatureExist(ClientFeature feature) {
		return doesClientFeatureExist(feature.getRegistryName());
	}

	public boolean doesServerFeatureExist(ServerFeature feature) {
		return doesServerFeatureExist(feature.getRegistryName());
	}

	public boolean isCommonFeatureEnabled(Identifier id) {
		CommonFeature feature = COMMON_FEATURES.getValue(id);
		return feature != null && feature.isEnabled();
	}

	public boolean isClientFeatureEnabled(Identifier id) {
		ClientFeature feature = CLIENT_FEATURES.getValue(id);
		return feature != null && feature.isEnabled();
	}

	public boolean isServerFeatureEnabled(Identifier id) {
		ServerFeature feature = SERVER_FEATURES.getValue(id);
		return feature != null && feature.isEnabled();
	}

	public boolean isFeatureEnabled(Identifier id) {
		Feature feature = COMMON_FEATURES.getValue(id);

		if (feature == null) {
			feature = CLIENT_FEATURES.getValue(id);
		}

		if (feature == null) {
			feature = SERVER_FEATURES.getValue(id);
		}

		return feature != null && feature.isEnabled();
	}

	private <T extends Feature> void registerFeature(Registry<T> registry, T feature) {
		Objects.requireNonNull(feature, "feature");

		if (!belongsToManager(feature)) {
			throw createWrongNamespaceException(feature);
		}

		Identifier id = feature.getRegistryName();
		T existing = registry.getValue(id);

		if (existing != null) {
			if (existing == feature) {
				return;
			}

			throw new IllegalStateException(
				"Feature " + id + " is already registered with a different instance"
			);
		}

		Registry.register(registry, id, feature);
	}

	private boolean belongsToManager(Feature feature) {
		return feature.getRegistryName().getNamespace().equals(this.getNamespace());
	}

	private IllegalArgumentException createWrongNamespaceException(Feature feature) {
		return new IllegalArgumentException(
			"Feature " + feature.getRegistryName()
				+ " does not belong to feature manager "
				+ this.modIdentifier
		);
	}
}
