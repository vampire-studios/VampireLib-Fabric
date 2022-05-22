/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.village;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class TradeOfferFactories {

    public static class ProcessItemFactory implements VillagerTrades.ItemListing {
        private final ItemStack secondBuy;
        private final int secondCount;
        private final int price;
        private final ItemStack sell;
        private final int sellCount;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public ProcessItemFactory(ItemLike itemProvider_1, int int_1, Item item_1, int int_2, int int_3, int int_4) {
            this(itemProvider_1, int_1, 1, item_1, int_2, int_3, int_4);
        }

        public ProcessItemFactory(ItemLike itemProvider_1, int int_1, int int_2, Item item_1, int int_3, int int_4, int int_5) {
            this.secondBuy = new ItemStack(itemProvider_1);
            this.secondCount = int_1;
            this.price = int_2;
            this.sell = new ItemStack(item_1);
            this.sellCount = int_3;
            this.maxUses = int_4;
            this.experience = int_5;
            this.multiplier = 0.05F;
        }


        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.secondBuy.getItem(), this.secondCount), new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class TreasureMapForEmeralds implements VillagerTrades.ItemListing {
        private final int price;
        private final TagKey<ConfiguredStructureFeature<?, ?>> structure;
        private final String displayName;
        private final MapDecoration.Type iconType;
        private final int maxUses;
        private final int experience;

        public TreasureMapForEmeralds(int price, TagKey<ConfiguredStructureFeature<?, ?>> structure, String displayName, MapDecoration.Type iconType, int maxUses,
                                      int experience) {
            this.price = price;
            this.structure = structure;
            this.displayName = displayName;
            this.iconType = iconType;
            this.maxUses = maxUses;
            this.experience = experience;
        }


        public MerchantOffer getOffer(Entity entity_1, @NotNull Random random_1) {
            if (!(entity_1.level instanceof ServerLevel serverWorld_1)) {
                return null;
            } else {
                BlockPos blockPos_1 = serverWorld_1.findNearestMapFeature(this.structure, new BlockPos(entity_1.blockPosition()), 100, true);
                if (blockPos_1 != null) {
                    ItemStack itemStack_1 = MapItem.create(serverWorld_1, blockPos_1.getX(), blockPos_1.getZ(), (byte) 2, true, true);
                    MapItem.lockMap(serverWorld_1, itemStack_1);
                    MapItemSavedData.addTargetDecoration(itemStack_1, blockPos_1, "+", this.iconType);
                    itemStack_1.setHoverName(new TranslatableComponent(this.displayName));
                    return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(Items.COMPASS), itemStack_1, this.maxUses, this.experience, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }

    /*public static class EnchantBookFactory implements TradeOffers.Factory {
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
    }*/

    public static class TwoItemsForOneItemFactory implements VillagerTrades.ItemListing {
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

        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            return new MerchantOffer(buyItem, buyItem2, sellItem, maxUses, experience, multiplier);
        }
    }

    static class SellDyedArmorFactory implements VillagerTrades.ItemListing {
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

        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
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

                itemStack_2 = DyeableLeatherItem.dyeArmor(itemStack_2, list_1);
            }

            return new MerchantOffer(itemStack_1, itemStack_2, this.maxUses, this.experience, 0.2F);
        }
    }

    public static class SellPotionHoldingItemFactory implements VillagerTrades.ItemListing {
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

        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(Items.EMERALD, this.price);
            List<Potion> list_1 = Registry.POTION.stream().filter((potion_1x) ->
                    !potion_1x.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion_1x)).collect(Collectors.toList());
            Potion potion_1 = list_1.get(random_1.nextInt(list_1.size()));
            ItemStack itemStack_2 = PotionUtils.setPotion(new ItemStack(this.sell.getItem(), this.sellCount), potion_1);
            return new MerchantOffer(itemStack_1, new ItemStack(this.secondBuy, this.secondCount), itemStack_2, this.maxUses, this.experience, this.priceMultiplier);
        }
    }

    public static class SellEnchantedToolFactory implements VillagerTrades.ItemListing {
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

        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            int int_1 = 5 + random_1.nextInt(15);
            ItemStack itemStack_1 = EnchantmentHelper.enchantItem(random_1, new ItemStack(this.tool.getItem()), int_1, false);
            int int_2 = Math.min(this.basePrice + int_1, 64);
            ItemStack itemStack_2 = new ItemStack(Items.EMERALD, int_2);
            return new MerchantOffer(itemStack_2, itemStack_1, this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class SellSuspiciousStewFactory implements VillagerTrades.ItemListing {
        final MobEffect effect;
        final int duration;
        final int experience;
        private final float multiplier;

        public SellSuspiciousStewFactory(MobEffect statusEffect_1, int int_1, int int_2) {
            this.effect = statusEffect_1;
            this.duration = int_1;
            this.experience = int_2;
            this.multiplier = 0.05F;
        }


        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.saveMobEffect(itemStack_1, this.effect, this.duration);
            return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemStack_1, 6, this.experience, this.multiplier);
        }
    }

    public static class SellItemFactory2 implements VillagerTrades.ItemListing {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory2(Block sell, int price, int count, int experience) {
            this(new ItemStack(sell), price, count, 6, experience);
        }

        public SellItemFactory2(Block sell, int price, int count, int maxUses, int experience) {
            this(new ItemStack(sell), price, count, maxUses, experience);
        }

        public SellItemFactory2(Item sell, int price, int count, int experience) {
            this(new ItemStack(sell), price, count, 6, experience);
        }

        public SellItemFactory2(Item sell, int price, int count, int maxUses, int experience) {
            this(new ItemStack(sell), price, count, maxUses, experience);
        }

        public SellItemFactory2(ItemStack sell, int price, int count, int maxUses, int experience) {
            this(sell, price, count, maxUses, experience, 0.05F);
        }

        /**
            The player buys the spesific stack with the defined price of emeralds

            @param sell The stack the player sells
            @param price The amount of emeralds the stack costs
            @param count The amount of the stack the player get
            @param maxUses The number of amount you can trade the spesific stack
            @param experience The amount of experience you get from the trade
            @param multiplier Influences by the reputation system, the multiplier says how much up or down the price goes
         **/
        public SellItemFactory2(ItemStack sell, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = sell;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class BuyForOneEmeraldFactory implements VillagerTrades.ItemListing {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemLike itemProvider_1, int price, int maxUses, int experience) {
            this.buy = itemProvider_1.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            ItemStack itemStack_1 = new ItemStack(this.buy, this.price);
            return new MerchantOffer(itemStack_1, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class TypeAwareBuyForOneEmeraldFactory implements VillagerTrades.ItemListing {
        private final Map<VillagerType, Item> map;
        private final int count;
        private final int maxUses;
        private final int experience;

        public TypeAwareBuyForOneEmeraldFactory(int int_1, int int_2, int int_3, Map<VillagerType, Item> map_1) {
            Registry.VILLAGER_TYPE.stream().filter((villagerType_1) -> !map_1.containsKey(villagerType_1)).findAny().ifPresent((villagerType_1) -> {
                throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getKey(villagerType_1));
            });
            this.map = map_1;
            this.count = int_1;
            this.maxUses = int_2;
            this.experience = int_3;
        }


        public MerchantOffer getOffer(Entity entity_1, Random random_1) {
            if (entity_1 instanceof VillagerDataHolder) {
                ItemStack itemStack_1 = new ItemStack(this.map.get(((VillagerDataHolder) entity_1).getVillagerData().getType()), this.count);
                return new MerchantOffer(itemStack_1, new ItemStack(Items.EMERALD), this.maxUses, this.experience, 0.05F);
            } else {
                return null;
            }
        }
    }

    // The player sells the spesific item/block with the defined price to get emeralds
    public static class SellItemFactory implements VillagerTrades.ItemListing {
        private final Item item;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;


        /**
         The player sells the spesific stack for the defined price to get emeralds

         @param sell The stack the player sells
         @param price The amount of emeralds the stack costs
         @param maxUses The number of amount you can trade the spesific stack
         @param experience The amount of experience you get from the trade
         **/
        public SellItemFactory(ItemLike sell, int price, int maxUses, int experience) {
            this.item = sell.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity var1, Random var2) {
            ItemStack itemStack_1 = new ItemStack(this.item, this.price);
            return new MerchantOffer(itemStack_1, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class TypeAwareBuyItemFactory implements VillagerTrades.ItemListing, ConditionalTradeFactory {
        private final Map<VillagerType, Item> itemMap;
        private final int price;
        private final int maxUses;
        private final int experience;

        public TypeAwareBuyItemFactory(Map<VillagerType, Item> itemMap, int price, int maxUses, int experience) {
            this.itemMap = itemMap;
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            if (entity instanceof VillagerDataHolder) {
                ItemStack itemStack = new ItemStack(this.itemMap.get(((VillagerDataHolder)entity).getVillagerData().getType()), this.price);
                return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, 0.05F);
            } else {
                return null;
            }
        }

        @Override
        public boolean hg$isApplicable(AbstractVillager entity, Random random) {
            if (entity.level.getBiome(entity.blockPosition()).unwrapKey().isPresent()) {
                System.out.println(VillagerTypeRegistry.getVillagerTypeForBiome(entity.level.getBiome(entity.blockPosition())).toString());
                return itemMap.containsKey(VillagerTypeRegistry.getVillagerTypeForBiome(entity.level.getBiome(entity.blockPosition())));
            } else {
                return itemMap.containsKey(VillagerType.PLAINS);
            }
        }
    }

}
