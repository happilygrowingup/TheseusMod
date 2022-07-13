package com.growuphappily.theseus.world.player.effects;

import com.growuphappily.theseus.world.player.Player;

import java.util.Timer;
import java.util.TimerTask;

public class EffectPowerless {
    public static void addPlayer(Player player, int duration){
        player.state.add(EnumPlayerState.POWERLESS);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                player.state.remove(EnumPlayerState.POWERLESS);
            }
        }, (long)duration * 1000L);
    }
}
