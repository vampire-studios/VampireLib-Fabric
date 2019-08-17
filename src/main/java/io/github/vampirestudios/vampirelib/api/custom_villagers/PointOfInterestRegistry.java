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

package io.github.vampirestudios.vampirelib.api.custom_villagers;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;

public interface PointOfInterestRegistry {

    static PointOfInterestType register(String modid, PointOfInterestTypeCustom pointOfInterestType_1) {
        if (!Registry.POINT_OF_INTEREST_TYPE.containsId(new Identifier(modid, pointOfInterestType_1.id)))
            return Registry.register(Registry.POINT_OF_INTEREST_TYPE, new Identifier(modid, pointOfInterestType_1.id), pointOfInterestType_1.register());
        else return PointOfInterestType.UNEMPLOYED;
    }

}