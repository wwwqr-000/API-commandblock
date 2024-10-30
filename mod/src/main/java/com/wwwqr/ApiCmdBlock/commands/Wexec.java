package com.wwwqr.ApiCmdBlock.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.wwwqr.ApiCmdBlock.ApiCmdBlock;

public class Wexec {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("wexec")
                .then(Commands.argument("url", StringArgumentType.string())
                    .executes(context -> {
                        String url =  StringArgumentType.getString(context, "url");
                        CommandSourceStack src = context.getSource();

                        StringBuilder response = new StringBuilder();
                        try {
                            URL okUrl = new URL(url);
                            String result = "";
                            HttpURLConnection conn = (HttpURLConnection)okUrl.openConnection();
                            conn.setRequestMethod("GET");

                            int resCode = conn.getResponseCode();
                            if (resCode != HttpURLConnection.HTTP_OK) {
                                ApiCmdBlock.LOGGER.warn("wexec command did not return a HTTP_OK value. Aborted execution.");
                                return Command.SINGLE_SUCCESS;
                            }
                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String tmpLine;
                            while ((tmpLine = in.readLine()) != null) {
                                response.append(tmpLine + "\n");
                            }
                            in.close();
                            result = response.toString();
                            ApiCmdBlock.LOGGER.info(result);

                            tmpLine = "";
                            for (int i = 0; i < result.length(); i++) {
                                char c = result.charAt(i);
                                if (c != '\n') {
                                    tmpLine += c;
                                    continue;
                                }
                                src.getServer().getCommands().performPrefixedCommand(src, tmpLine);
                                tmpLine = "";
                            }
                        }
                        catch (MalformedURLException e) {
                            ApiCmdBlock.LOGGER.error("wexec command url invalid", e);
                            return Command.SINGLE_SUCCESS;
                        }
                        catch (IOException e) {
                            ApiCmdBlock.LOGGER.error("wexec command IOException", e);
                            return Command.SINGLE_SUCCESS;
                        }
                        return Command.SINGLE_SUCCESS;
                    }))
        );
    }
}