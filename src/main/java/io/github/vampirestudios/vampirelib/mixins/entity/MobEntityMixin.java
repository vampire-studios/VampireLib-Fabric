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

package io.github.vampirestudios.vampirelib.mixins.entity;

import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    /**
     * @author OliviaTheVampire
     */
    @Overwrite
    public static EquipmentSlot getPreferredEquipmentSlot(ItemStack itemStack_1) {
        Item item_1 = itemStack_1.getItem();
        if (item_1 != Blocks.CARVED_PUMPKIN.asItem() && (!(item_1 instanceof BlockItem) || !(((BlockItem) item_1).getBlock() instanceof AbstractSkullBlock))) {
            if (item_1 instanceof ArmorItem) {
                return ((ArmorItem) item_1).getSlotType();
            } else if (item_1 instanceof ElytraItem) {
                return EquipmentSlot.CHEST;
            } else {
                return item_1 instanceof ShieldItem ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            }
        } else {
            return EquipmentSlot.HEAD;
        }
    }

}
