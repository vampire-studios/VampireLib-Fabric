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

package io.github.vampirestudios.vampirelib.mixins;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import io.github.vampirestudios.vampirelib.api.datagen.FabricBlockStateModelGenerator;

@Mixin(BlockModelGenerators.class)
public abstract class BlockStateModelGeneratorMixin implements FabricBlockStateModelGenerator {
	@Shadow
	@Final
	public BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

	@Shadow
	@Final
	public Consumer<BlockStateGenerator> blockStateOutput;

	@Override
	public void registerEmptyModel(Block block) {
		registerEmptyModel(block, ModelLocationUtils.getModelLocation(block));
	}

	@Override
	public void registerEmptyModel(Block block, ResourceLocation id) {
		this.modelOutput.accept(id, JsonObject::new);
		this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, id));
	}
}
