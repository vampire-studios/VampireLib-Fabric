package io.github.vampirestudios.vampirelib.modules.utils;

import static io.github.vampirestudios.vampirelib.modules.FeatureManager.CLIENT_FEATURES;
import static io.github.vampirestudios.vampirelib.modules.FeatureManager.COMMON_FEATURES;
import static io.github.vampirestudios.vampirelib.modules.FeatureManager.SERVER_FEATURES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;

import io.github.vampirestudios.vampirelib.modules.api.Feature;

public final class ConsoleUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleUtils.class);

	public static void logCommonFeatures() {
		logFeatures(COMMON_FEATURES, "Common");
	}

	public static void logClientFeatures() {
		logFeatures(CLIENT_FEATURES, "Client");
	}

	public static void logServerFeatures() {
		logFeatures(SERVER_FEATURES, "Server");
	}

	private static <T extends Feature> void logFeatures(Registry<T> registry, String type) {
		int count = registry.size();

		LOGGER.info("Loaded {} {} Feature{}:", count, type, count == 1 ? "" : "s");

		for (T feature : registry) {
			LOGGER.info(" - {} ({}) - {}", feature.getRegistryName(), feature.getName(), feature.isEnabled() ? "Enabled" : "Disabled");
		}
	}
}
