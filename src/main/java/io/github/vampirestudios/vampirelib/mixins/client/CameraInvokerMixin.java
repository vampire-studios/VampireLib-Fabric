package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Camera;

@Mixin(Camera.class)
public interface CameraInvokerMixin {
	@Invoker
	void callMove(double z, double y, double x);
}