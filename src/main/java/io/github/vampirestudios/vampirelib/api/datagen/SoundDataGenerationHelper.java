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

import java.util.function.Consumer;

import net.minecraft.sounds.SoundEvent;

import io.github.vampirestudios.vampirelib.utils.registry.ResourceLocationUtils;

public final class SoundDataGenerationHelper {
	private SoundDataGenerationHelper() {
	}

	public static void createMultipleSoundsBlockSoundDefinitionWithSubtitle(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String subtitle,
																			String type, int amount) {
		createMultipleSoundsSoundDefinitionWithSubtitle(registry, ResourceLocationUtils.IdType.BLOCK, soundEvent, name, subtitle, type, amount);
	}

	public static void createMultipleSoundsBlockSoundDefinition(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String type, int amount) {
		createMultipleSoundsSoundDefinition(registry, ResourceLocationUtils.IdType.BLOCK, soundEvent, name, type, amount);
	}

	public static void createSimpleBlockSoundDefinitionWithSubtitle(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		createSimpleSoundDefinitionWithSubtitle(registry, ResourceLocationUtils.IdType.BLOCK, soundEvent, name, subtitle, type);
	}

	public static void createSimpleBlockSoundDefinition(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String type) {
		createSimpleSoundDefinition(registry, ResourceLocationUtils.IdType.BLOCK, soundEvent, name, type);
	}

	public static void createMultipleSoundsParticleSoundDefinitionWithSubtitle(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String subtitle,
																			   String type, int amount) {
		createMultipleSoundsSoundDefinitionWithSubtitle(registry, ResourceLocationUtils.IdType.PARTICLE, soundEvent, name, subtitle, type, amount);
	}

	public static void createMultipleSoundsParticleSoundDefinition(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String type, int amount) {
		createMultipleSoundsSoundDefinition(registry, ResourceLocationUtils.IdType.PARTICLE, soundEvent, name, type,
				amount);
	}

	public static void createSimpleParticleSoundDefinitionWithSubtitle(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String subtitle, String type) {
		createSimpleSoundDefinitionWithSubtitle(registry, ResourceLocationUtils.IdType.PARTICLE, soundEvent, name,
				subtitle, type);
	}

	public static void createSimpleParticleSoundDefinition(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name, String type) {
		createSimpleSoundDefinition(registry, ResourceLocationUtils.IdType.PARTICLE, soundEvent, name, type);
	}

	public static void createMusicDiscSoundDefinition(Consumer<SoundDefinition> registry, SoundEvent soundEvent, String name) {
		SoundDefinition definition = SoundDefinition.definition(() -> soundEvent)
				.addSound(new SoundDefinition.SoundBuilder(
						ResourceLocationUtils.modId("records/" + name)));
		registry.accept(definition);
	}

	public static void createMultipleSoundsSoundDefinitionWithSubtitle(Consumer<SoundDefinition> registry, ResourceLocationUtils.IdType idType, SoundEvent soundEvent,
																	   String name, String subtitle, String type, int amount) {
		SoundDefinition definition = SoundDefinition.definition(() -> soundEvent).subtitle(subtitle);
		for (int i = 0; i < amount; i++) {
			definition.addSound(
					new SoundDefinition.SoundBuilder(ResourceLocationUtils.modSpecialId(idType, name + "/" + type + i)));
		}

		registry.accept(definition);
	}

	public static void createMultipleSoundsSoundDefinition(Consumer<SoundDefinition> registry, ResourceLocationUtils.IdType idType, SoundEvent soundEvent, String name,
														   String type, int amount) {
		SoundDefinition definition = SoundDefinition.definition(() -> soundEvent);
		for (int i = 0; i < amount; i++) {
			definition.addSound(
					new SoundDefinition.SoundBuilder(ResourceLocationUtils.modSpecialId(idType, name + "/" + type + i)));
		}

		registry.accept(definition);
	}

	public static void createSimpleSoundDefinitionWithSubtitle(Consumer<SoundDefinition> registry, ResourceLocationUtils.IdType idType, SoundEvent soundEvent,
															   String name, String subtitle, String type) {
		SoundDefinition definition = SoundDefinition.definition(() -> soundEvent)
				.subtitle(subtitle)
				.addSound(new SoundDefinition.SoundBuilder(ResourceLocationUtils.modSpecialId(idType, name + "/" + type)));
		registry.accept(definition);
	}

	public static void createSimpleSoundDefinition(Consumer<SoundDefinition> registry, ResourceLocationUtils.IdType idType, SoundEvent soundEvent, String name,
												   String type) {
		SoundDefinition definition = SoundDefinition.definition(() -> soundEvent)
				.addSound(new SoundDefinition.SoundBuilder(ResourceLocationUtils.modSpecialId(idType, name + "/" + type)));
		registry.accept(definition);
	}
}
