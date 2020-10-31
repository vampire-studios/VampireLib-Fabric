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

import io.github.vampirestudios.vampirelib.mixins.ServerWorldAccessor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.Spawner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FabricSpawnerRegistryInternals {
	public static void register(ServerWorld world, Spawner spawner) {
		//Copy the list, because it may be immutable
		List<Spawner> spawnerList = new ArrayList<>(((ServerWorldAccessor) world).getSpawners());
		spawnerList.add(spawner);
		((ServerWorldAccessor) world).setSpawners(spawnerList);
	}

	public static void register(ServerWorld world, Collection<Spawner> spawners) {
		//Copy the list, because it may be immutable
		List<Spawner> spawnerList = new ArrayList<>(((ServerWorldAccessor) world).getSpawners());
		spawnerList.addAll(spawners);
		((ServerWorldAccessor) world).setSpawners(spawnerList);
	}

	public static void unregister(ServerWorld world, Spawner spawner) {
		//Copy the list, because it may be immutable
		List<Spawner> spawnerList = new ArrayList<>(((ServerWorldAccessor) world).getSpawners());
		spawnerList.remove(spawner);
		((ServerWorldAccessor) world).setSpawners(spawnerList);
	}

	public static void unregister(ServerWorld world, Collection<Spawner> spawners) {
		//Copy the list, because it may be immutable
		List<Spawner> spawnerList = new ArrayList<>(((ServerWorldAccessor) world).getSpawners());
		spawnerList.removeAll(spawners);
		((ServerWorldAccessor) world).setSpawners(spawnerList);
	}
}