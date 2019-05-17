package team.hollow.abnormalib.utils.generators.recipe;

import net.minecraft.item.ItemStack;

public class ShapelessRecipeIngredient {

    private ItemStack stack;

    public ShapelessRecipeIngredient(ItemStack stack) {
        this.stack = stack;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ItemStack getStack() {
        return stack;
    }

    public static class Builder {

        private ItemStack stack;

        Builder withIngredient(ItemStack stack) {
            this.stack = stack;
            return this;
        }

        public ShapelessRecipeIngredient build() {
            return new ShapelessRecipeIngredient(stack);
        }

    }

}