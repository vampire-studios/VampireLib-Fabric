/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
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

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.modules.api.Feature;
import io.github.vampirestudios.vampirelib.modules.api.Module;
import io.github.vampirestudios.vampirelib.modules.api.NewFeature;
import io.github.vampirestudios.vampirelib.modules.api.SubModule;
import io.github.vampirestudios.vampirelib.modules.utils.ConsoleUtils;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    public static final Map<Module, Identifier> MODULES = new HashMap<>();
    public static final Map<Module, Identifier> SERVER_MODULES = new HashMap<>();
    public static final Map<Module, Identifier> CLIENT_MODULES = new HashMap<>();
    private static Logger LOGGER = LogManager.getFormatterLogger("[VampireLib: Module Loader]");

    public static ModuleManager createModuleManager(Identifier modIdentifier) {
        return Registry.register(VampireLib.MODULE_MANAGERS, modIdentifier, new ModuleManager());
    }

    public static ModuleManager getModuleManager(Identifier modIdentifier) {
        return VampireLib.MODULE_MANAGERS.get(modIdentifier);
    }

    public void registerModule(Module module) {
        if (!MODULES.containsKey(module)) {
            MODULES.put(module, module.getRegistryName());
        }
        if (module.isConfigAvailable() && module.getConfig() != null) {
            AutoConfig.register(module.getConfig(), GsonConfigSerializer::new);
            LOGGER.info(String.format("Registered a config for: %s", module.getRegistryName()));
        }
        for (NewFeature features : module.getNewFeatures()) {
            if (features.getConfig() != null) {
                AutoConfig.register(features.getConfig().getClass(), GsonConfigSerializer::new);
                LOGGER.info(String.format("Registered a config for: %s", features.name));
            }
        }
    }

    public void registerServerModule(Module module) {
        if (!SERVER_MODULES.containsKey(module)) {
            SERVER_MODULES.put(module, module.getRegistryName());
        }
        if (module.isConfigAvailable() && module.getConfig() != null) {
            AutoConfig.register(module.getConfig(), GsonConfigSerializer::new);
            LOGGER.info(String.format("Registered a config for: %s", module.getRegistryName()));
        }
        for (NewFeature features : module.getNewServerFeatures()) {
            if (features.getConfig() != null) {
                AutoConfig.register(features.getConfig().getClass(), GsonConfigSerializer::new);
                LOGGER.info(String.format("Registered a config for: %s", features.name));
            }
        }
    }

    public void registerClientModule(Module module) {
        if (!CLIENT_MODULES.containsKey(module)) {
            CLIENT_MODULES.put(module, module.getRegistryName());
        }
        if (module.isConfigAvailable() && module.getConfig() != null) {
            AutoConfig.register(module.getConfig(), GsonConfigSerializer::new);
            LOGGER.info(String.format("Registered a config for: %s", module.getRegistryName()));
        }
        for (NewFeature features : module.getNewClientFeatures()) {
            if (features.getConfig() != null) {
                AutoConfig.register(features.getConfig().getClass(), GsonConfigSerializer::new);
                LOGGER.info(String.format("Registered a config for: %s", features.name));
            }
        }
    }

    public void init() {
        SERVER_MODULES.keySet().forEach(module -> {
            module.init();
            module.getServerFeatures().forEach(Feature::init);
            module.getServerSubModules().forEach(SubModule::init);
            module.getServerSubModules().forEach(subModule -> subModule.getServerFeatures().forEach(Feature::init));
            module.getFeatures().forEach(Feature::init);
            module.getSubModules().forEach(SubModule::init);
            module.getSubModules().forEach(subModule -> subModule.getFeatures().forEach(Feature::init));
        });
        MODULES.keySet().forEach(module -> {
            module.init();
            module.getServerFeatures().forEach(Feature::init);
            module.getServerSubModules().forEach(SubModule::init);
            module.getServerSubModules().forEach(subModule -> subModule.getServerFeatures().forEach(Feature::init));
            module.getFeatures().forEach(Feature::init);
            module.getSubModules().forEach(SubModule::init);
            module.getSubModules().forEach(subModule -> subModule.getFeatures().forEach(Feature::init));
        });

        ConsoleUtils.logServerModules();
    }

    @Environment(EnvType.CLIENT)
    public void initClient() {
        CLIENT_MODULES.keySet().forEach(module -> {
            module.initClient();
            module.getClientFeatures().forEach(Feature::initClient);
            module.getClientSubModules().forEach(SubModule::initClient);
            module.getClientSubModules().forEach(subModule -> subModule.getClientFeatures().forEach(Feature::initClient));
            System.out.println("Initializing old client features");
            module.getFeatures().forEach(Feature::initClient);
            module.getSubModules().forEach(SubModule::initClient);
            module.getSubModules().forEach(subModule -> subModule.getFeatures().forEach(Feature::initClient));
        });
        MODULES.keySet().forEach(module -> {
            module.initClient();
            module.getClientFeatures().forEach(Feature::initClient);
            module.getClientSubModules().forEach(SubModule::initClient);
            module.getClientSubModules().forEach(subModule -> subModule.getClientFeatures().forEach(Feature::initClient));
            System.out.println("Initializing old client features");
            module.getFeatures().forEach(Feature::initClient);
            module.getSubModules().forEach(SubModule::initClient);
            module.getSubModules().forEach(subModule -> subModule.getFeatures().forEach(Feature::initClient));
        });

        ConsoleUtils.logClientModules();
    }

}
