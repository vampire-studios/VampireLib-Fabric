/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
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

package io.github.vampirestudios.vampirelib.mixins.client;

import io.github.vampirestudios.vampirelib.entities.EntityRegistry;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    private static double abnormaLib$x;
    private static double abnormaLib$y;
    private static double abnormaLib$z;
    private static EntityType abnormaLib$entityType;
    private static int abnormaLib$entityData;
    @Shadow
    private ClientWorld world;

    @Inject(method = "onEntitySpawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/network/packet/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo callbackInfo, double x, double y, double z, EntityType entityType) {
        abnormaLib$x = x;
        abnormaLib$y = y;
        abnormaLib$z = z;
        abnormaLib$entityType = entityType;
        abnormaLib$entityData = packet.getEntityData();
    }

    @ModifyVariable(
            method = "onEntitySpawn",
            at = @At(value = "JUMP", opcode = Opcodes.IFNULL, ordinal = 0, shift = At.Shift.BEFORE),
            index = 8
    )
    private Entity setSpawnEntity(Entity old) {
        if (old == null)
            return EntityRegistry.construct(abnormaLib$entityType, world, abnormaLib$x, abnormaLib$y, abnormaLib$z, abnormaLib$entityData);
        return old;
    }

}
