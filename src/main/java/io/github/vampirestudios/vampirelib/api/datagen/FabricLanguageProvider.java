/*
 * Copyright (c) 2022 OliviaTheVampire
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

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Extend this class and implement {@link FabricLanguageProvider#generateLanguages(LanguageConsumer)}.
 * Make sure to use {@link FabricLanguageProvider#FabricLanguageProvider(FabricDataGenerator, String)}  FabricLanguageProvider} to declare what language code is being generated if it isn't en_us
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricLanguageProvider implements DataProvider {
	protected final FabricDataGenerator dataGenerator;
	private final String languageCode;

	protected FabricLanguageProvider(FabricDataGenerator dataGenerator) {
		this(dataGenerator, "en_us");
	}

	protected FabricLanguageProvider(FabricDataGenerator dataGenerator, String languageCode) {
		this.dataGenerator = dataGenerator;
		this.languageCode = languageCode;
	}

	/**
	 * Implement this method to register languages.
	 *
	 * <p>Call {@link LanguageConsumer#addLanguage(String, String)} to add a language entry.
	 */
	public abstract void generateLanguages(LanguageConsumer languageConsumer);

	@Override
	public void run(CachedOutput writer) throws IOException {
		TreeMap<String, String> languageEntries = new TreeMap<>();

		this.generateLanguages(languageEntries::put);

		JsonObject langEntryJson = new JsonObject();

		for (Map.Entry<String, String> entry : languageEntries.entrySet()) {
			langEntryJson.addProperty(entry.getKey(), entry.getValue());
		}

		DataProvider.saveStable(writer, langEntryJson, this.getLangFilePath(this.languageCode));
	}

	private Path getLangFilePath(String code) {
		return this.dataGenerator.getOutputFolder().resolve("assets/%s/lang/%s.json".formatted(this.dataGenerator.getModId(), code));
	}

	@Override
	public String getName() {
		return "Languages";
	}
}
