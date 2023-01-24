/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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

import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import io.github.vampirestudios.vampirelib.api.callbacks.RenderGuiCallback;

@Mixin(AbstractContainerScreen.class)
public class HandledScreenMixin {
	/**
	 * Simulates Forge's GuiContainerEvent.DrawForeground
	 */
	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderLabels(Lcom/mojang/blaze3d/vertex/PoseStack;II)V"
			)
	)
	private void hookRender(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		Minecraft client = Minecraft.getInstance();
		RenderGuiCallback.EVENT.invoker().interact(client, matrices, mouseX, mouseY, delta);
	}

}
