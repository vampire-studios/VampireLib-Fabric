/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package io.github.vampirestudios.vampirelib;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;

public class VampireLibCommand {
    public VampireLibCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            LiteralArgumentBuilder.<CommandSourceStack>literal("vampirelib")
                .then(EntityCommand.register())
                .then(DimensionsCommand.register())
                .then(TagsCommand.register())
        );
    }
}