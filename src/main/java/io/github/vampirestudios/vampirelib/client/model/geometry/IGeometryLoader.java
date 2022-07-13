package io.github.vampirestudios.vampirelib.client.model.geometry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * A loader for custom {@linkplain IUnbakedGeometry model geometries}.
 * <p>
 */
public interface IGeometryLoader<T extends IUnbakedGeometry<T>> {
	T read(JsonObject jsonObject, JsonDeserializationContext deserializationContext)
		throws JsonParseException;
}