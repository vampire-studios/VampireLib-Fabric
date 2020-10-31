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

package io.github.vampirestudios.vampirelib;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.Spawner;

import java.util.Collection;

/**
 * Allows registering custom spawners for entities.
 *
 * <p>Entities that need custom spawning logic use {@link Spawner}s. These are run
 * every tick by the server, and are used for entities such as Wandering Traders.</p>
 *
 * <p>Note: The registry only adds spawners to one world - the recommended use is to add a callback to
 * {@link net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents#LOAD} and do your spawner registration in there.
 *
 * @see net.minecraft.world.gen.Spawner
 */
public final class FabricSpawnerRegistry {
	/**
	 * Adds an entity spawner to a world.
	 *
	 * @param world the world to add the spawner to
	 * @param spawner the spawner to add
	 */
	public static void register(ServerWorld world, Spawner spawner) {
		FabricSpawnerRegistryInternals.register(world, spawner);
	}

	/**
	 * Adds a collection of entity spawners to a world.
	 *
	 * @param world the world to add the spawners to
	 * @param spawners the spawners to add
	 */
	public static void register(ServerWorld world, Collection<Spawner> spawners) {
		FabricSpawnerRegistryInternals.register(world, spawners);
	}

	/**
	 * Removes an entity spawner from a world.
	 *
	 * @param world the world to remove the spawner from
	 * @param spawner the spawner to remove
	 */
	public static void unregister(ServerWorld world, Spawner spawner) {
		FabricSpawnerRegistryInternals.unregister(world, spawner);
	}

	/**
	 * Removes a collection of entity spawners from a world.
	 *
	 * @param world the world to remove the spawners from
	 * @param spawners the spawners to remove
	 */
	public static void unregister(ServerWorld world, Collection<Spawner> spawners) {
		FabricSpawnerRegistryInternals.unregister(world, spawners);
	}
}