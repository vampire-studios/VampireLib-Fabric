package io.github.vampirestudios.vampirelib.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(StructureBlockEntity.class)
public abstract class StructureBlockBlockEntityMixin extends BlockEntity {

	public StructureBlockBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	@ModifyConstant(method = "loadAdditional", constant = @Constant(intValue = 48))
	public int vl_loadAdditional(int old) {
		return 4096;
	}

	@ModifyConstant(method = "getEnclosingBoundingBox", constant = @Constant(intValue = 80))
	private static int vl_getEnclosingBoundingBox(int old) {
		return 4096;
	}

}
