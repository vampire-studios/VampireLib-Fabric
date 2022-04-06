package io.github.vampirestudios.vampirelib.api;

import com.mojang.math.Transformation;

public interface ModelStateExtensions {
	default Transformation getPartTransformation(Object part) {
		return Transformation.identity();
	}
}