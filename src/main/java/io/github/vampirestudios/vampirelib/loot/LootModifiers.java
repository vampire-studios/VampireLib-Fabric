/*
package io.github.vampirestudios.vampirelib.loot;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Suppliers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.init.VRegistries;

public class LootModifiers {
    private static final Codec<DungeonLootEnhancerModifier> DUNGEON_LOOT = Registry.register(VRegistries.LOOT_MODIFIER_SERIALIZERS, "dungeon_loot", DungeonLootEnhancerModifier.CODEC);
    private static final Codec<SmeltingEnchantmentModifier> SMELTING = Registry.register(VRegistries.LOOT_MODIFIER_SERIALIZERS, "smelting", SmeltingEnchantmentModifier.CODEC);
    private static final Codec<WheatSeedsConverterModifier> WHEAT_SEEDS = Registry.register(VRegistries.LOOT_MODIFIER_SERIALIZERS, "wheat_harvest", WheatSeedsConverterModifier.CODEC);
    private static final Codec<SilkTouchTestModifier> SILK_TOUCH = Registry.register(VRegistries.LOOT_MODIFIER_SERIALIZERS, "silk_touch_bamboo", SilkTouchTestModifier.CODEC);
    private static final Enchantment SMELT = Registry.register(Registry.ENCHANTMENT, VampireLib.INSTANCE.identifier("smelt"), new SmelterEnchantment());

    private static class SmelterEnchantment extends Enchantment {
        protected SmelterEnchantment() {
            super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
        }
    }

    */
/**
     * The smelting enchantment causes this modifier to be invoked, via the smelting loot_modifier json
     *//*

    private static class SmeltingEnchantmentModifier extends LootModifier {
        public static final Codec<SmeltingEnchantmentModifier> CODEC = RecordCodecBuilder.create(inst ->
            codecStart(inst).apply(inst, SmeltingEnchantmentModifier::new)
        );

        public SmeltingEnchantmentModifier(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        private static ItemStack smelt(ItemStack stack, LootContext context) {
            return context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel())
                .map(SmeltingRecipe::getResultItem)
                .filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                .orElse(stack);
        }

        @NotNull
        @Override
        public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            ObjectArrayList<ItemStack> ret = new ObjectArrayList<>();
            generatedLoot.forEach((stack) -> ret.add(smelt(stack, context)));
            return ret;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }
    }

    */
/**
     * When harvesting blocks with bamboo, this modifier is invoked, via the silk_touch_bamboo loot_modifier json
     *//*

    private static class SilkTouchTestModifier extends LootModifier {
        public static final Codec<SilkTouchTestModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, SilkTouchTestModifier::new));

        public SilkTouchTestModifier(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @NotNull
        @Override
        public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            ItemStack ctxTool = context.getParamOrNull(LootContextParams.TOOL);
            //return early if silk-touch is already applied (otherwise we'll get stuck in an infinite loop).
            if (ctxTool == null || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, ctxTool) > 0)
                return generatedLoot;
            ItemStack fakeTool = ctxTool.copy();
            fakeTool.enchant(Enchantments.SILK_TOUCH, 1);
            LootContext.Builder builder = new LootContext.Builder(context);
            builder.withParameter(LootContextParams.TOOL, fakeTool);
            LootContext ctx = builder.create(LootContextParamSets.BLOCK);
            LootTable loottable = context.getLevel().getServer().getLootTables().get(context.getParamOrNull(LootContextParams.BLOCK_STATE).getBlock().getLootTable());
            return loottable.getRandomItems(ctx);
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }
    }

    */
/**
     * When harvesting wheat with shears, this modifier is invoked via the wheat_harvest loot_modifier json<br/>
     * This modifier checks how many seeds were harvested and turns X seeds into Y wheat (3:1)
     *//*

    private static class WheatSeedsConverterModifier extends LootModifier {
        public static final Codec<WheatSeedsConverterModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).and(inst.group(
            Codec.INT.fieldOf("numSeeds").forGetter(m -> m.numSeedsToConvert),
            Registry.ITEM.byNameCodec().fieldOf("seedItem").forGetter(m -> m.itemToCheck),
            Registry.ITEM.byNameCodec().fieldOf("replacement").forGetter(m -> m.itemReward)
        )).apply(inst, WheatSeedsConverterModifier::new));

        private final int numSeedsToConvert;
        private final Item itemToCheck;
        private final Item itemReward;

        public WheatSeedsConverterModifier(LootItemCondition[] conditionsIn, int numSeeds, Item itemCheck, Item reward) {
            super(conditionsIn);
            numSeedsToConvert = numSeeds;
            itemToCheck = itemCheck;
            itemReward = reward;
        }

        @NotNull
        @Override
        public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            //
            // Additional conditions can be checked, though as much as possible should be parameterized via JSON data.
            // It is better to write a new ILootCondition implementation than to do things here.
            //
            int numSeeds = 0;
            for (ItemStack stack : generatedLoot) {
                if (stack.getItem() == itemToCheck)
                    numSeeds += stack.getCount();
            }
            if (numSeeds >= numSeedsToConvert) {
                generatedLoot.removeIf(x -> x.getItem() == itemToCheck);
                generatedLoot.add(new ItemStack(itemReward, (numSeeds / numSeedsToConvert)));
                numSeeds = numSeeds % numSeedsToConvert;
                if (numSeeds > 0)
                    generatedLoot.add(new ItemStack(itemToCheck, numSeeds));
            }
            return generatedLoot;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }
    }

    private static class DungeonLootEnhancerModifier extends LootModifier {
        public static final Codec<DungeonLootEnhancerModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(ExtraCodecs.POSITIVE_INT.optionalFieldOf("multiplication_factor", 2).forGetter(m -> m.multiplicationFactor))
            .apply(inst, DungeonLootEnhancerModifier::new)
        );

        private final int multiplicationFactor;

        public DungeonLootEnhancerModifier(final LootItemCondition[] conditionsIn, final int multiplicationFactor) {
            super(conditionsIn);
            this.multiplicationFactor = multiplicationFactor;
        }

        @Override
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            return generatedLoot.stream()
                .map(ItemStack::copy)
                .peek(stack -> stack.setCount(Math.min(stack.getMaxStackSize(), stack.getCount() * this.multiplicationFactor)))
                .collect(Collectors.toCollection(ObjectArrayList::new));
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }
    }
}
*/
