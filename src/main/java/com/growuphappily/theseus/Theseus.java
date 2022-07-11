package com.growuphappily.theseus;

import com.growuphappily.theseus.client.events.DemoEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Theseus.modid)
public class Theseus {
    public final static String modid = "theseus";
    public final static boolean isDemo = true;

    public Theseus(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onComplete);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, com.growuphappily.theseus.client.ModConfig.COMMON_CONFIG);
    }

    private void onComplete(final FMLLoadCompleteEvent event){
        //DemoEvents.onLoaded();
    }
}
