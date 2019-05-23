package team.hollow.abnormalib.modules.api.features;

import de.siphalor.tweed.config.ConfigEnvironment;
import de.siphalor.tweed.config.ConfigScope;
import de.siphalor.tweed.config.entry.BooleanEntry;

public abstract class OptionalFeature extends Feature {
	protected BooleanEntry enabledEntry;
	public String name;

	public OptionalFeature(String name, String enablesDescription) {
		this.name = name;
		enabledEntry = register(name, createEnabledEntry(enablesDescription));
	}

	protected BooleanEntry createEnabledEntry(String description) {
		return new BooleanEntry(true).setComment(description).setScope(ConfigScope.GAME).setEnvironment(ConfigEnvironment.SERVER);
	}

	public OptionalFeature disableByDefault() {
		enabledEntry.setDefaultValue(false);
		return this;
	}

	public boolean isEnabled() {
		return enabledEntry.value;
	}

	@Override
	public final void apply() {
		if(enabledEntry.value) {
			applyEnabled();
		}
	}

	protected abstract void applyEnabled();
}
