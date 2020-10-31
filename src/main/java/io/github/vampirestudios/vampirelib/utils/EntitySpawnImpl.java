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

package io.github.vampirestudios.vampirelib.utils;

import io.github.vampirestudios.vampirelib.callbacks.EntitySpawnCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.ServerWorldAccess;

import java.util.concurrent.atomic.AtomicReference;

public final class EntitySpawnImpl {
	private EntitySpawnImpl() {
	}

	/**
	 * @author Valoeghese
	 */
	public static boolean spawnEntityZ(ServerWorld self, Entity entity) {
		AtomicReference<Entity> currentEntity = new AtomicReference<>(entity);
		ActionResult result = EntitySpawnCallback.PRE.invoker().onEntitySpawnPre(entity, currentEntity, self, SpawnReason.COMMAND);
		entity = currentEntity.get();

		if (result != ActionResult.FAIL) {
			if (self.shouldCreateNewEntityWithPassenger(entity)) {
				EntitySpawnCallback.POST.invoker().onEntitySpawnPost(entity, self, entity.getPos(), SpawnReason.COMMAND);
				return true;
			}
		}

		return false;
	}

	/**
	 * @author Valoeghese
	 */
	public static void spawnEntityV(ServerWorldAccess self, Entity entity) {
		AtomicReference<Entity> currentEntity = new AtomicReference<>(entity);
		ActionResult result = EntitySpawnCallback.PRE.invoker().onEntitySpawnPre(entity, currentEntity, self, SpawnReason.NATURAL);
		entity = currentEntity.get();

		if (result != ActionResult.FAIL) {
			self.spawnEntityAndPassengers(entity);
			EntitySpawnCallback.POST.invoker().onEntitySpawnPost(entity, self, entity.getPos(), SpawnReason.NATURAL);
		}
	}

	/**
	 * @author Valoeghese
	 */
	public static Entity spawnEntityE(Entity entity, ServerWorld serverWorld, SpawnReason spawnReason) {
		if (entity != null) {
			AtomicReference<Entity> currentEntity = new AtomicReference<>(entity);
			ActionResult result = EntitySpawnCallback.PRE.invoker().onEntitySpawnPre(entity, currentEntity, serverWorld, spawnReason);
			entity = result != ActionResult.FAIL ? currentEntity.get() : null;
		}

		if (entity != null) {
			serverWorld.spawnEntityAndPassengers(entity);
			EntitySpawnCallback.POST.invoker().onEntitySpawnPost(entity, serverWorld, entity.getPos(), spawnReason);
		}

		return entity;
	}

	/**
	 * @author Valoeghese
	 */
	public static ActionResult eventPre(Entity original, AtomicReference<Entity> entity, ServerWorldAccess world, SpawnReason reason, EntitySpawnCallback.Pre[] listeners) {
		for (EntitySpawnCallback.Pre callback : listeners) {
			ActionResult result = callback.onEntitySpawnPre(original, entity, world, reason);

			if (result != ActionResult.PASS) {
				return result;
			}
		}

		return ActionResult.PASS;
	}
}