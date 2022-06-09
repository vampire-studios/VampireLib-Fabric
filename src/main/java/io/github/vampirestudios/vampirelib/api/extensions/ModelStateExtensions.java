package io.github.vampirestudios.vampirelib.api.extensions;

import com.mojang.math.Transformation;

public interface ModelStateExtensions {
	default Transformation getPartTransformation(Object part) {
		return Transformation.identity();
	}
}