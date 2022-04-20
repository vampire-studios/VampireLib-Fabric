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

package io.github.vampirestudios.vampirelib.api;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricSoundProvider implements DataProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(FabricSoundProvider.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

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
            if (!sounds.add(sound))
                throw new IllegalStateException("Duplicate sound " + sound.getSoundId());
        };

        this.registerSounds(registry);

        JsonObject json = new JsonObject();
        sounds.stream().sorted(Comparator.comparing(SoundDefinition::getSoundId)).forEachOrdered(definition -> json.add(definition.getSoundId(), definition.toJson()));

        try {
            DataProvider.save(GSON, cache, json, path);
        } catch (IOException e) {
            LOGGER.error("Couldn't save {}", path, e);
        }
    }

    @Override
    public String getName() {
        return "Sound Definitions";
    }

}