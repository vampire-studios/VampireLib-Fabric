package io.github.vampirestudios.vampirelib.utils.registry;

import java.util.Locale;

import net.minecraft.resources.ResourceLocation;

import io.github.vampirestudios.vampirelib.api.BasicModClass;

public class ResourceLocationUtils {

	private static BasicModClass instance;

	public static void setModInstance(BasicModClass instanceIn) {
		instance = instanceIn;
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
		MUSIC
	}

	public static ResourceLocation vanillaSpecialId(IdType idType, String path) {
		return vanillaId(idType.name().toLowerCase(Locale.ROOT) + path);
	}

	public static ResourceLocation modSpecialId(IdType idType, String path) {
		return modId(idType.name().toLowerCase(Locale.ROOT) + "/" + path);
	}

	public static ResourceLocation vanillaPrefixId(String prefix, String path) {
		return vanillaId(prefix + path);
	}

	public static ResourceLocation modPrefixId(String prefix, String path) {
		return modId(prefix + path);
	}

	public static ResourceLocation vanillaSuffixId(String path, String suffix) {
		return vanillaId(path + suffix);
	}

	public static ResourceLocation modSuffixId(String path, String suffix) {
		return modId(path + suffix);
	}

	public static ResourceLocation vanillaPrefixSuffixId(String prefix, String path, String suffix) {
		return vanillaId(prefix + path + suffix);
	}

	public static ResourceLocation modPrefixSuffixId(String prefix, String path, String suffix) {
		return modId(prefix + path + suffix);
	}

	public static ResourceLocation modId(String path) {
		return id(instance.modId(), path);
	}

	public static ResourceLocation vanillaId(String path) {
		return id("minecraft", path);
	}

	public static ResourceLocation id(String namespace, String path) {
		return new ResourceLocation(namespace, path);
	}

}
