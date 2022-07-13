package com.growuphappily.theseus.world.player.effects;

import com.growuphappily.theseus.world.player.Player;

public class EffectOverload {
    public static void addPlayer(Player player, int duration){
        player.state.add(EnumPlayerState.OVERLOAD);

    }
}
