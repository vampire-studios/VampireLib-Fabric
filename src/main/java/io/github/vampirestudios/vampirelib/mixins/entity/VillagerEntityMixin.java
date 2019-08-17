/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.mixins.entity;

import io.github.vampirestudios.vampirelib.api.custom_villagers.VillagerTypeRegistry;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerData;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends AbstractTraderEntity {
    public VillagerEntityMixin(EntityType<? extends AbstractTraderEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Shadow
    public abstract VillagerData getVillagerData();

    @Shadow
    public abstract void setVillagerData(VillagerData villagerData_1);

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;setVillagerData(Lnet/minecraft/village/VillagerData;)V", ordinal = 1, shift = At.Shift.AFTER))
    public void onInitialize(IWorld world, LocalDifficulty localDifficulty, SpawnType spawnType, EntityData entityData, CompoundTag compoundTag, CallbackInfoReturnable<EntityData> callbackInfoReturnable) {
        setVillagerData(getVillagerData().withType(VillagerTypeRegistry.getVillagerTypeForBiome(world.getBiome(new BlockPos(this)))));
    }
}
