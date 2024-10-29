package com.wwwqr.ApiCmdBlock;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.wwwqr.ApiCmdBlock.commands.Wexec;

import org.slf4j.Logger;

@Mod(ApiCmdBlock.MODID)
public class ApiCmdBlock {
    public static final String MODID = "apicmdblock";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ApiCmdBlock() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(RegisterCommandsEvent event) {
        Wexec.register(event.getDispatcher());
    }
}
