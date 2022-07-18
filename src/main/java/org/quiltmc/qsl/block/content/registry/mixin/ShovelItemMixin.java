package org.quiltmc.qsl.block.content.registry.mixin;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
	@Mutable @Final @Shadow
	public static Map<Block, BlockState> FLATTENABLES;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void makeMapMutable(CallbackInfo ci) {
		FLATTENABLES = new Reference2ObjectOpenHashMap<>(FLATTENABLES);
	}
}