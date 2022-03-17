package com.terraformersmc.terraform.boat.impl.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;
import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TerraformBoatEntityRenderer extends BoatRenderer {
	private final Map<TerraformBoatType, Pair<ResourceLocation, BoatModel>> texturesAndModels;

	public TerraformBoatEntityRenderer(EntityRendererProvider.Context context) {
		super(context);

		this.texturesAndModels = TerraformBoatTypeRegistry.INSTANCE.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getValue, entry -> {
			ResourceLocation id = entry.getKey().location();
			ResourceLocation textureId = new ResourceLocation(id.getNamespace(), "textures/entity/boat/" + id.getPath() + ".png");

			ModelLayerLocation layer = TerraformBoatClientHelper.getLayer(id);
			BoatModel model = new BoatModel(context.bakeLayer(layer));

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull Boat entity) {
		if (entity instanceof TerraformBoatEntity terraformBoatEntity) {
			TerraformBoatType boat = terraformBoatEntity.getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTextureLocation(entity);
	}

	public Pair<ResourceLocation, BoatModel> getTextureAndModel(TerraformBoatEntity entity) {
		return this.texturesAndModels.get(entity.getTerraformBoat());
	}
}
