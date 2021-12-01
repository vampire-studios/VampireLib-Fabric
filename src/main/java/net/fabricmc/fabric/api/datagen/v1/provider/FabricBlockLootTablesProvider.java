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

package net.fabricmc.fabric.api.datagen.v1.provider;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.common.collect.Sets;

import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Extend this class and implement {@link FabricBlockLootTablesProvider#generateBlockLootTables}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricBlockLootTablesProvider extends BlockLoot implements FabricLootTableProvider {
	protected final FabricDataGenerator dataGenerator;

	protected FabricBlockLootTablesProvider(FabricDataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
	}


	/**
	 * Implement this method to add block drops.
	 *
	 * <p>Use the range of {@link BlockLoot#dropSelf} methods to generate block drops.
	 */
	protected abstract void generateBlockLootTables();

	@Override
	public LootContextParamSet getLootContextType() {
		return LootContextParamSets.BLOCK;
	}

	@Override
	public FabricDataGenerator getFabricDataGenerator() {
		return dataGenerator;
	}

	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
		generateBlockLootTables();

		for (Map.Entry<ResourceLocation, LootTable.Builder> entry : map.entrySet()) {
			ResourceLocation identifier = entry.getKey();

			if (identifier.equals(BuiltInLootTables.EMPTY)) {
				continue;
			}

			biConsumer.accept(identifier, entry.getValue());
		}

		if (dataGenerator.isStrictValidationEnabled()) {
			Set<ResourceLocation> missing = Sets.newHashSet();

			for (ResourceLocation blockId : Registry.BLOCK.keySet()) {
				if (blockId.getNamespace().equals(dataGenerator.getModId())) {
					if (!map.containsKey(Registry.BLOCK.get(blockId).getLootTable())) {
						missing.add(blockId);
					}
				}
			}

			if (!missing.isEmpty()) {
				throw new IllegalStateException("Missing loot table(s) for %s".formatted(missing));
			}
		}
	}

	@Override
	public String getName() {
		return "Block Loot Tables";
	}
}