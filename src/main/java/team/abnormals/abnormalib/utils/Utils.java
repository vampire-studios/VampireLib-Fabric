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

package team.abnormals.abnormalib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import team.abnormals.abnormalib.entity.FlyingLanternEntity;

public class Utils {

    public static ItemStack dispense(BlockPointer blockPointer, ItemStack itemStack) {
        Direction direction = blockPointer.getBlockState().get(DispenserBlock.FACING);
        Position output = DispenserBlock.getOutputLocation(blockPointer);

        FlyingLanternEntity entity = FlyingLanternEntity.create(blockPointer.getWorld(), output.getX(), output.getY(), output.getZ(), Block.getBlockFromItem(itemStack.getItem()).getDefaultState());
        entity.setVelocity(direction.getOffsetX() * 0.03, 0, direction.getOffsetZ() * 0.03);
        blockPointer.getWorld().spawnEntity(entity);

        itemStack.subtractAmount(1);
        return itemStack;
    }

}