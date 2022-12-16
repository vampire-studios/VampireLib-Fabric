/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vampirestudios.vampirelib.api;

import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Helper methods for working with and creating {@link FriendlyByteBuf}s.
 */
public final class FriendlyByteBufs {
	private static final FriendlyByteBuf EMPTY_PACKET_BYTE_BUF = new FriendlyByteBuf(Unpooled.EMPTY_BUFFER);

	/**
	 * Returns an empty instance of packet byte buffer.
	 *
	 * @return an empty buffer
	 */
	public static FriendlyByteBuf empty() {
		return EMPTY_PACKET_BYTE_BUF;
	}

	/**
	 * Returns a new heap memory-backed instance of packet byte buffer.
	 *
	 * @return a new buffer
	 */
	public static FriendlyByteBuf create() {
		return new FriendlyByteBuf(Unpooled.buffer());
	}

	// Convenience methods for byte buffer methods that return a new byte buffer

	/**
	 * Wraps the newly created buf from {@code buf.readBytes} in a packet byte buffer.
	 *
	 * @param buf    the original buffer
	 * @param length the number of bytes to transfer
	 * @return the transferred bytes
	 * @see ByteBuf#readBytes(int)
	 */
	public static FriendlyByteBuf readBytes(ByteBuf buf, int length) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.readBytes(length));
	}

	/**
	 * Wraps the newly created buffer from {@code buf.readSlice} in a packet byte buffer.
	 *
	 * @param buf    the original buffer
	 * @param length the size of the new slice
	 * @return the newly created slice
	 * @see ByteBuf#readSlice(int)
	 */
	public static FriendlyByteBuf readSlice(ByteBuf buf, int length) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.readSlice(length));
	}

	/**
	 * Wraps the newly created buffer from {@code buf.readRetainedSlice} in a packet byte buffer.
	 *
	 * @param buf    the original buffer
	 * @param length the size of the new slice
	 * @return the newly created slice
	 * @see ByteBuf#readRetainedSlice(int)
	 */
	public static FriendlyByteBuf readRetainedSlice(ByteBuf buf, int length) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.readRetainedSlice(length));
	}

	/**
	 * Wraps the newly created buffer from {@code buf.copy} in a packet byte buffer.
	 *
	 * @param buf the original buffer
	 * @return a copy of the buffer
	 * @see ByteBuf#copy()
	 */
	public static FriendlyByteBuf copy(ByteBuf buf) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.copy());
	}

	/**
	 * Wraps the newly created buffer from {@code buf.copy} in a packet byte buffer.
	 *
	 * @param buf    the original buffer
	 * @param index  the starting index
	 * @param length the size of the copy
	 * @return a copy of the buffer
	 * @see ByteBuf#copy(int, int)
	 */
	public static FriendlyByteBuf copy(ByteBuf buf, int index, int length) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.copy(index, length));
	}

	/**
	 * Wraps the newly created buffer from {@code buf.slice} in a packet byte buffer.
	 *
	 * @param buf the original buffer
	 * @return a slice of the buffer
	 * @see ByteBuf#slice()
	 */
	public static FriendlyByteBuf slice(ByteBuf buf) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.slice());
	}

	/**
	 * Wraps the newly created buffer from {@code buf.retainedSlice} in a packet byte buffer.
	 *
	 * @param buf the original buffer
	 * @return a slice of the buffer
	 * @see ByteBuf#retainedSlice()
	 */
	public static FriendlyByteBuf retainedSlice(ByteBuf buf) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.retainedSlice());
	}

	/**
	 * Wraps the newly created buffer from {@code buf.slice} in a packet byte buffer.
	 *
	 * @param buf    the original buffer
	 * @param index  the starting index
	 * @param length the size of the copy
	 * @return a slice of the buffer
	 * @see ByteBuf#slice(int, int)
	 */
	public static FriendlyByteBuf slice(ByteBuf buf, int index, int length) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.slice(index, length));
	}

	/**
	 * Wraps the newly created buffer from {@code buf.retainedSlice} in a packet byte buffer.
	 *
	 * @param buf    the original buffer
	 * @param index  the starting index
	 * @param length the size of the copy
	 * @return a slice of the buffer
	 * @see ByteBuf#retainedSlice(int, int)
	 */
	public static FriendlyByteBuf retainedSlice(ByteBuf buf, int index, int length) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.retainedSlice(index, length));
	}

	/**
	 * Wraps the newly created buffer from {@code buf.duplicate} in a packet byte buffer.
	 *
	 * @param buf the original buffer
	 * @return a duplicate of the buffer
	 * @see ByteBuf#duplicate()
	 */
	public static FriendlyByteBuf duplicate(ByteBuf buf) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.duplicate());
	}

	/**
	 * Wraps the newly created buffer from {@code buf.retainedDuplicate} in a packet byte buffer.
	 *
	 * @param buf the original buffer
	 * @return a duplicate of the buffer
	 * @see ByteBuf#retainedDuplicate()
	 */
	public static FriendlyByteBuf retainedDuplicate(ByteBuf buf) {
		Objects.requireNonNull(buf, "ByteBuf cannot be null");

		return new FriendlyByteBuf(buf.retainedDuplicate());
	}

	private FriendlyByteBufs() {
	}
}
