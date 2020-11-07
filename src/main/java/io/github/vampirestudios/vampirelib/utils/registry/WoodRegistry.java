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

package io.github.vampirestudios.vampirelib.utils.registry;

import io.github.vampirestudios.vampirelib.blocks.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class WoodRegistry {

    public Identifier name;
    private Block log;
    private Block wood;
    private Block strippedLog;
    private Block strippedWood;
    private Block stairs;
    private Block slab;
    private Block planks;
    private Block leaves;
    private Block sapling;
    private Block fence;
    private Block fenceGate;
    private Block bookshelf;
    private Block door;
    private Block trapdoor;
    private Block button;
    private Block pressurePlate;
    private Block ladder;
    private SaplingGenerator saplingGenerator;

    private WoodRegistry(Identifier name, SaplingGenerator saplingGenerator) {
        this.name = name;
        this.saplingGenerator = saplingGenerator;
    }

    private WoodRegistry(Identifier name) {
        this.name = name;
        this.saplingGenerator = null;
    }

    public static WoodRegistry.Builder of(Identifier name) {
        return new WoodRegistry.Builder().of(name);
    }

    public static WoodRegistry.Builder of(Identifier name, Block planks) {
        return new WoodRegistry.Builder().of(name, planks);
    }

    public static WoodRegistry.Builder of(Identifier name, SaplingGenerator saplingGenerator) {
        return new WoodRegistry.Builder().of(name, saplingGenerator);
    }

    public Block getLog() {
        return log;
    }

    public Block getWood() {
        return wood;
    }

    public Block getStrippedLog() {
        return strippedLog;
    }

    public Block getStrippedWood() {
        return strippedWood;
    }

    public Block getStairs() {
        return stairs;
    }

    public Block getSlab() {
        return slab;
    }

    public Block getPlanks() {
        return planks;
    }

    public Block getLeaves() {
        return leaves;
    }

    public Block getSapling() {
        return sapling;
    }

    public Block getFence() {
        return fence;
    }

    public Block getFenceGate() {
        return fenceGate;
    }

    public Block getBookshelf() {
        return bookshelf;
    }

    public Block getDoor() {
        return door;
    }

    public Block getTrapdoor() {
        return trapdoor;
    }

    public Block getButton() {
        return button;
    }

    public Block getPressurePlate() {
        return pressurePlate;
    }

    public Block getLadder() {
        return ladder;
    }

    public static class Builder {

        public Identifier name;
        private WoodRegistry woodRegistry;
        private RegistryHelper registryHelper;

        public Builder of(Identifier name) {
            this.name = name;
            woodRegistry = new WoodRegistry(name);
            registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
            return this;
        }

        public Builder of(Identifier name, Block planks) {
            this.name = name;
            woodRegistry = new WoodRegistry(name);
            woodRegistry.planks = planks;
            registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
            return this;
        }

        public Builder of(Identifier name, SaplingGenerator saplingGenerator) {
            this.name = name;
            woodRegistry = new WoodRegistry(name, saplingGenerator);
            registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
            return this;
        }

        public Builder log() {
            woodRegistry.log = registryHelper.registerBlock(new PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE).hardness(2.0F)
                    .sounds(BlockSoundGroup.WOOD)), name.getPath() + "_log", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder wood() {
            woodRegistry.wood = registryHelper.registerBlock(new Block(FabricBlockSettings.of(Material.WOOD, MapColor.WOOD).hardness(2.0F)
                    .sounds(BlockSoundGroup.WOOD)), name.getPath() + "_wood", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedLog() {
            woodRegistry.strippedLog = registryHelper.registerBlock(new PillarBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE).hardness(2.0F)
                            .sounds(BlockSoundGroup.WOOD)), "stripped_" + name.getPath() + "_log",
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedWood() {
            woodRegistry.strippedWood = registryHelper.registerBlock(new Block(FabricBlockSettings.of(Material.WOOD, MapColor.WOOD).hardness(2.0F)
                            .sounds(BlockSoundGroup.WOOD)), "stripped_" + name.getPath() + "_wood",
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder stairs() {
            woodRegistry.stairs = registryHelper.registerBlock(new StairsBaseBlock(woodRegistry.planks.getDefaultState()),
                    name.getPath() + "_stairs", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder slab() {
            woodRegistry.slab = registryHelper.registerBlock(new SlabBaseBlock(AbstractBlock.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_slab", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder planks() {
            woodRegistry.planks = registryHelper.registerBlock(new Block(FabricBlockSettings.of(Material.WOOD, MapColor.WOOD)
                            .strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)),
                    name.getPath() + "_planks", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder leaves() {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), name.getPath() + "_leaves",
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder leaves(String nameIn) {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), nameIn + "_leaves",
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder coloredLeaves() {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), name.getPath() + "_leaves",
                    ItemGroup.DECORATIONS);
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ColorProviderRegistryImpl.BLOCK.register((block, world, pos, layer) -> {
                    BlockColorProvider provider = ColorProviderRegistryImpl.BLOCK.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(block, world, pos, layer);
                }, woodRegistry.leaves);
                ColorProviderRegistryImpl.ITEM.register((item, layer) -> {
                    ItemColorProvider provider = ColorProviderRegistryImpl.ITEM.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(item, layer);
                }, woodRegistry.leaves);
            }
            return this;
        }

        public Builder sapling() {
            woodRegistry.sapling = registryHelper.registerBlock(new SaplingBaseBlock(woodRegistry.saplingGenerator),
                    name.getPath() + "_sapling", ItemGroup.DECORATIONS);
            return this;
        }

        public Builder fence() {
            woodRegistry.fence = registryHelper.registerBlock(new FenceBlock(AbstractBlock.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_fence", ItemGroup.DECORATIONS);
            return this;
        }

        public Builder fenceGate() {
            woodRegistry.fenceGate = registryHelper.registerBlock(new FenceGateBlock(AbstractBlock.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_fence_gate", ItemGroup.REDSTONE);
            return this;
        }

        public Builder bookshelf() {
            woodRegistry.bookshelf = registryHelper.registerBlock(new Block(Block.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_bookshelf", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder door() {
            woodRegistry.door = registryHelper.registerBlock(new DoorBaseBlock(AbstractBlock.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_door", ItemGroup.REDSTONE);
            return this;
        }

        public Builder trapdoor() {
            woodRegistry.trapdoor = registryHelper.registerBlock(new TrapdoorBaseBlock(AbstractBlock.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_trapdoor", ItemGroup.REDSTONE);
            return this;
        }

        public Builder button() {
            woodRegistry.button = registryHelper.registerBlock(new ButtonBaseBlock(true, AbstractBlock.Settings.copy(woodRegistry.planks)),
                    name.getPath() + "_button", ItemGroup.REDSTONE);
            return this;
        }

        public Builder pressurePlate(PressurePlateBlock.ActivationRule type) {
            woodRegistry.pressurePlate = registryHelper.registerBlock(new PressurePlateBaseBlock(AbstractBlock.Settings.copy(woodRegistry.planks), type),
                    name.getPath() + "_pressure_plate", ItemGroup.REDSTONE);
            return this;
        }

        public Builder ladder() {
            woodRegistry.ladder = registryHelper.registerBlock(new CustomLadderBlock(),
                    name.getPath() + "_ladder", ItemGroup.DECORATIONS);
            return this;
        }

        public WoodRegistry build() {
            return woodRegistry;
        }
    }

}