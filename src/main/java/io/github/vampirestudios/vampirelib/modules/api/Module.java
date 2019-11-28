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

package io.github.vampirestudios.vampirelib.modules.api;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    public String description;
    public boolean hasConfig;
    public boolean enabled = true;
    public Identifier registryName;
    private List<Feature> features = new ArrayList<>();
    private List<Feature> serverFeatures = new ArrayList<>();
    private List<Feature> clientFeatures = new ArrayList<>();
    private List<NewFeature> newFeatures = new ArrayList<>();
    private List<NewFeature> newServerFeatures = new ArrayList<>();
    private List<NewFeature> newClientFeatures = new ArrayList<>();
    private List<SubModule> subModules = new ArrayList<>();
    private List<SubModule> serverSubModules = new ArrayList<>();
    private List<SubModule> clientSubModules = new ArrayList<>();

    public Module(Identifier name, String description, boolean hasConfig) {
        this.registryName = name;
        this.description = description;
        this.hasConfig = hasConfig;
    }

    public Module(Identifier name, String description) {
        this.registryName = name;
        this.description = description;
        this.hasConfig = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isConfigAvailable() {
        return hasConfig;
    }

    public Class<? extends ConfigData> getConfig() {
        return null;
    }

    public boolean isHasConfig() {
        return hasConfig;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void init() {

    }

    @Environment(EnvType.CLIENT)
    public void initClient() {

    }

    public Identifier getRegistryName() {
        return registryName;
    }

    public <T extends Feature> T registerFeature(T feature) {
        this.features.add(feature);
        return feature;
    }

    public <T extends Feature> T registerClientFeature(T feature) {
        this.clientFeatures.add(feature);
        return feature;
    }

    public <T extends Feature> T registerServerFeature(T feature) {
        this.serverFeatures.add(feature);
        return feature;
    }

    public <T extends NewFeature> T registerNewFeature(T feature) {
        this.newFeatures.add(feature);
        return feature;
    }

    public <T extends NewFeature> T registerNewClientFeature(T feature) {
        this.newClientFeatures.add(feature);
        return feature;
    }

    public <T extends NewFeature> T registerNewServerFeature(T feature) {
        this.newServerFeatures.add(feature);
        return feature;
    }

    public <T extends SubModule> T registerSubModule(T subModule) {
        this.subModules.add(subModule);
        return subModule;
    }

    public <T extends SubModule> T registerClientSubModule(T subModule) {
        this.clientSubModules.add(subModule);
        return subModule;
    }

    public <T extends SubModule> T registerServerSubModule(T subModule) {
        this.serverSubModules.add(subModule);
        return subModule;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public List<SubModule> getSubModules() {
        return subModules;
    }

    public List<Feature> getServerFeatures() {
        return serverFeatures;
    }

    public List<SubModule> getServerSubModules() {
        return serverSubModules;
    }

    public List<Feature> getClientFeatures() {
        return clientFeatures;
    }

    public List<SubModule> getClientSubModules() {
        return clientSubModules;
    }

    public List<NewFeature> getNewFeatures() {
        return newFeatures;
    }

    public List<NewFeature> getNewServerFeatures() {
        return newServerFeatures;
    }

    public List<NewFeature> getNewClientFeatures() {
        return newClientFeatures;
    }

}