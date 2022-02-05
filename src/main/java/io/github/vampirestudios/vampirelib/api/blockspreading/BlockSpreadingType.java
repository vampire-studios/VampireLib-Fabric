package io.github.vampirestudios.vampirelib.api.blockspreading;

import java.util.HashMap;
import java.util.Map;

public record BlockSpreadingType(String name) {
	private static final Map<String, BlockSpreadingType> TYPES = new HashMap<>();

	public static final BlockSpreadingType GRASS = get("grass");
	public static final BlockSpreadingType MYCELIUM = get("mycelium");
	public static final BlockSpreadingType CRIMSON = get("crimson");
	public static final BlockSpreadingType WARPED = get("warped");
	public static final BlockSpreadingType REVERT = get("revert");

	public static BlockSpreadingType get(String name) {
		return TYPES.computeIfAbsent(name, BlockSpreadingType::new);
	}

	public String getName() {
		return name;
	}
}