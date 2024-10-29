package com.wwwqr.ApiCmdBlock.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Wexec {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("wexec")
                .then(Commands.argument("url", StringArgumentType.string())
                    .executes(context -> {
                        String url =  StringArgumentType.getString(context, "url");
                        CommandSourceStack src = context.getSource();
                        src.getServer().getCommands().performPrefixedCommand(src, "say goeie! (" + url + ")");
                        return Command.SINGLE_SUCCESS;
                    }))
        );
    }
}