package org.quiltmc.qsl.block.content.registry.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FlammableBlockEntry(int burn, int spread) {
	public static final Codec<FlammableBlockEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.intRange(0, Integer.MAX_VALUE).fieldOf("burn").forGetter(FlammableBlockEntry::burn),
			Codec.intRange(0, Integer.MAX_VALUE).fieldOf("spread").forGetter(FlammableBlockEntry::spread)
	).apply(instance, FlammableBlockEntry::new));
}