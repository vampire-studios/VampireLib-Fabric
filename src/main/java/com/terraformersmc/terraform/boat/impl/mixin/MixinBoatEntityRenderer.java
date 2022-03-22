package com.terraformersmc.terraform.boat.impl.mixin;

import java.util.Map;

import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.client.TerraformBoatEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Mixin(BoatRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinBoatEntityRenderer {
	@Redirect(method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private Object getTerraformBoatTextureAndModel(Map<Boat.Type, Pair<ResourceLocation, BoatModel>> map, Object type, Boat entity) {
		if (entity instanceof TerraformBoatEntity terraformBoatEntity && (Object) this instanceof TerraformBoatEntityRenderer terraformBoatEntityRenderer) {
			return terraformBoatEntityRenderer.getTextureAndModel(terraformBoatEntity);
		}
		return map.get(type);
	}
}