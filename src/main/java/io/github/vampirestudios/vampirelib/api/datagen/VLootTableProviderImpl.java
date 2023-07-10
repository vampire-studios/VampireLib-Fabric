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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;

public final class VLootTableProviderImpl {
	/**
	 * Shared run logic for {@link VBlockLootTableProvider} and {@link SimpleFabricLootTableProvider}.
	 */
	public static CompletableFuture<?> run(
			CachedOutput writer,
			VBlockLootTableProvider provider,
			LootContextParamSet lootContextType,
			FabricDataOutput fabricDataOutput) {
		HashMap<ResourceLocation, LootTable> builders = Maps.newHashMap();
		HashMap<ResourceLocation, ConditionJsonProvider[]> conditionMap = new HashMap<>();

		provider.generate((identifier, builder) -> {
			ConditionJsonProvider[] conditions = FabricDataGenHelper.consumeConditions(builder);
			conditionMap.put(identifier, conditions);

			if (builders.put(identifier, builder.setParamSet(lootContextType).build()) != null) {
				throw new IllegalStateException("Duplicate loot table " + identifier);
			}
		});

		final List<CompletableFuture<?>> futures = new ArrayList<>();

		for (Map.Entry<ResourceLocation, LootTable> entry : builders.entrySet()) {
			JsonObject tableJson = (JsonObject) LootDataType.TABLE.parser().toJsonTree(entry.getValue());
			ConditionJsonProvider.write(tableJson, conditionMap.remove(entry.getKey()));

			futures.add(DataProvider.saveStable(writer, tableJson, getOutputPath(fabricDataOutput, entry.getKey())));
		}

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	private static Path getOutputPath(FabricDataOutput dataOutput, ResourceLocation lootTableId) {
		return dataOutput.createPathProvider(PackOutput.Target.DATA_PACK, "loot_tables").json(lootTableId);
	}

	private VLootTableProviderImpl() {
	}
}
