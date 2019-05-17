package team.hollow.abnormalib.modules.api.features.woodtype;

import team.hollow.abnormalib.modules.api.features.OptionalFeature;
import team.hollow.abnormalib.utils.registry.WoodTypeRegistry;

public abstract class ModdedWoodTypeFeature extends OptionalFeature implements WoodTypeRegistry.ModdedTypeListener {
	public ModdedWoodTypeFeature(String name, String enablesDescription) {
		super(name, enablesDescription);

		WoodTypeRegistry.registerModdedTypeListener(this);
	}
}
