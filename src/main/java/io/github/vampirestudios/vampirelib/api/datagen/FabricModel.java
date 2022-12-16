/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

public interface FabricModel {
	default ModelTemplate withDisplay(DisplayBuilder.Position position, DisplayBuilder builder) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default EnumMap<DisplayBuilder.Position, DisplayBuilder> getDisplayBuilders() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default ModelTemplate addElement(ElementBuilder builder) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default List<ElementBuilder> getElementBuilders() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default ModelTemplate addOverride(OverrideBuilder builder) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default List<OverrideBuilder> getOverrideBuilders() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default ModelTemplate setGuiLight(ItemModelBuilder.GuiLight light) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default ItemModelBuilder.GuiLight getGuiLight() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default ModelTemplate setAmbientOcclusion(boolean occlude) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default boolean getAmbientOcclusion() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default Optional<ResourceLocation> getModel() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default Optional<String> getVariant() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	default Set<TextureSlot> getRequiredSlots() {
		throw new UnsupportedOperationException("Implemented via mixin");
	}
}
