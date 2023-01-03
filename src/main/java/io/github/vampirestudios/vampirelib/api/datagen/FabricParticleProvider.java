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

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

/**
 * Extend this class and implement {@link FabricParticleProvider#generateParticleTextures(ParticleGenerator)}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class FabricParticleProvider implements DataProvider {
	protected final FabricDataOutput dataOutput;
	private final PackOutput.PathProvider pathResolver;

	protected FabricParticleProvider(FabricDataOutput dataOutput) {
		this.dataOutput = dataOutput;
		this.pathResolver = dataOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "particles");
	}

	/**
	 * Implement this method to register particle textures.
	 *
	 * <p>Call {@link ParticleGenerator#add(net.minecraft.core.particles.ParticleType, ResourceLocation...)} to add a list of texture IDs for a given
	 * {@link ParticleType}.
	 */
	protected abstract void generateParticleTextures(ParticleGenerator particleGenerator);

	@Override
	public CompletableFuture<?> run(CachedOutput writer) {
		final Set<ResourceLocation> identifiers = Sets.newHashSet();
		HashMap<ResourceLocation, JsonArray> particleJsons = new HashMap<>();

		generateParticleTextures(((particle, textures) -> {
			Objects.requireNonNull(particle);
			JsonArray textureEntries = new JsonArray();
			Sets.newHashSet(textures).forEach(t -> textureEntries.add(t.toString()));
			particleJsons.put(BuiltInRegistries.PARTICLE_TYPE.getKey(particle), textureEntries);
		}));

		final List<CompletableFuture<?>> futures = new ArrayList<>();

		for (Map.Entry<ResourceLocation, JsonArray> particleJson : particleJsons.entrySet()) {
			if (!identifiers.add(particleJson.getKey())) {
				throw new IllegalStateException("Duplicate particle " + particleJson.getKey());
			}

			JsonObject particleFile = new JsonObject();
			particleFile.add("textures", particleJson.getValue());
			futures.add(DataProvider.saveStable(writer, particleFile, pathResolver.json(particleJson.getKey())));
		}

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	@Override
	public String getName() {
		return "Particles";
	}

	@ApiStatus.NonExtendable
	@FunctionalInterface
	public interface ParticleGenerator {
		/**
		 * Adds a new JSON file for a given particle.
		 *
		 * @param particle The {@link ParticleType} to generate a file for.
		 * @param textures The IDs for any textures to apply to the given particle.
		 */
		void add(ParticleType<?> particle, ResourceLocation... textures);
	}
}
