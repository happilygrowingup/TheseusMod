package com.growuphappily.theseus.network;

import com.growuphappily.theseus.Theseus;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Theseus.version)
public class PacketJumpState {
    public static boolean jumpState;

    public boolean msg;

    public PacketJumpState(boolean state){
        msg = state;
    }

    public PacketJumpState(PacketBuffer buf){
        msg = buf.readBoolean();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeBoolean(msg);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> jumpState = msg);
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
