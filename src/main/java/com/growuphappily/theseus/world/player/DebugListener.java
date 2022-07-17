package com.growuphappily.theseus.world.player;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.world.events.JudgeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class DebugListener {
    @SubscribeEvent
    public static void onJudge(JudgeEvent event){

    }
}
