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

import java.util.HashSet;
import java.util.Set;

public class ShieldRegistryImpl implements ShieldRegistry {
	public static final ShieldRegistryImpl INSTANCE = new ShieldRegistryImpl();

	private final Set<ItemConvertible> registeredItemEntries = new HashSet<>();
	private final Set<Tag<Item>> registeredTagEntries = new HashSet<>();

	@Override
	public void add(ItemConvertible item) {
		registeredItemEntries.add(item);
	}

	@Override
	public void add(Tag<Item> tag) {
		registeredTagEntries.add(tag);
	}

	@Override
	public void clear(ItemConvertible item) {
		registeredItemEntries.remove(item);
	}

	@Override
	public void clear(Tag<Item> tag) {
		registeredTagEntries.remove(tag);
	}

	public boolean isShield(Item item) {
		if (registeredItemEntries.contains(item)) {
			return true;
		}

		for (Tag<Item> entry : registeredTagEntries) {
			if (entry.contains(item)) {
				return true;
			}
		}

		return false;
	}
}