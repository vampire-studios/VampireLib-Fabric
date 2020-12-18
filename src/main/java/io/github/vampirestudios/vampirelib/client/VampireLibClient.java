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