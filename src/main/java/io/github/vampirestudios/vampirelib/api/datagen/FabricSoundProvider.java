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

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.SampledFloat;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

/**
 * Extend this class and implement {@link FabricSoundProvider#generateSounds(SoundGenerator)}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class FabricSoundProvider implements DataProvider {
	private static final SampledFloat ONE = ConstantFloat.of(1.0F);
	protected final FabricDataOutput dataOutput;

	protected FabricSoundProvider(FabricDataOutput dataOutput) {
		this.dataOutput = dataOutput;
	}

	/**
	 * Implement this method to register sounds.
	 *
	 * <p>Call {@link SoundGenerator#add(SoundEvent, SoundBuilder...)} to add a list of sound entries
	 * for a given {@link SoundEvent}.
	 */
	public abstract void generateSounds(SoundGenerator soundGenerator);

	@Override
	public CompletableFuture<?> run(CachedOutput writer) {
		HashMap<String, JsonObject> soundEvents = new HashMap<>();

		generateSounds(((sound, replace, subtitle, entries) -> {
			Objects.requireNonNull(sound);
			Objects.requireNonNull(entries);

			List<ResourceLocation> keys = Arrays.stream(entries).map(SoundBuilder::getName).toList();

			if (!keys.stream().filter(i -> Collections.frequency(keys, i) > 1).toList().isEmpty()) {
				throw new RuntimeException("Entries for sound event " + sound.getLocation() + " contain duplicate sound names. Event will be omitted.");
			}

			JsonObject soundEventData = new JsonObject();
			JsonArray soundEntries = new JsonArray();

			Arrays.asList(entries).forEach(s -> soundEntries.add(s.build()));
			soundEventData.add("sounds", soundEntries);

			if (replace) {
				soundEventData.addProperty("replace", true);
			}

			if (subtitle != null) {
				soundEventData.addProperty("subtitle", subtitle);
			}

			soundEvents.put(sound.getLocation().toString(), soundEventData);
		}));

		JsonObject soundsJson = new JsonObject();

		for (Map.Entry<String, JsonObject> entry : soundEvents.entrySet()) {
			soundsJson.add(entry.getKey(), entry.getValue());
		}

		Path soundsPath = dataOutput
				.createPathProvider(PackOutput.Target.RESOURCE_PACK, ".")
				.json(new ResourceLocation(dataOutput.getModId(), "sounds"));
		return DataProvider.saveStable(writer, soundsJson, soundsPath.normalize());
	}

	private boolean allDefaults(Sound sound) {
		return sound.getVolume().sample(null) == 1 && sound.getPitch().sample(null) == 1
				&& sound.getWeight() == 1 && sound.getAttenuationDistance() == 16
				&& !sound.shouldStream() && !sound.shouldPreload()
				&& sound.getType() == Sound.Type.FILE;
	}
	private JsonElement toJson(Sound sound) {
		String soundId = sound.getLocation().toString();
		if (allDefaults(sound)) {
			return new JsonPrimitive(soundId);
		} else {
			JsonObject soundEntry = new JsonObject();
			soundEntry.addProperty("name", soundId);

			float volume = sound.getVolume().sample(null);
			float pitch = sound.getPitch().sample(null);

			if (volume != 1) {
				soundEntry.addProperty("volume", volume);
			}

			if (pitch != 1) {
				soundEntry.addProperty("pitch", pitch);
			}

			if (sound.getWeight() != 1) {
				soundEntry.addProperty("weight", sound.getWeight());
			}

			if (sound.shouldStream()) {
				soundEntry.addProperty("stream", true);
			}

			if (sound.getAttenuationDistance() != 16) {
				soundEntry.addProperty("attenuation_distance", sound.getAttenuationDistance());
			}

			if (sound.shouldPreload()) {
				soundEntry.addProperty("preload", true);
			}

			if (sound.getType() == Sound.Type.SOUND_EVENT) {
				soundEntry.addProperty("type", "event");
			}

			return soundEntry;
		}
	}


	@Override
	public String getName() {
		return "Sounds";
	}

	@ApiStatus.NonExtendable
	@FunctionalInterface
	public interface SoundGenerator {
		/**
		 * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
		 *
		 * @param sound The {@link SoundEvent} to add an entry for.
		 * @param replace Set this to <code>true</code> if this entry corresponds to a sound event from vanilla
		 *                Minecraft or some other mod's namespace, in order to replace the default sounds from the
		 *                original namespace's sounds file via your own namespace's resource pack.
		 * @param subtitle An optional subtitle to use for the event, given as a translation key for the subtitle.
		 * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
		 *                this event.
		 */
		void add(SoundEvent sound, boolean replace, @Nullable String subtitle, SoundBuilder... entries);

		/**
		 * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
		 *
		 * @param sound The {@link SoundEvent} to add an entry for.
		 * @param replace Set this to <code>true</code> if this entry corresponds to a sound event from vanilla
		 *                Minecraft or some other mod's namespace, in order to replace the default sounds from the
		 *                original namespace's sounds file via your own namespace's resource pack.
		 * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
		 *                this event.
		 */
		default void add(SoundEvent sound, boolean replace, SoundBuilder... entries) {
			add(sound, replace, null, entries);
		}

		/**
		 * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
		 *
		 * @param sound The {@link SoundEvent} to add an entry for.
		 * @param subtitle An optional subtitle to use for the event, given as a translation key for the subtitle.
		 * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
		 *                this event.
		 */
		default void add(SoundEvent sound, @Nullable String subtitle, SoundBuilder... entries) {
			add(sound, false, subtitle, entries);
		}

		/**
		 * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
		 *
		 * @param sound The {@link SoundEvent} to add an entry for.
		 * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
		 *                this event.
		 */
		default void add(SoundEvent sound, SoundBuilder... entries) {
			add(sound, false, null, entries);
		}
	}
}
