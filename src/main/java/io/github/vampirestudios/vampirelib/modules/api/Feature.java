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

public abstract class Feature {

    private boolean enabled = true;
    public Identifier name;
    public String description;
    public EnvType envType;

    public Feature(Identifier name, String description, EnvType envType) {
        this.name = name;
        this.description = description;
        this.envType = envType;
    }

    public Feature(Identifier name, String description) {
        this(name, description, EnvType.SERVER);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabledIn) {
        enabled = enabledIn;
    }

    public void init() {

    }

    @Environment(EnvType.CLIENT)
    public void initClient() {

    }

    public void configEntries() {

    }

    public EnvType getEnvType() {
        return envType;
    }

}