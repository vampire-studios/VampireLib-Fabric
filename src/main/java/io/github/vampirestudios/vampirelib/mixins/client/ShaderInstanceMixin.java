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

package io.github.vampirestudios.vampirelib.mixins.client;

import com.mojang.blaze3d.shaders.Program;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

@Mixin(ShaderInstance.class)
public class ShaderInstanceMixin {

	@Shadow
	@Final
	private String name;

	@Unique
	private static String captureLocation;
	@Unique
	private static Program.Type captureType;

	@ModifyVariable(method = "<init>",
					at = @At(value = "NEW", target = "net/minecraft/resources/ResourceLocation", ordinal = 0,
							 shift = At.Shift.BEFORE), index = 2, argsOnly = true)
	public String modifyLocationString(String value) {
		return "";
	}

	@ModifyVariable(method = "<init>", at = @At(value = "INVOKE",
												target = "Lnet/minecraft/server/packs/resources/ResourceProvider;openAsReader(Lnet/minecraft/resources/ResourceLocation;)Ljava/io/BufferedReader;",
												shift = At.Shift.BEFORE), index = 4)
	public ResourceLocation modifyLocation(ResourceLocation location) {
		ResourceLocation id = new ResourceLocation(this.name);
		return new ResourceLocation(id.getNamespace(), "shaders/core/" + id.getPath() + ".json");
	}

	@Inject(method = "getOrCreate", at = @At("HEAD"))
	private static void captureGetOrCreate(ResourceProvider resourceProvider, Program.Type type, String string, CallbackInfoReturnable<Program> cir) {
		captureLocation = string;
		captureType = type;
	}

	@Inject(method = "getOrCreate", at = @At("TAIL"))
	private static void deleteGetOrCreate(ResourceProvider resourceProvider, Program.Type type, String string, CallbackInfoReturnable<Program> cir) {
		captureLocation = null;
		captureType = null;
	}

	@ModifyVariable(method = "getOrCreate",
					at = @At(value = "NEW", target = "net/minecraft/resources/ResourceLocation", ordinal = 0,
							 shift = At.Shift.BEFORE), ordinal = 1)
	private static String modifyStaticLocation(String value) {
		ResourceLocation id = new ResourceLocation(captureLocation);
		return id.getNamespace() + ":shaders/core/" + id.getPath() + captureType.getExtension();
	}
}
