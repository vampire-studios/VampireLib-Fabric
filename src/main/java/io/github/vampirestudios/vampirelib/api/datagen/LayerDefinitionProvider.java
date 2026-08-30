package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.function.BiConsumer;

import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.model.animal.sniffer.SnifferModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import io.github.vampirestudios.vampirelib.client.EntityModelCodecHolder;

public class LayerDefinitionProvider extends JsonCodecProvider<LayerDefinition> {
	public LayerDefinitionProvider(PackOutput output) {
		super(output, PackOutput.Target.RESOURCE_PACK, "models/entity", EntityModelCodecHolder.LAYER_DEFINITION_CODEC);
	}

	@Override
	protected void configure(BiConsumer<Identifier, LayerDefinition> provider) {
		provider.accept(Identifier.withDefaultNamespace("sniffer"), SnifferModel.createBodyLayer());
		provider.accept(Identifier.withDefaultNamespace("bat"), BatModel.createBodyLayer());
	}

	@Override
	public String getName() {
		return "Layer Definition Generator";
	}
}
