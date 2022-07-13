package io.github.vampirestudios.vampirelib.api.datagen;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;

@VisibleForTesting
public interface IGeneratedBlockState {
	JsonObject toJson();
}