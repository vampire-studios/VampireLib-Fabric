package io.github.vampirestudios.vampirelib.api.datagen;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public abstract class JsonCodecProvider<T> implements DataProvider {
	private final PackOutput.PathProvider pathResolver;
	private final Codec<T> codec;

	protected JsonCodecProvider(PackOutput dataOutput, PackOutput.Target outputType, String directoryName, Codec<T> codec) {
		this.pathResolver = dataOutput.createPathProvider(outputType, directoryName);
		this.codec = codec;
	}

	@Override
	public CompletableFuture<?> run(final CachedOutput cache) {
		Map<Identifier, JsonElement> entries = new HashMap<>();
		BiConsumer<Identifier, T> provider = (id, value) -> {
			JsonElement json = this.convert(id, value);
			JsonElement existingJson = entries.put(id, json);

			if (existingJson != null) {
				throw new IllegalArgumentException("Duplicate entry " + id);
			}
		};

		this.configure(provider);
		return this.write(cache, entries);
	}

	/**
	 * Implement this method to register entries to generate.
	 *
	 * @param provider A consumer that accepts an {@link Identifier} and a value to register.
	 */
	protected abstract void configure(BiConsumer<Identifier, T> provider);

	private JsonElement convert(Identifier id, T value) {
		DataResult<JsonElement> dataResult = this.codec.encodeStart(JsonOps.INSTANCE, value);
		return dataResult.getOrThrow(s -> new IllegalArgumentException("Invalid entry %s: %s".formatted(id, s)));
	}

	private CompletableFuture<?> write(CachedOutput writer, Map<Identifier, JsonElement> entries) {
		return CompletableFuture.allOf(entries.entrySet().stream().map(entry -> {
			Path path = this.pathResolver.json(entry.getKey());
			return DataProvider.saveStable(writer, entry.getValue(), path);
		}).toArray(CompletableFuture[]::new));
	}

}
