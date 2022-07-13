package com.growuphappily.theseus.world.time;

import com.growuphappily.theseus.world.WorldData;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.Serializable;

@Mod.EventBusSubscriber
public class Time implements Serializable {
    public EnumSeason season;
    public int days;
    public long dayTime;
    public long lastSecond = 0;

    public Time(){
        season = EnumSeason.SPRING;
        days = 0;
        dayTime = 0;
    }

    @SubscribeEvent
    public static void tick(TickEvent.WorldTickEvent event){
        Time time = WorldData.get(event.world).time;
        event.world.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(false, event.world.getServer());
        event.world.getServer().getAllLevels().forEach((world)->{
            world.setDayTime(time.dayTime);
        });
        if(time.lastSecond + (long)1000 <= System.currentTimeMillis()){
            if(time.dayTime < 1200){
                time.dayTime++;
            }else{
                time.days++;
                time.dayTime = 0;
            }
        }
    }
}
