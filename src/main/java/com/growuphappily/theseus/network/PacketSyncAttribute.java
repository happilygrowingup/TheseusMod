package com.growuphappily.theseus.network;

import com.growuphappily.theseus.client.ClientSidePlayer;
import com.growuphappily.theseus.util.SerializeUtil;
import com.growuphappily.theseus.world.player.CardAttributes;
import com.growuphappily.theseus.world.player.Player;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.io.IOException;
import java.util.function.Supplier;

public class PacketSyncAttribute {
    public CardAttributes msg;

    public PacketSyncAttribute(PacketBuffer buf){
        try {
            msg = (CardAttributes) SerializeUtil.unSerialize(buf.readByteArray(buf.readVarInt()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            msg = new CardAttributes();
        }
    }

    public PacketSyncAttribute(CardAttributes attrs){
        msg = attrs;
    }

    public void toBytes(PacketBuffer buf){
        try {
            //buf.writeInt(msg.);
            buf.writeBytes(SerializeUtil.serialize(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handler(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection().getReceptionSide().isServer()) {
                Player.byPlayerEntity(ctx.get().getSender()).card.attrs.genAttribute();
                Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new PacketSyncAttribute(Player.byPlayerEntity(ctx.get().getSender()).card.attrs));
            }else{
                ClientSidePlayer.attr = msg;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
