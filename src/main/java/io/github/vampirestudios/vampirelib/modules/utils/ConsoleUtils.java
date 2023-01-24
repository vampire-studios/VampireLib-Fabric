/*
 * Copyright (c) 2022-2023 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.modules.utils;

import static io.github.vampirestudios.vampirelib.modules.FeatureManager.CLIENT_FEATURES;
import static io.github.vampirestudios.vampirelib.modules.FeatureManager.COMMON_FEATURES;
import static io.github.vampirestudios.vampirelib.modules.FeatureManager.SERVER_FEATURES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.vampirestudios.vampirelib.modules.FeatureManager;
import io.github.vampirestudios.vampirelib.modules.api.ClientFeature;
import io.github.vampirestudios.vampirelib.modules.api.CommonFeature;
import io.github.vampirestudios.vampirelib.modules.api.ServerFeature;

public class ConsoleUtils {

	protected static Logger LOGGER = LoggerFactory.getLogger("[VampireLib: Console Utils]");

	public static void logCommonFeatures() {
		String moduleText;
		if (COMMON_FEATURES.stream().count() > 1) {
			moduleText = "Loading %d Common Features:";
		} else {
			moduleText = "Loading %d Common Feature:";
		}

		LOGGER.info("[" + FeatureManager.class.getSimpleName() + "] " + moduleText, COMMON_FEATURES.stream().count());

		for (CommonFeature feature : COMMON_FEATURES) {
			LOGGER.info(String.format(" - %s(%s) - Enabled: %s", feature.getRegistryName(), feature.getName(),
					feature.isEnabled() ? "true" : "false"));
		}
	}

	public static void logClientFeatures() {
		String moduleText;
		if (CLIENT_FEATURES.stream().count() > 1) {
			moduleText = "Loading %d Client Features:";
		} else {
			moduleText = "Loading %d Client Feature:";
		}

		LOGGER.info("[" + FeatureManager.class.getSimpleName() + "] " + moduleText, CLIENT_FEATURES.stream().count());

		for (ClientFeature candidate : CLIENT_FEATURES) {
			LOGGER.info(String.format(" - %s(%s) - Enabled: %s", candidate.getRegistryName(), candidate.getName(),
					candidate.isEnabled() ? "true" : "false"));
		}
	}

	public static void logServerFeatures() {
		String moduleText;
		if (SERVER_FEATURES.stream().count() > 1) {
			moduleText = "Loading %d Server Features:";
		} else {
			moduleText = "Loading %d Server Feature:";
		}

		LOGGER.info("[" + FeatureManager.class.getSimpleName() + "] " + moduleText, SERVER_FEATURES.stream().count());

		for (ServerFeature candidate : SERVER_FEATURES) {
			LOGGER.info(String.format(" - %s(%s) - Enabled: %s", candidate.getRegistryName(), candidate.getName(),
					candidate.isEnabled() ? "true" : "false"));
		}
	}

}
