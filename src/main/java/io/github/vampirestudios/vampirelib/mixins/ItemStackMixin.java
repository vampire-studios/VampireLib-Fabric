/*
 * Copyright (c) 2022-2023 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import io.github.vampirestudios.vampirelib.api.callbacks.ItemTooltipDataCallback;
import io.github.vampirestudios.vampirelib.item.BundledTooltipData;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "getTooltipImage", at = @At("RETURN"), cancellable = true)
	private void getTooltipData(CallbackInfoReturnable<Optional<TooltipComponent>> cir) {
		List<TooltipComponent> list = new ArrayList<>();
		cir.getReturnValue().ifPresent(list::add);
		ItemTooltipDataCallback.EVENT.invoker().getTooltipData((ItemStack) (Object) this, list);

		if (list.size() > 1) {
			cir.setReturnValue(Optional.of(new BundledTooltipData(list)));
		}

		if (list.size() == 1) {
			cir.setReturnValue(Optional.of(list.get(0)));
		}
	}
}
