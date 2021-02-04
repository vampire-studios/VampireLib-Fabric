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

package io.github.vampirestudios.vampirelib.client.network;

import io.github.vampirestudios.vampirelib.VampireLib;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class BoatSpawnNetworkHandler {
    public void register() {
        ClientSidePacketRegistry.INSTANCE.register(new Identifier(VampireLib.MOD_ID, "spawn_boat"), BoatSpawnNetworkHandler::accept);
    }

    public static void accept(PacketContext context, PacketByteBuf buffer) {
        final MinecraftClient client = MinecraftClient.getInstance();

        int id = buffer.readVarInt();
        UUID uuid = buffer.readUuid();
        EntityType<?> type = Registry.ENTITY_TYPE.get(buffer.readVarInt());
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        byte pitch = buffer.readByte();
        byte yaw = buffer.readByte();

        if (client.isOnThread()) {
            spawn(client, id, uuid, type, x, y, z, pitch, yaw);
        } else {
            client.execute(() -> spawn(client, id, uuid, type, x, y, z, pitch, yaw));
        }
    }

    private static void spawn(MinecraftClient client, int id, UUID uuid, EntityType<?> type, double x, double y, double z, byte pitch, byte yaw) {
        ClientWorld world = client.world;

        if (world == null) {
            return;
        }

        Entity entity = type.create(world);

        if (entity == null) {
            return;
        }

        entity.setEntityId(id);
        entity.setUuid(uuid);
        entity.updatePosition(x, y, z);
        entity.updateTrackedPosition(x, y, z);
        entity.pitch = pitch * 360 / 256F;
        entity.yaw = yaw * 360 / 256F;

        world.addEntity(entity.getId(), entity);
    }
}