/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.function.Supplier;

/**
 * A nicer wrapper around {@link net.minecraft.util.registry.DynamicRegistryManager}.
 *
 * @param <T> the type that the registry holds
 */
public class CustomDynamicRegistry<T> {
	private final SimpleRegistry<T> registry;
	private final Supplier<T> defaultValueSupplier;
	private final Codec<T> codec;

	public CustomDynamicRegistry(SimpleRegistry<T> registry, Supplier<T> defaultValueSupplier, Codec<T> codec) {
		this.registry = registry;
		this.defaultValueSupplier = defaultValueSupplier;
		this.codec = codec;
	}

	public SimpleRegistry<T> getRegistry() {
		return this.registry;
	}

	public RegistryKey<? extends Registry<T>> getRegistryRef() {
		return this.registry.getKey();
	}

	public Lifecycle getLifecycle() {
		return this.registry.getLifecycle();
	}

	public Supplier<T> getDefaultValueSupplier() {
		return this.defaultValueSupplier;
	}

	public Codec<T> getCodec() {
		return this.codec;
	}
}