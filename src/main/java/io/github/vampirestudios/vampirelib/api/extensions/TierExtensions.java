package io.github.vampirestudios.vampirelib.api.extensions;

import org.jetbrains.annotations.Nullable;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public interface TierExtensions {
	@Nullable
	default TagKey<Block> getTag() {
		return null;
	}
}