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
