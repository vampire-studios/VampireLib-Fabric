package io.github.vampirestudios.vampirelib.api.datagen;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

/**
 * Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class LegacyFabricSoundProvider implements DataProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(LegacyFabricSoundProvider.class);

	protected final FabricPackOutput dataOutput;
	protected final String modId;

	protected LegacyFabricSoundProvider(FabricPackOutput dataOutput) {
		this.dataOutput = dataOutput;
		this.modId = dataOutput.getModId();
	}

	/**
	 * Registers all sound instances to be generated.
	 *
	 * @param registry The registry to validate and create files
	 */
	protected abstract void registerSounds(Consumer<SoundDefinition> registry);

	@Override
	public CompletableFuture<?> run(@NotNull CachedOutput cache) {
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

		return DataProvider.saveStable(cache, json, getLangFilePath());
	}

	private Path getLangFilePath() {
		return dataOutput
				.createPathProvider(PackOutput.Target.RESOURCE_PACK, "")
				.json(Identifier.fromNamespaceAndPath(dataOutput.getModId(), "sounds.json"));
	}

	@Override
	public String getName() {
		return "Sound Definitions";
	}
}
