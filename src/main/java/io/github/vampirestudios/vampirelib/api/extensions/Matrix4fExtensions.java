package io.github.vampirestudios.vampirelib.api.extensions;

import org.jetbrains.annotations.Contract;

import com.mojang.math.Matrix4f;

public interface Matrix4fExtensions {
	@Contract(mutates = "this")
	default void fromFloatArray(float[] floats) {
		throw new RuntimeException("this should be overridden via mixin. what?");
	}

	default void setTranslation(float x, float y, float z) {
		throw new RuntimeException("this should be overridden via mixin. what?");
	}

	default void multiplyBackward(Matrix4f other) {
		throw new RuntimeException("this should be overridden via mixin. what?");
	}
}