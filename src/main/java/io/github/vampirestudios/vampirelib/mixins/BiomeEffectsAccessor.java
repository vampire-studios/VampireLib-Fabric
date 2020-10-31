/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.mixins;

import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(BiomeEffects.class)
public interface BiomeEffectsAccessor {
	@Accessor
	int getFogColor();

	@Accessor
	int getWaterColor();

	@Accessor
	int getWaterFogColor();

	@Accessor
	int getSkyColor();

	@Accessor
	Optional<Integer> getFoliageColor();

	@Accessor
	Optional<Integer> getGrassColor();

	@Accessor
	BiomeEffects.GrassColorModifier getGrassColorModifier();

	@Accessor
	Optional<BiomeParticleConfig> getParticleConfig();

	@Accessor
	Optional<SoundEvent> getLoopSound();

	@Accessor
	Optional<BiomeMoodSound> getMoodSound();

	@Accessor
	Optional<BiomeAdditionsSound> getAdditionsSound();

	@Accessor
	Optional<MusicSound> getMusic();
}