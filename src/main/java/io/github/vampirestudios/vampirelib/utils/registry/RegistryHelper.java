/*
 * Copyright (c) 2022 OliviaTheVampire
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

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
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
		private final String modId;

		public Blocks(String modId) {
			this.modId = modId;
		}

		public Block registerBlock(Block block, String name) {
			registerBlock(block, name, CreativeModeTabs.BUILDING_BLOCKS);
			return block;
		}

		public Block registerBlock(Block block, String name, CreativeModeTab... itemGroups) {
			Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(modId, name), block);
			Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(modId, name),
					new BlockItem(block, new Item.Properties()));
			for (CreativeModeTab itemGroup : itemGroups) {
				ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.accept(item));
			}
			return block;
		}

		public Block registerBlockWithWallBlock(Block block, Block wallBlock, String name) {
			Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(modId, name), block);
			Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(modId, name),
					new StandingAndWallBlockItem(block, wallBlock, new Item.Properties(), Direction.DOWN));
			ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> entries.accept(item));
			return block;
		}

		public Block registerBlockWithoutItem(String name, Block block) {
			Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(modId, name), block);
			return block;
		}
	}

	public static class Items {
		private final String modId;

		public Items(String modId) {
			this.modId = modId;
		}

		public Item registerItem(String name, Item item) {
			return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(modId, name), item);
		}

		public Item registerSpawnEgg(String name, EntityType<? extends Mob> entity, int primaryColor, int secondaryColor) {
			Item item = registerItem(name + "_spawn_egg",
					new SpawnEggItem(entity, primaryColor, secondaryColor, new Item.Properties()));
			ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> entries.accept(item));
			SpawnEggItem.BY_ID.put(entity, (SpawnEggItem) item);
			return item;
		}

		public Potion registerPotion(String name, Potion potion) {
			return Registry.register(BuiltInRegistries.POTION, new ResourceLocation(modId, name), potion);
		}
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder.Factory<T> blockEntityType, Class<? extends Block> block, String name) {
		FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(blockEntityType,
				collectBlocks(block));
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder<T> builder, String name) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return BuiltInRegistries.BLOCK.stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}

	public <T extends Entity> EntityType<T> registerEntity(FabricEntityTypeBuilder<T> builder, String name) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public SoundEvent createSoundEvent(String name) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, name, SoundEvent.createVariableRangeEvent(new ResourceLocation(modId, name)));
	}

	public SoundEvent registerSoundEvent(SoundEvent soundEvent, String name) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(modId, name), soundEvent);
	}

}
