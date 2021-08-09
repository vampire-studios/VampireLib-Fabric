package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class BrewingStandScreenHandlerPotionSlotMixin {
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
