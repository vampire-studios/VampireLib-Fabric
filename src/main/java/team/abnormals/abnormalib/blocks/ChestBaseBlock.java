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

package team.abnormals.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;
import team.abnormals.abnormalib.blocks.entity.ChestBaseBlockEntity;

public class ChestBaseBlock extends ChestBlock {

    private Identifier chestTexture;
    private Identifier doubleChestTexture;
    private Identifier trappedChestTexture;
    private Identifier trappedDoubleChestTexture;

    public ChestBaseBlock() {
        super(FabricBlockSettings.of(Material.STONE).hardness(3.0f).resistance(30.0f).build());
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new ChestBaseBlockEntity(this);
    }

    public Identifier getModelTexture() {
        return chestTexture;
    }

    public ChestBaseBlock setChestTexture(Identifier chestTexture) {
        this.chestTexture = chestTexture;
        return this;
    }

    public Identifier getDoubleModelTexture() {
        return doubleChestTexture;
    }

    public ChestBaseBlock setDoubleChestTexture(Identifier doubleChestTexture) {
        this.doubleChestTexture = doubleChestTexture;
        return this;
    }

    public Identifier getTrappedChestTexture() {
        return trappedChestTexture;
    }

    public ChestBaseBlock setTrappedChestTexture(Identifier trappedChestTexture) {
        this.trappedChestTexture = trappedChestTexture;
        return this;
    }

    public Identifier getTrappedDoubleChestTexture() {
        return trappedDoubleChestTexture;
    }

    public ChestBaseBlock setTrappedDoubleChestTexture(Identifier trappedDoubleChestTexture) {
        this.trappedDoubleChestTexture = trappedDoubleChestTexture;
        return this;
    }

}