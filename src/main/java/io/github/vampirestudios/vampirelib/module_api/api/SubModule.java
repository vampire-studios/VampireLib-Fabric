package io.github.vampirestudios.vampirelib.module_api.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class SubModule extends Feature {

    public boolean enabled = true;
    private List<Feature> features = new ArrayList<>();
    private List<Feature> serverFeatures = new ArrayList<>();
    private List<Feature> clientFeatures = new ArrayList<>();

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