package team.abnormals.abnormalib.old.utils.generators.recipe;

import net.minecraft.item.ItemStack;

public class ShapedRecipeIngredient {

    private String pattern;
    private ItemStack stack;

    public ShapedRecipeIngredient(String pattern, ItemStack stack) {
        this.pattern = pattern;
        this.stack = stack;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPattern() {
        return pattern;
    }

    public ItemStack getStack() {
        return stack;
    }

    public static class Builder {

        private String pattern;
        private ItemStack stack;

        public Builder withIngredient(String pattern, ItemStack stack) {
            this.pattern = pattern;
            this.stack = stack;
            return this;
        }

        public ShapedRecipeIngredient build() {
            return new ShapedRecipeIngredient(pattern, stack);
        }

    }

}