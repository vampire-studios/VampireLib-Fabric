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

package io.github.vampirestudios.vampirelib.mixins;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import io.github.vampirestudios.vampirelib.api.datagen.DisplayBuilder;
import io.github.vampirestudios.vampirelib.api.datagen.ElementBuilder;
import io.github.vampirestudios.vampirelib.api.datagen.FabricModel;
import io.github.vampirestudios.vampirelib.api.datagen.ItemModelBuilder;
import io.github.vampirestudios.vampirelib.api.datagen.OverrideBuilder;

@Mixin(ModelTemplate.class)
public class ModelMixin implements FabricModel {
	@Shadow
	@Final
	private Optional<ResourceLocation> model;
	@Shadow
	@Final
	private Set<TextureSlot> requiredSlots;
	@Shadow
	@Final
	private Optional<String> variant;
	@Unique
	private final EnumMap<DisplayBuilder.Position, DisplayBuilder> displays = new EnumMap<>(DisplayBuilder.Position.class);
	@Unique
	private final List<ElementBuilder> elements = new ArrayList<>();
	@Unique
	private final List<OverrideBuilder> overrides = new ArrayList<>();
	@Unique
	private ItemModelBuilder.GuiLight guiLight = null;
	@Unique
	private boolean ambientOcclusion = true;

	@Override
	public Optional<ResourceLocation> getModel() {
		return model;
	}

	@Override
	public Set<TextureSlot> getRequiredSlots() {
		return requiredSlots;
	}

	@Override
	public Optional<String> getVariant() {
		return variant;
	}

	@Override
	public ModelTemplate withDisplay(DisplayBuilder.Position position, DisplayBuilder builder) {
		this.displays.put(position, builder);
		return (ModelTemplate) (Object) this;
	}

	@Override
	public EnumMap<DisplayBuilder.Position, DisplayBuilder> getDisplayBuilders() {
		return displays;
	}

	@Override
	public ModelTemplate addElement(ElementBuilder builder) {
		this.elements.add(builder);
		return (ModelTemplate) (Object) this;
	}

	@Override
	public List<ElementBuilder> getElementBuilders() {
		return elements;
	}

	@Override
	public ModelTemplate addOverride(OverrideBuilder builder) {
		this.overrides.add(builder);
		return (ModelTemplate) (Object) this;
	}

	@Override
	public List<OverrideBuilder> getOverrideBuilders() {
		return overrides;
	}

	@Override
	public ModelTemplate setGuiLight(ItemModelBuilder.GuiLight guiLight) {
		this.guiLight = guiLight;
		return (ModelTemplate) (Object) this;
	}

	@Override
	public ItemModelBuilder.GuiLight getGuiLight() {
		return guiLight;
	}

	@Override
	public ModelTemplate setAmbientOcclusion(boolean ambientOcclusion) {
		this.ambientOcclusion = ambientOcclusion;
		return (ModelTemplate) (Object) this;
	}

	@Override
	public boolean getAmbientOcclusion() {
		return ambientOcclusion;
	}

	@Inject(method = "method_25851", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void addExtraProperties(Map<TextureSlot, ResourceLocation> map, CallbackInfoReturnable<JsonElement> cir, JsonObject jsonObject) {
		if (!displays.isEmpty()) {
			JsonObject display = new JsonObject();
			this.displays.forEach((p, d) -> display.add(p.name().toLowerCase(), d.build()));
			jsonObject.add("display", display);
		}

		if (!elements.isEmpty()) {
			JsonArray elements = new JsonArray();
			this.elements.forEach(e -> elements.add(e.build()));
			jsonObject.add("elements", elements);
		}

		if (!overrides.isEmpty()) {
			JsonArray overrides = new JsonArray();
			this.overrides.forEach(o -> overrides.add(o.build()));
			jsonObject.add("overrides", overrides);
		}

		if (guiLight != null) {
			jsonObject.addProperty("gui_light", guiLight.name().toLowerCase());
		}

		if (!ambientOcclusion) {
			jsonObject.addProperty("ambientocclusion", false);
		}

		displays.clear();
		elements.clear();
		overrides.clear();
		guiLight = null;
		ambientOcclusion = true;
	}
}
