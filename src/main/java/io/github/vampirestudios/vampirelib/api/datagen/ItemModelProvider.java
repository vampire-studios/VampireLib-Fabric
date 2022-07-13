package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import io.github.vampirestudios.vampirelib.client.model.ModelFile;

/**
 * Stub class to extend for item model data providers, eliminates some
 * boilerplate constructor parameters.
 */
public abstract class ItemModelProvider extends ModelProvider<ItemModelBuilder> {

	public ItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, ITEM_FOLDER, ItemModelBuilder::new, existingFileHelper);
	}

	public ItemModelBuilder basicItem(Item item) {
		return basicItem(Objects.requireNonNull(Registry.ITEM.getKey(item)));
	}

	public ItemModelBuilder basicItem(ResourceLocation item) {
		return getBuilder(item.toString())
			.parent(new ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
	}

	@NotNull
	@Override
	public String getName() {
		return "Item Models: " + modid;
	}
}