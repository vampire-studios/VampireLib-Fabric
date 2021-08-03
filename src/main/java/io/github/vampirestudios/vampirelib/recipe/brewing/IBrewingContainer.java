/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package io.github.vampirestudios.vampirelib.recipe.brewing;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Wraps the ingredients for a brewing recipe in a container for use in the recipe implementation
 */
public interface IBrewingContainer extends Inventory {

    @Override
    default void clear() {}

    @Override
    default int size()
    {
        return 2;
    }

    @Override
    default boolean isEmpty() {
        return base().isEmpty() && reagent().isEmpty();
    }

    @Override
    default ItemStack getStack(final int slot) {
        return switch (slot) {
            case 0 -> base();
            case 1 -> reagent();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    default ItemStack removeStack(final int slot, final int amount) {
        return getStack(slot).split(amount);
    }

    @Override
    default ItemStack removeStack(final int slot) {
        return getStack(slot);
    }

    @Override
    default void setStack(final int slot, final ItemStack stack) {}

    @Override
    default void markDirty() {}

    @Override
    default boolean canPlayerUse(final PlayerEntity player)
    {
        return true;
    }

    ItemStack base();

    ItemStack reagent();

    record Impl(ItemStack base, ItemStack reagent) implements IBrewingContainer {}
}