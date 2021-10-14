package io.github.vampirestudios.vampirelib.api;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.utils.ItemStackUtils;

/**
 * Implementation class of {@link ItemGroupFiller} for filling {@link Item}s after a target {@link Item}.
 *
 * @see ItemGroupFiller
 */
public final record ModdedTargetedItemGroupFiller(String modId, Item targetItem) implements ItemGroupFiller {
    private static final Map<ItemGroup, OffsetValue> offsetMap = Maps.newHashMap();

    @Override
    public void fillItem(Item item, ItemGroup group, DefaultedList<ItemStack> items) {
        if (ItemStackUtils.isAllowedInGroup(item, group)) {
            if (FabricLoader.getInstance().isModLoaded(modId)) {
                OffsetValue offset = offsetMap.computeIfAbsent(group, (key) -> new OffsetValue());
                Set<Item> itemsProcessed = offset.itemsProcessed;
                if (itemsProcessed.contains(item)) {
                    offset.reset();
                }
                int index = ItemStackUtils.findIndexOfItem(this.targetItem, items);
                if (index != -1) {
                    items.add(index + offset.offset, new ItemStack(item));
                    itemsProcessed.add(item);
                    offset.offset++;
                } else {
                    items.add(new ItemStack(item));
                }
            }
        }
    }

}