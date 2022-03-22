package com.terraformersmc.terraform.boat.impl.item;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * A {@linkplain DispenseItemBehavior dispenser behavior} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatDispenserBehavior extends DefaultDispenseItemBehavior {
	private static final DispenseItemBehavior FALLBACK_BEHAVIOR = new DefaultDispenseItemBehavior();
	private static final float OFFSET_MULTIPLIER = 1.125F;

	private final Supplier<TerraformBoatType> boatSupplier;

	/**
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 */
	public TerraformBoatDispenserBehavior(Supplier<TerraformBoatType> boatSupplier) {
		this.boatSupplier = boatSupplier;
	}

	@Override
	public ItemStack execute(BlockSource pointer, ItemStack stack) {
		Direction facing = pointer.getBlockState().getValue(DispenserBlock.FACING);

		double x = pointer.x() + facing.getStepX() * OFFSET_MULTIPLIER;
		double y = pointer.y() + facing.getStepY() * OFFSET_MULTIPLIER;
		double z = pointer.z() + facing.getStepZ() * OFFSET_MULTIPLIER;

		Level world = pointer.getLevel();
		BlockPos pos = pointer.getPos().relative(facing);

		if (world.getFluidState(pos).is(FluidTags.WATER)) {
			y += 1;
		} else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.below()).is(FluidTags.WATER)) {
			return FALLBACK_BEHAVIOR.dispense(pointer, stack);
		}

		TerraformBoatEntity boatEntity = new TerraformBoatEntity(world, x, y, z);

		boatEntity.setTerraformBoat(this.boatSupplier.get());
		boatEntity.setYRot(facing.toYRot());

		world.addFreshEntity(boatEntity);

		stack.shrink(1);
		return stack;
	}
}