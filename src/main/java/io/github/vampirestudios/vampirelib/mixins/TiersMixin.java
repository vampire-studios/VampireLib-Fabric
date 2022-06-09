package io.github.vampirestudios.vampirelib.mixins;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;

import io.github.vampirestudios.vampirelib.api.extensions.TierExtensions;
import io.github.vampirestudios.vampirelib.utils.TagUtil;

@Mixin(Tiers.class)
public abstract class TiersMixin implements TierExtensions {
	@Nullable
	@Override
	public TagKey<Block> getTag() {
		return TagUtil.getTagFromVanillaTier((Tiers) (Object) this);
	}
}