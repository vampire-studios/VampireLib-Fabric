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

package io.github.vampirestudios.vampirelib.api.callbacks;

import java.util.List;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Event for adding more tooltip data to a stack's tooltip.
 * Called when an item stack's tooltip is rendered.
 *
 * <p>Keep in mind that:
 * <ul>
 *     <li> This event should be used for adding item independent data.
 *     If your data only affects one item, or one class, you should override
 *     {@link net.minecraft.world.item.Item#getTooltipImage} instead of using this event.
 *     <li> If {@link net.minecraft.world.item.Item#getTooltipImage} is not empty
 *     for the stack, it's added to the list before the event is fired.
 *     <li> Data added to {@code list} will be converted to a {@link net.minecraft.world.inventory.tooltip.TooltipComponent}
 * 	   via {@link net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback#EVENT} before being rendered.
 * 	   If you don't register a TooltipComponent for your data, minecraft will throw an error.
 * 	   <li> These are rendered between item's name and the rest of the tooltip and
 * 	   all data added to {@code list} will be rendered in the order they were added,
 * 	   so the first added is rendered on top while the last added is at the bottom.
 * </ul>
 */
public interface ItemTooltipDataCallback {
	/**
	 * Fired when {@link ItemStack#getTooltipImage()} is called.
	 */
	Event<ItemTooltipDataCallback> EVENT = EventFactory.createArrayBacked(ItemTooltipDataCallback.class, callbacks -> (stack, list) -> {
		for (ItemTooltipDataCallback callback : callbacks) {
			callback.getTooltipData(stack, list);
		}
	});

	/**
	 * Called when an item stack's tooltip is rendered.
	 * Any data added to {@code list} will be rendered with the tooltip.
	 *
	 * @param stack the stack requesting tooltip data.
	 * @param list	the list containing the data's to be displayed on the stack's tooltip.
	 */
	void getTooltipData(ItemStack stack, List<TooltipComponent> list);
}
