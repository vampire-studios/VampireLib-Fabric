package team.hollow.abnormalib.modules.api;

import net.minecraft.util.Identifier;
import team.hollow.abnormalib.modules.api.features.Feature;
import team.hollow.abnormalib.modules.api.features.OptionalFeature;

import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Module extends OptionalFeature {

    public String name;
    public String description;
    protected ConcurrentLinkedQueue<Feature> features;
    protected Identifier backgroundTexture;

    public Module(String name, String description) {
        super("_enable", "Enable/disable this module.");
        this.description = description;
        this.name = name;
        this.features = new ConcurrentLinkedQueue<>();
    }

    public <T extends Feature> T register(T feature) {
        features.add(feature);
        return feature;
    }

    @Override
    protected void applyEnabled() {
        features.forEach(Feature::apply);
    }

    protected void setBackgroundTexture(Identifier identifier) {
        backgroundTexture = identifier;
    }
}