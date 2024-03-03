/*
 * Copyright (c) 2024 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.joml.Vector3d;

/**
 * Instantiate this class in order to provide an optional set of <code>display</code> properties for a given model JSON.
 */
public class DisplayBuilder {
	private Vector3d rotation = new Vector3d();
	private Vector3d translation = new Vector3d();
	private Vector3d scale = new Vector3d();

	/**
	 * Rotate the model by the given vector.
	 *
	 * @param x The X-coordinate of the rotation.
	 * @param y The Y-coordinate of the rotation.
	 * @param z The Z-coordinate of the rotation.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder rotate(double x, double y, double z) {
		return rotate(new Vector3d(x, y, z));
	}

	/**
	 * Rotate the model by the given vector.
	 *
	 * @param vec A rotation vector.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder rotate(Vector3d vec) {
		this.rotation = vec;
		return this;
	}

	/**
	 * Translate the model by the given vector.
	 *
	 * @param x The X-coordinate of the translation.
	 * @param y The Y-coordinate of the translation.
	 * @param z The Z-coordinate of the translation.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder translate(double x, double y, double z) {
		return translate(new Vector3d(x, y, z));
	}

	/**
	 * Translate the model by the given vector.
	 *
	 * @param vec A translation vector.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder translate(Vector3d vec) {
		this.translation = vec;
		return this;
	}

	/**
	 * Scale the model by the given scale factor.
	 *
	 * @param f The constant factor to scale the model by in all three cardinal directions.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder scale(double f) {
		return scale(new Vector3d(f));
	}

	/**
	 * Scale the model by the given vector.
	 *
	 * @param x The scalar for the X-direction.
	 * @param y The scalar for the Y-direction.
	 * @param z The scalar for the Z-direction.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder scale(double x, double y, double z) {
		return scale(new Vector3d(x, y, z));
	}

	/**
	 * Scale the model by the given vector.
	 *
	 * @param vec A scale vector.
	 * @return The current newly-modified {@link DisplayBuilder} instance.
	 */
	public DisplayBuilder scale(Vector3d vec) {
		this.scale = vec;
		return this;
	}

	private JsonArray vecArray(Vector3d vec) {
		JsonArray entry = new JsonArray();
		entry.add(vec.x());
		entry.add(vec.y());
		entry.add(vec.z());
		return entry;
	}

	public JsonObject build() {
		JsonObject display = new JsonObject();
		Vector3d zero = new Vector3d();

		if (!Objects.equals(rotation, zero)) {
			display.add("rotation", vecArray(rotation));
		}

		if (!Objects.equals(translation, zero)) {
			display.add("translation", vecArray(translation));
		}

		if (!Objects.equals(scale, zero)) {
			display.add("scale", vecArray(scale));
		}

		return display;
	}

	/**
	 * The place where a given set of display transformations should be applied. <code>FIXED</code> refers to the item
	 * model as attached to an <em>item frame</em>, while the others are as their name implies.
	 */
	public enum Position {
		THIRDPERSON_RIGHTHAND,
		THIRDPERSON_LEFTHAND,
		FIRSTPERSON_RIGHTHAND,
		FIRSTPERSON_LEFTHAND,
		GUI,
		HEAD,
		GROUND,
		FIXED
	}
}
