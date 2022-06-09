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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;
import org.jetbrains.annotations.NotNull;

import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricLanguageProvider implements DataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, String> data = new TreeMap<>();
    protected final FabricDataGenerator dataGenerator;
    private final String modId;
    private final String locale;

    protected FabricLanguageProvider(FabricDataGenerator dataGenerator, String locale) {
        this.dataGenerator = dataGenerator;
        this.modId = dataGenerator.getModId();
        this.locale = locale;
    }

	/**
	 * Registers all translations to be placed inside the lang file.
	 */
	protected abstract void addTranslations();

	@Override
	public void run(@NotNull HashCache cache) throws IOException {
		addTranslations();
		if (!data.isEmpty())
			save(cache, data, this.dataGenerator.getOutputFolder().resolve("assets/" + modId + "/lang/" + locale + ".json"));
	}

	@Override
	public String getName() {
		return "Languages: " + locale;
	}

	private void save(HashCache cache, Object object, Path target) throws IOException {
		String data = GSON.toJson(object);
		data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data); // Escape unicode after the fact so that it's not double escaped by GSON
		String hash = DataProvider.SHA1.hashUnencodedChars(data).toString();
		if (!Objects.equals(cache.getHash(target), hash) || !Files.exists(target)) {
			Files.createDirectories(target.getParent());

			try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
				bufferedwriter.write(data);
			}
		}

		cache.putNew(target, hash);
	}

    public void addBlock(Block key, String name) {
        add(key, name);
    }

    public void add(Block key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addItem(Item key, String name) {
        add(key, name);
    }

    public void add(Item key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addItemStack(ItemStack key, String name) {
        add(key, name);
    }

    public void add(ItemStack key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEnchantment(Enchantment key, String name) {
        add(key, name);
    }

    public void add(Enchantment key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addBiome(ResourceKey<Biome> key, String name) {
        add(key.location(), name);
    }

    public void add(ResourceLocation key, String name) {
        add("biome." + key.getNamespace() + "." + key.getPath(), name);
    }

    public void addEffect(MobEffect key, String name) {
        add(key, name);
    }

    public void add(MobEffect key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEntityType(EntityType<?> key, String name) {
        add(key, name);
    }

    public void add(EntityType<?> key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void add(String key, String value) {
        if (data.put(key, value) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }

}