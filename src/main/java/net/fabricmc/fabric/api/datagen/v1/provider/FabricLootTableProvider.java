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

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * A base interface for Loot table providers. You should not implement this class directly.
 *
 * <p>{@link FabricBlockLootTablesProvider} provides additional features specific to block drop loot tables.
 *
 * <p>Use {@link SimpleFabricLootTableProvider} for a simple abstract class that you can implement to handle standard loot table functions.
 */
@ApiStatus.NonExtendable
public interface FabricLootTableProvider extends Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>, DataProvider {
	Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	LootContextParamSet getLootContextType();

	FabricDataGenerator getFabricDataGenerator();

	@ApiStatus.Internal
	@Override
	default void run(HashCache cache) throws IOException {
		HashMap<ResourceLocation, LootTable> builders = Maps.newHashMap();

		accept((identifier, builder) -> {
			if (builders.put(identifier, builder.setParamSet(getLootContextType()).build()) != null) {
				throw new IllegalStateException("Duplicate loot table " + identifier);
			}
		});

		for (Map.Entry<ResourceLocation, LootTable> entry : builders.entrySet()) {
			DataProvider.save(GSON, cache, LootTables.serialize(entry.getValue()), getOutputPath(entry.getKey()));
		}
	}

	private Path getOutputPath(ResourceLocation lootTableId) {
		return getFabricDataGenerator().getOutputFolder().resolve("data/%s/loot_tables/%s.json".formatted(lootTableId.getNamespace(), lootTableId.getPath()));
	}
}