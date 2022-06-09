package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.resources.model.ModelState;

import io.github.vampirestudios.vampirelib.api.extensions.ModelStateExtensions;

@Mixin(ModelState.class)
public interface ModelStateMixin extends ModelStateExtensions {
}