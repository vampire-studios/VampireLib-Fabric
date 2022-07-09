/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package io.github.vampirestudios.vampirelib;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.commands.SharedSuggestionProvider;

import net.fabricmc.loader.api.FabricLoader;

public class ModIdArgument implements ArgumentType<String> {
    private static final List<String> EXAMPLES = Arrays.asList("vmapirelib", "rei", "raa");

    public static ModIdArgument modIdArgument() {
        return new ModIdArgument();
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        return reader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(FabricLoader.getInstance().getAllMods().stream().map(modContainer -> modContainer.getMetadata().getId()), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}