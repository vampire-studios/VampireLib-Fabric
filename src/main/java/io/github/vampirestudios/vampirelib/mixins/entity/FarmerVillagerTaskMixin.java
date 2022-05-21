/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vampirestudios.vampirelib.mixins.entity;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import io.github.vampirestudios.vampirelib.village.VillagerPlantableRegistry;

@Mixin(HarvestFarmland.class)
public class FarmerVillagerTaskMixin {
	@Nullable
	@Shadow private BlockPos aboveFarmlandPos;

	@Inject(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;getInventory()Lnet/minecraft/world/SimpleContainer;"))
	private void fabric_useRegistry(ServerLevel serverWorld, Villager villagerEntity, long l, CallbackInfo ci) {
		SimpleContainer simpleInventory = villagerEntity.getInventory();

		for (int i = 0; i < simpleInventory.getContainerSize(); ++i) {
			ItemStack itemStack = simpleInventory.getItem(i);

			if (VillagerPlantableRegistry.INSTANCE.contains(itemStack.getItem())) {
				BlockState state = VillagerPlantableRegistry.INSTANCE.getPlantState(itemStack.getItem());
				serverWorld.setBlock(this.aboveFarmlandPos, state, 3);
				serverWorld.playSound(null, this.aboveFarmlandPos.getX(), this.aboveFarmlandPos.getY(), this.aboveFarmlandPos.getZ(), state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				itemStack.shrink(1);

				if (itemStack.isEmpty()) {
					simpleInventory.setItem(i, ItemStack.EMPTY);
				}

				break;
			}
		}
	}

	@Redirect(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SimpleContainer;getContainerSize()I"))
	private int fabric_stopDefaultBehaviour(SimpleContainer inventory) {
		return -1;
	}
}