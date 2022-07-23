package com.growuphappily.theseus.network;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.client.ClientSidePlayer;
import com.growuphappily.theseus.util.SerializeUtil;
import com.growuphappily.theseus.world.player.effects.EnumPlayerState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Theseus.version)
public class PacketSyncState {
    public static boolean jumpState;
    public ArrayList<EnumPlayerState> msg;

    public PacketSyncState(ArrayList<EnumPlayerState> states){
        msg = states;
    }

    public PacketSyncState(PacketBuffer buf){
        try {
            msg = (ArrayList<EnumPlayerState>) SerializeUtil.unSerialize(buf.readByteArray(buf.readVarInt()));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void toBytes(PacketBuffer buf){
        try {
            buf.writeByteArray(SerializeUtil.serialize(msg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handler(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            jumpState = msg.contains(EnumPlayerState.POWERLESS);
            ClientSidePlayer.states = msg;
        });
        ctx.get().setPacketHandled(true);
    }

    @SubscribeEvent
    public static void onExit(TickEvent.RenderTickEvent event){
        if(Minecraft.getInstance().level == null){
            jumpState = true;
        }
    }

    public static void onJump(MovementInputFromOptions instance){
        if(Minecraft.getInstance().player != null){
            if(Minecraft.getInstance().player.isInWater()){
                return;
            }
        }
        instance.jumping = instance.jumping && jumpState;
    }

}
