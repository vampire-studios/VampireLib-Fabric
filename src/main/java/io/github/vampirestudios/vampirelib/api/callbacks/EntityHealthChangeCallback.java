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

package io.github.vampirestudios.vampirelib.api.callbacks;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface EntityHealthChangeCallback {
	/**
	 * Callback for entity health change. Triggered whenever the game updates the entity's health. Returns the entity
	 * and it's new health
	 */
	Event<EntityHealthChangeCallback> EVENT = EventFactory.createArrayBacked(EntityHealthChangeCallback.class,
			listeners ->
					(entity, health) -> {
						for (EntityHealthChangeCallback event : listeners) {
							InteractionResult result = event.health(entity, health);
							if (result != InteractionResult.PASS) return result;
						}

						return InteractionResult.PASS;
					});

	InteractionResult health(LivingEntity entity, float health);
}
