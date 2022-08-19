/*
 * Copyright (c) 2022 OliviaTheVampire
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

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MaterialColor;

import io.github.vampirestudios.vampirelib.api.extensions.DyeExtension;

@Mixin(DyeColor.class)
public class DyeColorMixin implements DyeExtension {
	@Shadow
	@Final
	private String name;
	@Unique
	private TagKey<Item> tag;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void addTag(String woolId, int id, int color, String mapColor, int fireworkColor, MaterialColor signColor, int j, int k, CallbackInfo ci) {
		tag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", name + "_dyes"));
	}

	@Override
	public TagKey<Item> getTag() {
		return tag;
	}
}