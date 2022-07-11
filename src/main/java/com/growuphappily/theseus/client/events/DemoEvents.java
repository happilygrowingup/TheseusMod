package com.growuphappily.theseus.client.events;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.client.ModConfig;
import com.growuphappily.theseus.client.demo.SaveBackuper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.AlertScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Theseus.modid)
public class DemoEvents {
    private static Logger logger = LogManager.getLogger();
    private static boolean isProceed = false;
    @SubscribeEvent
    public static void onLoaded(GuiScreenEvent event){
        if(Theseus.isDemo && !isProceed && event.getGui() instanceof MainMenuScreen && !ModConfig.WARNING_PROCEED.get()){
            isProceed = true;
            logger.info("Load Completed" + (Theseus.isDemo ? " with warning." : "."));
            Minecraft.getInstance().setOverlay(null);
            Minecraft.getInstance().setScreen(new AlertScreen(() -> {
                Minecraft.getInstance().setScreen(new MainMenuScreen());
                ModConfig.COMMON_CONFIG.afterReload();
                ModConfig.WARNING_PROCEED.set(true);
                SaveBackuper.backupAll();
            }, new TranslationTextComponent("gui.text.demoWarningTitle"),new TranslationTextComponent("gui.text.demoWarning"), new TranslationTextComponent("gui.text.demoWarningButton")));
        }
    }
}
