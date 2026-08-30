package io.github.vampirestudios.vampirelib.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.model.geom.ModelPart;

/**
 * A safe variant of {@link ModelPart}.
 * If a child is requested from this model part that does not exist, it will return an empty model part, which is also safe like this one.
 * The same empty model part instance is guaranteed to be returned for each call to {@link #getChild(String)} with the same name.
 */
public class SafeModelPart extends ModelPart {
    protected final Map<String, Empty> cachedEmpties = new HashMap<>();

    public SafeModelPart(List<Cube> cubes, Map<String, ModelPart> children) {
        super(cubes, children);
    }

    @Override
    public ModelPart getChild(String name) {
        ModelPart childPart = this.children.get(name);
        return childPart == null ? getEmptyChild(name) : childPart;
    }

    protected ModelPart getEmptyChild(String name) {
        return cachedEmpties.computeIfAbsent(name, n -> new Empty());
    }

    private static class Empty extends SafeModelPart {
        private Empty() {
            super(List.of(), Map.of());
        }

        @Override
        public ModelPart getChild(String name) {
            return getEmptyChild(name);
        }
    }
}
