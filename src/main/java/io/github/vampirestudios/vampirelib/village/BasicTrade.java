package io.github.vampirestudios.vampirelib.village;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

/**
 * A default, exposed implementation of ITrade.  All of the other implementations of ITrade (in VillagerTrades) are not public.
 * This class contains everything needed to make a MerchantOffer, the actual "trade" object shown in trading guis.
 */
public class BasicTrade implements VillagerTrades.ItemListing {

    protected final ItemStack price;
    protected final ItemStack price2;
    protected final ItemStack forSale;
    protected final int maxTrades;
    protected final int xp;
    protected final float priceMult;

    public BasicTrade(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult)
    {
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
    }

    public BasicTrade(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult)
    {
        this(price, ItemStack.EMPTY, forSale, maxTrades, xp, priceMult);
    }

    public BasicTrade(int emeralds, ItemStack forSale, int maxTrades, int xp, float mult)
    {
        this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, mult);
    }

    public BasicTrade(int emeralds, ItemStack forSale, int maxTrades, int xp)
    {
        this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, 1);
    }

    @Override
    @Nullable
    public MerchantOffer getOffer(Entity merchant, Random rand)
    {
        return new MerchantOffer(price, price2, forSale, maxTrades, xp, priceMult);
    }

}