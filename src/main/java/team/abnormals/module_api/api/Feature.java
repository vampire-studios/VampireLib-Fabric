package team.abnormals.module_api.api;

public abstract class Feature {

    public String name, description;

    public Feature(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void init();

    public abstract void initClient();

}
