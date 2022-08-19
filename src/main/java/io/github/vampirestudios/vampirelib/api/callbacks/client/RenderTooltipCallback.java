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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface RenderTooltipCallback {
	/**
	 * @see Item#append(ItemStack, List, TooltipFlag)
	 */
	Event<Item> ITEM = EventFactory.createArrayBacked(Item.class, listeners ->
			(itemStack, lines, flag) -> {
				for (Item event : listeners) {
					event.append(itemStack, lines, flag);
				}
			});

	/**
	 * @see Render#renderTooltip(PoseStack, List, int, int)
	 */
	Event<Render> RENDER_PRE = EventFactory.createArrayBacked(Render.class, listeners ->
			(matrices, text, x, y) -> {
				for (Render event : listeners) {
					InteractionResult result = event.renderTooltip(matrices, text, x, y);
					if (result != InteractionResult.PASS) return result;
				}

				return InteractionResult.PASS;
			});

	/**
	 * @see RenderModifyPosition#renderTooltip(PoseStack, PositionContext)
	 */
	Event<RenderModifyPosition> RENDER_MODIFY_POSITION = EventFactory.createArrayBacked(RenderModifyPosition.class,
			listeners -> (matrices, context) -> {
				for (RenderModifyPosition event : listeners) {
					event.renderTooltip(matrices, context);
				}
			});

	/**
	 * @see RenderModifyColor#renderTooltip(PoseStack, int, int, ColorContext)
	 */
	Event<RenderModifyColor> RENDER_MODIFY_COLOR = EventFactory.createArrayBacked(RenderModifyColor.class, listeners ->
			(matrices, x, y, context) -> {
				for (RenderModifyColor event : listeners) {
					event.renderTooltip(matrices, x, y, context);
				}
			});

	@Environment(EnvType.CLIENT)
	interface Item {
		/**
		 * Invoked whenever an item tooltip is rendered.
		 * Equivalent to Forge's {@code ItemTooltipEvent} event and
		 * Fabric's {@code ItemTooltipCallback}.
		 *
		 * @param stack The rendered stack.
		 * @param lines The mutable list of tooltip components.
		 * @param flag  A flag indicating if advanced mode is active.
		 */
		void append(ItemStack stack, List<Component> lines, TooltipFlag flag);
	}

	@Environment(EnvType.CLIENT)
	interface Render {
		/**
		 * Invoked before the tooltip for a tooltip is rendered.
		 *
		 * @param matrices The pose stack.
		 * @param texts    The mutable list of components that are rendered.
		 * @param x        The x-coordinate of the tooltip.
		 * @param y        The y-coordinate of the tooltip.
		 *
		 * @return A {@link InteractionResult} determining the outcome of the event,
		 * the execution of the vanilla tooltip rendering may be cancelled by the result.
		 */
		InteractionResult renderTooltip(PoseStack matrices, List<? extends ClientTooltipComponent> texts, int x, int y);
	}

	@Environment(EnvType.CLIENT)
	interface RenderModifyPosition {
		/**
		 * Event to manipulate the position of the tooltip.
		 *
		 * @param matrices The pose stack.
		 * @param context  The current position context.
		 */
		void renderTooltip(PoseStack matrices, PositionContext context);
	}

	@Environment(EnvType.CLIENT)
	interface RenderModifyColor {
		/**
		 * Event to manipulate the color of the tooltip.
		 *
		 * @param matrices The pose stack.
		 * @param x        The x-coordinate of the tooltip.
		 * @param y        The y-coordinate of the tooltip.
		 * @param context  The current color context.
		 */
		void renderTooltip(PoseStack matrices, int x, int y, ColorContext context);
	}

	@Environment(EnvType.CLIENT)
	interface PositionContext {
		int getTooltipX();

		void setTooltipX(int x);

		int getTooltipY();

		void setTooltipY(int y);
	}

	@Environment(EnvType.CLIENT)
	interface ColorContext {
		int getBackgroundColor();

		void setBackgroundColor(int color);

		int getOutlineGradientTopColor();

		void setOutlineGradientTopColor(int color);

		int getOutlineGradientBottomColor();

		void setOutlineGradientBottomColor(int color);
	}
}
