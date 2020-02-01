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

package io.github.vampirestudios.vampirelib.utils.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class WoodType {
    public static final WoodType SPRUCE = WoodTypeRegistry.registerVanilla(new WoodType("spruce", Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG));
    public static final WoodType OAK = WoodTypeRegistry.registerVanilla(new WoodType("oak", Blocks.OAK_LEAVES, Blocks.OAK_LOG));
    public static final WoodType BIRCH = WoodTypeRegistry.registerVanilla(new WoodType("birch", Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG));
    public static final WoodType JUNGLE = WoodTypeRegistry.registerVanilla(new WoodType("jungle", Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG));
    public static final WoodType ACACIA = WoodTypeRegistry.registerVanilla(new WoodType("acacia", Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG));
    public static final WoodType DARK_OAK = WoodTypeRegistry.registerVanilla(new WoodType("dark_oak", Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG));
    public static final WoodType[] VANILLA = new WoodType[]{OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK};

    protected Identifier identifier;
    private Block leaves;
    private Block log;

    public WoodType(String name, Block leaves, Block log) {
        this(new Identifier(name), leaves, log);
    }

    public WoodType(Identifier identifier, Block leaves, Block log) {
        this.identifier = identifier;
        this.leaves = leaves;
        this.log = log;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Block getLeaves() {
        return leaves;
    }

    public Block getLog() {
        return log;
    }

}