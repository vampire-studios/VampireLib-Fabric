/*
 * Copyright (c) 2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.google.common.collect.Sets;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;

/**
 * Extend this class and implement {@link net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider#generate}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class VBlockLootTableProvider extends BlockLootSubProvider implements FabricLootTableProvider {
	private final FabricDataOutput output;
	private final Set<ResourceLocation> excludedFromStrictValidation = new HashSet<>();

	protected VBlockLootTableProvider(FabricDataOutput dataOutput) {
		super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
		this.output = dataOutput;
	}

	/**
	 * Implement this method to add block drops.
	 *
	 * <p>Use the range of {@link BlockLootSubProvider#dropSelf} methods to generate block drops.
	 */
	@Override
	public abstract void generate();

	/**
	 * Disable strict validation for the passed block.
	 */
	public void excludeFromStrictValidation(Block block) {
		excludedFromStrictValidation.add(BuiltInRegistries.BLOCK.getKey(block));
	}

	@Override
	public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
		generate();

		for (Map.Entry<ResourceLocation, LootTable.Builder> entry : map.entrySet()) {
			ResourceLocation identifier = entry.getKey();

			if (identifier.equals(BuiltInLootTables.EMPTY)) {
				continue;
			}

			biConsumer.accept(identifier, entry.getValue());
		}

		if (output.isStrictValidationEnabled()) {
			Set<ResourceLocation> missing = Sets.newHashSet();

			for (ResourceLocation blockId : BuiltInRegistries.BLOCK.keySet()) {
				if (blockId.getNamespace().equals(output.getModId())) {
					ResourceLocation blockLootTableId = BuiltInRegistries.BLOCK.get(blockId).getLootTable();

					if (blockLootTableId.getNamespace().equals(output.getModId())) {
						if (!map.containsKey(blockLootTableId)) {
							missing.add(blockId);
						}
					}
				}
			}

			missing.removeAll(excludedFromStrictValidation);

			if (!missing.isEmpty()) {
				throw new IllegalStateException("Missing loot table(s) for %s".formatted(missing));
			}
		}
	}

	@Override
	public CompletableFuture<?> run(CachedOutput writer) {
		return VLootTableProviderImpl.run(writer, this, LootContextParamSets.BLOCK, output);
	}

	@Override
	public String getName() {
		return "Block Loot Tables";
	}
}
