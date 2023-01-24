/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import java.util.function.Consumer;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface TextureStitchCallback {
	Event<Pre> PRE = EventFactory.createArrayBacked(Pre.class, callbacks -> (atlas, spriteAdder) -> {
		for (Pre e : callbacks) e.stitch(atlas, spriteAdder);
	});

	Event<Post> POST = EventFactory.createArrayBacked(Post.class, callbacks -> atlas -> {
		for (Post e : callbacks) e.stitch(atlas);
	});

	@Environment(EnvType.CLIENT)
	interface Pre {
		void stitch(TextureAtlas atlas, Consumer<ResourceLocation> spriteAdder);
	}

	@Environment(EnvType.CLIENT)
	interface Post {
		void stitch(TextureAtlas atlas);
	}
}
