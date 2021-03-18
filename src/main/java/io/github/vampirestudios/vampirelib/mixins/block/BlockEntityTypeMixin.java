package io.github.vampirestudios.vampirelib.mixins.block;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.vampirelib.blocks.entity.IBlockEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements IBlockEntityType {
	@Shadow @Final @Mutable
	private Set<Block> blocks;

	@Override
	public void vl_addBlocks(Block... newBlocks) {
		ArrayList<Block> tempList = new ArrayList<>(newBlocks.length + blocks.size());
		tempList.addAll(Arrays.asList(newBlocks));
		tempList.addAll(blocks);
		blocks = ImmutableSet.copyOf(tempList);
	}
}
