/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.mixins.entity;

import io.github.vampirestudios.vampirelib.api.Climbable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow public abstract BlockState getBlockState();

    @Shadow protected int roll;

    /**
     * @author OliviaTheVampire
     */
    @Overwrite
    public void initAi() {
        boolean boolean_1 = this.getFlag(7);
        if (boolean_1 && !this.onGround && !this.hasVehicle()) {
            ItemStack itemStack_1 = this.getEquippedStack(EquipmentSlot.CHEST);
            if (itemStack_1.getItem() instanceof ElytraItem && ElytraItem.isUsable(itemStack_1)) {
             boolean_1 = true;
             if (!this.world.isClient && (this.roll + 1) % 20 == 0) {
                 itemStack_1.damage(1, (LivingEntity) (Object) this, (livingEntity_1) ->
                         livingEntity_1.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
             }
            } else {
             boolean_1 = false;
            }
        } else {
            boolean_1 = false;
        }

        if (!this.world.isClient) {
            this.setFlag(7, boolean_1);
        }

    }

    /**
     * @author OliviaTheVampire
     */
    @Overwrite
    public void onEquipStack(ItemStack itemStack_1) {
        if (!itemStack_1.isEmpty()) {
            SoundEvent soundEvent_1 = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
            Item item_1 = itemStack_1.getItem();
            if (item_1 instanceof ArmorItem) {
             soundEvent_1 = ((ArmorItem) item_1).getMaterial().getEquipSound();
            } else if (item_1 instanceof ElytraItem) {
             soundEvent_1 = SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
            }

            this.playSound(soundEvent_1, 1.0F, 1.0F);
        }
    }

    @Inject(method = "isClimbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void isClimbing(CallbackInfoReturnable<Boolean> cir, final BlockState state) {

        final Climbable climbable = (Climbable) state.getBlock();
        final LivingEntity thisLivingEntity = (LivingEntity) (Object) this;

        Climbable.ClimbBehavior behavior = climbable.canClimb(thisLivingEntity, state, thisLivingEntity.getBlockPos());
        if (behavior != Climbable.ClimbBehavior.Vanilla) {

            if (behavior == Climbable.ClimbBehavior.True || behavior == Climbable.ClimbBehavior.Scaffolding) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
            cir.cancel();
        }
    }

    @Inject(method = "applyClimbingSpeed", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Math;max(DD)D"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void applyClimbingSpeed(Vec3d vec3d, CallbackInfoReturnable<Vec3d> callbackInfoReturnable, float temp, double x, double z, double y) {
        Block block = getBlockState().getBlock();
        if(y < 0.0D && block instanceof Climbable && ((Climbable) block).canClimb((LivingEntity)(Object) this, getBlockState(), getBlockPos()) == Climbable.ClimbBehavior.Scaffolding)
            callbackInfoReturnable.setReturnValue(new Vec3d(x, y, z));
    }

}
