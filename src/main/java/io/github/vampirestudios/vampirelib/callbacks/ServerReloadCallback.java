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

package io.github.vampirestudios.vampirelib.callbacks;

import net.minecraft.server.MinecraftServer;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * A callback for the server's reloading, as of {@code /reload} command is typed.
 */
public interface ServerReloadCallback {
	/**
	 * An event invoked immediately before the server starts reloading.
	 */
	Event<ServerReloadCallback> BEFORE = EventFactory.createArrayBacked(ServerReloadCallback.class, listeners -> server -> {
		for (ServerReloadCallback event : listeners) {
			event.onServerReload(server);
		}
	});

	/**
	 * An event invoked after the server just finished reloading or its initial loading.
	 */
	Event<ServerReloadCallback> AFTER = EventFactory.createArrayBacked(ServerReloadCallback.class, listeners -> server -> {
		for (ServerReloadCallback event : listeners) {
			event.onServerReload(server);
		}
	});

	/**
	 * Handles the event.
	 *
	 * @param server the server
	 */
	void onServerReload(MinecraftServer server);
}