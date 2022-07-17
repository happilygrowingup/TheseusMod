package com.growuphappily.theseus.network;

import com.growuphappily.theseus.util.SerializeUtil;
import com.growuphappily.theseus.world.player.CardAttributes;
import com.growuphappily.theseus.world.player.Player;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class PacketGenAttribute {
    public CardAttributes msg;

    public PacketGenAttribute(PacketBuffer buf){
        try {
            msg = (CardAttributes) SerializeUtil.unSerialize(buf.readByteArray());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            msg = new CardAttributes();
        }
    }

    public PacketGenAttribute(CardAttributes attrs){
        msg = attrs;
    }

    public void toBytes(PacketBuffer buf){
        try {
            buf.writeBytes(SerializeUtil.serialize(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handler(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection().getReceptionSide().isServer()) {
                Player.byPlayerEntity(ctx.get().getSender()).card.attrs.genAttribute();
            }else{
                //TODO: Display to GUI
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
