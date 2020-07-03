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

package io.github.vampirestudios.vampirelib.utils;

import io.github.vampirestudios.vampirelib.api.IItemGroupItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ItemStackUtils {

    /**
     * Searches for a specific item in a {@link DefaultedList} of {@link ItemStack} and returns its index
     * @param item - The item to search for
     * @param items - The list of ItemStacks
     * @return - The index of the specified item in the list, if no item was found returns -1
     */
    public static int findIndexOfItem(Item item, DefaultedList<ItemStack> items) {
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Searches for if an {@link Item} is present in an {@link ItemGroup} and returns if it is
     * @param item - The item searched
     * @param group - The group searched
     * @return - Whether or not the item is present in the group
     */
    public static boolean isInGroup(Item item, ItemGroup group) {
        if (((IItemGroupItem)item).getCreativeTabs().stream().anyMatch(tab -> tab == group)) return true;
        ItemGroup itemgroup = item.getGroup();
        return itemgroup != null && (group == ItemGroup.SEARCH || group == itemgroup);
    }

}
