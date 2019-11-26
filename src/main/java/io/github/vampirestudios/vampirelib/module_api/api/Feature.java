package io.github.vampirestudios.vampirelib.module_api.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

public abstract class Feature {

    public Identifier name;
    public String description;
    private static boolean enabled = true;
    public EnvType envType;

    public Feature(Identifier name, String description, EnvType envType) {
        this.name = name;
        this.description = description;
        this.envType = envType;
    }

    public Feature(Identifier name, String description) {
        this(name, description, EnvType.SERVER);
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

    public static boolean isEnabled() {
        return enabled;
    }

    public EnvType getEnvType() {
        return envType;
    }

}