package io.github.vampirestudios.vampirelib.init;

import org.jetbrains.annotations.ApiStatus;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.resources.ResourceLocation;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.api.ShaderRegistry;

@ApiStatus.Internal
public class VShaderTypes {

    public static final ResourceLocation RENDERTYPE_GEOMETRY_SOLID = new ResourceLocation(VampireLib.INSTANCE.modId(), "rendertype_geometry_solid");
    public static final ResourceLocation RENDERTYPE_GEOMETRY_CUTOUT = new ResourceLocation(VampireLib.INSTANCE.modId(), "rendertype_geometry_cutout");
    public static final ResourceLocation RENDERTYPE_GEOMETRY_CUTOUT_CULL = new ResourceLocation(VampireLib.INSTANCE.modId(), "rendertype_geometry_cutout_cull");
    public static final ResourceLocation RENDERTYPE_GEOMETRY_TRANSLUCENT = new ResourceLocation(VampireLib.INSTANCE.modId(), "rendertype_geometry_translucent");
    public static final ResourceLocation RENDERTYPE_GEOMETRY_TRANSLUCENT_CULL = new ResourceLocation(VampireLib.INSTANCE.modId(), "rendertype_geometry_translucent_cull");

    public static void init() {
        ShaderRegistry.register(RENDERTYPE_GEOMETRY_SOLID, DefaultVertexFormat.NEW_ENTITY);
        ShaderRegistry.register(RENDERTYPE_GEOMETRY_CUTOUT, DefaultVertexFormat.NEW_ENTITY);
        ShaderRegistry.register(RENDERTYPE_GEOMETRY_CUTOUT_CULL, DefaultVertexFormat.NEW_ENTITY);
        ShaderRegistry.register(RENDERTYPE_GEOMETRY_TRANSLUCENT, DefaultVertexFormat.NEW_ENTITY);
        ShaderRegistry.register(RENDERTYPE_GEOMETRY_TRANSLUCENT_CULL, DefaultVertexFormat.NEW_ENTITY);
    }
}