/*
package io.github.vampirestudios.vampirelib.client.model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.client.model.geometry.GeometryLoaderManager;
import net.minecraftforge.common.util.TransformationHelper;
import org.jetbrains.annotations.Nullable;

import com.mojang.math.Transformation;

import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import io.github.vampirestudios.vampirelib.client.model.geometry.IUnbakedGeometry;

*/
/**
 * A version of {@link BlockModel.Deserializer} capable of deserializing models with custom loaders, as well as other
 * changes introduced to the spec by Forge.
 *//*

public class ExtendedBlockModelDeserializer extends BlockModel.Deserializer {
	public static final Gson INSTANCE = (new GsonBuilder())
		.registerTypeAdapter(BlockModel.class, new ExtendedBlockModelDeserializer())
		.registerTypeAdapter(BlockElement.class, new BlockElement.Deserializer())
		.registerTypeAdapter(BlockElementFace.class, new BlockElementFace.Deserializer())
		.registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
		.registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer())
		.registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer())
		.registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer())
		.registerTypeAdapter(Transformation.class, new TransformationHelper.Deserializer())
		.create();

	@Nullable
	public static IUnbakedGeometry<?> deserializeGeometry(JsonDeserializationContext deserializationContext, JsonObject object)
		throws JsonParseException {
		if (!object.has("loader"))
			return null;

		var name = new ResourceLocation(GsonHelper.getAsString(object, "loader"));
		var loader = GeometryLoaderManager.get(name);
		if (loader == null)
			throw new JsonParseException(String.format(Locale.ENGLISH, "Model loader '%s' not found. Registered loaders: %s", name, GeometryLoaderManager.getLoaderList()));

		return loader.read(object, deserializationContext);
	}

	public BlockModel deserialize(JsonElement element, Type targetType, JsonDeserializationContext deserializationContext)
		throws JsonParseException {
		BlockModel model = super.deserialize(element, targetType, deserializationContext);
		JsonObject jsonobject = element.getAsJsonObject();
		IUnbakedGeometry<?> geometry = deserializeGeometry(deserializationContext, jsonobject);

		List<BlockElement> elements = model.getElements();
		if (geometry != null) {
			elements.clear();
			model.customData.setCustomGeometry(geometry);
		}

		if (jsonobject.has("transform")) {
			JsonObject transform = GsonHelper.getAsJsonObject(jsonobject, "transform");
			model.customData.setRootTransform(deserializationContext.deserialize(transform, Transformation.class));
		}

		if (jsonobject.has("render_type")) {
			var renderTypeHintName = GsonHelper.getAsString(jsonobject, "render_type");
			model.customData.setRenderTypeHint(new ResourceLocation(renderTypeHintName));
		}

		if (jsonobject.has("visibility")) {
			JsonObject visibility = GsonHelper.getAsJsonObject(jsonobject, "visibility");
			for (Map.Entry<String, JsonElement> part : visibility.entrySet()) {
				model.customData.visibilityData.setVisibilityState(part.getKey(), part.getValue().getAsBoolean());
			}
		}

		return model;
	}
}*/
