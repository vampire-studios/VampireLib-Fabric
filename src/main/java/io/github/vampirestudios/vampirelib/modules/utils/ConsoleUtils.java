/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.modules.utils;

import io.github.vampirestudios.vampirelib.modules.FeatureManager;
import io.github.vampirestudios.vampirelib.modules.api.ClientFeature;
import io.github.vampirestudios.vampirelib.modules.api.CommonFeature;
import io.github.vampirestudios.vampirelib.modules.api.ServerFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.vampirestudios.vampirelib.modules.FeatureManager.*;

public class ConsoleUtils {

    protected static Logger LOGGER = LogManager.getFormatterLogger("[VampireLib: Console Utils]");

    public static void logCommonFeatures() {
        String moduleText;
        if (COMMON_FEATURES.stream().count() > 1) {
            moduleText = "Loading %d Common Features:";
        } else {
            moduleText = "Loading %d Common Feature:";
        }

        LOGGER.info("[" + FeatureManager.class.getSimpleName() + "] " + moduleText, COMMON_FEATURES.stream().count());

        for (CommonFeature feature : COMMON_FEATURES) {
            LOGGER.info(String.format(" - %s(%s) - Enabled: %s", feature.getRegistryName(), feature.getName(), feature.isEnabled() ? "true" : "false"));
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
            LOGGER.info(String.format(" - %s(%s) - Enabled: %s", candidate.getRegistryName(), candidate.getName(), candidate.isEnabled() ? "true" : "false"));
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
            LOGGER.info(String.format(" - %s(%s) - Enabled: %s", candidate.getRegistryName(), candidate.getName(), candidate.isEnabled() ? "true" : "false"));
        }
    }

}