/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;

import io.github.vampirestudios.vampirelib.api.callbacks.DynamicRegistryFinalizeCallback;

@Mixin(RegistryDataLoader.class)
public class RegistryLoaderMixin {
	@Redirect(
			method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/core/RegistryAccess;Ljava/util/List;)Lnet/minecraft/core/RegistryAccess$Frozen;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/core/RegistryAccess$ImmutableRegistryAccess;freeze()Lnet/minecraft/core/RegistryAccess$Frozen;"
			)
	)
	private static RegistryAccess.Frozen afterLoad(RegistryAccess.ImmutableRegistryAccess instance) {
		DynamicRegistryFinalizeCallback.EVENT.invoker().onRegistryFinalize(instance);
		return instance.freeze();
	}
}
