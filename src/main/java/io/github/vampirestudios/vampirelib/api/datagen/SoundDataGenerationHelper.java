package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.sounds.SoundEvent;

import io.github.vampirestudios.vampirelib.api.datagen.FabricSoundProvider.SoundGenerator;
import io.github.vampirestudios.vampirelib.utils.IdentifierUtils;

public class SoundDataGenerationHelper {

	public static void createMultipleSoundsBlockSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type, int amount) {
		createMultipleSoundsSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.BLOCK, soundEvent, name, subtitle, type, amount);
	}

	public static void createMultipleSoundsBlockSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type, int amount) {
		createMultipleSoundsSoundDefinition(registry, IdentifierUtils.IdType.BLOCK, soundEvent, name, type, amount);
	}

	public static void createSimpleBlockSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		createSimpleSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.BLOCK, soundEvent, name, subtitle, type);
	}

	public static void createSimpleBlockSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type) {
		createSimpleSoundDefinition(registry, IdentifierUtils.IdType.BLOCK, soundEvent, name, type);
	}

	public static void createMultipleSoundsItemSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type, int amount) {
		createMultipleSoundsSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.ITEM, soundEvent, name, subtitle, type, amount);
	}

	public static void createMultipleSoundsItemSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type, int amount) {
		createMultipleSoundsSoundDefinition(registry, IdentifierUtils.IdType.ITEM, soundEvent, name, type, amount);
	}

	public static void createSimpleItemSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		createSimpleSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.ITEM, soundEvent, name, subtitle, type);
	}

	public static void createSimpleItemSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type) {
		createSimpleSoundDefinition(registry, IdentifierUtils.IdType.ITEM, soundEvent, name, type);
	}

	public static void createMultipleSoundsParticleSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type, int amount) {
		createMultipleSoundsSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.PARTICLE, soundEvent, name, subtitle, type, amount);
	}

	public static void createMultipleSoundsParticleSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type, int amount) {
		createMultipleSoundsSoundDefinition(registry, IdentifierUtils.IdType.PARTICLE, soundEvent, name, type, amount);
	}

	public static void createSimpleParticleSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		createSimpleSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.PARTICLE, soundEvent, name, subtitle, type);
	}

	public static void createSimpleParticleSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type) {
		createSimpleSoundDefinition(registry, IdentifierUtils.IdType.PARTICLE, soundEvent, name, type);
	}

	public static void createMultipleSoundsEntitySoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type, int amount) {
		createMultipleSoundsSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.MOB, soundEvent, name, subtitle, type, amount);
	}

	public static void createMultipleSoundsEntitySoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type, int amount) {
		createMultipleSoundsSoundDefinition(registry, IdentifierUtils.IdType.MOB, soundEvent, name, type, amount);
	}

	public static void createSimpleEntitySoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		createSimpleSoundDefinitionWithSubtitle(registry, IdentifierUtils.IdType.MOB, soundEvent, name, subtitle, type);
	}

	public static void createSimpleEntitySoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type) {
		createSimpleSoundDefinition(registry, IdentifierUtils.IdType.MOB, soundEvent, name, type);
	}

	public static void createMusicDiscSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name) {
		registry.add(soundEvent, SoundBuilder.sound(IdentifierUtils.modId("records/" + name)));
	}

	public static void createMultipleSoundsSoundDefinitionWithSubtitle(SoundGenerator registry, IdentifierUtils.IdType idType, SoundEvent soundEvent, String name, String subtitle, String type, int amount) {
		List<SoundBuilder> soundBuilders = new ArrayList<>();
		for (int i = 1; i < amount; i++) {
			soundBuilders.add(SoundBuilder.sound(IdentifierUtils.modSpecialId(idType, name + "/" + type + i)));
		}
		registry.add(soundEvent, subtitle, soundBuilders.toArray(new SoundBuilder[0]));
	}

	public static void createMultipleSoundsSoundDefinition(SoundGenerator registry, IdentifierUtils.IdType idType, SoundEvent soundEvent, String name, String type, int amount) {
		List<SoundBuilder> soundBuilders = new ArrayList<>();
		for (int i = 1; i < amount; i++) {
			soundBuilders.add(SoundBuilder.sound(IdentifierUtils.modSpecialId(idType, name + "/" + type + i)));
		}
		registry.add(soundEvent, soundBuilders.toArray(new SoundBuilder[0]));
	}

	public static void createSimpleSoundDefinitionWithSubtitle(SoundGenerator registry, IdentifierUtils.IdType idType, SoundEvent soundEvent, String name, String subtitle, String type) {
		registry.add(soundEvent, subtitle, SoundBuilder.sound(IdentifierUtils.modSpecialId(idType, name + "/" + type)));
	}

	public static void createSimpleSoundDefinitionWithSubtitle(SoundGenerator registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		registry.add(soundEvent, subtitle, SoundBuilder.sound(IdentifierUtils.modId(name + "/" + type)));
	}

	public static void createSimpleSoundDefinition(SoundGenerator registry, IdentifierUtils.IdType idType, SoundEvent soundEvent, String name, String type) {
		registry.add(soundEvent, SoundBuilder.sound(IdentifierUtils.modSpecialId(idType, name + "/" + type)));
	}

	public static void createSimpleSoundDefinition(SoundGenerator registry, SoundEvent soundEvent, String name, String type) {
		registry.add(soundEvent, SoundBuilder.sound(IdentifierUtils.modId(name + "/" + type)));
	}

}
