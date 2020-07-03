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

import io.github.vampirestudios.vampirelib.blocks.CompatBlock;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class RegistryHelper {

    private final String modId;

    RegistryHelper(String modId) {
        this.modId = modId;
    }

    public static RegistryHelper createRegistryHelper(String modId) {
        return new RegistryHelper(modId);
    }

    public Block registerBlock(Block block, String name) {
        registerBlock(block, name, ItemGroup.DECORATIONS);
        return block;
    }

    public Block registerBlock(Block block, String name, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(modId, name), block);
        BlockItem item = new BlockItem(block, new Item.Settings().group(itemGroup));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, new Identifier(modId, name), item);
        return block;
    }

    public Block registerBlockWithWallBlock(Block block, Block wallBlock, String name) {
        Registry.register(Registry.BLOCK, new Identifier(modId, name), block);
        Registry.register(Registry.ITEM, new Identifier(modId, name), new WallStandingBlockItem(block, wallBlock, new Item.Settings().group(ItemGroup.DECORATIONS)));
        return block;
    }

    public Block registerNetherStem(String name, MaterialColor materialColor) {
        return registerBlock(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> materialColor)
                        .strength(1.0F).sounds(BlockSoundGroup.NETHER_STEM)), name,
                ItemGroup.BUILDING_BLOCKS);
    }

    public Block registerLog(String name, MaterialColor materialColor, MaterialColor materialColor2) {
        return registerBlock(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) ->
                blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? materialColor : materialColor2)
                .strength(2.0F).sounds(BlockSoundGroup.WOOD)), name);
    }

    public Block registerBlockWithoutItem(Block block, String name) {
        Registry.register(Registry.BLOCK, new Identifier(modId, name), block);
        return block;
    }

    public Item registerItem(Item item, String name) {
        return Registry.register(Registry.ITEM, new Identifier(modId, name), item);
    }

    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(BlockEntityType.Builder<T> builder, String name) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modId, name), blockEntityType);
    }

    public Block registerCompatBlock(String modName, String blockName, Block block, ItemGroup itemGroup) {
        if (!FabricLoader.getInstance().isModLoaded(modName)) {
            return registerBlock(block, blockName, itemGroup);
        } else {
            return null;
        }
    }

    public Block registerCompatBlock(String modName, String blockName, Identifier modBlock, AbstractBlock.Settings settings, ItemGroup itemGroup) {
        if (!FabricLoader.getInstance().isModLoaded(modName)) {
            return registerBlock(new CompatBlock(modName, Registry.BLOCK.get(modBlock), settings), blockName, itemGroup);
        } else {
            return null;
        }
    }

    public Item registerCompatItem(String modName, String itemName, Item.Settings settings, ItemGroup itemGroup) {
        if (!FabricLoader.getInstance().isModLoaded(modName)) {
            return registerItem(new Item(settings.group(itemGroup)), itemName);
        } else {
            return null;
        }
    }

    public SoundEvent createSoundEvent(String name) {
        return Registry.register(Registry.SOUND_EVENT, name, new SoundEvent(new Identifier(modId, name)));
    }

    public SoundEvent registerSoundEvent(SoundEvent soundEvent, String name) {
        return Registry.register(Registry.SOUND_EVENT, new Identifier(modId, name), soundEvent);
    }

    public Item registerSpawnEgg(String name, EntityType<? extends Entity> entity, int primaryColor, int secondaryColor) {
        return registerItem(new SpawnEggItem(entity, primaryColor, secondaryColor, new Item.Settings().group(ItemGroup.MISC)), name + "_spawn_egg");
    }

    public Potion registerPotion(String name, Potion potion) {
        return Registry.register(Registry.POTION, new Identifier(modId, name), potion);
    }

}