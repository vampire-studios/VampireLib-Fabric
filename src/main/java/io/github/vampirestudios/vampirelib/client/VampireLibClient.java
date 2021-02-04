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

package io.github.vampirestudios.vampirelib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;

import java.util.ArrayList;
import java.util.List;

public class VampireLibClient implements ClientModInitializer {

    public static final List<ColoredLeaves> COLORED_LEAVES = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        COLORED_LEAVES.forEach(coloredLeaves -> {
            if(!coloredLeaves.customColor) {
                ColorProviderRegistryImpl.BLOCK.register((block, world, pos, layer) -> {
                    BlockColorProvider provider = ColorProviderRegistryImpl.BLOCK.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(block, world, pos, layer);
                }, coloredLeaves.leavesBlock);
                ColorProviderRegistryImpl.ITEM.register((item, layer) -> {
                    ItemColorProvider provider = ColorProviderRegistryImpl.ITEM.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(item, layer);
                }, coloredLeaves.leavesBlock);
            } else {
                ColorProviderRegistryImpl.BLOCK.register((block, world, pos, layer) -> coloredLeaves.color, coloredLeaves.leavesBlock);
                ColorProviderRegistryImpl.ITEM.register((item, layer) -> coloredLeaves.color, coloredLeaves.leavesBlock);
            }
        });
    }

    public static class ColoredLeaves {

        private Block leavesBlock;
        private boolean customColor;
        private int color;

        public ColoredLeaves(Block leavesBlock, boolean customColor, int color) {
            this.leavesBlock = leavesBlock;
            this.customColor = customColor;
            this.color = color;
        }

        public Block getLeavesBlock() {
            return leavesBlock;
        }

        public boolean isCustomColor() {
            return customColor;
        }

        public int getColor() {
            return color;
        }
    }

}