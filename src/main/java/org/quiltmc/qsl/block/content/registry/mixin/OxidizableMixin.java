package org.quiltmc.qsl.block.content.registry.mixin;

import com.google.common.collect.BiMap;
import org.quiltmc.qsl.block.content.registry.impl.BlockContentRegistriesInitializer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

@Mixin(WeatheringCopper.class)
public interface OxidizableMixin {
	@Dynamic("Replace old map with one updated by Registry Attachments")
	@Inject(method = "method_34740", at = @At("RETURN"), cancellable = true)
	private static void createOxidationLevelIncreasesMap(CallbackInfoReturnable<BiMap<Block, Block>> cir) {
		BlockContentRegistriesInitializer.INITIAL_OXIDATION_BLOCKS.putAll(cir.getReturnValue());
		cir.setReturnValue(BlockContentRegistriesInitializer.OXIDATION_INCREASE_BLOCKS);
	}

	@Dynamic("Replace old map with one updated by Registry Attachments")
	@Inject(method = "method_34739", at = @At("RETURN"), cancellable = true)
	private static void createOxidationLevelDecreasesMap(CallbackInfoReturnable<BiMap<Block, Block>> cir) {
		cir.setReturnValue(BlockContentRegistriesInitializer.OXIDATION_DECREASE_BLOCKS);
	}
}