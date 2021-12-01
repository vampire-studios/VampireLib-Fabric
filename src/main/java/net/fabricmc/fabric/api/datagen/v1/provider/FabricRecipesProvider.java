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

package net.fabricmc.fabric.api.datagen.v1.provider;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Extend this class and implement {@link FabricRecipesProvider#generateRecipes}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricRecipesProvider extends RecipeProvider {
	protected final FabricDataGenerator dataGenerator;

	public FabricRecipesProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
		this.dataGenerator = dataGenerator;
	}

	/**
	 * Implement this method and then use the range of methods in {@link RecipeProvider} or from one of the recipe json factories such as {@link net.minecraft.data.recipes.ShapedRecipeBuilder} and {@link net.minecraft.data.recipes.ShapelessRecipeBuilder}.
	 */
	protected abstract void generateRecipes(Consumer<FinishedRecipe> exporter);

	@Override
	public void run(HashCache cache) {
		Path path = this.dataGenerator.getOutputFolder();
		Set<ResourceLocation> generatedRecipes = Sets.newHashSet();
		generateRecipes(provider -> {
			ResourceLocation identifier = getRecipeIdentifier(provider.getId());

			if (!generatedRecipes.add(identifier)) {
				throw new IllegalStateException("Duplicate recipe " + identifier);
			}

			saveRecipe(cache, provider.serializeRecipe(), path.resolve("data/" + identifier.getNamespace() + "/recipes/" + identifier.getPath() + ".json"));
			JsonObject jsonObject = provider.serializeAdvancement();

			if (jsonObject != null) {
				saveAdvancement(cache, jsonObject, path.resolve("data/" + identifier.getNamespace() + "/advancements/" + provider.getAdvancementId().getPath() + ".json"));
			}
		});
	}

	/**
	 * Override this method to change the recipe identifier. The default implementation normalizes the namespace to the mod ID.
	 */
	protected ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
		return new ResourceLocation(dataGenerator.getModId(), identifier.getPath());
	}
}