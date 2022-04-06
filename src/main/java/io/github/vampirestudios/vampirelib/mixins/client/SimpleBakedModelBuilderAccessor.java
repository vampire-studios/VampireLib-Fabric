package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.SimpleBakedModel;

@Mixin(SimpleBakedModel.Builder.class)
public interface SimpleBakedModelBuilderAccessor {
	@Invoker("<init>")
	static SimpleBakedModel.Builder vl$create(boolean hasAmbientOcclusion, boolean usesBlockLight,
											  boolean isGui3d, ItemTransforms transforms,
											  ItemOverrides overrides) {
		throw new RuntimeException("mixin failed!");
	}
}