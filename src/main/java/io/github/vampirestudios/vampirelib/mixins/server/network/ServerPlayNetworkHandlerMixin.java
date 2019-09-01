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

package io.github.vampirestudios.vampirelib.mixins.server.network;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;
    @Shadow
    private Vec3d requestedTeleportPos;

    /**
     * @author OliviaTheVampire
     */
    @Overwrite
    public void onClientCommand(ClientCommandC2SPacket clientCommandC2SPacket_1) {
        NetworkThreadUtils.forceMainThread(clientCommandC2SPacket_1, (ServerPlayNetworkHandler) (Object) this, this.player.getServerWorld());
        this.player.updateLastActionTime();
        JumpingMount jumpingMount_1;
        switch (clientCommandC2SPacket_1.getMode()) {
            case PRESS_SHIFT_KEY:
                this.player.setSneaking(true);
                break;
            case RELEASE_SHIFT_KEY:
                this.player.setSneaking(false);
                break;
            case START_SPRINTING:
                this.player.setSprinting(true);
                break;
            case STOP_SPRINTING:
                this.player.setSprinting(false);
                break;
            case STOP_SLEEPING:
                if (this.player.isSleeping()) {
                    this.player.wakeUp(false, true, true);
                    this.requestedTeleportPos = new Vec3d(this.player.x, this.player.y, this.player.z);
                }
                break;
            case START_RIDING_JUMP:
                if (this.player.getVehicle() instanceof JumpingMount) {
                    jumpingMount_1 = (JumpingMount) this.player.getVehicle();
                    int int_1 = clientCommandC2SPacket_1.getMountJumpHeight();
                    if (jumpingMount_1.canJump() && int_1 > 0) {
                        jumpingMount_1.startJumping(int_1);
                    }
                }
                break;
            case STOP_RIDING_JUMP:
                if (this.player.getVehicle() instanceof JumpingMount) {
                    jumpingMount_1 = (JumpingMount) this.player.getVehicle();
                    jumpingMount_1.stopJumping();
                }
                break;
            case OPEN_INVENTORY:
                if (this.player.getVehicle() instanceof HorseBaseEntity) {
                    ((HorseBaseEntity) this.player.getVehicle()).openInventory(this.player);
                }
                break;
            case START_FALL_FLYING:
                if (!this.player.onGround && this.player.getVelocity().y < 0.0D && !this.player.isFallFlying() && !this.player.isInsideWater()) {
                    ItemStack itemStack_1 = this.player.getEquippedStack(EquipmentSlot.CHEST);
                    if (itemStack_1.getItem() instanceof ElytraItem && ElytraItem.isUsable(itemStack_1)) {
                        this.player.method_14243();
                    }
                } else {
                    this.player.method_14229();
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid client command!");
        }

    }
}
