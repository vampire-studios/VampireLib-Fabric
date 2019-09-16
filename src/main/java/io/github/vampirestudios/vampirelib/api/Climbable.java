/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
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

package io.github.vampirestudios.vampirelib.api;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Blocks that implement this can be climbed by LivingEntities.
 */
public interface Climbable {

    /**
     * Determines if the passed LivingEntity can climb this block.
     *
     * @param entity The LivingEntity that is attempting to climb this block.
     * @param state  The block state of the block being climbed.
     * @param pos    The position of the block being climbed.
     * @return Should either result ClimbBehavior.True or ClimbBehavior.False to indicate
     * whether or not this block can be climbed. Returning ClimbBehavior.Vanilla is used
     * to indicate that the game should ignore the result of this method and instead
     * perform the usual checks.
     */
    ClimbBehavior canClimb(LivingEntity entity, BlockState state, BlockPos pos);

    /**
     * @return The suffix of the death message when falling off this block and dying. Your translation file should
     * include the translation key "death.fell.accident.suffix", where "suffix" is a string returned by this method.
     * By default, this returns "generic", which is a vanilla suffix, so you only need to override this if you have
     * a custom death message.
     */
    default String getFallDeathSuffix() {
        return "generic";
    }

    enum ClimbBehavior {
        True,
        Scaffolding,
        False,
        Vanilla
    }

}