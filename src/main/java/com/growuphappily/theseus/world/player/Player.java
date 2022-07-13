package com.growuphappily.theseus.world.player;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.world.WorldData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class Player implements Serializable {
    //public PlayerEntity entity;
    public static MinecraftServer server;
    public CharacterCard card;
    public PlayerAttributes attrs;
    public String name;
    public boolean isOnline;

    public int getMaxNutrition(){
        return 21000 + 600 * (card.attrs.constitution - 5);
    }

    public int getMaxFull(){
        return 7000 + 200 * (card.attrs.constitution - 5);
    }

    public void init(){

    }

    @SubscribeEvent
    public static void tick(TickEvent.WorldTickEvent event){
        for (Player p: WorldData.get(event.world).playerList) {
            if(p.getEntity() == null || !p.isOnline){
                p.isOnline = false;
                continue;
            }
            p.card.attrs.doAttrSync(p);
            p.attrs.doRegen();
        }
    }

    @SubscribeEvent
    public static void onPlayerEnter(PlayerEvent.PlayerLoggedInEvent event){
        Player player = byPlayerEntity(event.getPlayer());
        if(player == null){
            Player p = new Player();
            p.attrs = new PlayerAttributes();
            p.card = new CharacterCard(new ArrayList<>(), new CardAttributes());
            p.name = event.getPlayer().getName().getString();
            WorldData.get(event.getEntity().level).addPlayer(p); //TODO: Generate attributes
        }else{
            player.isOnline = true;
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event){
        if (event.getEntity() instanceof PlayerEntity){
            //TODO: Respawn
        }
    }

    @Nullable
    public static Player byName(World world, String name){
        AtomicReference<Player> player = new AtomicReference<>();
        WorldData.get(world).playerList.forEach((p)->{
            if(p.name.equals(name)){
                player.set(p);
            }
        });
        return player.get();
    }

    @Nullable
    public static Player byPlayerEntity(PlayerEntity entity){
        return byName(entity.level, entity.getName().getString());
    }

    public PlayerEntity getEntity(){
        return server.getPlayerList().getPlayerByName(name);
    }

    @SubscribeEvent
    public static void onServerStart(FMLServerStartingEvent event){
        server = event.getServer();
    }
}
