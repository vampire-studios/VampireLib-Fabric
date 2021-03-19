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

package io.github.vampirestudios.vampirelib.modules.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    private final String description;
    private boolean enabled = true;
    private final Identifier registryName;
    private final List<NonFeatureModule> nonFeatureModules = new ArrayList<>();
    private final List<NonFeatureModule> serverNonFeatureModules = new ArrayList<>();
    private final List<NonFeatureModule> clientNonFeatureModules = new ArrayList<>();

    public Module(Identifier name, String description) {
        this.registryName = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void init() {

    }

    @Environment(EnvType.CLIENT)
    public void initClient() {

    }

    public Identifier getRegistryName() {
        return registryName;
    }

    public <T extends NonFeatureModule> T registerNonFeatureModule(T subModule) {
        this.nonFeatureModules.add(subModule);
        return subModule;
    }

    public <T extends NonFeatureModule> T registerClientNonFeatureModule(T subModule) {
        this.clientNonFeatureModules.add(subModule);
        return subModule;
    }

    public <T extends NonFeatureModule> T registerServerNonFeatureModule(T subModule) {
        this.serverNonFeatureModules.add(subModule);
        return subModule;
    }

    public List<NonFeatureModule> getNonFeatureModules() {
        return nonFeatureModules;
    }

    public List<NonFeatureModule> getServerNonFeatureModules() {
        return serverNonFeatureModules;
    }

    public List<NonFeatureModule> getClientNonFeatureModules() {
        return clientNonFeatureModules;
    }

}