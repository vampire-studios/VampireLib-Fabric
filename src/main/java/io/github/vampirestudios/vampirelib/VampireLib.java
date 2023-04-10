/*
 * Copyright (c) 2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vampirestudios.vampirelib.api.BasicModClass;
import io.github.vampirestudios.vampirelib.api.ConvertibleBlockPair;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeatureCommands;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeatureSync;
import io.github.vampirestudios.vampirelib.utils.BlockChiseler;
import io.github.vampirestudios.vampirelib.utils.Rands;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

import io.github.vampirestudios.vampirelib.api.BasicModClass;
import io.github.vampirestudios.vampirelib.api.ConvertibleBlockPair;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeatureCommands;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeatureSync;
import io.github.vampirestudios.vampirelib.utils.Rands;
import io.github.vampirestudios.vampirelib.utils.BlockChiseler;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry;
import io.github.vampirestudios.vampirelib.utils.registry.WoodRegistry.WoodPropertyType;

public class VampireLib extends BasicModClass {
	public static final VampireLib INSTANCE = new VampireLib();

	public static final List<ConvertibleBlockPair> CONVERTIBLE_BLOCKS = new ArrayList<>();

	public static final Gson GSON = new GsonBuilder()
			.setLenient().setPrettyPrinting()
			.create();

	public static final boolean TEST_CONTENT_ENABLED = false;

	public static WoodRegistry TEST_WOOD;
	public static WoodRegistry TEST_WOOD1;
	public static WoodRegistry TEST_WOOD2;
	public static WoodRegistry TEST_WOOD3;
	public static WoodRegistry TEST_WOOD4;
	public static WoodRegistry TEST_WOOD5;
	public static WoodRegistry TEST_WOOD6;
	public static WoodRegistry TEST_WOOD7;
	public static WoodRegistry TEST_WOOD8;
	public static WoodRegistry TEST_WOOD9;
	public static WoodRegistry TEST_WOOD10;
	public static WoodRegistry TEST_WOOD11;
	public static WoodRegistry TEST_WOOD12;
	public static WoodRegistry TEST_WOOD13;
	public static WoodRegistry TEST_WOOD14;
	public static WoodRegistry TEST_WOOD15;
	public static WoodRegistry TEST_WOOD16;

	public static WoodRegistry TEST_NETHER_WOOD;
	public static WoodRegistry TEST_NETHER_WOOD1;
	public static WoodRegistry TEST_NETHER_WOOD2;
	public static WoodRegistry TEST_NETHER_WOOD3;
	public static WoodRegistry TEST_NETHER_WOOD4;
	public static WoodRegistry TEST_NETHER_WOOD5;
	public static WoodRegistry TEST_NETHER_WOOD6;
	public static WoodRegistry TEST_NETHER_WOOD7;
	public static WoodRegistry TEST_NETHER_WOOD8;
	public static WoodRegistry TEST_NETHER_WOOD9;
	public static WoodRegistry TEST_NETHER_WOOD10;
	public static WoodRegistry TEST_NETHER_WOOD11;
	public static WoodRegistry TEST_NETHER_WOOD12;
	public static WoodRegistry TEST_NETHER_WOOD13;

	public static Block BLOCK_WITH_CUSTOM_MODEL_1;
	public static Block BLOCK_WITH_CUSTOM_MODEL_2;
	public static Block BLOCK_WITH_EMPTY_MODEL;

	public static Item ITEM_WITH_CUSTOM_MODEL_1;
	public static Item ITEM_WITH_CUSTOM_MODEL_2;
	public static Item ITEM_WITH_NORMAL_MODEL;

	private static final ResourceLocation GREETING_A_ID = INSTANCE.identifier("greeting_a");
	private static final Greetings GREETING_A = new Greetings("Welcome to Quilt!", 5);
	private static final ResourceLocation GREETING_B_ID = INSTANCE.identifier("greeting_b");
	private static final Greetings GREETING_B = new Greetings("Howdy!", 2);

	public VampireLib() {
		super("vampirelib", "VampireLib", "5.5.0+build.1");
	}

	@Override
	public void onInitialize() {
		shouldNotPrintVersionMessage();
		getLogger().info(String.format("%s running %s v%s for %s",
				Rands.chance(15) ? "Your are" : (Rands.chance(15) ? "You're" : "You are"),
				modName(), modVersion(), SharedConstants.getCurrentVersion().getName()));
		BlockChiseler.setup();
		DebugFeatureSync.init();
		DebugFeatureCommands.init();

		if (TEST_CONTENT_ENABLED) {
			ResourceManagerHelper.registerBuiltinResourcePack(INSTANCE.identifier("wood_types"), FabricLoader.getInstance().getModContainer(INSTANCE.modId()).get(), ResourcePackActivationType.ALWAYS_ENABLED);

			//Overworld
			TEST_WOOD = WoodRegistry.of(identifier("test")).defaultBlocks().build();
			TEST_WOOD1 = WoodRegistry.of(identifier("test1")).defaultBlocksColoredLeaves().build();

			TEST_WOOD2 = WoodRegistry.of(identifier("test2")).defaultBlocks().defaultExtras().build();
			TEST_WOOD3 = WoodRegistry.of(identifier("test3")).defaultBlocksColoredLeaves().defaultExtras().build();

			TEST_WOOD4 = WoodRegistry.of(identifier("test4")).defaultBlocks().defaultExtras().build();
			TEST_WOOD5 = WoodRegistry.of(identifier("test5")).defaultBlocksColoredLeaves().defaultExtras().build();

			TEST_WOOD6 = WoodRegistry.of(identifier("test6")).defaultBlocks().defaultExtras().build();
			TEST_WOOD7 = WoodRegistry.of(identifier("test7")).defaultBlocksColoredLeaves().defaultExtras().build();

			TEST_WOOD8 = WoodRegistry.of(identifier("test8")).defaultBlocks().defaultExtras().build();
			TEST_WOOD9 = WoodRegistry.of(identifier("test9")).defaultBlocksColoredLeaves().defaultExtras().build();

			TEST_WOOD10 = WoodRegistry.of(identifier("test10")).defaultBlocks().defaultExtras().build();
			TEST_WOOD11 = WoodRegistry.of(identifier("test11")).defaultBlocksColoredLeaves().defaultExtras().build();

			TEST_WOOD12 = WoodRegistry.of(identifier("test12")).defaultBlocks().defaultExtras().build();
			TEST_WOOD13 = WoodRegistry.of(identifier("test13")).defaultBlocksColoredLeaves().defaultExtras().build();

			TEST_WOOD14 = WoodRegistry.of(identifier("test14")).woodPropertyType(WoodPropertyType.OVERWORLD).leaves().sapling().build();

			TEST_WOOD15 = WoodRegistry.of(identifier("test15")).woodPropertyType(WoodPropertyType.OVERWORLD).leaves().build();

			TEST_WOOD16 = WoodRegistry.of(identifier("test16")).woodPropertyType(WoodPropertyType.OVERWORLD).sapling().build();

			//Nether
			TEST_NETHER_WOOD = WoodRegistry.of(identifier("test_nether")).defaultBlocks(WoodPropertyType.NETHER).build();
			TEST_NETHER_WOOD1 = WoodRegistry.of(identifier("test1_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).build();

			TEST_NETHER_WOOD2 = WoodRegistry.of(identifier("test2_nether")).defaultBlocks(WoodPropertyType.NETHER).defaultExtras().build();
			TEST_NETHER_WOOD3 = WoodRegistry.of(identifier("test3_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).defaultExtras().build();

			TEST_NETHER_WOOD4 = WoodRegistry.of(identifier("test4_nether")).defaultBlocks(WoodPropertyType.NETHER).defaultExtras().build();
			TEST_NETHER_WOOD5 = WoodRegistry.of(identifier("test5_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).defaultExtras().build();

			TEST_NETHER_WOOD6 = WoodRegistry.of(identifier("test6_nether")).defaultBlocks(WoodPropertyType.NETHER).defaultExtras().nonFlammable().build();
			TEST_NETHER_WOOD7 = WoodRegistry.of(identifier("test7_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).defaultExtras().build();

			TEST_NETHER_WOOD8 = WoodRegistry.of(identifier("test8_nether")).defaultBlocks(WoodPropertyType.NETHER).defaultExtras().nonFlammable().build();
			TEST_NETHER_WOOD9 = WoodRegistry.of(identifier("test9_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).defaultExtras()
					.nonFlammable().build();

			TEST_NETHER_WOOD10 = WoodRegistry.of(identifier("test10_nether")).defaultBlocks(WoodPropertyType.NETHER).defaultExtras().nonFlammable().build();
			TEST_NETHER_WOOD11 = WoodRegistry.of(identifier("test11_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).defaultExtras()
					.nonFlammable().build();

			TEST_NETHER_WOOD12 = WoodRegistry.of(identifier("test12_nether")).defaultBlocks(WoodPropertyType.NETHER).defaultExtras().nonFlammable().build();
			TEST_NETHER_WOOD13 = WoodRegistry.of(identifier("test13_nether")).defaultBlocksColoredLeaves(WoodPropertyType.NETHER).defaultExtras()
					.nonFlammable().build();

			BLOCK_WITH_CUSTOM_MODEL_1 = createBlock("block_with_custom_model_1", false);
			BLOCK_WITH_CUSTOM_MODEL_2 = createBlock("block_with_custom_model_2", false);
			BLOCK_WITH_EMPTY_MODEL = createBlock("block_with_empty_model", false);

			ITEM_WITH_CUSTOM_MODEL_1 = createItem("item_with_custom_model_1");
			ITEM_WITH_CUSTOM_MODEL_2 = createItem("item_with_custom_model_2");
			ITEM_WITH_NORMAL_MODEL = createItem("item_with_normal_model");
		}

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (!world.isClientSide) {
				for (ConvertibleBlockPair convertibleBlock : CONVERTIBLE_BLOCKS) {
					ItemStack itemStack = player.getItemInHand(hand);
					BlockState blockState = world.getBlockState(hitResult.getBlockPos());
					if (convertibleBlock.getConversionItem().matches(itemStack)) {
						if (blockState.getBlock() == convertibleBlock.getOriginal()) {
							if (convertibleBlock.getSound() != null)
								world.playSound(null, hitResult.getBlockPos(), convertibleBlock.getSound(),
										SoundSource.BLOCKS, 1.0F, 1.0F);

							if (convertibleBlock.getDroppedItem() != null) {
								ItemStack newStack = new ItemStack(convertibleBlock.getDroppedItem());
								if (!newStack.isEmpty() &&
										world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
									ItemEntity itemEntity = new ItemEntity(world, hitResult.getBlockPos().getX() + 0.5,
											hitResult.getBlockPos().getY() + 0.5,
											hitResult.getBlockPos().getZ() + 0.5,
											newStack);
									itemEntity.setDefaultPickUpDelay();
									world.addFreshEntity(itemEntity);
								}
							}

							world.setBlock(hitResult.getBlockPos(), convertibleBlock.getConverted()
									.withPropertiesOf(blockState), 11);
							itemStack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
							world.gameEvent(GameEvent.BLOCK_CHANGE, hitResult.getBlockPos(),
									GameEvent.Context.of(player, blockState));
							return InteractionResult.SUCCESS;
						}
					} else if (convertibleBlock.getReversingItem() != null &&
							convertibleBlock.getReversingItem().matches(itemStack) &&
							blockState.is(convertibleBlock.getConverted())) {
						if (convertibleBlock.getSound() != null)
							world.playSound(null, hitResult.getBlockPos(), convertibleBlock.getSound(),
									SoundSource.BLOCKS, 1.0F, 1.0F);

						if (convertibleBlock.getDroppedItem() != null) {
							ItemStack newStack = new ItemStack(convertibleBlock.getDroppedItem());
							if (!newStack.isEmpty() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
								ItemEntity itemEntity = new ItemEntity(world, hitResult.getBlockPos().getX() + 0.5,
										hitResult.getBlockPos().getY() + 0.5,
										hitResult.getBlockPos().getZ() + 0.5, newStack);
								itemEntity.setDefaultPickUpDelay();
								world.addFreshEntity(itemEntity);
							}
						}

						world.setBlock(hitResult.getBlockPos(), convertibleBlock.getOriginal()
								.withPropertiesOf(blockState), 11);
						itemStack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
						world.gameEvent(GameEvent.BLOCK_CHANGE, hitResult.getBlockPos(),
								GameEvent.Context.of(player, blockState));
						return InteractionResult.SUCCESS;
					}
				}
			}
			return InteractionResult.PASS;
		});
	}

	private static Block createBlock(String name, boolean hasItem) {
		Block block = Registry.register(BuiltInRegistries.BLOCK, INSTANCE.identifier(name), new Block(BlockBehaviour.Properties.of(Material.STONE)));

		if (hasItem) {
			Registry.register(BuiltInRegistries.ITEM, INSTANCE.identifier(name), new BlockItem(block, new Item.Properties()));
		}

		return block;
	}

	private static Item createItem(String name) {
		ResourceLocation identifier = INSTANCE.identifier(name);
		return Registry.register(BuiltInRegistries.ITEM, identifier, new Item(new Item.Properties()));
	}

//	private record DynamicData(Holder<Biome> biome) {
//		private static final ResourceKey<Registry<DynamicData>> BEFORE_KEY = ResourceKey.createRegistryKey(new ResourceLocation(INSTANCE.modId(), "fabric-api/before_biome"));
//		private static final ResourceKey<Registry<DynamicData>> AFTER_KEY = ResourceKey.createRegistryKey(new ResourceLocation(INSTANCE.modId(), "fabric-api/after_biome"));
//		private static final Codec<DynamicData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//				Biome.CODEC.fieldOf("biome").forGetter(DynamicData::biome)
//		).apply(instance, DynamicData::new));
//	}

}
