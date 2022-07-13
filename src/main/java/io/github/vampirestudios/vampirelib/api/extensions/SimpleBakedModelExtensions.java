package io.github.vampirestudios.vampirelib.api.extensions;

import java.util.List;

import org.quiltmc.qsl.base.api.util.InjectedInterface;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.SimpleBakedModel;

import io.github.vampirestudios.vampirelib.client.ChunkRenderTypeSet;
import io.github.vampirestudios.vampirelib.client.RenderTypeGroup;

@InjectedInterface(SimpleBakedModel.class)
public interface SimpleBakedModelExtensions {
	ChunkRenderTypeSet getBlockRenderTypes();

	List<RenderType> getItemRenderTypes();

	List<RenderType> getFabulousItemRenderTypes();

	void setRenderType(RenderTypeGroup renderTypeGroup);

	/*ChunkRenderTypeSet getRenderTypes(@org.jetbrains.annotations.NotNull BlockState state, @org.jetbrains.annotations.NotNull RandomSource rand, @org.jetbrains.annotations.NotNull ModelData data);

	List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous);*/
}
