package io.github.vampirestudios.vampirelib.impl.boat.mixin;

import com.mojang.datafixers.util.Pair;
import io.github.vampirestudios.vampirelib.impl.boat.TerraformBoatEntity;
import io.github.vampirestudios.vampirelib.impl.boat.client.TerraformBoatEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(BoatRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinBoatEntityRenderer {
	@Redirect(method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private Object getTerraformBoatTextureAndModel(Map<Boat.Type, Pair<ResourceLocation, Boat>> map, Object type, Boat entity) {
		if (entity instanceof TerraformBoatEntity && (Object) this instanceof TerraformBoatEntityRenderer boatEntityRenderer) {
			return boatEntityRenderer.getTextureAndModel((TerraformBoatEntity) entity);
		}
		return map.get(type);
	}
}
