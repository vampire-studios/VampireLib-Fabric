/*
 * Copyright (c) 2023 OliviaTheVampire
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

import io.github.vampirestudios.vampirelib.api.oreVeins.VOreVeinType;
import io.github.vampirestudios.vampirelib.api.oreVeins.VOreVeinTypes;
import io.github.vampirestudios.vampirelib.init.VRegistries;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.OreVeinifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(OreVeinifier.VeinType.class)
public class VeinTypeMixin {

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static OreVeinifier.VeinType newType(String internalName, int internalId, BlockState ore, BlockState rawOreBlock, BlockState filler, int minY, int maxY) {
		throw new AssertionError("Mixin injection failed - FrozenLib VeinTypeMixin");
	}

	@SuppressWarnings("ShadowTarget")
	@Shadow
	@Final
	@Mutable
	private static OreVeinifier.VeinType[] $VALUES;

	@Inject(
			method = "<clinit>",
			at = @At(
					value = "FIELD",
					opcode = Opcodes.PUTSTATIC,
					target = "Lnet/minecraft/world/level/levelgen/OreVeinifier$VeinType;$VALUES:[Lnet/minecraft/world/level/levelgen/OreVeinifier$VeinType;",
					shift = At.Shift.AFTER
			)
	)
	private static void addCustomCategories(CallbackInfo ci) {
		var categories = new ArrayList<>(Arrays.asList($VALUES));
		var last = categories.get(categories.size() - 1);
		int currentOrdinal = last.ordinal();

		ArrayList<String> internalIds = new ArrayList<>();
		for (OreVeinifier.VeinType veinType : categories) {
			internalIds.add(veinType.name());
		}

		for (VOreVeinType frozenVeinType : VRegistries.ORE_VEIN_TYPES) {
			var namespace = frozenVeinType.key().getNamespace();
			var path = frozenVeinType.key().getPath();
			StringBuilder internalId = new StringBuilder(namespace.toUpperCase());
			internalId.append(path.toUpperCase());
			if (internalIds.contains(internalId.toString())) {
				throw new IllegalStateException("Cannot add duplicate VeinType " + internalId + "!");
			}
			currentOrdinal += 1;
			var addedVeinType = newType(internalId.toString(), currentOrdinal, frozenVeinType.ore(), frozenVeinType.rawOreBlock(), frozenVeinType.filler(), frozenVeinType.minY(), frozenVeinType.maxY());
			categories.add(addedVeinType);
			VOreVeinTypes.addVeinType(internalId.toString(), addedVeinType);
		}

		$VALUES = categories.toArray(new OreVeinifier.VeinType[0]);
	}
}
