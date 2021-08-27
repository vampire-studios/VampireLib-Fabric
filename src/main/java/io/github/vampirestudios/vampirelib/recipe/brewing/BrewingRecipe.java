package io.github.vampirestudios.vampirelib.recipe.brewing;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import io.github.vampirestudios.vampirelib.VampireLib;

public record BrewingRecipe(@NotNull ResourceLocation id,
                            @NotNull Ingredient base,
                            @NotNull Ingredient reagent,
                            @NotNull ItemStack result) implements IBrewingRecipe {

    @Override
    public ItemStack assemble(final IBrewingContainer brewingContainer) {
        return result().copy();
    }

    @Override
    public ItemStack getResultItem() {
        return result().copy();
    }

    @Override
    public ResourceLocation getId() {
        return id();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VampireLib.BREWING_SERIALIZER;
    }

    @Override
    public boolean isBase(final ItemStack base) {
        return base().test(base);
    }

    @Override
    public boolean isReagent(final ItemStack reagent) {
        return reagent().test(reagent);
    }

    public static class Serializer implements RecipeSerializer<BrewingRecipe> {

        @Override
        public BrewingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "base"));
            Ingredient reagent = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "reagent"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            return new BrewingRecipe(resourceLocation, base, reagent, result);
        }


        @Override
        public BrewingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient base = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient reagent = Ingredient.fromNetwork(friendlyByteBuf);
            ItemStack result = friendlyByteBuf.readItem();
            return new BrewingRecipe(resourceLocation, base, reagent, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, BrewingRecipe recipe) {
            recipe.base().toNetwork(friendlyByteBuf);
            recipe.reagent().toNetwork(friendlyByteBuf);
            friendlyByteBuf.writeItem(recipe.result());
        }
    }
}