package team.hollow.abnormalib.modules.api.features.woodtype;

import team.hollow.abnormalib.utils.registry.WoodType;

import java.util.Collections;
import java.util.Set;

public abstract class WoodTypeFeature extends ModdedWoodTypeFeature {
	public static final Set<WoodType> SKIP_OAK = Collections.singleton(WoodType.OAK);
	protected Set<WoodType> skipWoodTypes;

	public WoodTypeFeature(String name, String enablesDescription, Set<WoodType> skipWoodTypes) {
		super(name, enablesDescription);
		this.skipWoodTypes = skipWoodTypes;
	}

	@Override
	protected void applyEnabled() {
		for(WoodType woodType : WoodType.VANILLA) {
			if(skipWoodTypes.contains(woodType))
				continue;
			process(woodType);
		}
	}

	@Override
	public void onModdedWoodTypeRegistered(WoodType woodType, float hardness, float resistance) {
		if(isEnabled()) {
			if(!skipWoodTypes.contains(woodType))
				process(woodType);
		}
	}

	protected abstract void process(WoodType woodType);
}
