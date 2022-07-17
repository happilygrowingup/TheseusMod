package com.growuphappily.theseus.network;

import com.growuphappily.theseus.util.SerializeUtil;
import com.growuphappily.theseus.world.player.Player;
import com.growuphappily.theseus.world.player.identity.IIdentity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

public class PacketIdentity {
    public IIdentity msg;

    public PacketIdentity(IIdentity id){
        msg = id;
    }

    public PacketIdentity(PacketBuffer buf){
        try {
            SerializeUtil.unSerialize(buf.readByteArray());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void toBytes(PacketBuffer buf){
        try {
            buf.writeByteArray(SerializeUtil.serialize(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handler(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            if(Player.byPlayerEntity(Objects.requireNonNull(ctx.get().getSender())) != null){
                Player.byPlayerEntity(Objects.requireNonNull(ctx.get().getSender())).setIdentity(msg);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
