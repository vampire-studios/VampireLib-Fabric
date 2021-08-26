package io.github.vampirestudios.vampirelib.mixins;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {

    /**
     * @author Olivia
     */
    @Overwrite
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    /**
     * @author Olivia
     */
    @Overwrite
    public static boolean canCraft(DefaultedList<ItemStack> stack) {
        return true;
    }
}
