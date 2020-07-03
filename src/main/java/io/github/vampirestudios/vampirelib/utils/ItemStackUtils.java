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
