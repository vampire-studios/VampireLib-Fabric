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

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public interface PointOfInterestRegistry {

    static PoiType register(PointOfInterestTypeCustom pointOfInterestTypeCustom) {
        if (!Registry.POINT_OF_INTEREST_TYPE.containsKey(new ResourceLocation(pointOfInterestTypeCustom.getName())) &&
            !pointOfInterestTypeCustom.getWorkStationStates().isEmpty())
            return Registry.register(Registry.POINT_OF_INTEREST_TYPE, new ResourceLocation(pointOfInterestTypeCustom.getName()), pointOfInterestTypeCustom);
        else return Registry.POINT_OF_INTEREST_TYPE.get(ResourceLocation.tryParse(pointOfInterestTypeCustom.getName()));
    }

    static PoiType register(ResourceLocation name, PointOfInterestTypeCustom pointOfInterestType) {
        if (!Registry.POINT_OF_INTEREST_TYPE.containsKey(name) && !pointOfInterestType.getWorkStationStates().isEmpty())
            return Registry.register(Registry.POINT_OF_INTEREST_TYPE, name, pointOfInterestType);
        else return Registry.POINT_OF_INTEREST_TYPE.get(name);
    }

}