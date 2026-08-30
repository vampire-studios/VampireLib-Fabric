package io.github.vampirestudios.vampirelib.utils.registry;

import java.util.List;
import java.util.Map;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

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

	private static Identifier identifier(String modId, String name) {
		return Identifier.fromNamespaceAndPath(modId, name);
	}

	private static ResourceKey<Item> itemKey(String modId, String name) {
		return ResourceKey.create(Registries.ITEM, identifier(modId, name));
	}

	private static Item.Properties itemProperties(String modId, String name) {
		return new Item.Properties().setId(itemKey(modId, name));
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

		public Block registerBlock(Block block, String name, ResourceKey<CreativeModeTab> itemGroup) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = register(
				BuiltInRegistries.ITEM,
				name,
				new BlockItem(block, itemProperties(modId, name))
			);

			CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(entries -> entries.accept(item));

			return block;
		}

		public Block registerBlock(Block block, String name, ResourceKey<CreativeModeTab>... itemGroups) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = register(
				BuiltInRegistries.ITEM,
				name,
				new BlockItem(block, itemProperties(modId, name))
			);

			for (ResourceKey<CreativeModeTab> itemGroup : itemGroups) {
				CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(entries -> entries.accept(item));
			}

			return block;
		}

		public Block registerBlock(
			Block block,
			String name,
			ResourceKey<CreativeModeTab> itemGroup,
			Block parentBlock
		) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = register(
				BuiltInRegistries.ITEM,
				name,
				new BlockItem(block, itemProperties(modId, name))
			);

			if (parentBlock != null) {
				CreativeModeTabEvents.modifyOutputEvent(itemGroup)
					.register(entries -> entries.insertAfter(parentBlock, item));
			}

			return block;
		}

		public Block registerBlockWood(
			Block block,
			String name,
			ResourceKey<CreativeModeTab> itemGroup,
			Block parentBlock
		) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = register(
				BuiltInRegistries.ITEM,
				name,
				new BlockItem(block, itemProperties(modId, name))
			);

			if (parentBlock != null) {
				CreativeModeTabEvents.modifyOutputEvent(itemGroup)
					.register(entries -> entries.insertBefore(parentBlock, item));
			}

			return block;
		}

		public Block registerDoubleBlock(
			Block block,
			String name,
			ResourceKey<CreativeModeTab> itemGroup
		) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = register(
				BuiltInRegistries.ITEM,
				name,
				new DoubleHighBlockItem(block, itemProperties(modId, name))
			);

			CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(entries -> entries.accept(item));

			return block;
		}

		public Block registerDoubleBlock(
			Block block,
			String name,
			ResourceKey<CreativeModeTab> itemGroup,
			Block parentBlock
		) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = register(
				BuiltInRegistries.ITEM,
				name,
				new DoubleHighBlockItem(block, itemProperties(modId, name))
			);

			CreativeModeTabEvents.modifyOutputEvent(itemGroup)
				.register(entries -> entries.insertAfter(parentBlock, item));

			return block;
		}

		public Block registerBlockWithoutCreativeTab(Block block, String name) {
			register(BuiltInRegistries.BLOCK, name, block);

			register(
				BuiltInRegistries.ITEM,
				name,
				new BlockItem(block, itemProperties(modId, name))
			);

			return block;
		}

		public Block registerBlock(
			Block block,
			String name,
			Block parentBlock,
			ResourceKey<CreativeModeTab>... itemGroups
		) {
			Identifier id = identifier(modId, name);

			Registry.register(BuiltInRegistries.BLOCK, id, block);

			Item item = Registry.register(
				BuiltInRegistries.ITEM,
				id,
				new BlockItem(block, itemProperties(modId, name))
			);

			for (ResourceKey<CreativeModeTab> itemGroup : itemGroups) {
				CreativeModeTabEvents.modifyOutputEvent(itemGroup)
					.register(entries -> entries.insertAfter(parentBlock, item));
			}

			return block;
		}

		public Block registerBlockWood(
			Block block,
			String name,
			Block parentBlock,
			ResourceKey<CreativeModeTab>... itemGroups
		) {
			Identifier id = identifier(modId, name);

			Registry.register(BuiltInRegistries.BLOCK, id, block);

			Item item = Registry.register(
				BuiltInRegistries.ITEM,
				id,
				new BlockItem(block, itemProperties(modId, name))
			);

			for (ResourceKey<CreativeModeTab> itemGroup : itemGroups) {
				CreativeModeTabEvents.modifyOutputEvent(itemGroup)
					.register(entries -> entries.insertBefore(parentBlock, item));
			}

			return block;
		}

		public Block registerBlock(
			Block block,
			String name,
			Map<ItemLike, ResourceKey<CreativeModeTab>> itemGroups
		) {
			Identifier id = identifier(modId, name);

			Registry.register(BuiltInRegistries.BLOCK, id, block);

			Item item = Registry.register(
				BuiltInRegistries.ITEM,
				id,
				new BlockItem(block, itemProperties(modId, name))
			);

			itemGroups.forEach((parentItem, creativeModeTab) ->
				CreativeModeTabEvents.modifyOutputEvent(creativeModeTab)
					.register(entries -> entries.insertAfter(parentItem, item))
			);

			return block;
		}

		public Block registerBlockWithWallBlock(Block block, Block wallBlock, String name) {
			register(BuiltInRegistries.BLOCK, name, block);

			Item item = new StandingAndWallBlockItem(
				block,
				wallBlock,
				Direction.DOWN,
				itemProperties(modId, name)
			);

			register(BuiltInRegistries.ITEM, name, item);

			CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
				.register(entries -> entries.accept(item));

			return block;
		}

		public Block registerBlockWithoutItem(String name, Block block) {
			register(BuiltInRegistries.BLOCK, name, block);
			return block;
		}

		protected <T> T register(Registry<T> registry, String name, T object) {
			return Registry.register(registry, identifier(modId, name), object);
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

		public Item registerItem(
			String name,
			Item item,
			ResourceKey<CreativeModeTab> creativeModeTab
		) {
			Item registeredItem = register(BuiltInRegistries.ITEM, name, item);

			CreativeModeTabEvents.modifyOutputEvent(creativeModeTab)
				.register(entries -> entries.accept(registeredItem));

			return registeredItem;
		}

		public Item registerItem(
			String name,
			Item item,
			ResourceKey<CreativeModeTab> creativeModeTab,
			Item vanillaItem
		) {
			Item registeredItem = register(BuiltInRegistries.ITEM, name, item);

			CreativeModeTabEvents.modifyOutputEvent(creativeModeTab)
				.register(entries -> entries.insertAfter(vanillaItem, registeredItem));

			return registeredItem;
		}

		public Item registerItemWood(
			String name,
			Item item,
			ResourceKey<CreativeModeTab> creativeModeTab,
			Item vanillaItem
		) {
			Item registeredItem = register(BuiltInRegistries.ITEM, name, item);

			CreativeModeTabEvents.modifyOutputEvent(creativeModeTab)
				.register(entries -> entries.insertBefore(vanillaItem, registeredItem));

			return registeredItem;
		}

		public Item registerSpawnEgg(
			String name,
			EntityType<? extends Mob> entity,
			int primaryColor,
			int secondaryColor
		) {
			String itemName = name + "_spawn_egg";

			Item item = registerItem(
				itemName,
				new SpawnEggItem(
					itemProperties(modId, itemName)
						.component(
							DataComponents.CUSTOM_MODEL_DATA,
							new CustomModelData(
								List.of(),
								List.of(),
								List.of(),
								List.of(primaryColor, secondaryColor)
							)
						)
						.spawnEgg(entity)
				)
			);

			CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS)
				.register(entries -> entries.accept(item));

			return item;
		}

		public Potion registerPotion(String name, Potion potion) {
			return register(BuiltInRegistries.POTION, name, potion);
		}

		private <T> T register(Registry<T> registry, String name, T object) {
			return Registry.register(registry, identifier(modId, name), object);
		}
	}

	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(
		FabricBlockEntityTypeBuilder.Factory<T> blockEntityType,
		Class<? extends Block> block,
		String name
	) {
		FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(
			blockEntityType,
			collectBlocks(block)
		);

		return Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			identifier(modId, name),
			builder.build()
		);
	}

	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(
		FabricBlockEntityTypeBuilder<T> builder,
		String name
	) {
		return (BlockEntityType<T>) register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			name,
			builder.build()
		);
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return BuiltInRegistries.BLOCK.stream()
			.filter(blockClass::isInstance)
			.toArray(Block[]::new);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> EntityType<T> registerEntity(
		EntityType.Builder<T> builder,
		String name
	) {
		Identifier id = identifier(modId, name);
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);

		return (EntityType<T>) register(
			BuiltInRegistries.ENTITY_TYPE,
			name,
			builder.build(key)
		);
	}

	public SoundEvent createSoundEvent(String name) {
		Identifier id = identifier(modId, name);

		return register(
			BuiltInRegistries.SOUND_EVENT,
			name,
			SoundEvent.createVariableRangeEvent(id)
		);
	}

	public SoundEvent registerSoundEvent(SoundEvent soundEvent, String name) {
		return register(BuiltInRegistries.SOUND_EVENT, name, soundEvent);
	}

	private <T> T register(Registry<T> registry, String name, T object) {
		return Registry.register(registry, identifier(modId, name), object);
	}
}
