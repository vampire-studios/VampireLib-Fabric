package io.github.vampirestudios.vampirelib.utils;

import java.util.Locale;

import net.minecraft.resources.Identifier;

import io.github.vampirestudios.vampirelib.api.BasicModClass;

public class IdentifierUtils {

	private static String modId;

	public static void setModInstance(BasicModClass instanceIn) {
		modId = instanceIn.modId();
	}

	public static void setModId(String modIdIn) {
		modId = modIdIn;
	}

	public enum IdType {
		BLOCK,
		ITEM,
		ENTITY,
		MOB,
		WOOD_SETS,
		GUI,
		MISC,
		MOB_EFFECTS,
		PAINTING,
		PARTICLE,
		SOUNDS,
		MODELS_BLOCK,
		MODELS_ITEM,
		PARTICLE_JSON,
		BLOCKSTATES,
		LANG,
		MUSIC,
		WOOD_TYPES
	}

	public static Identifier vanillaSpecialId(IdType idType, String path) {
		return vanillaId(idType.name().toLowerCase(Locale.ROOT) + path);
	}

	public static Identifier modSpecialId(IdType idType, String path) {
		return modId(idType.name().toLowerCase(Locale.ROOT) + "/" + path);
	}

	public static Identifier vanillaPrefixId(String prefix, String path) {
		return vanillaId(prefix + path);
	}

	public static Identifier modPrefixId(String prefix, String path) {
		return modId(prefix + path);
	}

	public static Identifier vanillaSuffixId(String path, String suffix) {
		return vanillaId(path + suffix);
	}

	public static Identifier modSuffixId(String path, String suffix) {
		return modId(path + suffix);
	}

	public static Identifier vanillaPrefixSuffixId(String prefix, String path, String suffix) {
		return vanillaId(prefix + path + suffix);
	}

	public static Identifier modPrefixSuffixId(String prefix, String path, String suffix) {
		return modId(prefix + path + suffix);
	}

	public static Identifier modId(String path) {
		return id(modId, path);
	}

	public static Identifier vanillaId(String path) {
		return Identifier.withDefaultNamespace(path);
	}

	public static Identifier id(String namespace, String path) {
		return Identifier.fromNamespaceAndPath(namespace, path);
	}

}
