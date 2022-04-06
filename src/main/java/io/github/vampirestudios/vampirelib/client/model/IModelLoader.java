package io.github.vampirestudios.vampirelib.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public interface IModelLoader<T extends IModelGeometry<T>> extends ResourceManagerReloadListener {
	T read(JsonDeserializationContext deserializationContext, JsonObject modelContents);
}