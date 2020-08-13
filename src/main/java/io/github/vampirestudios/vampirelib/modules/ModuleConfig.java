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

package io.github.vampirestudios.vampirelib.modules;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.modules.api.NonFeatureModule;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ModuleConfig {
    private static final Jankson JANKSON = Jankson.builder().build();

    public static void load(NonFeatureModule module, String modName, String configType) {
        File configFile = new File(String.format("config/%s-%s.json5", module.getRegistryName().getNamespace(), configType));
        JsonObject config = new JsonObject();
        if (configFile.exists()) {
            try {
                config = JANKSON.load(configFile);
                loadFrom(config, configType);
                writeConfigFile(modName, configFile, config, configType);
            } catch (IOException | SyntaxError e) {
                VampireLib.LOGGER.error(modName + " config could not be loaded. Default values will be used.", e);
            }
        } else {
            saveTo(config, configType);
            writeConfigFile(modName, configFile, config, configType);
        }
    }

    private static void writeConfigFile(String modName, File file, JsonObject config, String configType) {
        saveTo(config, configType);
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(config.toJson(JsonGrammar.JANKSON).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            VampireLib.LOGGER.error(modName + "config could not be written. This probably won't cause any problems, but it shouldn't happen.", e);
        }
    }

    public static void loadFrom(JsonObject obj, String configType) {
        if (configType.equals("server")) {
            for (NonFeatureModule module : ModuleManager.SERVER_NON_FEATURE_MODULES) {
                JsonObject moduleConfig = getObjectOrEmpty(module.getRegistryName().toString(), obj);
                module.setEnabled(moduleConfig);
                module.configure(moduleConfig);
            }
        } else if (configType.equals("client")) {
            for (NonFeatureModule module : ModuleManager.CLIENT_NON_FEATURE_MODULES) {
                JsonObject moduleConfig = getObjectOrEmpty(module.getRegistryName().toString(), obj);
                module.setEnabled(moduleConfig);
                module.configure(moduleConfig);
            }
        } else {
            for (NonFeatureModule module : ModuleManager.NON_FEATURE_MODULES) {
                JsonObject moduleConfig = getObjectOrEmpty(module.getRegistryName().toString(), obj);
                module.setEnabled(moduleConfig);
                module.configure(moduleConfig);
            }
        }
    }

    public static void saveTo(JsonObject obj, String configType) {
        if (configType.equals("server")) {
            for (NonFeatureModule module : ModuleManager.SERVER_NON_FEATURE_MODULES) {
                JsonObject moduleConfig = module.getConfig();
                obj.put(module.getRegistryName().toString(), moduleConfig);
            }
        } else if (configType.equals("client")) {
            for (NonFeatureModule module : ModuleManager.CLIENT_NON_FEATURE_MODULES) {
                JsonObject moduleConfig = module.getConfig();
                obj.put(module.getRegistryName().toString(), moduleConfig);
            }
        } else {
            for (NonFeatureModule module : ModuleManager.NON_FEATURE_MODULES) {
                JsonObject moduleConfig = module.getConfig();
                obj.put(module.getRegistryName().toString(), moduleConfig);
            }
        }
    }

    public static JsonObject getObjectOrEmpty(String key, JsonObject on) {
        JsonObject obj = on.getObject(key);
        return obj != null ? obj : new JsonObject();
    }
}