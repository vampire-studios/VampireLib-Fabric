package io.github.vampirestudios.vampirelib.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.api.IChestBlock;
import io.github.vampirestudios.vampirelib.blocks.entity.VTrappedChestBlockEntity;
import io.github.vampirestudios.vampirelib.init.VBlockEntityTypes;

/**
 * A {@link ChestBlock} extension used for Blueprint's trapped chests.
 */
@SuppressWarnings("deprecation")
public class BlueprintTrappedChestBlock extends ChestBlock implements IChestBlock {
	public final String type;

	public BlueprintTrappedChestBlock(String type, Properties props) {
		super(props, () -> VBlockEntityTypes.TRAPPED_CHEST);
		this.type = type;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new VTrappedChestBlockEntity(pos, state);
	}

	@Override
	public String getChestType() {
		return this.type;
	}

	@Override
	protected Stat<ResourceLocation> getOpenChestStat() {
		return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return Mth.clamp(ChestBlockEntity.getOpenCount(world, pos), 0, 15);
	}

	@Override
	public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return face == Direction.UP ? state.getSignal(world, pos, face) : 0;
	}
}