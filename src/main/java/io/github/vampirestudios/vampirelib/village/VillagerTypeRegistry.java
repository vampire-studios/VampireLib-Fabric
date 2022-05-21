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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;

public interface VillagerTypeRegistry {
    HashMap<ResourceKey<Biome>, ArrayList<VillagerType>> customVillagerTypes = new HashMap<>();

    @SafeVarargs
    static VillagerType register(ResourceLocation id, ResourceKey<Biome>... biomes) {
        VillagerType villagerType = Registry.register(Registry.VILLAGER_TYPE, id, new VillagerType(id.getPath()));
        for (ResourceKey<Biome> biome : biomes) {
            if (customVillagerTypes.containsKey(biome))
                customVillagerTypes.get(biome).add(villagerType);
            else {
                customVillagerTypes.put(biome, new ArrayList<>(Collections.singleton(villagerType)));
            }
            if(VillagerType.BY_BIOME.containsKey(biome)) {
                VillagerType.BY_BIOME.replace(biome, villagerType);
            }
        }
        return villagerType;
    }

    static VillagerType getVillagerTypeForBiome(Holder<Biome> biome) {
        Optional<ResourceKey<Biome>> optionalBiomeResourceKey = biome.unwrapKey();
        if (optionalBiomeResourceKey.isPresent()) {
            ResourceKey<Biome> biomeResourceKey = optionalBiomeResourceKey.get();
            if (customVillagerTypes.containsKey(biomeResourceKey)) {
                List<VillagerType> villagerTypes = customVillagerTypes.get(biomeResourceKey);
                return villagerTypes.get(Mth.floor(villagerTypes.size() * Math.random()));
            } else return VillagerType.byBiome(biome);
        } else {
            return VillagerType.PLAINS;
        }
    }
}