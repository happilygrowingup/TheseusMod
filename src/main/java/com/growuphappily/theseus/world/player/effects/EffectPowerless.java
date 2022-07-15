package com.growuphappily.theseus.world.player.effects;

import com.growuphappily.theseus.network.Networking;
import com.growuphappily.theseus.network.PacketJumpState;
import com.growuphappily.theseus.world.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Timer;
import java.util.TimerTask;

public class EffectPowerless {
    public static void addPlayer(Player player, int duration){
        if(player.state.contains(EnumPlayerState.POWERLESS)) return;
        player.state.add(EnumPlayerState.POWERLESS);
        ServerPlayerEntity pe = (ServerPlayerEntity) player.getEntity();
        Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> pe), new PacketJumpState(false));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                player.state.remove(EnumPlayerState.POWERLESS);
                Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> pe), new PacketJumpState(true));
            }
        }, (long)duration * 1000L);
    }
}
