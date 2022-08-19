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

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;

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
			registerBlock(block, name, CreativeModeTab.TAB_DECORATIONS);
			return block;
		}

		public Block registerBlock(Block block, String name, CreativeModeTab itemGroup) {
			Registry.register(Registry.BLOCK, new ResourceLocation(modId, name), block);
			Registry.register(Registry.ITEM, new ResourceLocation(modId, name),
					new BlockItem(block, new Item.Properties().tab(itemGroup)));
			return block;
		}

		public Block registerBlockWithWallBlock(Block block, Block wallBlock, String name) {
			Registry.register(Registry.BLOCK, new ResourceLocation(modId, name), block);
			Registry.register(Registry.ITEM, new ResourceLocation(modId, name),
					new StandingAndWallBlockItem(block, wallBlock, new Item.Properties()
							.tab(CreativeModeTab.TAB_DECORATIONS)));
			return block;
		}

		public Block registerBlockWithoutItem(String name, Block block) {
			Registry.register(Registry.BLOCK, new ResourceLocation(modId, name), block);
			return block;
		}
	}

	public static class Items {
		private final String modId;

		public Items(String modId) {
			this.modId = modId;
		}

		public Item registerItem(String name, Item item) {
			return Registry.register(Registry.ITEM, new ResourceLocation(modId, name), item);
		}

		public Item registerCompatItem(String modName, String itemName, Item.Properties settings, CreativeModeTab itemGroup) {
			if (!FabricLoader.getInstance().isModLoaded(modName)) {
				return registerItem(itemName, new Item(settings.tab(itemGroup)));
			} else {
				return null;
			}
		}

		public Item registerSpawnEgg(String name, EntityType<? extends Mob> entity, int primaryColor, int secondaryColor) {
			Item item = registerItem(name + "_spawn_egg",
					new SpawnEggItem(entity, primaryColor, secondaryColor, new Item.Properties()
							.tab(CreativeModeTab.TAB_MISC)));
			SpawnEggItem.BY_ID.put(entity, (SpawnEggItem) item);
			return item;
		}

		public Potion registerPotion(String name, Potion potion) {
			return Registry.register(Registry.POTION, new ResourceLocation(modId, name), potion);
		}
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder.Factory<T> blockEntityType, Class<? extends Block> block, String name) {
		FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(blockEntityType,
				collectBlocks(block));
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder<T> builder, String name) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return Registry.BLOCK.stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}

	public <T extends Entity> EntityType<T> registerEntity(FabricEntityTypeBuilder<T> builder, String name) {
		return Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(modId, name), builder.build());
	}

	public SoundEvent createSoundEvent(String name) {
		return Registry.register(Registry.SOUND_EVENT, name, new SoundEvent(new ResourceLocation(modId, name)));
	}

	public SoundEvent registerSoundEvent(SoundEvent soundEvent, String name) {
		return Registry.register(Registry.SOUND_EVENT, new ResourceLocation(modId, name), soundEvent);
	}

}
