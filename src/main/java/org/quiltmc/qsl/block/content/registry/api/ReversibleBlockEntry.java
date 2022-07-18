package org.quiltmc.qsl.block.content.registry.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public record ReversibleBlockEntry(Block block, boolean reversible) {
	public static final Codec<ReversibleBlockEntry> CODEC = Codec.either(
		RecordCodecBuilder.<ReversibleBlockEntry>create(instance -> instance.group(
			Registry.BLOCK.byNameCodec().fieldOf("block").forGetter(ReversibleBlockEntry::block),
			Codec.BOOL.fieldOf("reversible").forGetter(ReversibleBlockEntry::reversible)
		).apply(instance, ReversibleBlockEntry::new)),
		Registry.BLOCK.byNameCodec()
	).xmap(
		either -> either.map(entry -> entry, b -> new ReversibleBlockEntry(b, true)),
		entry -> entry.reversible ? Either.right(entry.block) : Either.left(entry)
	);
}