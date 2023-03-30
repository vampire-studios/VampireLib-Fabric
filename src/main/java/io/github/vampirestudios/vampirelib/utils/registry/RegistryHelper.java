/*
 * Copyright (c) 2022-2023 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.utils.registry;

import java.util.Map;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

public record RegistryHelper(String modId) {
	public static RegistryHelper createRegistryHelper(String modId) {
		return new RegistryHelper(modId);
	}

	public Blocks blocks() {
		return new Blocks(modId());
	}

	public Items items() {
		return new Items(modId());
	}

	public static class Blocks {
		protected final String modId;

		public Blocks(String modId) {
			this.modId = modId;
		}

		public Block registerBlock(Block block, String name) {
			registerBlock(block, name, CreativeModeTabs.BUILDING_BLOCKS);
			return block;
		}

		public Block registerBlock(Block block, String name, CreativeModeTab itemGroup) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = register(BuiltInRegistries.ITEM, name, new BlockItem(block, new Item.Properties()));
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.accept(item));
			return block;
		}

		public Block registerBlock(Block block, String name, CreativeModeTab... itemGroups) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = register(BuiltInRegistries.ITEM, name, new BlockItem(block, new Item.Properties()));
			for (CreativeModeTab itemGroup : itemGroups) {
				ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.accept(item));
			}
			return block;
		}

		public Block registerBlock(Block block, String name, CreativeModeTab itemGroup, Block parentBlock) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = register(BuiltInRegistries.ITEM, name, new BlockItem(block, new Item.Properties()));
			if (parentBlock != null) {
				ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.addAfter(parentBlock, item));
			}
			return block;
		}

		public Block registerBlockWood(Block block, String name, CreativeModeTab itemGroup, Block parentBlock) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = register(BuiltInRegistries.ITEM, name, new BlockItem(block, new Item.Properties()));
			if (parentBlock != null) {
				ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.addBefore(parentBlock, item));
			}
			return block;
		}

		public Block registerDoubleBlock(Block block, String name, CreativeModeTab itemGroup) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = register(BuiltInRegistries.ITEM, name, new DoubleHighBlockItem(block, new Item.Properties()));
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.accept(item));
			return block;
		}

		public Block registerDoubleBlock(Block block, String name, CreativeModeTab itemGroup, Block parentBlock) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = register(BuiltInRegistries.ITEM, name, new DoubleHighBlockItem(block, new Item.Properties()));
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.addAfter(parentBlock, item));
			return block;
		}

		public Block registerBlockWithoutCreativeTab(Block block, String name) {
			register(BuiltInRegistries.BLOCK, name, block);
			register(BuiltInRegistries.ITEM, name, new BlockItem(block, new Item.Properties()));
			return block;
		}

		public Block registerBlock(Block block, String name, Block parentBlock, CreativeModeTab... itemGroups) {
			Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(modId, name), block);
			Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(modId, name),
					new BlockItem(block, new Item.Properties()));
			for (CreativeModeTab itemGroup : itemGroups) {
				ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.addAfter(parentBlock, item));
			}
			return block;
		}

		public Block registerBlockWood(Block block, String name, Block parentBlock, CreativeModeTab... itemGroups) {
			Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(modId, name), block);
			Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(modId, name),
					new BlockItem(block, new Item.Properties()));
			for (CreativeModeTab itemGroup : itemGroups) {
				ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.addBefore(parentBlock, item));
			}
			return block;
		}

		public Block registerBlock(Block block, String name, Map<ItemLike, CreativeModeTab> itemGroups) {
			Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(modId, name), block);
			Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(modId, name),
					new BlockItem(block, new Item.Properties()));
			itemGroups.forEach((block1, creativeModeTab) -> ItemGroupEvents.modifyEntriesEvent(creativeModeTab)
					.register(entries -> entries.addAfter(block1, item)));
			return block;
		}

		public Block registerBlockWithWallBlock(Block block, Block wallBlock, String name) {
			register(BuiltInRegistries.BLOCK, name, block);
			Item item = new StandingAndWallBlockItem(block, wallBlock, new Item.Properties(), Direction.DOWN);
			register(BuiltInRegistries.ITEM, name, item);
			ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> entries.accept(item));
			return block;
		}

		public Block registerBlockWithoutItem(String name, Block block) {
			register(BuiltInRegistries.BLOCK, name, block);
			return block;
		}

		protected <T> T register(Registry<T> registry, String name, T object) {
			return Registry.register(registry, new ResourceLocation(modId, name), object);
		}
	}

	public static class Items {
		private final String modId;

		public Items(String modId) {
			this.modId = modId;
		}

		public Item registerItem(String name, Item item) {
			return register(BuiltInRegistries.ITEM, name, item);
		}

		public Item registerItem(String name, Item item, CreativeModeTab creativeModeTab) {
			Item registeredItem = register(BuiltInRegistries.ITEM, name, item);
			ItemGroupEvents.modifyEntriesEvent(creativeModeTab).register(entries -> entries.accept(registeredItem));
			return registeredItem;
		}

		public Item registerItem(String name, Item item, CreativeModeTab creativeModeTab, Item vanillaItem) {
			Item registeredItem = register(BuiltInRegistries.ITEM, name, item);
			ItemGroupEvents.modifyEntriesEvent(creativeModeTab).register(entries -> entries.addAfter(vanillaItem, registeredItem));
			return registeredItem;
		}

		public Item registerSpawnEgg(String name, EntityType<? extends Mob> entity, int primaryColor, int secondaryColor) {
			Item item = registerItem(name + "_spawn_egg",
					new SpawnEggItem(entity, primaryColor, secondaryColor, new Item.Properties()));
			ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> entries.accept(item));
			SpawnEggItem.BY_ID.put(entity, (SpawnEggItem) item);
			return item;
		}

		public Potion registerPotion(String name, Potion potion) {
			return register(BuiltInRegistries.POTION, name, potion);
		}

		private <T> T register(Registry<T> registry, String name, T object) {
			return Registry.register(registry, new ResourceLocation(modId, name), object);
		}
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder.Factory<T> blockEntityType, Class<? extends Block> block, String name) {
		FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(blockEntityType,
				collectBlocks(block));
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder<T> builder, String name) {
		return (BlockEntityType<T>) register(BuiltInRegistries.BLOCK_ENTITY_TYPE, name, builder.build());
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return BuiltInRegistries.BLOCK.stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}

	public <T extends Entity> EntityType<T> registerEntity(FabricEntityTypeBuilder<T> builder, String name) {
		return (EntityType<T>) register(BuiltInRegistries.ENTITY_TYPE, name, builder.build());
	}

	public SoundEvent createSoundEvent(String name) {
		return register(BuiltInRegistries.SOUND_EVENT, name, SoundEvent.createVariableRangeEvent(new ResourceLocation(modId, name)));
	}

	public SoundEvent registerSoundEvent(SoundEvent soundEvent, String name) {
		return register(BuiltInRegistries.SOUND_EVENT, name, soundEvent);
	}

	private <T> T register(Registry<T> registry, String name, T object) {
		return Registry.register(registry, new ResourceLocation(modId(), name), object);
	}

}
