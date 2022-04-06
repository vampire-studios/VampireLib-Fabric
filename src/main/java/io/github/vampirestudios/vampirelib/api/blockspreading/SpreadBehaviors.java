package io.github.vampirestudios.vampirelib.api.blockspreading;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SpreadBehaviors {

	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Table<Block, BlockSpreadingType, SpreadingBehavior> SPREADERS = HashBasedTable.create();

	static {
		setupVanillaBehavior();
	}

	/**
	 * This method allows you to add spreading behavior, which is used by {@link SpreadingBlock}
	 */
	private static void addSpreaderBehavior(Block block, BlockSpreadingType type, SpreadingBehavior behavior) {
		if (SPREADERS.contains(block, type))
			LOGGER.info("Replacing spreading behavior for block '{}' and spreader type '{}'", block, type.getName());
		SPREADERS.put(block, type, behavior);
	}

	/**
	 * This method allows you to add a simple spreading behavior
	 */
	public static void addSimpleSpreaderBehavior(Block block, BlockSpreadingType type) {
		addSpreaderBehavior(block, type, new SimpleSpreaderBehavior(block.defaultBlockState()));
	}

	/**
	 * This method allows you to add a complex spreading behavior
	 */
	public static void addComplexSpreaderBehavior(Block block, BlockSpreadingType type, SpreadingBehavior behavior) {
		addSpreaderBehavior(block, type, behavior);
	}

	public static boolean canSpread(BlockState state, BlockSpreadingType type) {
		return getSpreadingBehavior(state.getBlock(), type) != null;
	}

	public static BlockState getSpreadState(BlockState state, Level level, BlockPos pos, BlockSpreadingType type) {
		SpreadingBehavior behavior = getSpreadingBehavior(state.getBlock(), type);
		if (behavior == null) return state;
		return behavior.getStateForSpread(state, level, pos);
	}

	private static SpreadingBehavior getSpreadingBehavior(Block block, BlockSpreadingType type) {
		return SPREADERS.get(block, type);
	}

	private static void setupVanillaBehavior() {
		addSpreaderBehavior(Blocks.DIRT, BlockSpreadingType.GRASS, ((state, level, pos) ->
				Blocks.GRASS_BLOCK.defaultBlockState()
						.setValue(BlockStateProperties.SNOWY, level.getBlockState(pos.above()).is(Blocks.SNOW))));

		addSpreaderBehavior(Blocks.GRASS_BLOCK, BlockSpreadingType.REVERT, ((state, level, pos) ->
				Blocks.DIRT.defaultBlockState()));

		addSpreaderBehavior(Blocks.DIRT, BlockSpreadingType.MYCELIUM, ((state, level, pos) ->
				Blocks.MYCELIUM.defaultBlockState()
						.setValue(BlockStateProperties.SNOWY, level.getBlockState(pos.above()).is(Blocks.SNOW))));

		addSpreaderBehavior(Blocks.MYCELIUM, BlockSpreadingType.REVERT, ((state, level, pos) ->
				Blocks.DIRT.defaultBlockState()));

		addSpreaderBehavior(Blocks.NETHERRACK, BlockSpreadingType.CRIMSON, ((state, level, pos) ->
				Blocks.CRIMSON_NYLIUM.defaultBlockState()));

		addSpreaderBehavior(Blocks.CRIMSON_NYLIUM, BlockSpreadingType.REVERT, ((state, level, pos) ->
				Blocks.NETHERRACK.defaultBlockState()));

		addSpreaderBehavior(Blocks.NETHERRACK, BlockSpreadingType.WARPED, ((state, level, pos) ->
				Blocks.WARPED_NYLIUM.defaultBlockState()));

		addSpreaderBehavior(Blocks.WARPED_NYLIUM, BlockSpreadingType.REVERT, ((state, level, pos) ->
				Blocks.NETHERRACK.defaultBlockState()));
	}

	public static class SimpleSpreaderBehavior implements SpreadingBehavior {

		private final BlockState state;

		public SimpleSpreaderBehavior(BlockState state) {
			this.state = state;
		}

		@Override
		public BlockState getStateForSpread(BlockState state, Level level, BlockPos pos) {
			return this.state;
		}
	}

}