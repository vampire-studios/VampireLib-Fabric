package io.github.vampirestudios.vampirelib.modules.api;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import net.minecraft.util.Identifier;

public abstract class NonFeatureModule {

    private boolean enabled = true;
    private Identifier registryName;

    public abstract void init();

    public void initClient() {

    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final void setEnabled(JsonObject obj) {
        setEnabled(obj.getBoolean("enabled", true));
    }

    public Identifier getRegistryName() {
        return registryName;
    }

    public void setRegistryName(Identifier registryName) {
        this.registryName = registryName;
    }

    public void configure(JsonObject obj) {}

    public final JsonObject getConfig() {
        JsonObject obj = new JsonObject();
        obj.put("enabled", new JsonPrimitive(isEnabled()));
        writeToConfig(obj);
        return obj;
    }

    public void writeToConfig(JsonObject obj) {}

}
