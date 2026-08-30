package io.github.vampirestudios.vampirelib.client;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.function.Supplier;

public class LazyCodec<T> implements Codec<T> {
    private final Supplier<Codec<T>> internal;

    private LazyCodec(Supplier<Codec<T>> internal) {
        this.internal = Lazy.of(internal);
    }

    public static <T> LazyCodec<T> of(Supplier<Codec<T>> supplier) {
        return new LazyCodec<>(supplier);
    }

    @Override
    public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
        return internal.get().decode(ops, input);
    }

    @Override
    public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
        return internal.get().encode(input, ops, prefix);
    }
}
