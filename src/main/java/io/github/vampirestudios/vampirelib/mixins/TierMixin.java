package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.Tier;

import io.github.vampirestudios.vampirelib.api.extensions.TierExtensions;

@Mixin(Tier.class)
public interface TierMixin extends TierExtensions {
}