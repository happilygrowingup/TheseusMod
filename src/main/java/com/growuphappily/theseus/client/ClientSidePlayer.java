package com.growuphappily.theseus.client;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.world.player.CardAttributes;
import com.growuphappily.theseus.world.player.effects.EnumPlayerState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class ClientSidePlayer {
    public static CardAttributes attr;
    public static ArrayList<EnumPlayerState> states;

    public static void clear(){
        attr = new CardAttributes();
        states = new ArrayList<>();
    }

    public static void onExit(){
        clear();
    }
}
