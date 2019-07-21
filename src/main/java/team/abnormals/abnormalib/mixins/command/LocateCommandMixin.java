/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package team.abnormals.abnormalib.mixins.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = LocateCommand.class)
public class LocateCommandMixin {

    @Final
    @Shadow
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.locate.failed"));

    /**
     * @author OliviaTheVampire
     * @reason Allow to find all registered structures
     */
    @Overwrite
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher_1) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = commandDispatcher_1.register(CommandManager.literal("locate")
                .requires((serverCommandSource_1) -> serverCommandSource_1.hasPermissionLevel(2))).createBuilder();
        Registry.STRUCTURE_FEATURE.stream().forEach(structureFeature ->
                literalArgumentBuilder.then(CommandManager.literal(Registry.STRUCTURE_FEATURE.getId(structureFeature).toString()).executes(context ->
                        execute(context.getSource(), Registry.STRUCTURE_FEATURE.getId(structureFeature).toString()))));
    }

    @Shadow
    private static int execute(ServerCommandSource var0, String var1) throws CommandSyntaxException {
        BlockPos var2 = new BlockPos(var0.getPosition());
        BlockPos var3 = var0.getWorld().locateStructure(var1, var2, 100, false);
        if (var3 == null) {
            throw FAILED_EXCEPTION.create();
        } else {
            int var4 = MathHelper.floor(getDistance(var2.getX(), var2.getZ(), var3.getX(), var3.getZ()));
            Text var5 = Texts.bracketed(new TranslatableText("chat.coordinates", var3.getX(), "~", var3.getZ())).setStyle(
                    new Style().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + var3.getX() + " ~ " + var3.getZ()))
                            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.coordinates.tooltip"))));
            var0.sendFeedback(new TranslatableText("commands.locate.success", var1, var5, var4), false);
            return var4;
        }
    }

    @Shadow
    private static float getDistance(int var0, int var1, int var2, int var3) {
        int var4 = var2 - var0;
        int var5 = var3 - var1;
        return MathHelper.sqrt((float) (var4 * var4 + var5 * var5));
    }

}