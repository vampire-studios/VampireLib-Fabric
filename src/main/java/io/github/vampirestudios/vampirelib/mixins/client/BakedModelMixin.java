package io.github.vampirestudios.vampirelib.mixins.client;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.api.extensions.IForgeBakedModel;
import io.github.vampirestudios.vampirelib.client.model.data.ModelData;

@Mixin(BakedModel.class)
public interface BakedModelMixin extends IForgeBakedModel {

	@Shadow List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, RandomSource randomSource);

	@Shadow boolean useAmbientOcclusion();

	@Shadow ItemTransforms getTransforms();

	@Shadow TextureAtlasSprite getParticleIcon();

	@Override
	default @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
		return this.getQuads(state, side, rand);
	}

	@Override
	default boolean useAmbientOcclusion(BlockState state) {
		return this.useAmbientOcclusion();
	}

	@Override
	default BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
		this.getTransforms().getTransform(transformType).apply(applyLeftHandTransform, poseStack);
		return (BakedModel) this;
	}

	@Override
	default TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
		return this.getParticleIcon();
	}

	@Override
	default List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
		return List.of((BakedModel) this);
	}

}