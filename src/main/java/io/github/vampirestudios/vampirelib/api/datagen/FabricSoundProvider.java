/*
 * Copyright (c) 2022 OliviaTheVampire
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

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class FabricSoundProvider implements DataProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(FabricSoundProvider.class);

	protected final FabricDataGenerator dataGenerator;
	protected final String modId;

	protected FabricSoundProvider(FabricDataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
		this.modId = dataGenerator.getModId();
	}

	/**
	 * Registers all sound instances to be generated.
	 *
	 * @param registry The registry to validate and create files
	 */
	protected abstract void registerSounds(Consumer<SoundDefinition> registry);

	@Override
	public void run(@NotNull CachedOutput cache) throws IOException {
		Path path = this.dataGenerator.getOutputFolder().resolve("assets/" + this.modId + "/sounds.json");
		Set<SoundDefinition> sounds = new HashSet<>();
		Consumer<SoundDefinition> registry = sound -> {
			if (!sounds.add(sound)) {
				throw new IllegalStateException("Duplicate sound " + sound.getSoundId());
			}
		};

		this.registerSounds(registry);

		JsonObject json = new JsonObject();
		sounds.stream().sorted(Comparator.comparing(SoundDefinition::getSoundId))
				.forEachOrdered(definition -> json.add(definition.getSoundId(), definition.toJson()));

		try {
			DataProvider.saveStable(cache, json, path);
		} catch (IOException e) {
			LOGGER.error("Couldn't save {}", path, e);
		}
	}

	@Override
	public String getName() {
		return "Sound Definitions";
	}
}
