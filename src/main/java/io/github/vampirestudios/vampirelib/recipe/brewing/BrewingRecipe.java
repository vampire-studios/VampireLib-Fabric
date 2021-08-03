package io.github.vampirestudios.vampirelib.recipe.brewing;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import io.github.vampirestudios.vampirelib.VampireLib;

public record BrewingRecipe(@NotNull Identifier id,
                            @NotNull Ingredient base,
                            @NotNull Ingredient reagent,
                            @NotNull ItemStack result) implements IBrewingRecipe {

    @Override
    public ItemStack craft(final IBrewingContainer brewingContainer) {
        return result().copy();
    }

    @Override
    public ItemStack getOutput() {
        return result().copy();
    }

    @Override
    public Identifier getId() {
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
        public BrewingRecipe read(final Identifier id, final JsonObject json) {
            Ingredient base = Ingredient.fromJson(JsonHelper.getObject(json, "base"));
            Ingredient reagent = Ingredient.fromJson(JsonHelper.getObject(json, "reagent"));
            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            return new BrewingRecipe(id, base, reagent, result);
        }

        @Nullable
        @Override
        public BrewingRecipe read(final Identifier id, final PacketByteBuf buf) {
            Ingredient base = Ingredient.fromPacket(buf);
            Ingredient reagent = Ingredient.fromPacket(buf);
            ItemStack result = buf.readItemStack();
            return new BrewingRecipe(id, base, reagent, result);
        }

        @Override
        public void write(final PacketByteBuf buf, final BrewingRecipe recipe) {
            recipe.base().write(buf);
            recipe.reagent().write(buf);
            buf.writeItemStack(recipe.result());
        }
    }
}