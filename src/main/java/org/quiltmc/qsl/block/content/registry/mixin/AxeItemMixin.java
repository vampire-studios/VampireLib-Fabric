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

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AxeItem.class)
public class AxeItemMixin {
	@Mutable @Final @Shadow
	public static Map<Block, BlockState> STRIPPABLES;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void makeMapMutable(CallbackInfo ci) {
		STRIPPABLES = new Reference2ObjectOpenHashMap<>(STRIPPABLES);
	}
}