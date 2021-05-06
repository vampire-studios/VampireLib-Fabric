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

import com.google.common.collect.ImmutableMap;
import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.vampirelib.blocks.*;
import io.github.vampirestudios.vampirelib.boat.CustomBoatEntity;
import io.github.vampirestudios.vampirelib.boat.CustomBoatInfo;
import io.github.vampirestudios.vampirelib.client.VampireLibClient;
import io.github.vampirestudios.vampirelib.client.renderer.CustomBoatEntityRenderer;
import io.github.vampirestudios.vampirelib.init.VEntityModelLayers;
import io.github.vampirestudios.vampirelib.items.CustomBoatItem;
import io.github.vampirestudios.vampirelib.utils.ArtificeGenerationHelper;
import io.github.vampirestudios.vampirelib.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.fabricmc.fabric.impl.client.renderer.registry.EntityModelLayerImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//    private Block sign;
//    private Block wallSign;

//    private Item signItem;
    private Item boatItem;

    private EntityType<CustomBoatEntity> boatEntity;

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

    /*public Block getSign() {
        return sign;
    }

    public Block getWallSign() {
        return wallSign;
    }

    public Item getSignItem() {
        return signItem;
    }*/

    public Item getBoatItem() {
        return boatItem;
    }

    public static class Builder {

        public Identifier name;
        private WoodRegistry woodRegistry;
        private RegistryHelper registryHelper;
        private BoatEntity.Type boatType = BoatEntity.Type.OAK;
        private boolean flammable = true;
        private boolean mushroomLike = false;
        private boolean generateAssets = false;
        private boolean preRegisteredPlanks = false;
        private List<String> leaves = new ArrayList<>();
        private List<String> saplings = new ArrayList<>();

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
            preRegisteredPlanks = true;
            registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());

            return this;
        }

        public Builder of(Identifier name, SaplingGenerator saplingGenerator) {
            this.name = name;
            woodRegistry = new WoodRegistry(name, saplingGenerator);
            registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
            return this;
        }

        public Builder nonFlammable() {
            this.flammable = false;
            return this;
        }

        public Builder mushroomLike() {
            this.mushroomLike = true;
            return this;
        }

        public Builder shouldGenerateAssets() {
            this.generateAssets = true;
            return this;
        }

        public Builder defaultBlocks() {
            return this.log().strippedLog().wood().strippedWood().planks().leaves().sapling().door().trapdoor().boat();
        }

        public Builder log() {
            String logName = mushroomLike ? name.getPath() + "_stem" : this.name.getPath() + "_log";
            AbstractBlock.Settings blockSettings = mushroomLike ? FabricBlockSettings.copyOf(Blocks.WARPED_STEM) : FabricBlockSettings.copyOf(Blocks.OAK_LOG);
            woodRegistry.log = registryHelper.registerBlock(new PillarBlock(blockSettings), logName, ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder wood() {
            String woodName = mushroomLike ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
            AbstractBlock.Settings blockSettings = mushroomLike ? FabricBlockSettings.copyOf(Blocks.WARPED_HYPHAE) : FabricBlockSettings.copyOf(Blocks.OAK_WOOD);
            woodRegistry.wood = registryHelper.registerBlock(new PillarBlock(blockSettings), woodName, ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedLog() {
            String logName = mushroomLike ? name.getPath() + "_stem" : name.getPath() + "_log";
            AbstractBlock.Settings blockSettings = mushroomLike ? FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM) : FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG);
            woodRegistry.strippedLog = registryHelper.registerBlock(new PillarBlock(blockSettings), "stripped_" + logName, ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder strippedWood() {
            String woodName = mushroomLike ? name.getPath() + "_hyphae" : name.getPath() + "_wood";
            AbstractBlock.Settings blockSettings = mushroomLike ? FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_HYPHAE) : FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD);
            woodRegistry.strippedWood = registryHelper.registerBlock(new PillarBlock(blockSettings), "stripped_" + woodName, ItemGroup.BUILDING_BLOCKS);
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
            woodRegistry.planks = registryHelper.registerBlock(new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)),
                    name.getPath() + "_planks", ItemGroup.BUILDING_BLOCKS);
            return this;
        }

        public Builder wartBlock() {
            woodRegistry.leaves = registryHelper.registerBlock(new Block(FabricBlockSettings.copyOf(Blocks.NETHER_WART_BLOCK)),
                    name.getPath() + "_wart_block", ItemGroup.DECORATIONS);
            return this;
        }

        public Builder leaves() {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), name.getPath() + "_leaves",
                    ItemGroup.DECORATIONS);
            VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, false, 0xFFF);
            VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
            return this;
        }

        public Builder nonColoredLeaves() {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), name.getPath() + "_leaves",
                    ItemGroup.DECORATIONS);
            return this;
        }

        public Builder nonColoredLeaves(String nameIn) {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), nameIn + "_leaves",
                    ItemGroup.DECORATIONS);
            VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, false, 0xFFF);
            VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
            return this;
        }

        public Builder nonColoredLeaves(String... nameIn) {
            for(String leavesName : nameIn) {
                woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), leavesName + "_leaves",
                        ItemGroup.DECORATIONS);
                VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, false, 0xFFF);
                VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
                leaves.add(leavesName + "_leaves");
            }
            return this;
        }

        public Builder coloredLeaves(int color) {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), name.getPath() + "_leaves",
                    ItemGroup.DECORATIONS);
            VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color);
            VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
            return this;
        }

        public Builder coloredLeaves(String nameIn) {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), nameIn + "_leaves",
                    ItemGroup.DECORATIONS);
            VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, false, 0xFFF);
            VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
            return this;
        }

        public Builder coloredLeaves(String... nameIn) {
            for(String leavesName : nameIn) {
                woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), leavesName + "_leaves",
                        ItemGroup.DECORATIONS);
                VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, false, 0xFFF);
                VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
                leaves.add(leavesName + "_leaves");
            }
            return this;
        }

        public Builder coloredLeaves(String nameIn, int color) {
            woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), nameIn + "_leaves",
                    ItemGroup.DECORATIONS);
            VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, color);
            VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
            return this;
        }

        public Builder coloredLeaves(int color, String... nameIn) {
            for(String leavesName : nameIn) {
                woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), leavesName + "_leaves",
                        ItemGroup.DECORATIONS);
                VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, false, color);
                VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
                leaves.add(leavesName + "_leaves");
            }
            return this;
        }

        public Builder coloredLeaves(ColoredLeavesBlock... coloredLeavesBlocks) {
            for(ColoredLeavesBlock coloredLeavesBlock : coloredLeavesBlocks) {
                woodRegistry.leaves = registryHelper.registerBlock(new LeavesBaseBlock(), coloredLeavesBlock.name + "_leaves",
                        ItemGroup.DECORATIONS);
                VampireLibClient.ColoredLeaves coloredLeaves = new VampireLibClient.ColoredLeaves(woodRegistry.leaves, true, coloredLeavesBlock.color);
                VampireLibClient.COLORED_LEAVES.add(coloredLeaves);
                leaves.add(coloredLeavesBlock.name + "_leaves");
            }
            return this;
        }

        public Builder sapling() {
            woodRegistry.sapling = registryHelper.registerBlock(new SaplingBaseBlock(woodRegistry.saplingGenerator),
                    name.getPath() + "_sapling", ItemGroup.DECORATIONS);
            return this;
        }

        public Builder sapling(String nameIn) {
            woodRegistry.sapling = registryHelper.registerBlock(new SaplingBaseBlock(woodRegistry.saplingGenerator),
                    nameIn + "_sapling", ItemGroup.DECORATIONS);
            return this;
        }

        public Builder saplings(String... names) {
            for(String saplingName : names) {
                woodRegistry.sapling = registryHelper.registerBlock(new SaplingBaseBlock(woodRegistry.saplingGenerator),
                        saplingName + "_sapling", ItemGroup.DECORATIONS);
                saplings.add(saplingName + "_sapling");
            }
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

        /*public Builder sign() {
            BlockEntityType<SignBlockEntity> signBlockEntityBlockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, Utils.appendToPath(name, "_base"), BlockEntityType.Builder.create(SignBlockEntity::new).build(null));
            SignType signType = SignType.register(new SignType(name.getPath()));
            woodRegistry.sign = registryHelper.registerBlockWithoutItem(name.getPath() + "_sign", new CustomSignBlock(signType, FabricBlockSettings.copy(Blocks.OAK_SIGN)));
            woodRegistry.wallSign = registryHelper.registerBlockWithoutItem(name.getPath() + "_wall_sign", new CustomWallSignBlock(signType, FabricBlockSettings.copy(Blocks.OAK_WALL_SIGN)));
            woodRegistry.signItem = registryHelper.registerItem(name.getPath() + "_sign", new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), woodRegistry.sign, woodRegistry.wallSign));
            ((IBlockEntityType) signBlockEntityBlockEntityType).vl_addBlocks(woodRegistry.sign, woodRegistry.wallSign);
            return this;
        }*/

        public Builder boat() {
            woodRegistry.boatItem = registryHelper.registerItem(name.getPath() + "_boat", new CustomBoatItem(() -> woodRegistry.boatEntity, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION)));
            woodRegistry.boatEntity = Registry.register(Registry.ENTITY_TYPE, Utils.appendToPath(name,"_boat"), FabricEntityTypeBuilder.<CustomBoatEntity>create(SpawnGroup.MISC, (entity, world) -> new CustomBoatEntity(entity, world, new CustomBoatInfo(woodRegistry.boatItem, woodRegistry.planks.asItem(), Utils.appendAndPrependToPath(name, "textures/entity/boat/", ".png"), boatType))).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());
            return this;
        }

        public WoodRegistry build() {
            if(woodRegistry.leaves != null)
                ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(woodRegistry.leaves, 0.3F);
            if (flammable) {
                // flammable blocks
                int baseBurnChance = 5;
                int largeBurnChance = baseBurnChance * 6;

                int baseSpreadChance = 20;
                int smallSpreadChance = baseSpreadChance / 4;
                int largeSpreadChance = baseSpreadChance * 3;

                FlammableBlockRegistry fbrInstance = FlammableBlockRegistry.getDefaultInstance();
                if(woodRegistry.planks != null && !preRegisteredPlanks)
                    fbrInstance.add(woodRegistry.planks, baseBurnChance, baseSpreadChance);
                if(woodRegistry.slab != null)
                    fbrInstance.add(woodRegistry.slab, baseBurnChance, baseSpreadChance);
                if(woodRegistry.fenceGate != null)
                    fbrInstance.add(woodRegistry.fenceGate, baseBurnChance, baseSpreadChance);
                if(woodRegistry.fence != null)
                    fbrInstance.add(woodRegistry.fence, baseBurnChance, baseSpreadChance);
                if(woodRegistry.stairs != null)
                    fbrInstance.add(woodRegistry.stairs, baseBurnChance, baseSpreadChance);
                if(woodRegistry.log != null)
                    fbrInstance.add(woodRegistry.log, baseBurnChance, smallSpreadChance);
                if(woodRegistry.strippedLog != null)
                    fbrInstance.add(woodRegistry.strippedLog, baseBurnChance, smallSpreadChance);
                if(woodRegistry.strippedWood != null)
                    fbrInstance.add(woodRegistry.strippedWood, baseBurnChance, smallSpreadChance);
                if(woodRegistry.wood != null)
                    fbrInstance.add(woodRegistry.wood, baseBurnChance, smallSpreadChance);
                if(woodRegistry.leaves != null)
                    fbrInstance.add(woodRegistry.leaves, largeBurnChance, largeSpreadChance);

                // fuel registering
                int fenceFuelTime = 300;

                FuelRegistry frInstance = FuelRegistry.INSTANCE;
                if(woodRegistry.fence != null)
                    frInstance.add(woodRegistry.fence, fenceFuelTime);
                if(woodRegistry.fenceGate != null)
                    frInstance.add(woodRegistry.fenceGate, fenceFuelTime);
            }

            if(woodRegistry.log != null && woodRegistry.wood != null && woodRegistry.strippedLog != null && woodRegistry.strippedWood != null)
                new ImmutableMap.Builder<Block, Block>()
                        .put(woodRegistry.log, woodRegistry.strippedLog)
                        .put(woodRegistry.wood, woodRegistry.strippedWood)
                        .build().forEach((base, result) -> UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
                    if (player.getStackInHand(hand).isIn(FabricToolTags.AXES) && world.getBlockState(hit.getBlockPos()).getBlock() == base) {
                        BlockPos blockPos = hit.getBlockPos();
                        BlockState blockState = world.getBlockState(blockPos);

                        world.playSound(player, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!world.isClient) {
                            world.setBlockState(blockPos, result.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
                            if (!player.isCreative()) {
                                ItemStack stack = player.getStackInHand(hand);
                                stack.damage(1, player, ((p) -> p.sendToolBreakStatus(hand)));
                            }
                        }

                        return ActionResult.SUCCESS;
                    }

                    return ActionResult.PASS;
                }));

            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                if (generateAssets) {
                    Artifice.registerAssetPack(name, clientResourcePackBuilder -> {
                        String logName = mushroomLike ? "_stem" : "_log";
                        String woodSuffix = mushroomLike ? "_hyphae" : "_wood";
                        if(woodRegistry.log != null || woodRegistry.strippedLog != null) {
                            ArtificeGenerationHelper.generatePillarBlockState(clientResourcePackBuilder, Utils.appendToPath(name, logName));
                            ArtificeGenerationHelper.generateColumnBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, logName), Utils.appendToPath(name, logName + "_top"), Utils.appendToPath(name, logName));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, logName));

                            ArtificeGenerationHelper.generatePillarBlockState(clientResourcePackBuilder, Utils.appendAndPrependToPath(name, "stripped_", logName));
                            ArtificeGenerationHelper.generateColumnBlockModel(clientResourcePackBuilder, Utils.appendAndPrependToPath(name, "stripped_", logName), Utils.appendAndPrependToPath(name, "stripped_", logName + "_top"), Utils.appendAndPrependToPath(name, "stripped_", logName));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendAndPrependToPath(name, "stripped_", logName));
                        }
                        if(woodRegistry.wood != null || woodRegistry.strippedWood != null) {
                            ArtificeGenerationHelper.generatePillarBlockState(clientResourcePackBuilder, Utils.appendToPath(name, woodSuffix));
                            ArtificeGenerationHelper.generateAllBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, woodSuffix), Utils.appendToPath(name, logName));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, woodSuffix));

                            ArtificeGenerationHelper.generatePillarBlockState(clientResourcePackBuilder, Utils.appendAndPrependToPath(name, "stripped_", woodSuffix));
                            ArtificeGenerationHelper.generateAllBlockModel(clientResourcePackBuilder, Utils.appendAndPrependToPath(name, "stripped_", woodSuffix), Utils.appendAndPrependToPath(name, "stripped_", logName));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendAndPrependToPath(name, "stripped_", woodSuffix));
                        }

                        if(woodRegistry.planks != null && !preRegisteredPlanks) {
                            ArtificeGenerationHelper.generateBasicBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_planks"));
                            ArtificeGenerationHelper.generateAllBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, "_planks"), Utils.appendToPath(name, "_planks"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_planks"));
                        }

                        if(woodRegistry.bookshelf != null) {
                            ArtificeGenerationHelper.generateBasicBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_bookshelf"));
                            ArtificeGenerationHelper.generateColumnBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, "_bookshelf"), Utils.appendToPath(name, "_planks"), Utils.appendToPath(name, "_bookshelf"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_bookshelf"));
                        }

                        if(woodRegistry.leaves != null) {
                            if (!leaves.isEmpty()) {
                                for(String leaves : leaves) {
                                    Identifier leavesName = new Identifier(name.getNamespace(), leaves);
                                    ArtificeGenerationHelper.generateBasicBlockState(clientResourcePackBuilder, leavesName);
                                    ArtificeGenerationHelper.generateAllBlockModel(clientResourcePackBuilder, leavesName, leavesName);
                                    ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, leavesName);
                                }
                            } else {
                                ArtificeGenerationHelper.generateBasicBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_leaves"));
                                ArtificeGenerationHelper.generateAllBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, "_leaves"));
                                ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_leaves"));
                            }
                        }

                        if(woodRegistry.sapling != null) {
                            if (!saplings.isEmpty()) {
                                for(String sapling : saplings) {
                                    Identifier saplingName = new Identifier(name.getNamespace(), sapling);
                                    ArtificeGenerationHelper.generateBasicBlockState(clientResourcePackBuilder, saplingName);
                                    ArtificeGenerationHelper.generateCrossBlockModel(clientResourcePackBuilder, saplingName);
                                    ArtificeGenerationHelper.generateSimpleItemModel(clientResourcePackBuilder, saplingName, Utils.prependToPath(saplingName, "block/"));
                                }
                            } else {
                                ArtificeGenerationHelper.generateBasicBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_leaves"));
                                ArtificeGenerationHelper.generateAllBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, "_leaves"));
                                ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_leaves"));
                            }
                        }

                        if (woodRegistry.boatItem != null) {
                            ArtificeGenerationHelper.generateSimpleItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_boat"));
                        }

                        if (woodRegistry.ladder != null) {
                            ArtificeGenerationHelper.generateFacingBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_ladder"));
                            ArtificeGenerationHelper.generateLadderBlockModel(clientResourcePackBuilder, Utils.appendToPath(name, "_ladder"));
                            ArtificeGenerationHelper.generateSimpleItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_ladder"), Utils.appendAndPrependToPath(name, "block/", "_ladder"));
                        }

//                        if (woodRegistry.sign != null) {
//                            ArtificeGenerationHelper.generateSimpleItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_sign"));
//                        }

                        if (woodRegistry.slab != null) {
                            ArtificeGenerationHelper.generateSlabBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_slab"), Utils.appendToPath(name, "_planks"));
                            ArtificeGenerationHelper.generateSlabBlockModels(clientResourcePackBuilder, Utils.appendToPath(name, "_slab"), Utils.appendAndPrependToPath(name, "block/", "_planks"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_slab"));
                        }

                        if (woodRegistry.stairs != null) {
                            ArtificeGenerationHelper.generateStairsBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_stairs"));
                            ArtificeGenerationHelper.generateStairsBlockModels(clientResourcePackBuilder, Utils.appendToPath(name, "_stairs"), Utils.appendAndPrependToPath(name, "block/", "_planks"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_stairs"));
                        }

                        if (woodRegistry.fence != null) {
                            ArtificeGenerationHelper.generateFenceBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_fence"));
                            ArtificeGenerationHelper.generateFenceBlockModels(clientResourcePackBuilder, Utils.appendToPath(name, "_fence"), Utils.appendAndPrependToPath(name, "block/", "_planks"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_fence"), Utils.appendToPath(name, "_fence_inventory"));
                        }

                        if (woodRegistry.fenceGate != null) {
                            ArtificeGenerationHelper.generateFenceGateBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_fence_gate"));
                            ArtificeGenerationHelper.generateFenceGateBlockModels(clientResourcePackBuilder, Utils.appendToPath(name, "_fence_gate"), Utils.appendAndPrependToPath(name, "block/", "_planks"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_fence_gate"));
                        }

                        if (woodRegistry.door != null) {
                            ArtificeGenerationHelper.generateDoorBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_door"));
                            ArtificeGenerationHelper.generateDoorBlockModels(clientResourcePackBuilder, Utils.appendToPath(name, "_door"), Utils.appendAndPrependToPath(name, "block/", "_door_top"), Utils.appendAndPrependToPath(name, "block/", "_door_bottom"));
                            ArtificeGenerationHelper.generateSimpleItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_door"));
                        }

                        if (woodRegistry.trapdoor != null) {
                            ArtificeGenerationHelper.generateTrapdoorBlockState(clientResourcePackBuilder, Utils.appendToPath(name, "_trapdoor"));
                            ArtificeGenerationHelper.generateTrapdoorBlockModels(clientResourcePackBuilder, Utils.appendToPath(name, "_trapdoor"), Utils.appendAndPrependToPath(name, "block/", "_trapdoor"));
                            ArtificeGenerationHelper.generateBlockItemModel(clientResourcePackBuilder, Utils.appendToPath(name, "_trapdoor"), Utils.appendToPath(name, "_trapdoor_bottom"));
                        }
                        try {
                            clientResourcePackBuilder.dumpResources("test", "assets");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                if(woodRegistry.leaves != null) {
                    BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.leaves, RenderLayer.getCutoutMipped());
                }
                if(woodRegistry.sapling != null) {
                    BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.sapling, RenderLayer.getCutout());
                }
                if(woodRegistry.door != null) {
                    BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.sapling, RenderLayer.getCutout());
                }
                if(woodRegistry.trapdoor != null) {
                    BlockRenderLayerMapImpl.INSTANCE.putBlock(woodRegistry.sapling, RenderLayer.getCutout());
                }
//                if(woodRegistry.sign != null) {
//                    Map<SignType, SpriteIdentifier> textures = new HashMap<>(TexturedRenderLayersAccessor.getWOOD_TYPE_TEXTURES());
//                    SignType.stream().forEach(signType -> {
//                        if(signType.getName().equals(name.getPath())) {
//                            textures.put(signType, new SpriteIdentifier(SIGNS_ATLAS_TEXTURE, Utils.prependToPath(name, "entity/signs/")));
//                        }
//                    });
//                    TexturedRenderLayersAccessor.setWOOD_TYPE_TEXTURES(textures);
//                }
                if(woodRegistry.boatItem != null) {
                    EntityModelLayer entityModelLayer = VEntityModelLayers.createBoat(name);
                    EntityModelLayerImpl.PROVIDERS.put(entityModelLayer, BoatEntityModel::getTexturedModelData);
                    EntityRendererRegistry.INSTANCE.register(woodRegistry.boatEntity, ctx -> new CustomBoatEntityRenderer(entityModelLayer, ctx));
                }
            }
            Artifice.registerDataPack(name, serverResourcePackBuilder -> {
                if(woodRegistry.fence != null) {
                    serverResourcePackBuilder.addBlockTag(new Identifier("fences"), tagBuilder -> {
                        tagBuilder.replace(false);
                        tagBuilder.values(Utils.appendToPath(name, "_fence"));
                    });
                }
            });
//            WoodTypeRegistry.registerModded(new WoodType(name.toString(), woodRegistry.leaves, woodRegistry.log), 0.2F, 0.2F);
            return woodRegistry;
        }
    }

    public static class ColoredLeavesBlock {

        private String name;
        private int color;

        public ColoredLeavesBlock(String name, int color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return color;
        }
    }

}