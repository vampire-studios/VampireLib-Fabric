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

import io.github.vampirestudios.vampirelib.modules.ModuleManager;
import io.github.vampirestudios.vampirelib.modules.api.NonFeatureModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.vampirestudios.vampirelib.modules.ModuleManager.NON_FEATURE_MODULES;

public class ConsoleUtils {

    protected static Logger LOGGER = LogManager.getFormatterLogger("[VampireLib: Console Utils]");

    public static void logServerNonFeatureModules() {
        String moduleText;
        if (NON_FEATURE_MODULES.stream().count() > 1) {
            moduleText = "Loading %d non-feature-modules:";
        } else {
            moduleText = "Loading %d non-feature-module:";
        }

        LOGGER.info("[" + ModuleManager.class.getSimpleName() + "] " + moduleText, NON_FEATURE_MODULES.stream().count());

        for (NonFeatureModule nonFeatureModule : NON_FEATURE_MODULES) {
            LOGGER.info(String.format(" - %s(%s) - Enabled: %s", nonFeatureModule.getRegistryName(), nonFeatureModule.getName(), nonFeatureModule.isEnabled() ? "true" : "false"));
        }
    }

    public static void logClientNonFeatureModules() {
        String moduleText;
        if (NON_FEATURE_MODULES.stream().count() > 1) {
            moduleText = "Loading %d client non-feature-modules:";
        } else {
            moduleText = "Loading %d client non-feature-module:";
        }

        LOGGER.info("[" + ModuleManager.class.getSimpleName() + "] " + moduleText, NON_FEATURE_MODULES.stream().count());

        for (NonFeatureModule candidate : NON_FEATURE_MODULES) {
            LOGGER.info(String.format(" - %s(%s) - Enabled: %s", candidate.getRegistryName(), candidate.getName(), candidate.isEnabled() ? "true" : "false"));
        }
    }

}