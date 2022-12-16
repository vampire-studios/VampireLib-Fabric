package io.github.vampirestudios.vampirelib.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;

import io.github.vampirestudios.vampirelib.api.callbacks.DebugRendererRegistrationCallback;
import io.github.vampirestudios.vampirelib.client.DebugRendererRegistry;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
	@Inject(method = "<init>", at = @At(value = "INVOKE", shift = At.Shift.BY, by=2, target = "Lnet/minecraft/client/renderer/debug/DebugRenderer;<init>(Lnet/minecraft/client/Minecraft;)V"))
	private void quilt$initDebugRenderers(GameConfig args, CallbackInfo ci) {
		DebugRendererRegistrationCallback.EVENT.invoker().registerDebugRenderers(DebugRendererRegistry::register);
	}
}
