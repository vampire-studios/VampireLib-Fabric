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

import io.github.vampirestudios.vampirelib.callbacks.ServerSaveCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
	/*@Inject(method = "loadDataPacks", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;scanPacks()V"))
	private static void beforeReload(ResourcePackManager resourcePackManager, DataPackSettings dataPackSettings, boolean safeMode, CallbackInfoReturnable<Void> info) {
		ServerReloadCallback.BEFORE.invoker().onServerReload((MinecraftServer) null);
	}*/

	/*@Inject(method = "loadDataPacks(Lnet/minecraft/resource/ResourcePackManager;Lnet/minecraft/resource/DataPackSettings;Z)Lnet/minecraft/resource/DataPackSettings;", at = @At("TAIL"))
	private void afterInitialLoad(ResourcePackManager resourcePackManager, DataPackSettings dataPackSettings, boolean safeMode, CallbackInfoReturnable<Void> ci) {
		ServerReloadCallback.AFTER.invoker().onServerReload((MinecraftServer) (Object) this);
	}*/

	/*@Inject(method = "reloadResources()V", at = @At("TAIL"))
	public void afterReload(CallbackInfo info) {
		ServerReloadCallback.AFTER.invoker().onServerReload((MinecraftServer) (Object) this);
	}*/

	@Inject(method = "save(ZZZ)Z", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	public void onSave(boolean silent, boolean flush, boolean enforced, CallbackInfoReturnable<Boolean> info, boolean iteratedWorlds, ServerWorld overworld, LevelProperties mainLevelProperties) {
		ServerSaveCallback.EVENT.invoker().onServerSave((MinecraftServer) (Object) this, mainLevelProperties);
	}
}