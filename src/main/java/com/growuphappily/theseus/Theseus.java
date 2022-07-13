package com.growuphappily.theseus;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Theseus.MOD_ID)
public class Theseus {
    public final static String MOD_ID = "theseus";
    public final static boolean IS_DEMO = true;
    public final static String version = "1.0-DEV";
    public Theseus(){
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, com.growuphappily.theseus.client.ModConfig.COMMON_CONFIG);
    }
}
