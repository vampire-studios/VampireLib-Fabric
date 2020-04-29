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

public abstract class SubModule extends Feature {

    public boolean enabled = true;
    private final List<Feature> features = new ArrayList<>();
    private final List<Feature> serverFeatures = new ArrayList<>();
    private final List<Feature> clientFeatures = new ArrayList<>();

    public SubModule(Identifier name, String description) {
        super(name, description);
    }

    public void init() {

    }

    @Environment(EnvType.CLIENT)
    public void initClient() {

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public <T extends Feature> T registerFeature(T feature) {
        this.features.add(feature);
        return feature;
    }

    public <T extends Feature> T registerServerFeature(T feature) {
        this.serverFeatures.add(feature);
        return feature;
    }

    public <T extends Feature> T registerClientFeature(T feature) {
        this.clientFeatures.add(feature);
        return feature;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public List<Feature> getServerFeatures() {
        return serverFeatures;
    }

    public List<Feature> getClientFeatures() {
        return clientFeatures;
    }

}