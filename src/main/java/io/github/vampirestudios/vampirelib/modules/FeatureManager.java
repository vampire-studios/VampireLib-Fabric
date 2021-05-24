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

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.modules.api.*;
import io.github.vampirestudios.vampirelib.modules.utils.ConsoleUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;

public class FeatureManager {
    public static final Registry<CommonFeature> COMMON_FEATURES = FabricRegistryBuilder.createSimple(CommonFeature.class, new Identifier("vampirelib:common_features")).buildAndRegister();
    public static final Registry<ClientFeature> CLIENT_FEATURES = FabricRegistryBuilder.createSimple(ClientFeature.class, new Identifier("vampirelib:client_features")).buildAndRegister();
    public static final Registry<ServerFeature> SERVER_FEATURES = FabricRegistryBuilder.createSimple(ServerFeature.class, new Identifier("vampirelib:server_features")).buildAndRegister();

    public static FeatureManager createModuleManager(Identifier modIdentifier) {
        return Registry.register(VampireLib.FEATURE_MANAGERS, modIdentifier, new FeatureManager());
    }

    public static FeatureManager getModuleManager(Identifier modIdentifier) {
        return VampireLib.FEATURE_MANAGERS.get(modIdentifier);
    }

    public void registerCommonFeature(CommonFeature module) {
        if (!COMMON_FEATURES.getOrEmpty(module.getRegistryName()).isPresent()) {
            Registry.register(COMMON_FEATURES, module.getRegistryName(), module);
        }
        ModuleConfig.load(module, WordUtils.capitalize(module.getRegistryName().getNamespace()), "common");
    }

    public void registerClientFeature(ClientFeature module) {
        if (!CLIENT_FEATURES.getOrEmpty(module.getRegistryName()).isPresent()) {
            Registry.register(CLIENT_FEATURES, module.getRegistryName(), module);
        }
        ModuleConfig.load(module, WordUtils.capitalize(module.getRegistryName().getNamespace()), "client");
    }

    public void registerServerFeature(ServerFeature module) {
        if (!SERVER_FEATURES.getOrEmpty(module.getRegistryName()).isPresent()) {
            Registry.register(SERVER_FEATURES, module.getRegistryName(), module);
        }
        ModuleConfig.load(module, WordUtils.capitalize(module.getRegistryName().getNamespace()), "server");
    }

    public void initCommon(String modId) {
        COMMON_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initCommon();
        });
        CLIENT_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initClient();
        });
        SERVER_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initServer();
        });

        ConsoleUtils.logCommonFeatures();
    }

    @Environment(EnvType.CLIENT)
    public void initClient(String modId) {
        COMMON_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initCommon();
        });
        CLIENT_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initClient();
        });

        ConsoleUtils.logClientFeatures();
    }

    @Environment(EnvType.SERVER)
    public void initServer(String modId) {
        COMMON_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initCommon();
        });
        SERVER_FEATURES.forEach(feature -> {
            if (feature.getRegistryName().getNamespace().equals(modId)) feature.initServer();
        });

        ConsoleUtils.logServerFeatures();
    }

    public boolean doesCommonFeatureExist(CommonFeature module) {
        return COMMON_FEATURES.containsId(module.getRegistryName());
    }

    public boolean doesClientFeatureExist(ClientFeature module) {
        return CLIENT_FEATURES.containsId(module.getRegistryName());
    }

    public boolean doesServerFeatureExist(ServerFeature module) {
        return SERVER_FEATURES.containsId(module.getRegistryName());
    }

    public boolean isFeatureEnabled(Identifier name) {
        if (COMMON_FEATURES.containsId(name)) {
            CommonFeature module = COMMON_FEATURES.get(name);
            return Objects.requireNonNull(module).isEnabled();
        }
        if (CLIENT_FEATURES.containsId(name)) {
            ClientFeature module = CLIENT_FEATURES.get(name);
            return Objects.requireNonNull(module).isEnabled();
        }
        if (SERVER_FEATURES.containsId(name)) {
            ServerFeature module = SERVER_FEATURES.get(name);
            return Objects.requireNonNull(module).isEnabled();
        }
        return false;
    }

}
