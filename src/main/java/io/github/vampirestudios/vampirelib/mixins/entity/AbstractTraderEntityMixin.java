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

package io.github.vampirestudios.vampirelib.mixins.entity;

import io.github.vampirestudios.vampirelib.api.custom_villagers.ConditionalTradeFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TraderOfferList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(value = AbstractTraderEntity.class)
public abstract class AbstractTraderEntityMixin extends PassiveEntity {
    protected AbstractTraderEntityMixin(EntityType<? extends PassiveEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(method = "fillRecipesFromPool", at = @At(value = "INVOKE", target = "Ljava/util/Set;iterator()Ljava/util/Iterator;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void onTradesReady(TraderOfferList tradeOffers, TradeOffers.Factory[] factories, int amount, CallbackInfo callbackInfo, Set<Integer> tradeIds) {
        ArrayList<Integer> applicableTrades = new ArrayList<>();
        for (int i = 0; i < factories.length; i++) {
            if (factories[i] == null)
                continue;
            if (((ConditionalTradeFactory) factories[i]).neutronia$isApplicable((AbstractTraderEntity) (Object) this, random)) {
                applicableTrades.add(i);
            }
        }
        Set<Integer> disallowedTrades = tradeIds.stream().filter(id -> !applicableTrades.contains(id)).collect(Collectors.toSet());
        for (int id : disallowedTrades) {
            tradeIds.remove(id);
            disallowedTrades.remove(id);
            if (applicableTrades.size() > tradeIds.size() - disallowedTrades.size())
                while (tradeIds.size() < amount) {
                    tradeIds.add(applicableTrades.get(random.nextInt(applicableTrades.size())));
                }
        }
    }
}
