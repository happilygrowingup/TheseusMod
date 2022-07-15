package com.growuphappily.theseus.network;


import com.growuphappily.theseus.Theseus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Networking {
    public static SimpleChannel INSTANCE;
    public static int ID = 0;
    public static String VERSION = "1.0";

    @SubscribeEvent
    public static void onRegistry(FMLCommonSetupEvent event){
        register();
    }

    public static int getID(){
        return ID++;
    }

    public static void register(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("theseus", "networking"),
                ()-> VERSION,
                (v) -> v.equals(VERSION),
                (v) -> v.equals(VERSION)
        );
        INSTANCE.registerMessage(
                getID(),
                PacketGenAttribute.class,
                PacketGenAttribute::toBytes,
                PacketGenAttribute::new,
                PacketGenAttribute::handler
        );
        INSTANCE.registerMessage(
                getID(),
                PacketLockAttribute.class,
                PacketLockAttribute::toBytes,
                PacketLockAttribute::new,
                PacketLockAttribute::handler
        );
        INSTANCE.registerMessage(
                getID(),
                PacketJumpState.class,
                PacketJumpState::toBytes,
                PacketJumpState::new,
                PacketJumpState::handler
        );
        INSTANCE.registerMessage(
                getID(),
                PacketIdentity.class,
                PacketIdentity::toBytes,
                PacketIdentity::new,
                PacketIdentity::handler
        );
    }
}
