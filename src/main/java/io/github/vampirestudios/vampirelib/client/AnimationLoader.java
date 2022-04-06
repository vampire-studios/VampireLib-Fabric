package io.github.vampirestudios.vampirelib.client;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import com.mojang.serialization.JsonOps;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import io.github.vampirestudios.vampirelib.VampireLib;

/**
 * Handles the data-driven internals for {@link AnimationDefinition} instances.
 */
public final class AnimationLoader implements PreparableReloadListener {
	private static final JsonParser PARSER = new JsonParser();
	private final BiMap<ResourceLocation, AnimationDefinition> registry = HashBiMap.create();

	/**
	 * Gets the {@link AnimationDefinition} mapped to a given {@link ResourceLocation} key.
	 *
	 * @param key A {@link ResourceLocation} key to use to look up its {@link AnimationDefinition}.
	 * @return The {@link AnimationDefinition} mapped to a given {@link ResourceLocation} key, or null if no such mapping exists.
	 */
	@Nullable
	public AnimationDefinition getAnimationDefinition(ResourceLocation key) {
		return this.registry.get(key);
	}

	/**
	 * Gets {@link ResourceLocation} key for a given {@link AnimationDefinition}.
	 *
	 * @param animationDefinition An {@link AnimationDefinition} to use to look up its {@link ResourceLocation} key.
	 * @return The {@link ResourceLocation} key for a given {@link AnimationDefinition}, or null if no such key exists.
	 */
	@Nullable
	public ResourceLocation getKey(AnimationDefinition animationDefinition) {
		return this.registry.inverse().get(animationDefinition);
	}

	@Override
	public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) {
		return CompletableFuture.supplyAsync(() -> {
			Map<ResourceLocation, AnimationDefinition> animationDefinitions = new HashMap<>();
			for (ResourceLocation resourcelocation : manager.listResources("models/entity/animations", (file) -> file.getPath().endsWith(".json")).keySet()) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(manager.getResource(resourcelocation).orElseThrow().open())) {
					var dataResult = AnimationCodecs.ANIMATION_DEFINITION_CODEC.decode(JsonOps.INSTANCE, PARSER.parse(inputStreamReader));
					var error = dataResult.error();
					if (error.isPresent()) {
						throw new JsonParseException(error.get().message());
					} else {
						String path = resourcelocation.getPath();
						ResourceLocation adjustedLocation = new ResourceLocation(resourcelocation.getNamespace(), path.substring(12, path.length() - 5));
						if (animationDefinitions.put(adjustedLocation, dataResult.result().get().getFirst()) != null) {
							VampireLib.INSTANCE.getLogger().warn("Loaded Duplicate animation definition: {}", adjustedLocation);
						}
					}
				} catch (Exception exception) {
					VampireLib.INSTANCE.getLogger().error("Error while loading animation definition: {}", resourcelocation, exception);
				}
			}
			return animationDefinitions;
		}, executor).thenCompose(barrier::wait).thenAcceptAsync(animationDefinitions -> {
			BiMap<ResourceLocation, AnimationDefinition> registry = this.registry;
			registry.clear();
			registry.putAll(animationDefinitions);
			VampireLib.INSTANCE.getLogger().info("Animation Loader has loaded {} animation definitions", registry.size());
		}, executor2);
	}
}