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

package io.github.vampirestudios.vampirelib.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeature;
import io.github.vampirestudios.vampirelib.api.debug_renderers.DebugFeaturesImpl;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
final class DebugFeatureClientCommands {
	static void init() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) -> dispatcher.register(
				ClientCommandManager.literal("quilt_debug_client").then(
						ClientCommandManager.argument("feature", ResourceLocationArgument.id())
								.suggests((c, b) -> SharedSuggestionProvider.suggestResource(DebugFeaturesImpl.getFeatures().stream().filter(DebugFeature::needsServer).map(DebugFeature::id), b)).then(
										ClientCommandManager.literal("enable").executes(setEnabled(true))
								).then(
										ClientCommandManager.literal("disable").executes(setEnabled(false))
								)
				)
		));
	}

	private static final DynamicCommandExceptionType INVALID_FEATURE = new DynamicCommandExceptionType(id -> Component.literal("No such Debug Feature "+id+"!"));

	private static Command<FabricClientCommandSource> setEnabled(boolean value) {
		return ctx -> {
			var id = ctx.getArgument("feature", ResourceLocation.class);
			var feature = DebugFeaturesImpl.get(id);
			if (feature == null) {
				throw INVALID_FEATURE.create(id);
			}

			if (feature.needsServer() && !DebugFeaturesImpl.isEnabledOnServer(feature)) {
				var suggestedCommand = "/quilt_debug " + id + " enable";
				ctx.getSource().sendFeedback(
						Component.empty()
								.append(Component.literal("[Debug|Client]: ").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD))
								.append(Component.literal("Debug Feature " + id + " must be enabled on the server, but it is not - enable it with [")).withStyle(ChatFormatting.YELLOW)
								.append(Component.literal(suggestedCommand).withStyle(s -> s.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, suggestedCommand)).withColor(ChatFormatting.WHITE)))
								.append(Component.literal("]")).withStyle(ChatFormatting.YELLOW)
				);
			}

			DebugFeaturesImpl.setEnabledNotifyServer(feature, value);

			ctx.getSource().sendFeedback(
					Component.empty()
							.append(Component.literal("[Debug|Client]: ").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD))
							.append(Component.literal(id+" "+(value ? "enabled" : "disabled")))
			);
			return Command.SINGLE_SUCCESS;
		};
	}
}
