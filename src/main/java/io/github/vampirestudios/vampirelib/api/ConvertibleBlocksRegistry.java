package io.github.vampirestudios.vampirelib.api;

import java.util.Objects;

import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

import io.github.vampirestudios.vampirelib.VampireLib;

/**
 * Provides methods for registering convertible blocks.
 */
public final class ConvertibleBlocksRegistry {
	private ConvertibleBlocksRegistry() {
	}

	/**
	 * Registers a link between two different blocks.
	 * (See for example {@link OxidizableBlocksRegistry#registerNextStage(Block, Block)} and
	 * {@link OxidizableBlocksRegistry#registerWaxable(Block, Block)})
	 *
	 * @param convertibleBlockPair the convertible block pair
	 */
	public static void registerConvertibleBlockPair(ConvertibleBlockPair convertibleBlockPair) {
		Objects.requireNonNull(convertibleBlockPair, "ConvertibleBlockPair cannot be null!");
		VampireLib.CONVERTIBLE_BLOCKS.add(convertibleBlockPair);
	}
}
