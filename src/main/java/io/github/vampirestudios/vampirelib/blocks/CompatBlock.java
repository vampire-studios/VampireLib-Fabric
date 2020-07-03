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

package io.github.vampirestudios.vampirelib.blocks;

import io.github.vampirestudios.vampirelib.utils.ItemStackUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CompatBlock extends Block {

    private final String modName;
    private final Block modBlock;

    public CompatBlock(String modName, Block modBlock, Settings settings) {
        super(settings);
        this.modName = modName;
        this.modBlock = modBlock;
    }

    @Override
    public void addStacksForDisplay(ItemGroup group, DefaultedList<ItemStack> list) {
        if(ItemStackUtils.isInGroup(this.asItem(), group)) {
            if (FabricLoader.getInstance().isModLoaded(this.modName)) {
                int targetIndex = ItemStackUtils.findIndexOfItem(modBlock.asItem(), list);
                if(targetIndex != -1) {
                    list.add(targetIndex + 1, new ItemStack(this));
                } else {
                    super.addStacksForDisplay(group, list);
                }
            } else {
                super.addStacksForDisplay(group, list);
            }
        }
    }

}