package io.github.vampirestudios.vampirelib.mixins.client;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.util.GsonHelper;

import io.github.vampirestudios.vampirelib.api.extensions.BlockModelExtensions;
import io.github.vampirestudios.vampirelib.client.model.IModelGeometry;
import io.github.vampirestudios.vampirelib.client.model.ModelLoaderRegistry;

@Mixin(BlockModel.Deserializer.class)
public abstract class BlockModelDeserializerMixin {

	@Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/renderer/block/model/BlockModel;", at = @At("RETURN"), cancellable = true)
	public void modelLoading(JsonElement element, Type type, JsonDeserializationContext deserializationContext, CallbackInfoReturnable<BlockModel> cir) {
		BlockModel model = cir.getReturnValue();
		JsonObject jsonobject = element.getAsJsonObject();
		IModelGeometry<?> geometry = ModelLoaderRegistry.deserializeGeometry(deserializationContext, jsonobject);

		List<BlockElement> elements = model.getElements();
		if (geometry != null) {
			elements.clear();
			((BlockModelExtensions) model).getGeometry().setCustomGeometry(geometry);
		}

		ModelState modelState = ModelLoaderRegistry.deserializeModelTransforms(deserializationContext, jsonobject);
		if (modelState != null) {
			((BlockModelExtensions) model).getGeometry().setCustomModelState(modelState);
		}

		if (jsonobject.has("visibility")) {
			JsonObject visibility = GsonHelper.getAsJsonObject(jsonobject, "visibility");
			for (Map.Entry<String, JsonElement> part : visibility.entrySet()) {
				((BlockModelExtensions) model).getGeometry().visibilityData.setVisibilityState(part.getKey(), part.getValue().getAsBoolean());
			}
		}
	}
}