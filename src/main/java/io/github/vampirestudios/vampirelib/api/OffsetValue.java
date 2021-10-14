package io.github.vampirestudios.vampirelib.api;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.Item;

public class OffsetValue {
    public final Set<Item> itemsProcessed = Sets.newHashSet();
    public int offset = 1;

    /**
     * Vanilla doesn't cache its item group items, so we must make sure the offsets are reset when the process is run again.
     */
    public void reset() {
        this.offset = 1;
        this.itemsProcessed.clear();
    }
}