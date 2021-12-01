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

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricSoundProvider implements DataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    protected final FabricDataGenerator dataGenerator;
    protected final String modId;

    private final Map<String, SoundDefinition> sounds = new LinkedHashMap<>();

    protected FabricSoundProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
        this.modId = dataGenerator.getModId();
    }

    @Override
    public void run(HashCache cache) throws IOException {
        this.sounds.clear();
        this.validate();

        if (!this.sounds.isEmpty()) {
            DataProvider.save(GSON, cache, mapToJson(sounds), getOutputPath());
        }
    }

    private Path getOutputPath() {
        return dataGenerator.getOutputFolder().resolve("assets/%s/sounds.json".formatted(this.modId));
    }

    @Override
    public String getName() {
        return "Sound Definitions";
    }

    // Quick helpers

    /**
     * Creates a new {@link SoundDefinition}, which will host a set of
     * {@link SoundDefinition.Sound}s and the necessary parameters.
     */
    protected static SoundDefinition definition() {
        return SoundDefinition.definition();
    }

    /**
     * Creates a new sound with the given name and type.
     *
     * @param name The name of the sound to create.
     * @param type The type of sound to create.
     */
    protected static SoundDefinition.Sound sound(final ResourceLocation name, final SoundDefinition.SoundType type) {
        return SoundDefinition.Sound.sound(name, type);
    }

    /**
     * Creates a new sound with the given name and {@link SoundDefinition.SoundType#SOUND} as
     * sound type.
     *
     * @param name The name of the sound to create.
     */
    protected static SoundDefinition.Sound sound(final ResourceLocation name) {
        return sound(name, SoundDefinition.SoundType.SOUND);
    }

    /**
     * Creates a new sound with the given name and type.
     *
     * @param name The name of the sound to create.
     * @param type The type of sound to create.
     */
    protected static SoundDefinition.Sound sound(final String name, final SoundDefinition.SoundType type) {
        return sound(new ResourceLocation(name), type);
    }

    /**
     * Creates a new sound with the given name and {@link SoundDefinition.SoundType#SOUND} as
     * sound type.
     *
     * @param name The name of the sound to create.
     */
    protected static SoundDefinition.Sound sound(final String name) {
        return sound(new ResourceLocation(name));
    }

    // Addition methods

    /**
     * Adds the entry name associated with the given {@link SoundEvent} with the
     * {@link SoundDefinition} to the list.
     *
     * <p>This method should be preferred when a {@code SoundEvent} is already
     * available in the method context.
     *
     * @param soundEvent A {@link SoundEvent}.
     * @param definition The {@link SoundDefinition} that defines the given event.
     */
    protected void add(final SoundEvent soundEvent, final SoundDefinition definition) {
        this.add(soundEvent.getLocation(), definition);
    }

    /**
     * Adds the {@link SoundEvent} referenced by the given {@link ResourceLocation} with the
     * {@link SoundDefinition} to the list.
     *
     * @param soundEvent The {@link ResourceLocation} that identifies the event.
     * @param definition The {@link SoundDefinition} that defines the given event.
     */
    protected void add(final ResourceLocation soundEvent, final SoundDefinition definition) {
        this.addSounds(soundEvent.getPath(), definition);
    }

    /**
     * Adds the {@link SoundEvent} with the specified name along with its {@link SoundDefinition}
     * to the list.
     *
     * <p>The given sound event must NOT contain the namespace the name is a part of, since
     * the sound definition specification doesn't allow sounds to be defined outside the
     * namespace they're in. For this reason, any namespace will automatically be stripped
     * from the name.</p>
     *
     * @param soundEvent The name of the {@link SoundEvent}.
     * @param definition The {@link SoundDefinition} that defines the given event.
     */
    protected void add(final String soundEvent, final SoundDefinition definition) {
        this.add(new ResourceLocation(soundEvent), definition);
    }

    private void addSounds(final String soundEvent, final SoundDefinition definition) {
        if (this.sounds.put(soundEvent, definition) != null) {
            throw new IllegalStateException("Sound event '" + this.modId + ":" + soundEvent + "' already exists");
        }
    }

    // Internal handling stuff
    private void validate() {
        final List<String> notValid = this.sounds.keySet().stream()
                .map(it -> this.modId + ":" + it)
                .collect(Collectors.toList());
        if (!notValid.isEmpty()) {
            throw new IllegalStateException("Found invalid sound events: " + notValid);
        }
    }

    /*private boolean validate(final String name, final SoundDefinition def) {
        return def.soundList().stream().allMatch(it -> this.validate(name, it));
    }*/

    /*private boolean validate(final String name, final SoundDefinition.Sound sound) {
        return switch (sound.type()) {
            case SOUND -> this.validateSound(name, sound.name());
            case EVENT -> this.validateEvent(name, sound.name());
        };
        // Differently from all the other errors, this is not a 'missing sound' but rather something completely different
        // that has broken the invariants of this sound definitions provider. In fact, a sound may only be either of
        // SOUND or EVENT type. Any other values is somebody messing with the internals, reflectively adding something
        // to an enum or passing `null` to a parameter annotated with `@Nonnull`.
        throw new IllegalArgumentException("The given sound '" + sound.name() + "' does not have a valid type: expected either SOUND or EVENT, but found " + sound.type());
    }*/

    /*private boolean validateSound(final String soundName, final ResourceLocation name) {
        final boolean valid = this.helper.exists(name, PackType.CLIENT_RESOURCES, ".ogg", "sounds");
        if (!valid) {
            final String path = name.getNamespace() + ":sounds/" + name.getPath() + ".ogg";
            LOGGER.warn("Unable to find corresponding OGG file '{}' for sound event '{}'", path, soundName);
        }
        return valid;
    }*/

    private boolean validateEvent(final String soundName, final ResourceLocation name) {
        return this.sounds.containsKey(soundName) || Registry.SOUND_EVENT.containsKey(name);
    }

    private JsonObject mapToJson(final Map<String, SoundDefinition> map) {
        final JsonObject obj = new JsonObject();
        // namespaces are ignored when serializing
        map.forEach((k, v) -> obj.add(k, v.serialize()));
        return obj;
    }
}