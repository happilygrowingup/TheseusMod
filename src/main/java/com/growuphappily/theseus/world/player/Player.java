package com.growuphappily.theseus.world.player;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.util.Dice;
import com.growuphappily.theseus.world.WorldData;
import com.growuphappily.theseus.world.player.effects.EffectDizzy;
import com.growuphappily.theseus.world.player.effects.EffectPowerless;
import com.growuphappily.theseus.world.player.effects.EnumPlayerState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
    public ArrayList<EnumPlayerState> state = new ArrayList<>();
    public static MinecraftServer server;
    public CharacterCard card;
    public PlayerAttributes attrs;
    public String name;
    public boolean isOnline;
    public static long lastTick = 0;

    public int getMaxNutrition(){
        return 21000 + 600 * (card.attrs.constitution - 5);
    }

    public int getMaxFull(){
        return 7000 + 200 * (card.attrs.constitution - 5);
    }

    public void genAttrs(){

    }

    public boolean tryCast(int surgical){
        int d = Dice.onedX(100);
        if(d <= 50 + (card.attrs.mind * 0.5)){
            if(d != 1){
                attrs.surgical -= surgical;
            }
            return true;
        }else{
            if(d == 100){
                attrs.surgical -= 2 * surgical;
            }else{
                attrs.surgical -= surgical;
            }
            return false;
        }
    }

    public boolean doAttackPhysicalConsumption(){
        if(attrs.physical < card.attrs.strength / 2){
            EffectPowerless.addPlayer(this, 10);
            return false;
        }else{
            attrs.physical -= (int)Math.ceil((float)card.attrs.strength / 2f);
            return true;
        }
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event){
        Player p = byPlayerEntity(event.getPlayer());
        if(p != null) {
            if(p.doAttackPhysicalConsumption()){
                //TODO: Attack ..
            }else{
                event.setCanceled(true);
            }
            p.attrs.doPhysical();
        }
    }

    public boolean doJumpConsumption(){
        if(attrs.physical < Math.ceil((float)card.attrs.speed / 3f)){
            EffectPowerless.addPlayer(this, 10);
            return false;
        }else{
            attrs.physical -= (int)Math.ceil((float)card.attrs.speed / 3f);
            return true;
        }
    }

    public void doRunConsumption(){
        if(attrs.physical < card.attrs.getMaxPhysical() * 0.2f){
            getEntity().setSprinting(false);
        }else if(getEntity().isSprinting()){
            attrs.physical -= 2;
            attrs.doPhysical();
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        if(event.getEntity().level.isClientSide){
            return;
        }
        if(event.getEntity() instanceof PlayerEntity){
            Player p = byPlayerEntity((PlayerEntity) event.getEntity());
            if(p == null){
                return;
            }
            p.doJumpConsumption();
            p.attrs.doPhysical();
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.WorldTickEvent event){
        if(event.world.isClientSide()){
            return;
        }
        for (Player p: WorldData.get(event.world).playerList) {
            if(p.getEntity() == null || !p.isOnline){
                p.isOnline = false;
                continue;
            }
            p.card.attrs.doAttrSync(p);
            p.attrs.doRegen(p);
            if(lastTick + (long)1000 <= System.currentTimeMillis()) {
                p.doRunConsumption();
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            byPlayerEntity((PlayerEntity) event.getEntity()).attrs.doHurt();
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
            p.card.attrs.constitution = 50;
            p.card.attrs.speed = 90;
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
