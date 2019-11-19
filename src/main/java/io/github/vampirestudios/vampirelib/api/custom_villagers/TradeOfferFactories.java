/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.api.custom_villagers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.github.vampirestudios.vampirelib.VampireLib;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.InfoEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.*;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerType;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class TradeOfferFactories {

    public static class ProcessItemFactory implements TradeOffers.Factory {
        private final ItemStack secondBuy;
        private final int secondCount;
        private final int price;
        private final ItemStack sell;
        private final int sellCount;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public ProcessItemFactory(ItemConvertible itemProvider_1, int int_1, Item item_1, int int_2, int int_3, int int_4) {
            this(itemProvider_1, int_1, 1, item_1, int_2, int_3, int_4);
        }

        public ProcessItemFactory(ItemConvertible itemProvider_1, int int_1, int int_2, Item item_1, int int_3, int int_4, int int_5) {
            this.secondBuy = new ItemStack(itemProvider_1);
            this.secondCount = int_1;
            this.price = int_2;
            this.sell = new ItemStack(item_1);
            this.sellCount = int_3;
            this.maxUses = int_4;
            this.experience = int_5;
            this.multiplier = 0.05F;
        }


        public TradeOffer create(Entity entity_1, Random random_1) {
            return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.secondBuy.getItem(), this.secondCount), new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class SellMapFactory implements TradeOffers.Factory {
        private final int price;
        private final String structure;
        private final MapIcon.Type iconType;
        private final int maxUses;
        private final int experience;

        public SellMapFactory(int int_1, String string_1, MapIcon.Type mapIcon$Type_1, int int_2, int int_3) {
            this.price = int_1;
            this.structure = string_1;
            this.iconType = mapIcon$Type_1;
            this.maxUses = int_2;
            this.experience = int_3;
        }


        public TradeOffer create(Entity entity_1, Random random_1) {
            if (!(entity_1.world instanceof ServerWorld)) {
                return null;
            } else {
                ServerWorld serverWorld_1 = (ServerWorld) entity_1.world;
                BlockPos blockPos_1 = serverWorld_1.locateStructure(this.structure, new BlockPos(entity_1), 100, true);
                if (blockPos_1 != null) {
                    ItemStack itemStack_1 = FilledMapItem.createMap(serverWorld_1, blockPos_1.getX(), blockPos_1.getZ(), (byte) 2, true, true);
                    FilledMapItem.copyMap(serverWorld_1, itemStack_1);
                    MapState.addDecorationsTag(itemStack_1, blockPos_1, "+", this.iconType);
                    itemStack_1.setCustomName(new TranslatableText("filled_map." + this.structure.toLowerCase(Locale.ROOT)));
                    return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(Items.COMPASS), itemStack_1, this.maxUses, this.experience, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }

    public static class EnchantBookFactory implements TradeOffers.Factory {
        private final int experience;

        public EnchantBookFactory(int int_1) {
            this.experience = int_1;
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            Enchantment enchantment_1 = Registry.ENCHANTMENT.getRandom(random_1);
            int int_1 = MathHelper.nextInt(random_1, enchantment_1.getMinimumLevel(), enchantment_1.getMaximumLevel());
            ItemStack itemStack_1 = EnchantedBookItem.forEnchantment(new InfoEnchantment(enchantment_1, int_1));
            int int_2 = 2 + random_1.nextInt(5 + int_1 * 10) + 3 * int_1;
            if (enchantment_1.isTreasure()) {
                int_2 *= 2;
            }

            if (int_2 > 64) {
                int_2 = 64;
            }

            return new TradeOffer(new ItemStack(Items.EMERALD, int_2), new ItemStack(Items.BOOK), itemStack_1, 6, this.experience, 0.2F);
        }
    }

    public static class TwoItemsForOneItemFactory implements TradeOffers.Factory {
        private final ItemStack buyItem, buyItem2, sellItem;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public TwoItemsForOneItemFactory(ItemStack buyItem, ItemStack buyItem2, ItemStack sellItem, int maxUses, int experience) {
            this.buyItem = buyItem;
            this.buyItem2 = buyItem2;
            this.sellItem = sellItem;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            return new TradeOffer(buyItem, buyItem2, sellItem, maxUses, experience, multiplier);
        }
    }

    static class SellDyedArmorFactory implements TradeOffers.Factory {
        private final Item sell;
        private final int price;
        private final int maxUses;
        private final int experience;

        public SellDyedArmorFactory(Item item_1, int int_1) {
            this(item_1, int_1, 6, 1);
        }

        public SellDyedArmorFactory(Item item_1, int int_1, int int_2, int int_3) {
            this.sell = item_1;
            this.price = int_1;
            this.maxUses = int_2;
            this.experience = int_3;
        }

        private static DyeItem getDye(Random random_1) {
            return DyeItem.byColor(DyeColor.byId(random_1.nextInt(16)));
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(Items.EMERALD, this.price);
            ItemStack itemStack_2 = new ItemStack(this.sell);
            if (this.sell instanceof DyeableArmorItem) {
                List<DyeItem> list_1 = Lists.newArrayList();
                list_1.add(getDye(random_1));
                if (random_1.nextFloat() > 0.7F) {
                    list_1.add(getDye(random_1));
                }

                if (random_1.nextFloat() > 0.8F) {
                    list_1.add(getDye(random_1));
                }

                itemStack_2 = DyeableItem.blendAndSetColor(itemStack_2, list_1);
            }

            return new TradeOffer(itemStack_1, itemStack_2, this.maxUses, this.experience, 0.2F);
        }
    }

    public static class SellPotionHoldingItemFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int sellCount;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final Item secondBuy;
        private final int secondCount;
        private final float priceMultiplier;

        public SellPotionHoldingItemFactory(Item item_1, int int_1, Item item_2, int int_2, int int_3, int int_4, int int_5) {
            this.sell = new ItemStack(item_2);
            this.price = int_3;
            this.maxUses = int_4;
            this.experience = int_5;
            this.secondBuy = item_1;
            this.secondCount = int_1;
            this.sellCount = int_2;
            this.priceMultiplier = 0.05F;
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(Items.EMERALD, this.price);
            List<Potion> list_1 = Registry.POTION.stream().filter((potion_1x) ->
                    !potion_1x.getEffects().isEmpty() && BrewingRecipeRegistry.isBrewable(potion_1x)).collect(Collectors.toList());
            Potion potion_1 = list_1.get(random_1.nextInt(list_1.size()));
            ItemStack itemStack_2 = PotionUtil.setPotion(new ItemStack(this.sell.getItem(), this.sellCount), potion_1);
            return new TradeOffer(itemStack_1, new ItemStack(this.secondBuy, this.secondCount), itemStack_2, this.maxUses, this.experience, this.priceMultiplier);
        }
    }

    public static class SellEnchantedToolFactory implements TradeOffers.Factory {
        private final ItemStack tool;
        private final int basePrice;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellEnchantedToolFactory(Item item_1, int int_1, int int_2, int int_3) {
            this(item_1, int_1, int_2, int_3, 0.05F);
        }

        public SellEnchantedToolFactory(Item item_1, int int_1, int int_2, int int_3, float float_1) {
            this.tool = new ItemStack(item_1);
            this.basePrice = int_1;
            this.maxUses = int_2;
            this.experience = int_3;
            this.multiplier = float_1;
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            int int_1 = 5 + random_1.nextInt(15);
            ItemStack itemStack_1 = EnchantmentHelper.enchant(random_1, new ItemStack(this.tool.getItem()), int_1, false);
            int int_2 = Math.min(this.basePrice + int_1, 64);
            ItemStack itemStack_2 = new ItemStack(Items.EMERALD, int_2);
            return new TradeOffer(itemStack_2, itemStack_1, this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class SellSuspiciousStewFactory implements TradeOffers.Factory {
        final StatusEffect effect;
        final int duration;
        final int experience;
        private final float multiplier;

        public SellSuspiciousStewFactory(StatusEffect statusEffect_1, int int_1, int int_2) {
            this.effect = statusEffect_1;
            this.duration = int_1;
            this.experience = int_2;
            this.multiplier = 0.05F;
        }


        public TradeOffer create(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.addEffectToStew(itemStack_1, this.effect, this.duration);
            return new TradeOffer(new ItemStack(Items.EMERALD, 1), itemStack_1, 6, this.experience, this.multiplier);
        }
    }

    public static class SellItemFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory(Block sell, int price, int count, int maxUses, int experience) {
            this(new ItemStack(sell), price, count, maxUses, experience);
        }

        public SellItemFactory(Item sell, int price, int count, int experience) {
            this(new ItemStack(sell), price, count, 6, experience);
        }

        public SellItemFactory(Item sell, int price, int count, int maxUses, int experience) {
            this(new ItemStack(sell), price, count, maxUses, experience);
        }

        public SellItemFactory(ItemStack sell, int price, int count, int maxUses, int experience) {
            this(sell, price, count, maxUses, experience, 0.05F);
        }

        public SellItemFactory(ItemStack sell, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = sell;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class BuyForOneEmeraldFactory implements TradeOffers.Factory {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemConvertible itemProvider_1, int price, int maxUses, int experience) {
            this.buy = itemProvider_1.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        public TradeOffer create(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(this.buy, this.price);
            return new TradeOffer(itemStack_1, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class TypeAwareBuyForOneEmeraldFactory implements TradeOffers.Factory {
        private final Map<VillagerType, Item> map;
        private final int count;
        private final int maxUses;
        private final int experience;

        public TypeAwareBuyForOneEmeraldFactory(int int_1, int int_2, int int_3, Map<VillagerType, Item> map_1) {
            Registry.VILLAGER_TYPE.stream().filter((villagerType_1) -> !map_1.containsKey(villagerType_1)).findAny().ifPresent((villagerType_1) -> {
                throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getId(villagerType_1));
            });
            this.map = map_1;
            this.count = int_1;
            this.maxUses = int_2;
            this.experience = int_3;
        }


        public TradeOffer create(Entity entity_1, Random random_1) {
            if (entity_1 instanceof VillagerDataContainer) {
                ItemStack itemStack_1 = new ItemStack(this.map.get(((VillagerDataContainer) entity_1).getVillagerData().getType()), this.count);
                return new TradeOffer(itemStack_1, new ItemStack(Items.EMERALD), this.maxUses, this.experience, 0.05F);
            } else {
                return null;
            }
        }
    }

    public static class BuyItemFactory implements TradeOffers.Factory {
        private final Item item;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyItemFactory(ItemConvertible itemProvider, int price, int maxUses, int experience) {
            this.item = itemProvider.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Override
        public TradeOffer create(Entity var1, Random var2) {
            ItemStack itemStack_1 = new ItemStack(this.item, this.price);
            return new TradeOffer(itemStack_1, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class TypeAwareBuyItemFactory implements TradeOffers.Factory, ConditionalTradeFactory {
        private final Map<VillagerType, Item> itemMap;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public TypeAwareBuyItemFactory(Map<VillagerType, Item> itemMap, int price, int maxUses, int experience) {
            this.itemMap = itemMap;
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(itemMap.get(((VillagerEntity) entity).getVillagerData().getType())), new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }

        @Override
        public boolean vl$isApplicable(AbstractTraderEntity entity, Random random) {
            return itemMap.containsKey(VillagerTypeRegistry.getVillagerTypeForBiome(entity.world.getBiomeAccess().getBiome(entity.getBlockPos())));
        }
    }

    public static class TradingUtils {

        public static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> immutableMap_1) {
            return new Int2ObjectOpenHashMap<>(immutableMap_1);
        }

        public static class PriceRange {
            final int lower;
            final int range;

            public PriceRange(int int_1, int int_2) {
                this.lower = int_1;
                this.range = 1 + Math.max(0, int_2 - int_1);
                if (int_2 < int_1) {
                    VampireLib.LOGGER.warn("PriceRange({}, {}) invalid, {} smaller than {}", int_1, int_2, int_2, int_1);
                }

            }

            int getPrice(Random random_1) {
                return this.lower + random_1.nextInt(this.range);
            }
        }

    }

}
