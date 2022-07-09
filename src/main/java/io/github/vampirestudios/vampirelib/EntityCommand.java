/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package io.github.vampirestudios.vampirelib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

class EntityCommand {
    static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("entity")
            .then(KillCommand.register())
            .then(SummonCommand.register())
            .then(EntityListCommand.register()); //TODO: //Kill, spawn, etc..
    }

    private static class EntityListCommand {
        private static final SimpleCommandExceptionType INVALID_FILTER = new SimpleCommandExceptionType(Component.translatable("commands.forge.entity.list.invalid"));
        private static final DynamicCommandExceptionType INVALID_DIMENSION = new DynamicCommandExceptionType(dim -> Component.translatable("commands.forge.entity.list.invalidworld", dim));
        private static final SimpleCommandExceptionType NO_ENTITIES = new SimpleCommandExceptionType(Component.translatable("commands.forge.entity.list.none"));

        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("list")
                .requires(cs -> cs.hasPermission(2)) //permission
                .then(Commands.argument("filter", StringArgumentType.string())
                    .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(Registry.ENTITY_TYPE.keySet().stream().map(ResourceLocation::toString).map(StringArgumentType::escapeIfRequired), builder))
                    .then(Commands.argument("dim", DimensionArgument.dimension())
                        .executes(ctx -> execute(ctx.getSource(), StringArgumentType.getString(ctx, "filter"), DimensionArgument.getDimension(ctx, "dim").dimension()))
                    )
                    .executes(ctx -> execute(ctx.getSource(), StringArgumentType.getString(ctx, "filter"), ctx.getSource().getLevel().dimension()))
                )
                .executes(ctx -> execute(ctx.getSource(), "*", ctx.getSource().getLevel().dimension()));
        }

        private static int execute(CommandSourceStack sender, String filter, ResourceKey<Level> dim)
            throws CommandSyntaxException {
            final String cleanFilter = filter.replace("?", ".?").replace("*", ".*?");

            Set<ResourceLocation> names = Registry.ENTITY_TYPE.keySet().stream().filter(n -> n.toString().matches(cleanFilter)).collect(Collectors.toSet());

            if (names.isEmpty())
                throw INVALID_FILTER.create();

            ServerLevel level = sender.getServer().getLevel(dim); //TODO: DimensionManager so we can hotload? DimensionManager.getWorld(sender.getServer(), dim, false, false);
            if (level == null)
                throw INVALID_DIMENSION.create(dim);

            Map<ResourceLocation, MutablePair<Integer, Map<ChunkPos, Integer>>> list = Maps.newHashMap();
            level.getEntities().getAll().forEach(e -> {
                MutablePair<Integer, Map<ChunkPos, Integer>> info = list.computeIfAbsent(Registry.ENTITY_TYPE.getKey(e.getType()), k -> MutablePair.of(0, Maps.newHashMap()));
                ChunkPos chunk = new ChunkPos(e.blockPosition());
                info.left++;
                info.right.put(chunk, info.right.getOrDefault(chunk, 0) + 1);
            });

            if (names.size() == 1) {
                ResourceLocation name = names.iterator().next();
                Pair<Integer, Map<ChunkPos, Integer>> info = list.get(name);
                if (info == null)
                    throw NO_ENTITIES.create();

                sender.sendSuccess(Component.translatable("commands.forge.entity.list.single.header", name, info.getLeft()), false);
                List<Map.Entry<ChunkPos, Integer>> toSort = new ArrayList<>(info.getRight().entrySet());
                toSort.sort((a, b) -> {
                    if (Objects.equals(a.getValue(), b.getValue()))
                        return a.getKey().toString().compareTo(b.getKey().toString());
                    else
                        return b.getValue() - a.getValue();
                });

                long limit = 10;
                for (Map.Entry<ChunkPos, Integer> e : toSort) {
                    if (limit-- == 0) break;
                    sender.sendSuccess(Component.literal("  " + e.getValue() + ": " + e.getKey().x + ", " + e.getKey().z), false);
                }
                return toSort.size();
            } else {

                List<Pair<ResourceLocation, Integer>> info = new ArrayList<>();
                list.forEach((key, value) -> {
                    if (names.contains(key)) {
                        Pair<ResourceLocation, Integer> of = Pair.of(key, value.left);
                        info.add(of);
                    }
                });
                info.sort((a, b) -> {
                    if (Objects.equals(a.getRight(), b.getRight()))
                        return a.getKey().toString().compareTo(b.getKey().toString());
                    else
                        return b.getRight() - a.getRight();
                });

                if (info.size() == 0)
                    throw NO_ENTITIES.create();

                int count = info.stream().mapToInt(Pair::getRight).sum();
                sender.sendSuccess(Component.translatable("commands.forge.entity.list.multiple.header", count), false);
                info.forEach(e -> sender.sendSuccess(Component.literal("  " + e.getValue() + ": " + e.getKey()), false));
                return info.size();
            }
        }
    }

    private static class KillCommand {
        public KillCommand() {
        }

        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("kill")
                .requires((source) -> source.hasPermission(2))
                .executes((context) -> kill(context.getSource(), ImmutableList.of(context.getSource().getEntityOrException())))
                .then(Commands.argument("targets", EntityArgument.entities())
                    .executes((context) -> kill(context.getSource(), EntityArgument.getEntities(context, "targets")))
                );
        }

        private static int kill(CommandSourceStack source, Collection<? extends Entity> targets) {
            for (Entity entity : targets) {
                entity.kill();
            }
            if (targets.size() == 1) {
                source.sendSuccess(Component.translatable("commands.kill.success.single", targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendSuccess(Component.translatable("commands.kill.success.multiple", targets.size()), true);
            }

            return targets.size();
        }
    }

    private static class SummonCommand {
        private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed"));
        private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed.uuid"));
        private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(Component.translatable("commands.summon.invalidPosition"));

        static ArgumentBuilder<CommandSourceStack, ?> register() {
            return Commands.literal("summon")
                .requires((source) -> source.hasPermission(2))
                .then(Commands.argument("entity", EntitySummonArgument.id())
                    .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                    .executes((context) -> spawnEntity(context.getSource(), EntitySummonArgument.getSummonableEntity(context, "entity"),
                        context.getSource().getPosition(), new CompoundTag(), true))
                ).then(Commands.argument("pos", Vec3Argument.vec3())
                    .executes((context) -> spawnEntity(context.getSource(), EntitySummonArgument.getSummonableEntity(context, "entity"),
                        Vec3Argument.getVec3(context, "pos"), new CompoundTag(), true))
                    .then(Commands.argument("nbt", CompoundTagArgument.compoundTag()).executes((context) -> spawnEntity(context.getSource(),
                        EntitySummonArgument.getSummonableEntity(context, "entity"), Vec3Argument.getVec3(context, "pos"),
                        CompoundTagArgument.getCompoundTag(context, "nbt"), false))
                    )
                );
        }

        private static int spawnEntity(CommandSourceStack source, ResourceLocation type, Vec3 pos, CompoundTag nbt, boolean randomizeProperties)
            throws CommandSyntaxException {
            BlockPos blockPos = new BlockPos(pos);
            if (!Level.isInSpawnableBounds(blockPos)) {
                throw INVALID_POSITION.create();
            } else {
                CompoundTag compoundTag = nbt.copy();
                compoundTag.putString("id", type.toString());
                ServerLevel serverLevel = source.getLevel();
                Entity entity = EntityType.loadEntityRecursive(compoundTag, serverLevel, (entityx) -> {
                    entityx.moveTo(pos.x, pos.y, pos.z, entityx.getYRot(), entityx.getXRot());
                    return entityx;
                });
                if (entity == null) {
                    throw ERROR_FAILED.create();
                } else {
                    if (randomizeProperties && entity instanceof Mob) {
                        ((Mob) entity).finalizeSpawn(source.getLevel(), source.getLevel().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.COMMAND, (SpawnGroupData) null, (CompoundTag) null);
                    }

                    if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                        throw ERROR_DUPLICATE_UUID.create();
                    } else {
                        source.sendSuccess(Component.translatable("commands.summon.success", entity.getDisplayName()), true);
                        return 1;
                    }
                }
            }
        }
    }

}