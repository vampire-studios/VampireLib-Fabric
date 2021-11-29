package io.github.vampirestudios.vampirelib.api;

import java.util.Locale;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import io.github.vampirestudios.vampirelib.modules.FeatureManager;
import io.github.vampirestudios.vampirelib.utils.Rands;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryHelper;

public abstract class BasicModClass implements ModInitializer, ClientModInitializer {

	private final String modId;
	private final String modName;
	private final String modVersion;

	private boolean printVersionMessage = true;

	private static RegistryHelper REGISTRY_HELPER;
	private static FeatureManager FEATURE_MANAGER;
	private ConfigHolder<? extends CustomConfig> config;
	private final Logger LOGGER;

	protected BasicModClass(String modName, String modVersion) {
		this(modName, modVersion, false);
	}

	protected BasicModClass(String modId, String modName, String modVersion) {
		this(modId, modName, modVersion, false);
	}

	protected BasicModClass(String modName, String modVersion, boolean client) {
		this(modName, modName, modVersion, client);
	}

	protected BasicModClass(String modId, String modName, String modVersion, boolean client) {
		this.modId = modId.toLowerCase(Locale.ROOT);
		this.modName = modName;
		this.modVersion = modVersion;
		if (!client) {
			REGISTRY_HELPER = RegistryHelper.createRegistryHelper(this.modId);
		} else {
			REGISTRY_HELPER = null;
		}
		LOGGER = LogManager.getLogger(this.modName + (client ? " Client" : ""));
		FEATURE_MANAGER = FeatureManager.createFeatureManager(new ResourceLocation(this.modId,
				"feature_manager" + Rands.getRandom().nextInt()));
	}

	public void createConfig(Class<? extends CustomConfig> config) {
		AutoConfig.register(config, GsonConfigSerializer::new);
		this.config = AutoConfig.getConfigHolder(config);
	}

	public ConfigHolder<? extends CustomConfig> getConfig() {
		return config;
	}

	public ResourceLocation identifier(String path) {
		return new ResourceLocation(modId(), path);
	}

	public ResourceLocation identifier(String namespace, String path) {
		return new ResourceLocation(namespace, path);
	}

	public void registerFeatures() {

	}

	@Environment(EnvType.CLIENT)
	public void registerFeaturesClient() {

	}

	@Environment(EnvType.SERVER)
	public void registerFeaturesServer() {

	}

	public void commonPostRegisterFeatures() {
		FEATURE_MANAGER.initCommon(modId());
	}

	@Environment(EnvType.CLIENT)
	public void clientPostRegisterFeatures() {
		FEATURE_MANAGER.initClient(modId());
	}

	@Environment(EnvType.SERVER)
	public void serverPostRegisterFeatures() {
		FEATURE_MANAGER.initServer(modId());
	}

	public RegistryHelper registryHelper() {
		return REGISTRY_HELPER;
	}

	public FeatureManager featureManager() {
		return FEATURE_MANAGER;
	}

	public Logger getLogger() {
		return LOGGER;
	}

	public String modId() {
		return modId;
	}

	public String modName() {
		return modName;
	}

	public String modVersion() {
		return modVersion;
	}

	public void shouldNotPrintVersionMessage() {
		this.printVersionMessage = false;
	}

	@Override
	public void onInitialize() {
		if (printVersionMessage) getLogger().info(String.format("You're now running %s v%s for %s", modName(), modVersion(), SharedConstants.getCurrentVersion().getName()));
	}

	@Override
	public void onInitializeClient() {
		if (printVersionMessage) getLogger().info(String.format("You're now running %s v%s on Client-Side for %s", modName(), modVersion(), SharedConstants.getCurrentVersion().getName()));
	}
}