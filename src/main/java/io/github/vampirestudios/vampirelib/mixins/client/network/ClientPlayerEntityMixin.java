package io.github.vampirestudios.vampirelib.mixins.client.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.packet.ClientCommandC2SPacket;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow
    public int field_3921;
    @Shadow
    protected int field_3935;

    public ClientPlayerEntityMixin(ClientWorld clientWorld_1, GameProfile gameProfile_1) {
        super(clientWorld_1, gameProfile_1);
    }

    @Shadow
    protected abstract void updateNausea();

    @Shadow
    public Input input;

    @Shadow
    protected abstract boolean method_20623();

    @Shadow
    public abstract boolean isInSneakingPose();

    @Shadow
    @Final
    protected MinecraftClient client;

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    private int field_3934;

    @Shadow
    protected abstract void pushOutOfBlocks(double double_1, double double_2, double double_3);

    @Shadow
    public abstract boolean isInWater();

    @Shadow
    public abstract void setSprinting(boolean boolean_1);

    @Shadow
    public abstract void sendAbilitiesUpdate();

    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;
    @Shadow
    private boolean field_3939;
    @Shadow
    private int field_3917;

    @Shadow
    protected abstract boolean isCamera();

    @Shadow
    public abstract boolean hasJumpingMount();

    @Shadow
    private int field_3938;
    @Shadow
    private float field_3922;

    @Shadow
    public abstract float method_3151();

    @Shadow
    protected abstract void startRidingJump();

    /**
     * @author OliviaTheVampire
     */
    @Overwrite
    public void tickMovement() {
        ++this.field_3921;
        if (this.field_3935 > 0) {
            --this.field_3935;
        }

        this.updateNausea();
        boolean boolean_1 = this.input.jumping;
        boolean boolean_2 = this.input.sneaking;
        boolean boolean_3 = this.method_20623();
        boolean boolean_4 = this.isInSneakingPose() || this.shouldLeaveSwimmingPose();
        this.input.tick(boolean_4, this.isSpectator());
        this.client.getTutorialManager().onMovement(this.input);
        Input var10000;
        if (this.isUsingItem() && !this.hasVehicle()) {
            var10000 = this.input;
            var10000.movementSideways *= 0.2F;
            var10000 = this.input;
            var10000.movementForward *= 0.2F;
            this.field_3935 = 0;
        }

        boolean boolean_5 = false;
        if (this.field_3934 > 0) {
            --this.field_3934;
            boolean_5 = true;
            this.input.jumping = true;
        }

        if (!this.noClip) {
            Box box_1 = this.getBoundingBox();
            this.pushOutOfBlocks(this.x - (double) this.getWidth() * 0.35D, box_1.minY + 0.5D, this.z + (double) this.getWidth() * 0.35D);
            this.pushOutOfBlocks(this.x - (double) this.getWidth() * 0.35D, box_1.minY + 0.5D, this.z - (double) this.getWidth() * 0.35D);
            this.pushOutOfBlocks(this.x + (double) this.getWidth() * 0.35D, box_1.minY + 0.5D, this.z - (double) this.getWidth() * 0.35D);
            this.pushOutOfBlocks(this.x + (double) this.getWidth() * 0.35D, box_1.minY + 0.5D, this.z + (double) this.getWidth() * 0.35D);
        }

        boolean boolean_6 = (float) this.getHungerManager().getFoodLevel() > 6.0F || this.abilities.allowFlying;
        if ((this.onGround || this.isInWater()) && !boolean_2 && !boolean_3 && this.method_20623() && !this.isSprinting() && boolean_6 && !this.isUsingItem() && !this.hasStatusEffect(StatusEffects.BLINDNESS)) {
            if (this.field_3935 <= 0 && !this.client.options.keySprint.isPressed()) {
             this.field_3935 = 7;
            } else {
             this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && (!this.isInsideWater() || this.isInWater()) && this.method_20623() && boolean_6 && !this.isUsingItem() && !this.hasStatusEffect(StatusEffects.BLINDNESS) && this.client.options.keySprint.isPressed()) {
            this.setSprinting(true);
        }

        if (this.isSprinting()) {
            boolean boolean_7 = !this.input.method_20622() || !boolean_6;
            boolean boolean_8 = boolean_7 || this.horizontalCollision || this.isInsideWater() && !this.isInWater();
            if (this.isSwimming()) {
             if (!this.onGround && !this.input.sneaking && boolean_7 || !this.isInsideWater()) {
                 this.setSprinting(false);
             }
            } else if (boolean_8) {
             this.setSprinting(false);
            }
        }

        if (this.abilities.allowFlying) {
            if (this.client.interactionManager.isFlyingLocked()) {
             if (!this.abilities.flying) {
                 this.abilities.flying = true;
                 this.sendAbilitiesUpdate();
             }
            } else if (!boolean_1 && this.input.jumping && !boolean_5) {
             if (this.field_7489 == 0) {
                 this.field_7489 = 7;
             } else if (!this.isSwimming()) {
                 this.abilities.flying = !this.abilities.flying;
                 this.sendAbilitiesUpdate();
                 this.field_7489 = 0;
             }
            }
        }

        if (this.input.jumping && !boolean_1 && !this.onGround && this.getVelocity().y < 0.0D && !this.isFallFlying() && !this.abilities.flying) {
            ItemStack itemStack_1 = this.getEquippedStack(EquipmentSlot.CHEST);
            if (itemStack_1.getItem() == Items.ELYTRA && ElytraItem.isUsable(itemStack_1)) {
             this.networkHandler.sendPacket(new ClientCommandC2SPacket(this, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }
        }

        this.field_3939 = this.isFallFlying();
        if (this.isInsideWater() && this.input.sneaking) {
            this.method_6093();
        }

        int int_2;
        if (this.isInFluid(FluidTags.WATER)) {
            int_2 = this.isSpectator() ? 10 : 1;
            this.field_3917 = MathHelper.clamp(this.field_3917 + int_2, 0, 600);
        } else if (this.field_3917 > 0) {
            this.isInFluid(FluidTags.WATER);
            this.field_3917 = MathHelper.clamp(this.field_3917 - 10, 0, 600);
        }

        if (this.abilities.flying && this.isCamera()) {
            int_2 = 0;
            if (this.input.sneaking) {
             var10000 = this.input;
             var10000.movementSideways = (float) ((double) var10000.movementSideways / 0.3D);
             var10000 = this.input;
             var10000.movementForward = (float) ((double) var10000.movementForward / 0.3D);
             --int_2;
            }

            if (this.input.jumping) {
             ++int_2;
            }

            if (int_2 != 0) {
             this.setVelocity(this.getVelocity().add(0.0D, (double) ((float) int_2 * this.abilities.getFlySpeed() * 3.0F), 0.0D));
            }
        }

        if (this.hasJumpingMount()) {
            JumpingMount jumpingMount_1 = (JumpingMount) this.getVehicle();
            if (this.field_3938 < 0) {
             ++this.field_3938;
             if (this.field_3938 == 0) {
                 this.field_3922 = 0.0F;
             }
            }

            if (boolean_1 && !this.input.jumping) {
             this.field_3938 = -10;
             jumpingMount_1.setJumpStrength(MathHelper.floor(this.method_3151() * 100.0F));
             this.startRidingJump();
            } else if (!boolean_1 && this.input.jumping) {
             this.field_3938 = 0;
             this.field_3922 = 0.0F;
            } else if (boolean_1) {
             ++this.field_3938;
             if (this.field_3938 < 10) {
                 this.field_3922 = (float) this.field_3938 * 0.1F;
             } else {
                 this.field_3922 = 0.8F + 2.0F / (float) (this.field_3938 - 9) * 0.1F;
             }
            }
        } else {
            this.field_3922 = 0.0F;
        }

        super.tickMovement();
        if (this.onGround && this.abilities.flying && !this.client.interactionManager.isFlyingLocked()) {
            this.abilities.flying = false;
            this.sendAbilitiesUpdate();
        }

    }
}
