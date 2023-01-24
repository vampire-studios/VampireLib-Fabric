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

package io.github.vampirestudios.vampirelib.api.debug_renderers;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

@ApiStatus.Internal
public final class DebugFeatureCommands {
	public static void init() {
		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> dispatcher.register(
				LiteralArgumentBuilder.<CommandSourceStack>literal("quilt_debug").then(
						RequiredArgumentBuilder.<CommandSourceStack, ResourceLocation>argument("feature", ResourceLocationArgument.id())
								.suggests((c, b) -> SharedSuggestionProvider.suggestResource(DebugFeaturesImpl.getFeatures().stream().filter(DebugFeature::needsServer).map(DebugFeature::id), b)).then(
										LiteralArgumentBuilder.<CommandSourceStack>literal("enable").executes(setEnabled(true))
								).then(
										LiteralArgumentBuilder.<CommandSourceStack>literal("disable").executes(setEnabled(false))
								)
				)
		));
	}

	private static final DynamicCommandExceptionType INVALID_FEATURE = new DynamicCommandExceptionType(id -> Component.literal("No such Debug Feature " + id + "!"));

	private static final Dynamic2CommandExceptionType NOT_SERVER_FEATURE = new Dynamic2CommandExceptionType((id, enable) -> {
		var suggestedCommand = "/quilt_debug_client " + id + " " + (enable == Boolean.TRUE ? "enable" : "disable");
		return Component.empty()
				.append(Component.literal("Debug Feature " + id + " is not server-side! Did you mean to use ["))
				.append(Component.literal(suggestedCommand).withStyle(s -> s.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, suggestedCommand))))
				.append("]");
	});

	private static Command<CommandSourceStack> setEnabled(boolean value) {
		return ctx -> {
			var id = ResourceLocationArgument.getId(ctx, "feature");
			var feature = DebugFeaturesImpl.get(id);
			if (feature == null) {
				throw INVALID_FEATURE.create(id);
			}

			if (!feature.needsServer()) {
				throw NOT_SERVER_FEATURE.create(id, value);
			}

			DebugFeaturesImpl.setEnabledNotifyClients(feature, value, ctx.getSource().getServer());
			ctx.getSource().sendSuccess(
					Component.empty()
							.append(Component.literal("[Debug|Server]: ").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD))
							.append(Component.literal(id + " " + (value ? "enabled" : "disabled"))),
					true
			);
			return Command.SINGLE_SUCCESS;
		};
	}
}
