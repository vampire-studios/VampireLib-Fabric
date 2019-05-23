package team.hollow.abnormalib.modules.api;

import de.siphalor.tweed.config.ConfigEnvironment;
import de.siphalor.tweed.config.ConfigFile;
import de.siphalor.tweed.config.ConfigScope;
import de.siphalor.tweed.config.TweedRegistry;

public abstract class MainModule extends Module {
	protected ConfigFile configFile;

	public MainModule(String name, String description) {
		super(name, description);
	}

	public void setup(String modId) {
		configFile = TweedRegistry.registerConfigFile(modId + "/" + name);
		if(backgroundTexture != null) {
			configFile.setBackgroundTexture(backgroundTexture);
		}
		configFile.setComment(description);
		configEntries.forEach(entry -> configFile.register(entry.getLeft(), entry.getRight()));
		features.forEach(feature -> feature.getConfigEntries().forEach(entry -> configFile.register(entry.getLeft(), entry.getRight())));
		configFile.setReloadListener(this::onReload);
	}

	private void onReload(ConfigEnvironment environment, ConfigScope scope) {
		if(scope == ConfigScope.HIGHEST) {
			apply();
		}
	}

	public ConfigFile getConfigFile() {
		return configFile;
	}
}
