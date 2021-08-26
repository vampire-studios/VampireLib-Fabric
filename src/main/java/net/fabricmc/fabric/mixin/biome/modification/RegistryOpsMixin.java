package net.fabricmc.fabric.mixin.biome.modification;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.DynamicOps;

import net.minecraft.resource.ResourceManager;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.registry.DynamicRegistryManager;

import net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl;

@Mixin(RegistryOps.class)
public class RegistryOpsMixin {
	@Inject(method = "method_36574", at = @At("RETURN"))
	private static <T> void afterCreation(DynamicOps<T> dynamicOps, ResourceManager resourceManager, DynamicRegistryManager impl, CallbackInfoReturnable<RegistryOps<T>> cir) {
		BiomeModificationImpl.INSTANCE.modifyBiomes((DynamicRegistryManager.Impl) impl);
	}
}