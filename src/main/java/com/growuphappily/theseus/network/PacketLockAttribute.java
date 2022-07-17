package com.growuphappily.theseus.network;

import com.growuphappily.theseus.util.SerializeUtil;
import com.growuphappily.theseus.world.player.EnumAttributes;
import com.growuphappily.theseus.world.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

public class PacketLockAttribute {
    public EnumAttributes msg;

    public PacketLockAttribute(EnumAttributes msg){
        this.msg = msg;
    }

    public PacketLockAttribute(PacketBuffer buf){
        try {
            SerializeUtil.unSerialize(buf.readByteArray());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            if(ctx.get().getDirection().getReceptionSide().isServer()){
                if(Player.byPlayerEntity(Objects.requireNonNull(ctx.get().getSender())) == null){
                    ServerPlayerEntity spe = ctx.get().getSender();
                    spe.connection.disconnect(new TranslationTextComponent("FA Q Hacker"));
                }
                else if(!Objects.requireNonNull(Player.byPlayerEntity(Objects.requireNonNull(ctx.get().getSender()))).card.attrs.tryLock(msg)){
                    Objects.requireNonNull(ctx.get().getSender()).connection.disconnect(new TranslationTextComponent("FA Q HACKER"));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
