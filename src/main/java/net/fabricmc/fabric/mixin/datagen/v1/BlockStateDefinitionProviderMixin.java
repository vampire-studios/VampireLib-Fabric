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

package net.fabricmc.fabric.mixin.datagen.v1;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;

@Mixin(ModelProvider.class)
public class BlockStateDefinitionProviderMixin {
	@Shadow
	@Final
	private DataGenerator generator;

	@Unique
	private static final ThreadLocal<DataGenerator> dataGeneratorThreadLocal = new ThreadLocal<>();

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/BlockModelGenerators;run()V"))
	private void registerBlockStateModels(BlockModelGenerators instance) {
		if (((Object) this) instanceof FabricBlockStateDefinitionProvider fabricBlockStateDefinitionProvider) {
			fabricBlockStateDefinitionProvider.generateBlockStateModels(instance);
		} else {
			// Fallback to the vanilla registration when not a fabric provider
			instance.run();
		}
	}

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/ItemModelGenerators;run()V"))
	private void registerItemModels(ItemModelGenerators instance) {
		if (((Object) this) instanceof FabricBlockStateDefinitionProvider fabricBlockStateDefinitionProvider) {
			fabricBlockStateDefinitionProvider.generateItemModels(instance);
		} else {
			// Fallback to the vanilla registration when not a fabric provider
			instance.run();
		}
	}

	@Inject(method = "run", at = @At("HEAD"))
	private void runHead(HashCache cache, CallbackInfo ci) {
		dataGeneratorThreadLocal.set(generator);
	}

	@Inject(method = "run", at = @At("TAIL"))
	private void runTail(HashCache cache, CallbackInfo ci) {
		dataGeneratorThreadLocal.remove();
	}

	@Dynamic("Testing")
	@Inject(method = "method_25738", at = @At("HEAD"), cancellable = true)
	private static void filterBlocksForProcessingMod(Map<Block, BlockStateGenerator> map, Block block, CallbackInfoReturnable<Boolean> cir) {
		if (dataGeneratorThreadLocal.get() instanceof FabricDataGenerator dataGenerator) {
			if (dataGenerator.isStrictValidationEnabled()) {
				cir.setReturnValue(false);
				return;
			}

			if (!Registry.BLOCK.getKey(block).getNamespace().equals(dataGenerator.getModId())) {
				// Skip over blocks that are not from the mod we are processing.
				cir.setReturnValue(false);
			}
		}
	}

	@Dynamic("Testing")
	@Inject(method = "method_25741", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/model/ModelLocationUtils;getModelLocation(Lnet/minecraft/world/item/Item;)Lnet/minecraft/resources/ResourceLocation;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void filterItemsForProcessingMod(Set<Item> set, Map<ResourceLocation, Supplier<JsonElement>> map, Block block, CallbackInfo ci, Item item) {
		if (dataGeneratorThreadLocal.get() instanceof FabricDataGenerator dataGenerator) {
			if (!dataGenerator.isStrictValidationEnabled()) {
				ci.cancel();
				return;
			}

			if (!Registry.ITEM.getKey(item).getNamespace().equals(dataGenerator.getModId())) {
				// Skip over any items from other mods.
				ci.cancel();
			}
		}
	}
}