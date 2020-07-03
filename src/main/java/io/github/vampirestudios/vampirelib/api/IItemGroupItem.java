package io.github.vampirestudios.vampirelib.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.Collection;
import java.util.Collections;

public interface IItemGroupItem {

    // Helpers for accessing Item data
    default Item getItem() {
        return (Item) this;
    }

    default Collection<ItemGroup> getCreativeTabs() {
        return Collections.singletonList(getItem().getGroup());
    }

}
