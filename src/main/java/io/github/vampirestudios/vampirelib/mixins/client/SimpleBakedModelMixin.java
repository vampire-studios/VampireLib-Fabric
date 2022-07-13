package io.github.vampirestudios.vampirelib.mixins.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.SimpleBakedModel;

import io.github.vampirestudios.vampirelib.api.extensions.SimpleBakedModelExtensions;
import io.github.vampirestudios.vampirelib.client.ChunkRenderTypeSet;
import io.github.vampirestudios.vampirelib.client.RenderTypeGroup;

@Mixin(SimpleBakedModel.class)
public class SimpleBakedModelMixin implements SimpleBakedModelExtensions {
	protected ChunkRenderTypeSet blockRenderTypes;
	protected List<RenderType> itemRenderTypes;
	protected List<RenderType> fabulousItemRenderTypes;

	@Override
	public ChunkRenderTypeSet getBlockRenderTypes() {
		return blockRenderTypes;
	}

	@Override
	public List<RenderType> getItemRenderTypes() {
		return itemRenderTypes;
	}

	@Override
	public List<RenderType> getFabulousItemRenderTypes() {
		return fabulousItemRenderTypes;
	}

	@Override
	public void setRenderType(RenderTypeGroup renderTypeGroup) {
		this.blockRenderTypes = !renderTypeGroup.isEmpty() ? ChunkRenderTypeSet.of(renderTypeGroup.block()) : null;
		this.itemRenderTypes = !renderTypeGroup.isEmpty() ? List.of(renderTypeGroup.entity()) : null;
		this.fabulousItemRenderTypes = !renderTypeGroup.isEmpty() ? List.of(renderTypeGroup.entityFabulous()) : null;
	}

	/*@Override
	public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
		if (blockRenderTypes != null) return blockRenderTypes;
		return BakedModel.super.getRenderTypes(state, rand, data);
	}

	@Override
	public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
		if (!fabulous) {
			if (itemRenderTypes != null) return itemRenderTypes;
		} else {
			if (fabulousItemRenderTypes != null) return fabulousItemRenderTypes;
		}
		return BakedModel.super.getRenderTypes(itemStack, fabulous);
	}*/
}
