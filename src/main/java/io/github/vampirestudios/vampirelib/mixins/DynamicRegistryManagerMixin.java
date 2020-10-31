/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.mixins;

import com.google.common.collect.ImmutableMap;
import io.github.vampirestudios.vampirelib.api.CustomDynamicRegistry;
import io.github.vampirestudios.vampirelib.api.DynamicRegistryProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(DynamicRegistryManager.class)
public class DynamicRegistryManagerMixin {
	@Inject(method = "net/minecraft/util/registry/DynamicRegistryManager.method_30531()Lcom/google/common/collect/ImmutableMap;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/DynamicRegistryManager;register(Lcom/google/common/collect/ImmutableMap$Builder;Lnet/minecraft/util/registry/RegistryKey;Lcom/mojang/serialization/Codec;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void registerCustomDynamicRegistries(CallbackInfoReturnable<ImmutableMap<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>>> ci, ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder) {
		List<DynamicRegistryProvider> providers = FabricLoader.getInstance().getEntrypoints("dynamic-registry-provider", DynamicRegistryProvider.class);
		for (DynamicRegistryProvider provider : providers) {
			provider.addDynamicRegistries((customDynamicRegistry) -> {
				addRegistry(customDynamicRegistry);
				builder.put(customDynamicRegistry.getRegistryRef(), getInfo(customDynamicRegistry));
			});
		}
	}

	@Unique
	private static <T> DynamicRegistryManager.Info<T> getInfo(CustomDynamicRegistry<T> customDynamicRegistry) {
		return new DynamicRegistryManager.Info<>(customDynamicRegistry.getRegistryRef(), customDynamicRegistry.getCodec(), null);
	}

	@Unique
	private static <T> void addRegistry(CustomDynamicRegistry<T> customDynamicRegistry) {
		BuiltinRegistries.addRegistry(customDynamicRegistry.getRegistryRef(), customDynamicRegistry.getRegistry(), customDynamicRegistry.getDefaultValueSupplier(), customDynamicRegistry.getLifecycle());
	}
}