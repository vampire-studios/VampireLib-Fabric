package io.github.vampirestudios.vampirelib.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;

import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.resources.model.BakedModel;

import io.github.vampirestudios.vampirelib.api.TransformationExtensions;
import io.github.vampirestudios.vampirelib.client.TransformationHelper;

public interface TransformTypeDependentItemBakedModel {
	default BakedModel handlePerspective(TransformType type, PoseStack stack) {
		Transformation tr = TransformationHelper.toTransformation(((BakedModel) this).getTransforms().getTransform(type));
		if(!((TransformationExtensions)(Object)tr).isIdentity()) {
			((TransformationExtensions)(Object)tr).push(stack);
		}
		return (BakedModel) this;
	}
}