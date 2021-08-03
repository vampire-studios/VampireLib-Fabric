package io.github.vampirestudios.vampirelib.recipe.brewing;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.init.VTags;

public record MixingBrewingRecipe(@NotNull Identifier id,
                                  @NotNull Potion base,
                                  @NotNull Ingredient reagent,
                                  @NotNull Potion result) implements IBrewingRecipe {
    @Override
    public ItemStack craft(final IBrewingContainer container) {
        return PotionUtil.setPotion(new ItemStack(container.base().getItem()), result());
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return id();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VampireLib.MIXING_BREWING_SERIALIZER;
    }

    @Override
    public boolean isReagent(final ItemStack reagent) {
        return reagent().test(reagent);
    }

    @Override
    public boolean isBase(final ItemStack base) {
        return PotionUtil.getPotion(base) == base() && base.isIn(VTags.Items.POTION_CONTAINERS);
    }

    public static class Serializer implements RecipeSerializer<MixingBrewingRecipe> {

        @Override
        public MixingBrewingRecipe read(final Identifier p_199425_1_, final JsonObject json) {
            Potion base = Registry.POTION.get(new Identifier(json.get("base").getAsString()));
            Ingredient reagent = Ingredient.fromJson(json.getAsJsonObject("reagent"));
            Potion result = Registry.POTION.get(new Identifier(json.get("result").getAsString()));
            return new MixingBrewingRecipe(p_199425_1_, base, reagent, result);
        }

        @Nullable
        @Override
        public MixingBrewingRecipe read(final Identifier p_199426_1_, final PacketByteBuf p_199426_2_) {
            Potion base = Registry.POTION.get(p_199426_2_.readIdentifier());
            Ingredient reagent = Ingredient.fromPacket(p_199426_2_);
            Potion result = Registry.POTION.get(p_199426_2_.readIdentifier());
            return new MixingBrewingRecipe(p_199426_1_, base, reagent, result);
        }

        @Override
        public void write(final PacketByteBuf p_199427_1_, final MixingBrewingRecipe p_199427_2_) {
            p_199427_1_.writeIdentifier(Registry.POTION.getId(p_199427_2_.base()));
            p_199427_2_.reagent().write(p_199427_1_);
            p_199427_1_.writeIdentifier(Registry.POTION.getId(p_199427_2_.result()));
        }
    }
}