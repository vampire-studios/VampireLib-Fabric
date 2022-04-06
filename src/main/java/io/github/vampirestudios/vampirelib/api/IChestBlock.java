package io.github.vampirestudios.vampirelib.api;

import io.github.vampirestudios.vampirelib.ChestManager;

/**
 * Implemented on chest blocks that make use of Blueprint's chest system.
 *
 * @author SmellyModder (Luke Tonon)
 * @author bageldotjpg
 */
public interface IChestBlock {
	/**
	 * Gets the chest type ID of this {@link IChestBlock}.
	 * <p>Used on {@link ChestManager#getInfoForChest(String)}.</p>
	 *
	 * @return The chest type ID of this {@link IChestBlock}.
	 */
	String getChestType();
}