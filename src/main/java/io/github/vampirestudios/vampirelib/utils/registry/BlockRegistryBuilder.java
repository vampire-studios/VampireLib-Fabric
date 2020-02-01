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

package io.github.vampirestudios.vampirelib.utils.registry;

import io.github.vampirestudios.vampirelib.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class BlockRegistryBuilder {

    /**
     * Returns a new instance of this class
     */
    private static final ThreadLocal<BlockRegistryBuilder> INSTANCE = ThreadLocal.withInitial(BlockRegistryBuilder::new);

    /**
     * The base block of this registry
     */
    private static Block baseBlock;

    /**
     * Name that will be used as a base for all of the blocks
     */
    private static Identifier name;

    /**
     * @param nameIn  The name that will be used as a base for all blocks
     * @param blockIn the base block which will be used for some blocks
     * @return a new instance of this class
     */
    public static BlockRegistryBuilder getInstance(Identifier nameIn, Block blockIn) {
        name = nameIn;
        baseBlock = blockIn;
        return INSTANCE.get();
    }

    /**
     * Adds a new slab block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder slab() {
        Block slab = new SlabBaseBlock(Block.Settings.copy(baseBlock));
        RegistryUtils.register(slab, new Identifier(name.getNamespace(), name.getPath() + "_slab"), ItemGroup.BUILDING_BLOCKS);
        return this;
    }

    /**
     * Adds a new stair block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder stair() {
        Block stair = new StairsBaseBlock(baseBlock.getDefaultState());
        RegistryUtils.register(stair, new Identifier(name.getNamespace(), name.getPath() + "_stairs"), ItemGroup.BUILDING_BLOCKS);
        return this;
    }

    /**
     * Adds a new fence block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder fence() {
        Block fence = new FenceBlock(Block.Settings.copy(baseBlock));
        RegistryUtils.register(fence, new Identifier(name.getNamespace(), name.getPath() + "_fence"));
        return this;
    }

    /**
     * Adds a new fence gate block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder fenceGate() {
        RegistryUtils.register(new FenceGateBlock(Block.Settings.copy(baseBlock)), new Identifier(name.getNamespace(), name.getPath() + "_fence_gate"), ItemGroup.REDSTONE);
        return this;
    }

    /**
     * Adds a new wall block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder wall() {
        Block wall = new WallBaseBlock(Block.Settings.copy(baseBlock));
        RegistryUtils.register(wall, new Identifier(name.getNamespace(), name.getPath() + "_wall"), ItemGroup.DECORATIONS);
        return this;
    }

    /**
     * Adds a new button block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder button(boolean wooden) {
        Block button = new ButtonBaseBlock(wooden, Block.Settings.copy(baseBlock));
        RegistryUtils.register(button, new Identifier(name.getNamespace(), name.getPath() + "_button"), ItemGroup.REDSTONE);
        return this;
    }

    /**
     * Adds a new pressure plate block based on the base block properties
     *
     * @return an instance of this class
     */
    public BlockRegistryBuilder pressurePlate(PressurePlateBlock.ActivationRule type) {
        RegistryUtils.register(new PressurePlateBaseBlock(Block.Settings.copy(baseBlock), type),
                new Identifier(name.getNamespace(), name.getPath() + "_pressure_plate"), ItemGroup.REDSTONE);
        return this;
    }

}
