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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public interface OverlayRenderCallback {
	Event<OverlayRenderCallback> EVENT = EventFactory.createArrayBacked(OverlayRenderCallback.class,
			callbacks -> (stack, partialTicks, window, type) -> {
				for (OverlayRenderCallback callback : callbacks) {
					if (callback.onOverlayRender(stack, partialTicks, window, type)) {
						resetTexture();
						return true;
					}
				}

				resetTexture();
				return false;
			});

	private static void resetTexture() { // in case overlays change it, which is very likely.
		RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/icons.png"));
	}

	boolean onOverlayRender(PoseStack stack, float partialTicks, Window window, Types type);

	enum Types {
		AIR,
		CROSSHAIRS,
		PLAYER_HEALTH
	}
}
