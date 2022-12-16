/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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

package io.github.vampirestudios.vampirelib.api.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

/**
 * Fabric-provided extensions for {@link net.minecraft.data.models.BlockModelGenerators}.
 *
 * <p>Note: This interface is automatically implemented on all generators via Mixin and interface injection.
 */
public interface FabricBlockStateModelGenerator {
	/**
	 * Create a singleton block state and empty model file for this block. Useful in special cases where a block may
	 * be rendered/modelled entirely through code rather than a standard model.
	 *
	 * @param block The block to register a block state and model for.
	 */
	default void registerEmptyModel(Block block) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Create a singleton block state and empty model file with the given ID for this block. Useful in special cases
	 * where a block may be rendered/modelled entirely through code rather than a standard model.
	 *
	 * @param block The block to register a block state and model for.
	 * @param id   The ID/path for the generated "model".
	 */
	default void registerEmptyModel(Block block, ResourceLocation id) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}
}
