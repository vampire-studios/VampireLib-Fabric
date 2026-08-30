package io.github.vampirestudios.vampirelib.client;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

/**
 * A type of layer definition that can optionally inherit a material definition
 */
public class InheritingLayerDefinition extends LayerDefinition {
    public static final MaterialDefinition DEFAULT_MATERIAL = new MaterialDefinition(64, 32);
    private MaterialDefinition internalMaterial;

    public InheritingLayerDefinition(MeshDefinition mesh, Optional<MaterialDefinition> materialOptional) {
        this(mesh, materialOptional.orElse(null));
    }

    public InheritingLayerDefinition(MeshDefinition mesh, @Nullable MaterialDefinition material) {
        super(mesh, material == null ? DEFAULT_MATERIAL : material);
        this.internalMaterial = material;
    }

    @Override
    public ModelPart bakeRoot() {
        MaterialDefinition material = this.internalMaterial == null ? DEFAULT_MATERIAL : this.internalMaterial;
        return this.mesh.getRoot().bake(material.xTexSize, material.yTexSize);
    }

    @Nullable
    public MaterialDefinition getMaterial() {
        return internalMaterial;
    }

    public void setMaterial(@Nullable MaterialDefinition internalMaterial) {
        this.internalMaterial = internalMaterial;
    }
}
