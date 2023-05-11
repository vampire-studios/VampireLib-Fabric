/*
 * Copyright (c) 2023 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.mixins;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.pathfinder.Path;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import io.github.vampirestudios.vampirelib.api.debug_renderers.VanillaDebugFeatures;

@Mixin(DebugPackets.class)
public abstract class DebugInfoSenderMixin {
	//TODO re-implement the empty methods in DebugInfoSender

	@Shadow
	private static String getShortDescription(ServerLevel world, @Nullable Object object) {
		throw new UnsupportedOperationException("mixin");
	}

	@Shadow
	private static void writeBrain(LivingEntity entity, FriendlyByteBuf buf) {}

	/**
	 * @author QuiltMC, Will BL
	 */
	@Overwrite
	public static void sendPathFindingPacket(Level world, Mob mob, @Nullable Path path, float nodeReachProximity) {
		if (path == null || world.isClientSide()) {
			return;
		}
		var buf = PacketByteBufs.create();
		buf.writeInt(mob.getId());
		path.writeToStream(buf);
		buf.writeFloat(nodeReachProximity);
		for (ServerPlayer serverPlayer : VanillaDebugFeatures.PATHFINDING.getPlayersWithFeatureEnabled(world.getServer())) {
			ServerPlayNetworking.send(serverPlayer, ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET, buf);
		}
	}

	/**
	 * @author QuiltMC, Will BL
	 */
	@Overwrite
	public static void sendNeighborsUpdatePacket(Level world, BlockPos pos) {
		if (world.isClientSide()) {
			return;
		}

		var buf = PacketByteBufs.create();
		buf.writeVarLong(world.getGameTime());
		buf.writeBlockPos(pos);
		for (ServerPlayer serverPlayer : VanillaDebugFeatures.NEIGHBORS_UPDATE.getPlayersWithFeatureEnabled(world.getServer())) {
			ServerPlayNetworking.send(serverPlayer, ClientboundCustomPayloadPacket.DEBUG_NEIGHBORSUPDATE_PACKET, buf);
		}
	}

	/**
	 * @author QuiltMC, Will BL
	 */
	@Overwrite
	public static void sendStructurePacket(WorldGenLevel world, StructureStart structureStart) {
		if (world.isClientSide()) {
			return;
		}
		var server = Objects.requireNonNull(world.getServer());
		var registryManager = server.registryAccess();
		var buf = PacketByteBufs.create();
		buf.writeResourceLocation(registryManager.registry(Registries.DIMENSION_TYPE).get().getKey(world.dimensionType()));
		var box = structureStart.getBoundingBox();
		buf.writeInt(box.minX());
		buf.writeInt(box.minY());
		buf.writeInt(box.minZ());
		buf.writeInt(box.maxX());
		buf.writeInt(box.maxY());
		buf.writeInt(box.maxZ());
		var children = structureStart.getPieces();
		buf.writeInt(children.size());
		for (int i = 0; i < children.size(); i++) {
			StructurePiece child = children.get(i);
			box = child.getBoundingBox();
			buf.writeInt(box.minX());
			buf.writeInt(box.minY());
			buf.writeInt(box.minZ());
			buf.writeInt(box.maxX());
			buf.writeInt(box.maxY());
			buf.writeInt(box.maxZ());
			buf.writeBoolean(i == 0);
		}
		for (ServerPlayer serverPlayer : VanillaDebugFeatures.STRUCTURE.getPlayersWithFeatureEnabled(world.getServer())) {
			ServerPlayNetworking.send(serverPlayer, ClientboundCustomPayloadPacket.DEBUG_STRUCTURES_PACKET, buf);
		}
	}


	/**
	 * @author QuiltMC, Will BL
	 */
	@Overwrite
	public static void sendEntityBrain(LivingEntity living) {
		if (living.level().isClientSide()) {
			return;
		}

		var buf = PacketByteBufs.create();
		buf.writeDouble(living.getX());
		buf.writeDouble(living.getY());
		buf.writeDouble(living.getZ());
		buf.writeUUID(living.getUUID());
		buf.writeInt(living.getId());
		buf.writeUtf(getShortDescription((ServerLevel) living.level(), living));
		buf.writeUtf(living instanceof VillagerDataHolder villager ? villager.getVillagerData().getProfession().name() : "none");
		buf.writeInt(living instanceof Villager villager ? villager.getVillagerXp() : 0);
		buf.writeFloat(living.getHealth());
		buf.writeFloat(living.getMaxHealth());
		writeBrain(living, buf);
		for (ServerPlayer serverPlayer : VanillaDebugFeatures.BRAIN.getPlayersWithFeatureEnabled(living.getServer())) {
			ServerPlayNetworking.send(serverPlayer, ClientboundCustomPayloadPacket.DEBUG_BRAIN, buf);
		}
	}

}
