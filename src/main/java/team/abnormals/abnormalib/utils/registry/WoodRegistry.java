/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package team.abnormals.abnormalib.utils.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import team.abnormals.abnormalib.blocks.*;
import team.abnormals.abnormalib.blocks.entity.ChestBaseBlockEntity;
import team.abnormals.abnormalib.client.renderer.ChestBaseBlockEntityRenderer;
import team.abnormals.abnormalib.utils.Utils;

public class WoodRegistry {

    public Identifier name;
    private Block log;
    private Block wood;
    private Block strippedLog;
    private Block strippedWood;
    private Block stairs;
    private Block slab;
    private Block planks;
    private Block patternedPlanks;
    private Block carvedPlanks;
    private Block leaves;
    private Block chest;
    private Block trappedChest;
    private Block sapling;
    private Block fence;
    private Block fenceGate;
    private Block lectern;
    private Block paperLantern;
    private Block logCampfire;
    private Block strippedLogCampfire;
    private Block barrel;
    private Block bookshelf;
    private Block door;
    private Block trapdoor;
    private Block button;
    private Block pressurePlate;
    private Block ladder;
    private Block corner;
    private Block post;
    private Block siding;
    private SaplingGenerator saplingGenerator;

    private WoodRegistry(Identifier name, SaplingGenerator saplingGenerator) {
        this.name = name;
        this.saplingGenerator = saplingGenerator;
    }

    private WoodRegistry(Identifier name) {
        this.name = name;
        this.saplingGenerator = null;
    }

    public static WoodRegistry getInstance(Identifier name, SaplingGenerator saplingGenerator) {
        return new WoodRegistry(name, saplingGenerator);
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

    public Block getPatternedPlanks() {
        return patternedPlanks;
    }

    public Block getCarvedPlanks() {
        return carvedPlanks;
    }

    public Block getLeaves() {
        return leaves;
    }

    public Block getChest() {
        return chest;
    }

    public Block getTrappedChest() {
        return trappedChest;
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

    public Block getLectern() {
        return lectern;
    }

    public Block getPaperLantern() {
        return paperLantern;
    }

    public Block getLogCampfire() {
        return logCampfire;
    }

    public Block getStrippedLogCampfire() {
        return strippedLogCampfire;
    }

    public Block getBarrel() {
        return barrel;
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

    public Block getCorner() {
        return corner;
    }

    public Block getPost() {
        return post;
    }

    public Block getSiding() {
        return siding;
    }

    public static class Builder {

        public Identifier name;
        private WoodRegistry woodRegistry;

        public Builder(Identifier name) {
            this.name = name;
            woodRegistry = new WoodRegistry(name);
        }

        public Builder(Identifier name, Block planks) {
            this.name = name;
            woodRegistry = new WoodRegistry(name);
            woodRegistry.planks = planks;
        }

        public Builder(Identifier name, SaplingGenerator saplingGenerator) {
            this.name = name;
            woodRegistry = new WoodRegistry(name, saplingGenerator);
        }

        public Builder log() {
            woodRegistry.log = RegistryUtils.register(new PillarBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE).hardness(2.0F)
                    .sounds(BlockSoundGroup.WOOD).build()), new Identifier(name.getNamespace(), name.getPath() + "_log"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder wood() {
            woodRegistry.wood = RegistryUtils.register(new BaseModBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).hardness(2.0F)
                    .sounds(BlockSoundGroup.WOOD)), new Identifier(name.getNamespace(), name.getPath() + "_wood"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedLog() {
            woodRegistry.strippedLog = RegistryUtils.register(new PillarBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE).hardness(2.0F)
                            .sounds(BlockSoundGroup.WOOD).build()), new Identifier(name.getNamespace(), "stripped_" + name.getPath() + "_log"),
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedWood() {
            woodRegistry.strippedWood = RegistryUtils.register(new BaseModBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).hardness(2.0F)
                            .sounds(BlockSoundGroup.WOOD)), new Identifier(name.getNamespace(), "stripped_" + name.getPath() + "_wood"),
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder stairs() {
            woodRegistry.stairs = RegistryUtils.register(new StairsBaseBlock(woodRegistry.planks.getDefaultState()), new Identifier(name.getNamespace(),
                    name.getPath() + "_stairs"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder slab() {
            woodRegistry.slab = RegistryUtils.register(new SlabBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_slab"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder planks() {
            woodRegistry.planks = RegistryUtils.register(new BaseModBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
                            .strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_planks"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder patternedPlanks() {
            woodRegistry.patternedPlanks = RegistryUtils.register(new BaseModBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
                            .strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()),
                    new Identifier(name.getNamespace(), "patterned_" + name.getPath() + "_planks"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder carvedPlanks() {
            woodRegistry.carvedPlanks = RegistryUtils.register(new BaseModBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
                            .strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()),
                    new Identifier(name.getNamespace(), "carved_" + name.getPath() + "_planks"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder leaves() {
            woodRegistry.leaves = RegistryUtils.register(new LeavesBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_leaves"),
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder leaves(String nameIn) {
            woodRegistry.leaves = RegistryUtils.register(new LeavesBaseBlock(), new Identifier(name.getNamespace(), nameIn + "_leaves"),
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder coloredLeaves() {
            woodRegistry.leaves = RegistryUtils.register(new LeavesBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_leaves"),
                    ItemGroup.DECORATIONS);
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ColorProviderRegistry.BLOCK.register((block, world, pos, layer) -> {
                    BlockColorProvider provider = ColorProviderRegistry.BLOCK.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(block, world, pos, layer);
                }, woodRegistry.leaves);
                ColorProviderRegistry.ITEM.register((item, layer) -> {
                    ItemColorProvider provider = ColorProviderRegistry.ITEM.get(Blocks.OAK_LEAVES);
                    return provider == null ? -1 : provider.getColor(item, layer);
                }, woodRegistry.leaves);
            }
            return this;
        }

        public Builder chest() {
            ChestBaseBlock chestBaseBlock = new ChestBaseBlock();
            woodRegistry.chest = RegistryUtils.register(chestBaseBlock, new Identifier(name.getNamespace(), name.getPath() + "_chest"), ItemGroup.DECORATIONS);
            chestBaseBlock.setChestTexture(new Identifier(name.getNamespace(), "textures/entity/chest/" + name.getPath() + ".png"));
            chestBaseBlock.setDoubleChestTexture(new Identifier(name.getNamespace(), "textures/entity/chest/" + name.getPath() + "_double.png"));
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                BlockEntityRendererRegistry.INSTANCE.register(ChestBaseBlockEntity.class, new ChestBaseBlockEntityRenderer());
            }
            return this;
        }

        public Builder trappedChest() {
            ChestBaseBlock chestBaseBlock = new ChestBaseBlock();
            woodRegistry.trappedChest = RegistryUtils.register(chestBaseBlock, new Identifier(name.getNamespace(), "trapped_" + name.getPath() + "_chest"),
                    ItemGroup.DECORATIONS);
            chestBaseBlock.setTrappedChestTexture(new Identifier(name.getNamespace(), "textures/entity/chest/" + name.getPath() + "_trapped.png"));
            chestBaseBlock.setTrappedDoubleChestTexture(new Identifier(name.getNamespace(), "textures/entity/chest/" + name.getPath() + "_trapped_double.png"));
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                BlockEntityRendererRegistry.INSTANCE.register(ChestBaseBlockEntity.class, new ChestBaseBlockEntityRenderer());
            }
            return this;
        }

        public Builder sapling() {
            woodRegistry.sapling = RegistryUtils.register(new SaplingBaseBlock(woodRegistry.saplingGenerator),
                    new Identifier(name.getNamespace(), name.getPath() + "_sapling"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder waterloggedSapling() {
            woodRegistry.sapling = RegistryUtils.register(new WaterloggedSaplingBaseBlock(woodRegistry.saplingGenerator),
                    new Identifier(name.getNamespace(), name.getPath() + "_underwater_sapling"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder fence() {
            woodRegistry.fence = RegistryUtils.register(new FenceBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_fence"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder fenceGate() {
            woodRegistry.fenceGate = RegistryUtils.register(new FenceGateBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_fence_gate"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder lectern() {
            woodRegistry.lectern = RegistryUtils.register(new LecternBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_lectern"),
                    ItemGroup.REDSTONE);
            return this;
        }

        public Builder paperLantern() {
            woodRegistry.paperLantern = RegistryUtils.register(new PaperLanternBaseBlock(), new Identifier(name.getNamespace(),
                    name.getPath() + "_paper_lantern"), ItemGroup.BUILDING_BLOCKS);
            DispenserBlock.registerBehavior(woodRegistry.paperLantern, Utils::dispense);
            return this;
        }

        public Builder logCampfire() {
            woodRegistry.logCampfire = RegistryUtils.register(new CampfireBaseBlock(), new Identifier(name.getNamespace(),
                    name.getPath() + "_campfire"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder strippedLogCampfire() {
            woodRegistry.strippedLogCampfire = RegistryUtils.register(new CampfireBaseBlock(), new Identifier(name.getNamespace(),
                    "stripped_" + name.getPath() + "_campfire"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder barrel() {
            woodRegistry.barrel = RegistryUtils.register(new BarrelBlock(Block.Settings.copy(woodRegistry.planks)),
                    new Identifier(name.getNamespace(), name.getPath() + "_barrel"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder bookshelf() {
            woodRegistry.bookshelf = RegistryUtils.register(new BaseModBlock(Block.Settings.copy(woodRegistry.planks)),
                    new Identifier(name.getNamespace(), name.getPath() + "_bookshelf"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder door() {
            woodRegistry.door = RegistryUtils.register(new DoorBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_door"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder trapdoor() {
            woodRegistry.trapdoor = RegistryUtils.register(new TrapdoorBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_trapdoor"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder button() {
            woodRegistry.button = RegistryUtils.register(new ButtonBaseBlock(true, FabricBlockSettings.copy(woodRegistry.planks).build()),
                    new Identifier(name.getNamespace(), name.getPath() + "_button"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder pressurePlate(PressurePlateBlock.ActivationRule type) {
            woodRegistry.pressurePlate = RegistryUtils.register(new PressurePlateBaseBlock(FabricBlockSettings.copy(woodRegistry.planks).build(), type),
                    new Identifier(name.getNamespace(), name.getPath() + "_pressure_plate"), ItemGroup.REDSTONE);
            return this;
        }

        public Builder ladder() {
            woodRegistry.ladder = RegistryUtils.register(new CustomLadderBlock(), new Identifier(name.getNamespace(),
                    name.getPath() + "_ladder"), ItemGroup.DECORATIONS);
            return this;
        }

        public Builder corner() {
            woodRegistry.corner = RegistryUtils.register(new CornerBaseBlock(woodRegistry.planks.getDefaultState(),
                            Block.Settings.copy(woodRegistry.planks)), new Identifier(name.getNamespace(), name.getPath() + "_corner"),
                    ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder post() {
            woodRegistry.post = RegistryUtils.register(new PostBaseBlock(Block.Settings.copy(woodRegistry.planks)), new Identifier(name.getNamespace(),
                    name.getPath() + "_post"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder siding() {
            woodRegistry.siding = RegistryUtils.register(new SidingBaseBlock(Block.Settings.copy(woodRegistry.planks)), new Identifier(name.getNamespace(),
                    name.getPath() + "_siding"), ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public WoodRegistry build() {
            return woodRegistry;
        }

    }

}