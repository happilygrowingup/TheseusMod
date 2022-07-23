package com.growuphappily.theseus.client;

import com.growuphappily.theseus.Theseus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class UpdateChecker {
    public static ClientWorld lastWorld;
    public static String getLatestVersion() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://theseus.up.railway.app/version.html");
        HttpResponse response = client.execute(get);
        if(response.getStatusLine().getStatusCode() != 200){
            throw new RuntimeException("Update check returned unexpected code " + response.getStatusLine().getStatusCode());
        }
        return EntityUtils.toString(response.getEntity());
    }
    @SubscribeEvent
    public static void onPlayerEnter(TickEvent.ClientTickEvent event){
        if(Minecraft.getInstance().level != lastWorld){
            ClientSidePlayer.onExit();
            try{
                if(!Objects.equals(getLatestVersion(), Theseus.version)){
                    Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("message.update.newAvailable").append(getLatestVersion()), Util.NIL_UUID);
                    Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("message.update.download"), Util.NIL_UUID);
                }
            }catch (IOException e){
                e.printStackTrace();
                Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("message.update.checkFailed"), Util.NIL_UUID);
            }catch (Exception e){
                e.printStackTrace();
                Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("message.update.unknownError"), Util.NIL_UUID);
            }
            lastWorld = Minecraft.getInstance().level;
        }
    }

    @SubscribeEvent
    public static void onRender(GuiScreenEvent event){
        if(event.getGui() instanceof MainMenuScreen){
            lastWorld = null;
        }
    }
}
