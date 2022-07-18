package org.quiltmc.qsl.block.content.registry.impl;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class BlockContentRegistriesClientInitializer implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
			Item item = stack.getItem();

			Block block = Block.byItem(item);
			BlockContentRegistries.FLATTENABLE_BLOCK.getValue(block).ifPresent(state -> lines.add(Component.literal("Flattenable block: " + state)));
			BlockContentRegistries.OXIDIZABLE_BLOCK.getValue(block).ifPresent(_block -> lines.add(Component.literal("Oxidizes to: " + _block.block())));
			BlockContentRegistries.WAXABLE_BLOCK.getValue(block).ifPresent(_block -> lines.add(Component.literal("Waxes to: " + _block.block())));
			BlockContentRegistries.STRIPPABLE_BLOCK.getValue(block).ifPresent(_block -> lines.add(Component.literal("Strips to: " + _block)));
			BlockContentRegistries.FLAMMABLE_BLOCK.getValue(block).ifPresent(entry -> lines.add(Component.literal("Flammable: " + entry.burn() + " burn chance, " + entry.spread() + " spread chance")));
		});
	}
}