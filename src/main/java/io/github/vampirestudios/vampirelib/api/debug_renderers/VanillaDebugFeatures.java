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

package io.github.vampirestudios.vampirelib.api.debug_renderers;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

/**
 * In this class are {@link DebugFeature DebugFeatures} for the vanilla Debug Renderers which do not have other means
 * of activation (i.e., not chunk borders, not collision, and not game test)
 */
public final class VanillaDebugFeatures {
	/**
	 * @see DebugRenderer#pathfindingRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_PATHFINDING_PACKET
	 */
	public static final DebugFeature PATHFINDING = DebugFeature.register(new ResourceLocation("pathfinding"), true);
	/**
	 * @see DebugRenderer#waterDebugRenderer
	 */
	public static final DebugFeature WATER = DebugFeature.register(new ResourceLocation("water"), false);
	/**
	 * @see DebugRenderer#heightMapRenderer
	 */
	public static final DebugFeature HEIGHTMAP = DebugFeature.register(new ResourceLocation("heightmap"), false);
	/**
	 * @see DebugRenderer#neighborsUpdateRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_NEIGHBORSUPDATE_PACKET
	 */
	public static final DebugFeature NEIGHBORS_UPDATE = DebugFeature.register(new ResourceLocation("neighbors_update"), true);
	/**
	 * @see DebugRenderer#structureRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_STRUCTURES_PACKET
	 */
	public static final DebugFeature STRUCTURE = DebugFeature.register(new ResourceLocation("structure"), true);
	/**
	 * @see DebugRenderer#lightDebugRenderer
	 */
	public static final DebugFeature LIGHT = DebugFeature.register(new ResourceLocation("light"), false);
	/**
	 * @see DebugRenderer#worldGenAttemptRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_WORLDGENATTEMPT_PACKET
	 */
	public static final DebugFeature WORLD_GEN_ATTEMPT = DebugFeature.register(new ResourceLocation("world_gen_attempt"), true);
	/**
	 * @see DebugRenderer#solidFaceRenderer
	 */
	public static final DebugFeature SOLID_FACE = DebugFeature.register(new ResourceLocation("solid_face"), false);
	/**
	 * @see DebugRenderer#chunkRenderer
	 */
	public static final DebugFeature CHUNK = DebugFeature.register(new ResourceLocation("chunk"), false);
	/**
	 * @see DebugRenderer#brainDebugRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_POI_ADDED_PACKET
	 * @see ClientboundCustomPayloadPacket#DEBUG_POI_REMOVED_PACKET
	 * @see ClientboundCustomPayloadPacket#DEBUG_POI_TICKET_COUNT_PACKET
	 * @see ClientboundCustomPayloadPacket#DEBUG_BRAIN
	 */
	public static final DebugFeature BRAIN = DebugFeature.register(new ResourceLocation("brain"), true);
	/**
	 * @see DebugRenderer#villageSectionsDebugRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_VILLAGE_SECTIONS
	 */
	public static final DebugFeature VILLAGE_SECTIONS = DebugFeature.register(new ResourceLocation("village_sections"), true);
	/**
	 * @see DebugRenderer#beeDebugRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_BEE
	 * @see ClientboundCustomPayloadPacket#DEBUG_HIVE
	 */
	public static final DebugFeature BEE = DebugFeature.register(new ResourceLocation("bee"), true);
	/**
	 * @see DebugRenderer#raidDebugRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_RAIDS
	 */
	public static final DebugFeature RAID = DebugFeature.register(new ResourceLocation("raid"), true);
	/**
	 * @see DebugRenderer#goalSelectorRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_GOAL_SELECTOR
	 */
	public static final DebugFeature GOAL_SELECTOR = DebugFeature.register(new ResourceLocation("goal_selector"), true);
	/**
	 * @see DebugRenderer#gameEventListenerRenderer
	 * @see ClientboundCustomPayloadPacket#DEBUG_GAME_EVENT
	 * @see ClientboundCustomPayloadPacket#DEBUG_GAME_EVENT_LISTENER
	 */
	public static final DebugFeature GAME_EVENT = DebugFeature.register(new ResourceLocation("game_event"), true);

	private VanillaDebugFeatures() {}

	@ApiStatus.Internal
	public static void init() {}
}
