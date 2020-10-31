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

package io.github.vampirestudios.vampirelib.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;

/**
 * Registry for defining an item as a shield.
 * Shields should also override serveral {@code use} methods to work properly (see {@link net.minecraft.item.ShieldItem} for reference).
 */
public interface ShieldRegistry {
	ShieldRegistry INSTANCE = ShieldRegistryImpl.INSTANCE;

	/**
	 * @param item the item to define as shield
	 */
	void add(ItemConvertible item);

	/**
	 * @param tag the tag to define as shields
	 */
	void add(Tag<Item> tag);

	/**
	 * @param item the item to remove from the registry
	 */
	void clear(ItemConvertible item);

	/**
	 * @param tag the tag to remove from the registry
	 */
	void clear(Tag<Item> tag);

	boolean isShield(Item item);
}