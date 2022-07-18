package org.quiltmc.qsl.block.content.registry.api;


import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

/**
 * Holds {@link RegistryEntryAttachment}s for different properties that blocks can hold.
 * <p>
 * Current properties:
 * <ul>
 *     <li>{@link #FLATTENABLE_BLOCK}</li>
 *     <li>{@link #OXIDIZABLE_BLOCK}</li>
 *     <li>{@link #WAXABLE_BLOCK}</li>
 *     <li>{@link #STRIPPABLE_BLOCK}</li>
 *     <li>{@link #FLAMMABLE_BLOCK}</li>
 *     <li>{@link #SCULK_FREQUENCY}</li>
 * </ul>
 */
public class BlockContentRegistries {
	/**
	 * The namespace for the content registries.
	 */
	public static final String NAMESPACE = "quilt_block_content_registry";

	/**
	 * A {@link RegistryEntryAttachment} for when blocks are right clicked by a shovel.
	 * <p>
	 * Values can be set via code and through a datapack with the file {@code data/quilt_block_content_registry/attachments/minecraft/block/flattenable_block.json}
	 */
	public static final RegistryEntryAttachment<Block, BlockState> FLATTENABLE_BLOCK = RegistryEntryAttachment
			.builder(Registry.BLOCK,
					new ResourceLocation(NAMESPACE, "flattenable_block"),
					BlockState.class,
					BlockState.CODEC)
			.build();

	/**
	 * A {@link RegistryEntryAttachment} for oxidizable blocks.
	 * <p>
	 * Values can be set via code and through a datapack with the file {@code data/quilt_block_content_registry/attachments/minecraft/block/oxidizable_block.json}
	 */
	public static final RegistryEntryAttachment<Block, ReversibleBlockEntry> OXIDIZABLE_BLOCK = RegistryEntryAttachment
			.builder(Registry.BLOCK,
					new ResourceLocation(NAMESPACE, "oxidizable_block"),
					ReversibleBlockEntry.class,
					ReversibleBlockEntry.CODEC)
			.build();

	/**
	 * A {@link RegistryEntryAttachment} for waxable blocks.
	 * <p>
	 * Values can be set via code and through a datapack with the file {@code data/quilt_block_content_registry/attachments/minecraft/block/waxable_block.json}
	 */
	public static final RegistryEntryAttachment<Block, ReversibleBlockEntry> WAXABLE_BLOCK = RegistryEntryAttachment
			.builder(Registry.BLOCK,
					new ResourceLocation(NAMESPACE, "waxable_block"),
					ReversibleBlockEntry.class,
					ReversibleBlockEntry.CODEC)
			.build();

	/**
	 * A {@link RegistryEntryAttachment} for strippable blocks.
	 * <p>
	 * Values can be set via code and through a datapack with the file {@code data/quilt_block_content_registry/attachments/minecraft/block/strippable_block.json}
	 */
	public static final RegistryEntryAttachment<Block, Block> STRIPPABLE_BLOCK = RegistryEntryAttachment
			.builder(Registry.BLOCK,
					new ResourceLocation(NAMESPACE, "strippable_block"),
					Block.class,
					Registry.BLOCK.byNameCodec().flatXmap(block -> {
						if (!block.defaultBlockState().hasProperty(BlockStateProperties.AXIS)) {
							return DataResult.error("block does not contain AXIS property");
						}
						return DataResult.success(block);
					}, block -> {
						if (!block.defaultBlockState().hasProperty(BlockStateProperties.AXIS)) {
							return DataResult.error("block does not contain AXIS property");
						}
						return DataResult.success(block);
					}))
			.build();

	/**
	 * A {@link RegistryEntryAttachment} for flammable blocks.
	 * <p>
	 * Values can be set via code and through a datapack with the file {@code data/quilt_block_content_registry/attachments/minecraft/block/flammable_block.json}
	 */
	public static final RegistryEntryAttachment<Block, FlammableBlockEntry> FLAMMABLE_BLOCK = RegistryEntryAttachment
			.builder(Registry.BLOCK,
					new ResourceLocation(NAMESPACE, "flammable_block"),
					FlammableBlockEntry.class,
					FlammableBlockEntry.CODEC)
			.build();

	/**
	 * A {@link RegistryEntryAttachment} for sculk frequencies.
	 * <p>
	 * Values can be set via code and through a datapack with the file {@code data/quilt_block_content_registry/attachments/minecraft/game_event/sculk_frequency.json}
	 */
	public static final RegistryEntryAttachment<GameEvent, Integer> SCULK_FREQUENCY = RegistryEntryAttachment
			.builder(Registry.GAME_EVENT,
					new ResourceLocation(NAMESPACE, "sculk_frequency"),
					Integer.class,
					Codec.intRange(1, 15))
			.build();
}