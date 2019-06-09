/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package team.abnormals.module_api;

import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.JanksonConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.abnormalib.AbnormaLib;
import team.abnormals.module_api.api.Module;
import team.abnormals.module_api.data.ModuleDataManager;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    public static final Map<Module, String> MODULES = new HashMap<>();
    public static final Map<Class, String> MODULES_TEST = new HashMap<>();
    public static String modid;

    private static final Logger LOGGER = LogManager.getFormatterLogger(String.format("[%s Logger : Module Manager]", AbnormaLib.MOD_NAME));

    public static void registerModule(String modidIn, Module module, String name) {
        MODULES.put(module, name);
        LOGGER.info(String.format("Registered a module called: %s", name));
        AutoConfig.register(module.getConfig(), JanksonConfigSerializer::new);
        LOGGER.info(String.format("Registered a config for: %s", name));
        modid = modidIn;
    }

    public static void registerModule(String modidIn, ModuleDataManager.ModuleData moduleData) {
        MODULES_TEST.put(moduleData.moduleClass, moduleData.name);
        LOGGER.info(String.format("Registered a module called: %s", moduleData.name));
        AutoConfig.register(moduleData.configClass, JanksonConfigSerializer::new);
        LOGGER.info(String.format("Registered a config for: %s", moduleData.name));
        modid = modidIn;
    }

    public static void init() {
        MODULES.keySet().forEach(Module::init);
    }

    public static void initClient() {
        MODULES.keySet().forEach(Module::initClient);
    }

}