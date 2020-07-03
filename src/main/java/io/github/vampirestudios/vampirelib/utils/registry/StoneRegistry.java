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

import io.github.vampirestudios.vampirelib.blocks.SlabBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.StairsBaseBlock;
import io.github.vampirestudios.vampirelib.blocks.WallBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class StoneRegistry {

    public Identifier name;

    public Block raw;
    public Block slab;
    public Block stairs;
    public Block wall;

    public Block cobblestone;
    public Block cobblestoneSlab;
    public Block cobblestoneStairs;
    public Block cobblestoneWall;

    public Block polished;
    public Block polishedSlab;
    public Block polishedStairs;
    public Block polishedWall;

    public Block bricks;
    public Block brickSlab;
    public Block brickStairs;
    public Block brickWall;

    public Block crackedBricks;
    public Block crackedBricksWall;
    public Block crackedBricksStairs;
    public Block crackedBricksSlab;

    public Block mossyBricks;
    public Block mossyBricksWall;
    public Block mossyBricksStairs;
    public Block mossyBricksSlab;

    public StoneRegistry(Identifier name) {
        this.name = name;
    }

    public static StoneRegistry.Builder of(Identifier name) {
        return new Builder().of(name);
    }

    public Block getRaw() {
        return raw;
    }

    public Block getSlab() {
        return slab;
    }

    public Block getStairs() {
        return stairs;
    }

    public Block getWall() {
        return wall;
    }

    public Block getCobblestone() {
        return cobblestone;
    }

    public Block getCobblestoneSlab() {
        return cobblestoneSlab;
    }

    public Block getCobblestoneStairs() {
        return cobblestoneStairs;
    }

    public Block getCobblestoneWall() {
        return cobblestoneWall;
    }

    public Block getPolished() {
        return polished;
    }

    public Block getPolishedSlab() {
        return polishedSlab;
    }

    public Block getPolishedStairs() {
        return polishedStairs;
    }

    public Block getPolishedWall() {
        return polishedWall;
    }

    public Block getBricks() {
        return bricks;
    }

    public Block getBrickSlab() {
        return brickSlab;
    }

    public Block getBrickStairs() {
        return brickStairs;
    }

    public Block getBrickWall() {
        return brickWall;
    }

    public Block getCrackedBricks() {
        return crackedBricks;
    }

    public Block getCrackedBricksWall() {
        return crackedBricksWall;
    }

    public Block getCrackedBricksStairs() {
        return crackedBricksStairs;
    }

    public Block getCrackedBricksSlab() {
        return crackedBricksSlab;
    }

    public Block getMossyBricks() {
        return mossyBricks;
    }

    public Block getMossyBricksWall() {
        return mossyBricksWall;
    }

    public Block getMossyBricksStairs() {
        return mossyBricksStairs;
    }

    public Block getMossyBricksSlab() {
        return mossyBricksSlab;
    }

    public static class Builder {

        public Identifier name;
        private StoneRegistry stoneRegistry;
        private RegistryHelper registryHelper;

        public Builder of(Identifier name) {
            this.name = name;
            stoneRegistry = new StoneRegistry(name);
            registryHelper = RegistryHelper.createRegistryHelper(name.getNamespace());
            return this;
        }

        public Builder raw() {
            stoneRegistry.raw = registryHelper.registerBlock(new Block(Block.Settings.copy(Blocks.STONE)), name.getPath());
            return this;
        }

        public Builder raw(Block raw) {
            stoneRegistry.raw = raw;
            return this;
        }

        public Builder slab() {
            stoneRegistry.slab = registryHelper.registerBlock(new SlabBaseBlock(Block.Settings.copy(Blocks.STONE_SLAB)),
                    name.getPath() + "_slab");
            return this;
        }

        public Builder slab(Block slab) {
            stoneRegistry.slab = slab;
            return this;
        }

        public Builder stairs() {
            stoneRegistry.stairs = registryHelper.registerBlock(new StairsBaseBlock(stoneRegistry.raw.getDefaultState()),
                    name.getPath() + "_stairs");
            return this;
        }

        public Builder stairs(Block stairs) {
            stoneRegistry.stairs = stairs;
            return this;
        }

        public Builder wall() {
            stoneRegistry.wall = registryHelper.registerBlock(new WallBaseBlock(Block.Settings.copy(Blocks.COBBLESTONE_WALL)),
                    name.getPath() + "_wall");
            return this;
        }

        public Builder wall(Block wall) {
            stoneRegistry.wall = wall;
            return this;
        }

        public Builder cobblestone() {
            stoneRegistry.cobblestone = registryHelper.registerBlock(new Block(Block.Settings.copy(Blocks.COBBLESTONE)),
                    name.getPath() + "_cobblestone");
            return this;
        }

        public Builder cobblestone(Block cobblestone) {
            stoneRegistry.cobblestone = cobblestone;
            return this;
        }

        public Builder cobblestoneSlab() {
            stoneRegistry.cobblestoneSlab = registryHelper.registerBlock(new SlabBaseBlock(Block.Settings.copy(Blocks.COBBLESTONE_SLAB)),
                    name.getPath() + "_cobblestone_slab");
            return this;
        }

        public Builder cobblestoneSlab(Block cobblestoneSlab) {
            stoneRegistry.cobblestoneSlab = cobblestoneSlab;
            return this;
        }

        public Builder cobblestoneStairs() {
            stoneRegistry.cobblestoneStairs = registryHelper.registerBlock(new StairsBaseBlock(stoneRegistry.cobblestone.getDefaultState()),
                    name.getPath() + "_cobblestone_stairs");
            return this;
        }

        public Builder cobblestoneStairs(Block cobblestoneStairs) {
            stoneRegistry.cobblestoneStairs = cobblestoneStairs;
            return this;
        }

        public Builder cobblestoneWall() {
            stoneRegistry.cobblestoneWall = registryHelper.registerBlock(new WallBaseBlock(Block.Settings.copy(Blocks.COBBLESTONE_WALL)),
                    name.getPath() + "_cobblestone_wall");
            return this;
        }

        public Builder cobblestoneWall(Block cobblestoneWall) {
            stoneRegistry.cobblestoneWall = cobblestoneWall;
            return this;
        }

        public Builder polished(Block polished) {
            stoneRegistry.polished = polished;
            return this;
        }

        public Builder polished() {
            stoneRegistry.polished = registryHelper.registerBlock(new Block(Block.Settings.copy(Blocks.COBBLESTONE)),
                    "polished_" + name.getPath());
            return this;
        }

        public Builder polishedSlab(Block polishedSlab) {
            stoneRegistry.polishedSlab = polishedSlab;
            return this;
        }

        public Builder polishedSlab() {
            stoneRegistry.polishedSlab = registryHelper.registerBlock(new SlabBaseBlock(Block.Settings.copy(Blocks.STONE_SLAB)),
                    "polished_" + name.getPath() + "_slab");
            return this;
        }

        public Builder polishedStairs(Block polishedStairs) {
            stoneRegistry.polishedStairs = polishedStairs;
            return this;
        }

        public Builder polishedStairs() {
            stoneRegistry.polishedStairs = registryHelper.registerBlock(new StairsBaseBlock(stoneRegistry.polished.getDefaultState()),
                    "polished_" + name.getPath() + "_stairs");
            return this;
        }

        public Builder polishedWall(Block polishedWall) {
            stoneRegistry.polishedWall = polishedWall;
            return this;
        }

        public Builder polishedWall() {
            stoneRegistry.polishedWall = registryHelper.registerBlock(new WallBaseBlock(Block.Settings.copy(Blocks.COBBLESTONE_WALL)),
                    "polished_" + name.getPath() + "_wall");
            return this;
        }

        public Builder bricks() {
            stoneRegistry.bricks = registryHelper.registerBlock(new Block(Block.Settings.copy(Blocks.COBBLESTONE)),
                    name.getPath() + "_bricks");
            return this;
        }

        public Builder bricks(Block bricks) {
            stoneRegistry.bricks = bricks;
            return this;
        }

        public Builder brickSlab(Block brickSlab) {
            stoneRegistry.brickSlab = brickSlab;
            return this;
        }

        public Builder brickSlab() {
            stoneRegistry.brickSlab = registryHelper.registerBlock(new SlabBaseBlock(Block.Settings.copy(Blocks.STONE_SLAB)),
                    name.getPath() + "_brick_slab");
            return this;
        }

        public Builder brickStairs(Block brickStairs) {
            stoneRegistry.brickStairs = brickStairs;
            return this;
        }

        public Builder brickStairs() {
            stoneRegistry.brickStairs = registryHelper.registerBlock(new StairsBaseBlock(stoneRegistry.bricks.getDefaultState()),
                    name.getPath() + "_brick_stairs");
            return this;
        }

        public Builder brickWall(Block brickWall) {
            stoneRegistry.brickWall = brickWall;
            return this;
        }

        public Builder brickWall() {
            stoneRegistry.brickWall = registryHelper.registerBlock(new WallBaseBlock(Block.Settings.copy(Blocks.BRICK_WALL)),
                    name.getPath() + "_brick_wall");
            return this;
        }

        public Builder crackedBricks(Block crackedBricks) {
            stoneRegistry.crackedBricks = crackedBricks;
            return this;
        }

        public Builder crackedBricks() {
            stoneRegistry.crackedBricks = registryHelper.registerBlock(new Block(Block.Settings.copy(Blocks.COBBLESTONE)),
                    "cracked_" + name.getPath() + "_bricks");
            return this;
        }

        public Builder crackedBricksWall(Block crackedBricksWall) {
            stoneRegistry.crackedBricksWall = crackedBricksWall;
            return this;
        }

        public Builder crackedBricksWall() {
            stoneRegistry.crackedBricksWall = registryHelper.registerBlock(new WallBaseBlock(Block.Settings.copy(Blocks.COBBLESTONE_WALL)),
                    "cracked_" + name.getPath() + "_bricks_wall");
            return this;
        }

        public Builder crackedBricksStairs(Block crackedBricksStairs) {
            stoneRegistry.crackedBricksStairs = crackedBricksStairs;
            return this;
        }

        public Builder crackedBricksStairs() {
            stoneRegistry.crackedBricksStairs = registryHelper.registerBlock(new StairsBaseBlock(stoneRegistry.cobblestone.getDefaultState()),
                    "cracked_" + name.getPath() + "_bricks_stairs");
            return this;
        }

        public Builder crackedBricksSlab(Block crackedBricksSlab) {
            stoneRegistry.crackedBricksSlab = crackedBricksSlab;
            return this;
        }

        public Builder crackedBricksSlab() {
            stoneRegistry.crackedBricksSlab = registryHelper.registerBlock(new SlabBaseBlock(Block.Settings.copy(Blocks.STONE_SLAB)),
                    "cracked_" + name.getPath() + "_bricks_slab");
            return this;
        }

        public Builder mossyBricks(Block mossyBricks) {
            stoneRegistry.mossyBricks = mossyBricks;
            return this;
        }

        public Builder mossyBricks() {
            stoneRegistry.bricks = registryHelper.registerBlock(new Block(Block.Settings.copy(Blocks.COBBLESTONE)),
                    "mossy_" + name.getPath() + "_bricks");
            return this;
        }

        public Builder mossyBricksSlab(Block mossyBricksSlab) {
            stoneRegistry.mossyBricksSlab = mossyBricksSlab;
            return this;
        }

        public Builder mossyBricksSlab() {
            stoneRegistry.mossyBricksSlab = registryHelper.registerBlock(new SlabBaseBlock(Block.Settings.copy(Blocks.STONE_SLAB)),
                    "mossy_" + name.getPath() + "_bricks_slab");
            return this;
        }

        public Builder mossyBricksStairs(Block mossyBricksStairs) {
            stoneRegistry.mossyBricksStairs = mossyBricksStairs;
            return this;
        }

        public Builder mossyBricksStairs() {
            stoneRegistry.mossyBricksStairs = registryHelper.registerBlock(new StairsBaseBlock(stoneRegistry.cobblestone.getDefaultState()),
                    "mossy_" + name.getPath() + "_bricks_stairs");
            return this;
        }

        public Builder mossyBricksWall(Block mossyBricksWall) {
            stoneRegistry.mossyBricksWall = mossyBricksWall;
            return this;
        }

        public Builder mossyBricksWall() {
            stoneRegistry.mossyBricksWall = registryHelper.registerBlock(new WallBaseBlock(Block.Settings.copy(Blocks.COBBLESTONE_WALL)),
                    "mossy_" + name.getPath() + "_bricks_wall");
            return this;
        }

        public Builder all() {
            return this.raw().slab().stairs().wall()
                    .cobblestone().cobblestoneSlab().cobblestoneStairs().cobblestoneWall()
                    .polished().polishedSlab().polishedStairs().polishedWall()
                    .bricks().brickSlab().brickStairs().brickWall()
                    .crackedBricks().crackedBricksSlab().crackedBricksStairs().crackedBricksWall()
                    .mossyBricks().mossyBricksSlab().mossyBricksStairs().mossyBricksWall();
        }

        public StoneRegistry build() {
            return stoneRegistry;
        }

    }

}
