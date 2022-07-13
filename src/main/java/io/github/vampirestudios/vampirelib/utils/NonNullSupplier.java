package io.github.vampirestudios.vampirelib.utils;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

/**
 * Equivalent to {@link Supplier}, except with nonnull contract.
 *
 * @see Supplier
 */
@FunctionalInterface
public interface NonNullSupplier<T> {
	@NotNull T get();
}