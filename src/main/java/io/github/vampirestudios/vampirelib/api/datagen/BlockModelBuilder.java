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

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

public class BlockModelBuilder extends ModelBuilder {
	private boolean occlude = true;

	private BlockModelBuilder(ResourceLocation parent) {
		super(parent);
	}

	public static BlockModelBuilder createNew(ResourceLocation parent) {
		return new BlockModelBuilder(parent);
	}

	public static BlockModelBuilder copyFrom(ModelTemplate model) {
		BlockModelBuilder builder = new BlockModelBuilder(model.getModel().orElse(null));
		builder.requiredTextures.addAll(model.getRequiredSlots());
		builder.displays.putAll(model.getDisplayBuilders());
		builder.elements.addAll(model.getElementBuilders());
		builder.occlude = model.getAmbientOcclusion();
		return builder;
	}

	@Override
	public BlockModelBuilder addTextureKey(TextureSlot texture) {
		return (BlockModelBuilder) super.addTextureKey(texture);
	}

	@Override
	public BlockModelBuilder addDisplay(DisplayBuilder.Position position, DisplayBuilder display) {
		return (BlockModelBuilder) super.addDisplay(position, display);
	}

	@Override
	public BlockModelBuilder addElement(ElementBuilder element) {
		return (BlockModelBuilder) super.addElement(element);
	}

	/**
	 * Disables ambient occlusion for this model.
	 */
	public BlockModelBuilder noAmbientOcclusion() {
		this.occlude = false;
		return this;
	}

	@Override
	public ModelTemplate build() {
		ModelTemplate blockModel = super.build();
		blockModel.setAmbientOcclusion(occlude);
		return blockModel;
	}
}
