package io.github.vampirestudios.vampirelib.impl.boat.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatType;
import io.github.vampirestudios.vampirelib.api.boat.TerraformBoatTypeRegistry;
import io.github.vampirestudios.vampirelib.api.boat.client.TerraformBoatClientHelper;
import io.github.vampirestudios.vampirelib.impl.boat.TerraformBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class TerraformBoatEntityRenderer extends BoatRenderer {
	private final Map<TerraformBoatType, Pair<ResourceLocation, BoatModel>> texturesAndModels;

	public TerraformBoatEntityRenderer(EntityRendererProvider.Context context) {
		super(context);

		this.texturesAndModels = TerraformBoatTypeRegistry.INSTANCE.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getValue, entry -> {
			ResourceLocation id = entry.getKey().location();
			ResourceLocation textureId = new ResourceLocation(id.getNamespace(), "textures/wood_types/" + id.getPath() + "/boat.png");

			ModelLayerLocation layer = TerraformBoatClientHelper.getLayer(id);
			BoatModel model = new BoatModel(context.bakeLayer(layer));

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public ResourceLocation getTextureLocation(Boat entity) {
		if (entity instanceof TerraformBoatEntity) {
			TerraformBoatType boat = ((TerraformBoatEntity) entity).getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTextureLocation(entity);
	}

	public Pair<ResourceLocation, BoatModel> getTextureAndModel(TerraformBoatEntity entity) {
		return this.texturesAndModels.get(entity.getTerraformBoat());
	}
}
