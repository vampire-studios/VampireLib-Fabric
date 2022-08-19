/*
 * Copyright (c) 2022 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.utils.registry;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import io.github.vampirestudios.vampirelib.VampireLib;

public abstract class WoodTypeRegistry implements StringRepresentable {
	public static final Registry<WoodType> WOOD_TYPES = FabricRegistryBuilder.createSimple(WoodType.class,
					VampireLib.INSTANCE.identifier(
							"wood_type_registry"))
			.buildAndRegister();

	private static final Queue<ModdedTypeListener> listeners = new ConcurrentLinkedQueue<>();

	public static WoodType get(ResourceLocation id) {
		return WOOD_TYPES.get(id);
	}

	public static boolean contains(ResourceLocation id) {
		return WOOD_TYPES.containsKey(id);
	}

	public static boolean contains(WoodType woodType) {
		return WOOD_TYPES.getKey(woodType) != null;
	}

	static WoodType registerVanilla(WoodType woodType) {
		return Registry.register(WOOD_TYPES, woodType.identifier, woodType);
	}

	public static WoodType registerModded(WoodType woodType) {
		registerVanilla(woodType);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodType));
		return woodType;
	}

	public static WoodType registerModded(WoodRegistry woodRegistry) {
		WoodType woodType = new WoodType(woodRegistry.name(), woodRegistry.leaves(), woodRegistry.log());
		registerVanilla(woodType);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodType));
		return woodType;
	}

	public static WoodType registerModded(WoodRegistry woodRegistry, Block leaves) {
		WoodType woodType = new WoodType(woodRegistry.name(), leaves, woodRegistry.log());
		registerVanilla(woodType);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodType));
		return woodType;
	}

	public static void registerModdedTypeListener(ModdedTypeListener listener) {
		listeners.add(listener);
	}

	public interface ModdedTypeListener {
		void onModdedWoodTypeRegistered(WoodType woodType);
	}

}
