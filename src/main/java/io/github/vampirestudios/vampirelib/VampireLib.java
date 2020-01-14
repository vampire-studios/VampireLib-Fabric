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

package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.modules.ModuleManager;
import io.github.vampirestudios.vampirelib.utils.registry.BlockChiseler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VampireLib implements ModInitializer, ClientModInitializer {

    public static String MOD_ID = "vampirelib";
    public static String MOD_NAME = "VampireLib";
    public static String MOD_VERSION = "1.3.3+1.15.1-build.3";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static final Registry<ModuleManager> MODULE_MANAGERS = new DefaultedRegistry<>("vampirelib:module_managers");

    @Override
    public void onInitialize() {
        LOGGER.info("You're running " + MOD_NAME + " v" + MOD_VERSION);

        BlockChiseler.setup();
        MODULE_MANAGERS.forEach(ModuleManager::init);
    }

    @Override
    public void onInitializeClient() {
        MODULE_MANAGERS.forEach(ModuleManager::initClient);
    }

}