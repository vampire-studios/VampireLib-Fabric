package io.github.vampirestudios.vampirelib.client;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * A safe variant of {@link PartDefinition} that returns a {@link SafeModelPart safe model part} when baking.
 */
public class SafePartDefinition extends PartDefinition {
    public SafePartDefinition(List<CubeDefinition> cubes, PartPose partPose) {
        super(cubes, partPose);
    }

    @Override
    public SafeModelPart bake(int texWidth, int texHeight) {
        Object2ObjectArrayMap<String, ModelPart> map = this.children.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().bake(texWidth, texHeight), (firstPart, secondPart) -> firstPart, Object2ObjectArrayMap::new));
        List<ModelPart.Cube> list = this.cubes.stream().map(cube -> cube.bake(texWidth, texHeight)).toList();

        SafeModelPart modelPart = new SafeModelPart(list, map);
        modelPart.loadPose(this.partPose);
        return modelPart;
    }
}
