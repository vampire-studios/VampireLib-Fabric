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

import com.mojang.serialization.Lifecycle;
import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.modules.api.NonFeatureModule;
import io.github.vampirestudios.vampirelib.modules.utils.ConsoleUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;

public class ModuleManager {

    public static final Registry<NonFeatureModule> NON_FEATURE_MODULES = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier("vampirelib:non_feature_modules")), Lifecycle.stable());
    public static final Registry<NonFeatureModule> CLIENT_NON_FEATURE_MODULES = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier("vampirelib:client_non_feature_modules")), Lifecycle.stable());
    public static final Registry<NonFeatureModule> SERVER_NON_FEATURE_MODULES = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier("vampirelib:server_non_feature_modules")), Lifecycle.stable());

    public static ModuleManager createModuleManager(Identifier modIdentifier) {
        return Registry.register(VampireLib.MODULE_MANAGERS, modIdentifier, new ModuleManager());
    }

    public static ModuleManager getModuleManager(Identifier modIdentifier) {
        return VampireLib.MODULE_MANAGERS.get(modIdentifier);
    }

    public void registerNonFeatureModule(NonFeatureModule module) {
        if (!NON_FEATURE_MODULES.getOrEmpty(module.getRegistryName()).isPresent()) {
            Registry.register(NON_FEATURE_MODULES, module.getRegistryName(), module);
        }
        ModuleConfig.load(module, WordUtils.capitalize(module.getRegistryName().getNamespace()), "common");
    }

    public void registerClientNonFeatureModule(NonFeatureModule module) {
        if (!CLIENT_NON_FEATURE_MODULES.getOrEmpty(module.getRegistryName()).isPresent()) {
            Registry.register(CLIENT_NON_FEATURE_MODULES, module.getRegistryName(), module);
        }
        ModuleConfig.load(module, WordUtils.capitalize(module.getRegistryName().getNamespace()), "client");
    }

    public void registerServerNonFeatureModule(NonFeatureModule module) {
        if (!SERVER_NON_FEATURE_MODULES.getOrEmpty(module.getRegistryName()).isPresent()) {
            Registry.register(SERVER_NON_FEATURE_MODULES, module.getRegistryName(), module);
        }
        ModuleConfig.load(module, WordUtils.capitalize(module.getRegistryName().getNamespace()), "server");
    }

    public void init(String modId) {
        NON_FEATURE_MODULES.forEach(nonFeatureModule -> {
            if (nonFeatureModule.getRegistryName().getNamespace().equals(modId)) {
                nonFeatureModule.init();
            }
        });
        SERVER_NON_FEATURE_MODULES.forEach(nonFeatureModule -> {
            if (nonFeatureModule.getRegistryName().getNamespace().equals(modId)) nonFeatureModule.init();
        });

        ConsoleUtils.logServerNonFeatureModules();
    }

    @Environment(EnvType.CLIENT)
    public void initClient(String modId) {
        NON_FEATURE_MODULES.forEach(nonFeatureModule -> {
            if (nonFeatureModule.getRegistryName().getNamespace().equals(modId)) nonFeatureModule.initClient();
        });
        CLIENT_NON_FEATURE_MODULES.forEach(nonFeatureModule -> {
            if (nonFeatureModule.getRegistryName().getNamespace().equals(modId)) nonFeatureModule.initClient();
        });

        ConsoleUtils.logClientNonFeatureModules();
    }

    public boolean doesNonFeatureModuleExist(NonFeatureModule module) {
        return NON_FEATURE_MODULES.containsId(module.getRegistryName());
    }

    public boolean isNonFeatureModuleEnabled(Identifier name) {
        if (NON_FEATURE_MODULES.containsId(name)) {
            NonFeatureModule module = NON_FEATURE_MODULES.get(name);
            return Objects.requireNonNull(module).isEnabled();
        }
        return false;
    }

}
