package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.function.BiConsumer;

import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class LayerDefinitionProvider extends JsonCodecProvider<LayerDefinition> {
	public LayerDefinitionProvider(PackOutput output) {
		super(output, PackOutput.Target.RESOURCE_PACK, "models/entity", ModelCodecs.LAYER_DEFINITION);
	}

	@Override
	protected void configure(BiConsumer<ResourceLocation, LayerDefinition> provider) {
		provider.accept(new ResourceLocation("sniffer"), SnifferModel.createBodyLayer());
		provider.accept(new ResourceLocation("bat"), BatModel.createBodyLayer());
	}

	@Override
	public String getName() {
		return "Layer Definition Generator";
	}
}
