package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {

    /**
     * @author Olivia
     */
    @Overwrite
    public boolean canInsert(ItemStack stack) {
        return true;
    }

    /**
     * @author Olivia
     */
    @Overwrite
    public static boolean matches(ItemStack stack) {
        return true;
    }
}
