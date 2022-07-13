/*
package io.github.vampirestudios.vampirelib.mixins.client;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.Util;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import io.github.vampirestudios.vampirelib.api.extensions.ItemBlockRenderTypesExtensions;
import io.github.vampirestudios.vampirelib.client.ChunkRenderTypeSet;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin implements ItemBlockRenderTypesExtensions {
	@Shadow @Final private static Map<Block, RenderType> TYPE_BY_BLOCK;
	@Shadow @Final private static Map<Fluid, RenderType> TYPE_BY_FLUID;
	@Shadow private static boolean renderCutout;

	private static final ChunkRenderTypeSet CUTOUT_MIPPED = ChunkRenderTypeSet.of(RenderType.cutoutMipped());
	private static final ChunkRenderTypeSet SOLID = ChunkRenderTypeSet.of(RenderType.solid());
	private static final Map<Holder.Reference<Block>, ChunkRenderTypeSet> BLOCK_RENDER_TYPES = Util.make(new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(TYPE_BY_BLOCK.size(), 0.5F), map -> {
		map.defaultReturnValue(SOLID);
		for (Map.Entry<Block, RenderType> entry : TYPE_BY_BLOCK.entrySet()) {
			map.put(Registry.BLOCK.createIntrusiveHolder(entry.getKey()), ChunkRenderTypeSet.of(entry.getValue()));
		}
	});
	private static final Map<Holder.Reference<Fluid>, RenderType> FLUID_RENDER_TYPES = Util.make(new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(TYPE_BY_FLUID.size(), 0.5F), map -> {
		map.defaultReturnValue(RenderType.solid());
		for (Map.Entry<Fluid, RenderType> entry : TYPE_BY_FLUID.entrySet()) {
			map.put(Registry.FLUID.createIntrusiveHolder(entry.getKey()), entry.getValue());
		}
	});

	@Override
	public ChunkRenderTypeSet getRenderLayers(BlockState state) {
		Block block = state.getBlock();
		if (block instanceof LeavesBlock) {
			return renderCutout ? CUTOUT_MIPPED : SOLID;
		} else {
			return BLOCK_RENDER_TYPES.get(Registry.BLOCK.createIntrusiveHolder(block));
		}
	}

	public void setRenderLayer(Fluid fluid, RenderType type) {
		com.google.common.base.Preconditions.checkArgument(type.getChunkLayerId() >= 0, "The argument must be a valid chunk render type returned by RenderType#chunkBufferLayers().");
		FLUID_RENDER_TYPES.put(Registry.FLUID.createIntrusiveHolder(fluid), type);
	}

	private static ChunkRenderTypeSet createSetFromPredicate(java.util.function.Predicate<RenderType> predicate) {
		return ChunkRenderTypeSet.of(RenderType.chunkBufferLayers().stream().filter(predicate).toArray(RenderType[]::new));
	}

	*/
/**
	 * @author Olivia
	 * @reason Needed
	 *//*

	@Overwrite
	public static RenderType getRenderLayer(FluidState fluidState) {
		RenderType rendertype = FLUID_RENDER_TYPES.get(Registry.FLUID.createIntrusiveHolder(fluidState.getType()));
		return rendertype != null ? rendertype : RenderType.solid();
	}
}
*/
