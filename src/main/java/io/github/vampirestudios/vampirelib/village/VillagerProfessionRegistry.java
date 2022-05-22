/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.village;

import com.google.common.collect.ImmutableSet;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface VillagerProfessionRegistry {

    static VillagerProfession register(ResourceLocation name, PoiType workStation) {
        return register(name, workStation, ImmutableSet.of());
    }

    static VillagerProfession register(ResourceLocation name, PoiType workStation, ImmutableSet<Item> gatherableItems) {
        return register(name, workStation, gatherableItems, ImmutableSet.of());
    }

    static VillagerProfession register(ResourceLocation name, PoiType workStation, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites) {
        return register(name, workStation, gatherableItems, secondaryJobSites, null);
    }

    static VillagerProfession register(ResourceLocation name, PoiType workStation, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites, SoundEvent workSound) {
        return Registry.register(Registry.VILLAGER_PROFESSION, name, new VillagerProfession(name.toString(), workStation, gatherableItems, secondaryJobSites, workSound));
    }

    static VillagerProfession register(String name, PoiType workStation, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites, SoundEvent workSound) {
        return Registry.register(Registry.VILLAGER_PROFESSION, name, new VillagerProfession(name, workStation, gatherableItems, secondaryJobSites, workSound));
    }

}