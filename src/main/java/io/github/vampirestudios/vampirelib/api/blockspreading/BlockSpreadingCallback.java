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

package io.github.vampirestudios.vampirelib.api.blockspreading;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Example.
 * <code>
 * BlockSpreadingCallback.CUSTOM_SPREADING.register(spreading -> {
 * spreading.setBlock(Blocks.DIRT);
 * spreading.setType(BlockSpreadingType.GRASS);
 * spreading.setBehavior(new SnowySpreaderBehavior(Blocks.GRASS_BLOCK));
 * });
 * </code>
 */
public interface BlockSpreadingCallback {
	/**
	 * Allows to define blocks which can spread to each-other using a defined custom spreading behaviour.
	 */
	Event<CustomSpreading> CUSTOM_SPREADING = EventFactory.createArrayBacked(CustomSpreading.class,
			listeners -> spreading -> {
				for (CustomSpreading event : listeners) {
					event.spread(spreading);
				}
			});

	/**
	 * Allows to define blocks which can spread to each-other, but with no custom spreading behaviour.
	 */
	Event<NormalSpreading> NORMAL_SPREADING = EventFactory.createArrayBacked(NormalSpreading.class,
			listeners -> spreading -> {
				for (NormalSpreading event : listeners) {
					event.spread(spreading);
				}
			});

	interface CustomSpreading {
		/**
		 * @param spreading Impl class to define needed properties for spreading
		 */
		void spread(CustomSpreadingImpl spreading);
	}

	interface NormalSpreading {
		/**
		 * @param spreading Impl class to define needed properties for spreading
		 */
		void spread(NormalSpreadingImpl spreading);
	}
}
