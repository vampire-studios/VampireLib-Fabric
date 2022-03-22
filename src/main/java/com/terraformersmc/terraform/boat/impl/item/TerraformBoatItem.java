package com.terraformersmc.terraform.boat.impl.item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * An {@linkplain Item item} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatItem extends Item {
	private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::canBeCollidedWith);
	private final Supplier<TerraformBoatType> boatSupplier;

	/**
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this item
	 */
	public TerraformBoatItem(Supplier<TerraformBoatType> boatSupplier, Item.Properties settings) {
		super(settings);
		this.boatSupplier = boatSupplier;
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);

		HitResult hitResult = Item.getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(stack);
		}

		Vec3 rotationVec = user.getViewVector(1f);
		List<Entity> riders = world.getEntities(user, user.getBoundingBox().expandTowards(rotationVec.scale(5d)).inflate(1d), RIDERS);

		// Prevent collision with user
		if (!riders.isEmpty()) {
			Vec3 eyePos = user.getEyePosition();
			for (Entity entity : riders) {
				AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
				if (box.contains(eyePos)) {
					return InteractionResultHolder.pass(stack);
				}
			}
		}

		// Spawn boat entity
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			TerraformBoatEntity boatEntity = new TerraformBoatEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);

			boatEntity.setTerraformBoat(this.boatSupplier.get());
			boatEntity.setYRot(user.getYRot());

			if (!world.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1d))) {
				return InteractionResultHolder.fail(stack);
			}
			
			if (!world.isClientSide()) {
				world.addFreshEntity(boatEntity);
				world.gameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getLocation()));

				if (!user.getAbilities().instabuild) {
					stack.shrink(1);
				}
			}

			user.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
		}

		return InteractionResultHolder.pass(stack);
	}
}