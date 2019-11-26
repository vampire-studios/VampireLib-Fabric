package io.github.vampirestudios.vampirelib.module_api.api;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

public abstract class NewFeature<T extends ConfigData> {

    public Identifier name;
    public String description;
    private static boolean enabled = true;
    public EnvType envType;

    public NewFeature(Identifier name, String description, EnvType envType) {
        this.name = name;
        this.description = description;
        this.envType = envType;
    }

    public NewFeature(Identifier name, String description) {
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

    public T getConfig() {
        return null;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public EnvType getEnvType() {
        return envType;
    }

}