package io.github.vampirestudios.vampirelib.utils.modifications.selection;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;

/**
 * The functional interface used for iterating over a space to select targets from.
 *
 * @author SmellyModder (Luke Tonon)
 */
@FunctionalInterface
public interface SelectionSpace {
	/**
	 * Iterates over the space, using a {@link Consumer} instance for each resource.
	 *
	 * @param consumer A {@link Consumer} instance to use for processing each resource.
	 */
	void forEach(Consumer<ResourceLocation> consumer);
}