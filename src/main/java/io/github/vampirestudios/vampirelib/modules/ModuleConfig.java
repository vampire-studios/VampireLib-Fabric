package io.github.vampirestudios.vampirelib.modules;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.modules.api.NonFeatureModule;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ModuleConfig {
	private static final Jankson JANKSON = Jankson.builder().build();

	public static void load(NonFeatureModule module, String modName) {
		File configFile = new File("config/quark/" + module.getRegistryName().getPath() + ".json5");
		JsonObject config = new JsonObject();
		if(configFile.exists()) {
			try {
				config = JANKSON.load(configFile);
				loadFrom(module, config);
				writeConfigFile(module, modName, configFile, config);
			} catch (IOException | SyntaxError e) {
				VampireLib.LOGGER.error(modName + " config could not be loaded. Default values will be used.", e);
			}
		} else {
			saveTo(module, config);
			writeConfigFile(module, modName, configFile, config);
		}
	}

	private static void writeConfigFile(NonFeatureModule module, String modName, File file, JsonObject config) {
		saveTo(module, config);
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			out.write(config.toJson(JsonGrammar.JANKSON).getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			VampireLib.LOGGER.error(modName + "config could not be written. This probably won't cause any problems, but it shouldn't happen.", e);
		}
	}

	public static void loadFrom(NonFeatureModule module, JsonObject obj) {
		JsonObject moduleConfig = getObjectOrEmpty(module.getRegistryName().toString(), obj);
		module.setEnabled(moduleConfig);
		module.configure(moduleConfig);
	}

	public static void saveTo(NonFeatureModule module, JsonObject obj) {
		JsonObject moduleConfig = module.getConfig();
		obj.put(module.getRegistryName().toString(), moduleConfig);
	}

	public static JsonObject getObjectOrEmpty(String key, JsonObject on) {
		JsonObject obj = on.getObject(key);
		return obj != null ? obj : new JsonObject();
	}
}