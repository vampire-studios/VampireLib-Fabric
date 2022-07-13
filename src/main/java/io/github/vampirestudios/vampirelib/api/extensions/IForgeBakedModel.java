package io.github.vampirestudios.vampirelib.api.extensions;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.client.model.data.ModelData;

/**
 * Extension interface for {@link IForgeBakedModel}.
 */
@InjectedInterface(BakedModel.class)
public interface IForgeBakedModel {
	/**
	 * A null {@link RenderType} is used for the breaking overlay as well as non-standard rendering, so models should return all their quads.
	 */
	@NotNull
	List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType);

	boolean useAmbientOcclusion(BlockState state);

	default boolean useAmbientOcclusion(BlockState state, RenderType renderType) {
		return useAmbientOcclusion(state);
	}

	/**
	 * Applies a transform for the given {@link ItemTransforms.TransformType} and {@code applyLeftHandTransform}, and
	 * returns the model to be rendered.
	 */
	BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform);

	default @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
		return modelData;
	}

	TextureAtlasSprite getParticleIcon(@NotNull ModelData data);

	/**
	 * Gets the set of {@link RenderType render types} to use when drawing this block in the level.
	 * Supported types are those returned by {@link RenderType#chunkBufferLayers()}.
	 * <p>
	 * By default, defers query to {@link ItemBlockRenderTypes}.
	 */
	//TODO
	/*default ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
		return ItemBlockRenderTypes.getRenderLayers(state);
	}*/

	/**
	 * Gets an ordered list of {@link RenderType render types} to use when drawing this item.
	 * All render types using the {@link com.mojang.blaze3d.vertex.DefaultVertexFormat#NEW_ENTITY} format are supported.
	 * <p>
	 * This method will only be called on the models returned by {@link #getRenderPasses(ItemStack, boolean)}.
	 * <p>
	 * By default, defers query to {@link ItemBlockRenderTypes}.
	 *
	 * @see #getRenderPasses(ItemStack, boolean)
	 */
	default List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
		return List.of(ItemBlockRenderTypes.getRenderType(itemStack, fabulous));
	}

	/**
	 * Gets an ordered list of baked models used to render this model as an item.
	 * Each of those models' render types will be queried via {@link #getRenderTypes(ItemStack, boolean)}.
	 * <p>
	 * By default, returns the model itself.
	 *
	 * @see #getRenderTypes(ItemStack, boolean)
	 */
	List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous);
}