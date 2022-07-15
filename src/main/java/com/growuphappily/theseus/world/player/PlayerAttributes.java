package com.growuphappily.theseus.world.player;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.world.player.effects.EnumPlayerState;
import net.minecraftforge.fml.common.Mod;

import java.io.Serializable;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class PlayerAttributes implements Serializable {
    public int full;
    public int nutrition;
    public int thirsty;
    public float temperature;
    public float health;
    public boolean isSurgeon;
    public float surgical;
    public int physical;
    public long lastRegen = 0;
    public long lastHurt = 0;
    public int waitRegen = 0;
    // Regen health, con, surgical
    public void doRegen(Player p){
        if(
                lastRegen + (long)1000 <= System.currentTimeMillis() &&
                lastHurt + (long)60000 <= System.currentTimeMillis() &&
                health < 0.6f * p.card.attrs.getMaxHealth()
        ){
            health += p.card.attrs.getMaxHealth() * 0.01f;
            if(health > 0.6f * p.card.attrs.getMaxHealth()){
                health = 0.6f * p.card.attrs.getMaxHealth();
            }
        }
        if(waitRegen > 0){
            waitRegen --;
        }else if(!p.state.contains(EnumPlayerState.POWERLESS)){
            physical += (float)(p.card.attrs.constitution + p.card.attrs.strength) / 50f;
        }
        if(health > p.card.attrs.getMaxHealth()){
            health = p.card.attrs.getMaxHealth();
        }
        if(physical > p.card.attrs.getMaxPhysical()){
            physical = p.card.attrs.getMaxPhysical();
        }
        if(isSurgeon){
            surgical += (p.card.attrs.mind * 0.1f + p.card.attrs.spirit * 0.2f) / 20;
            if(surgical > p.card.attrs.getMaxSurgical()){
                surgical = p.card.attrs.getMaxSurgical();
            }
        }
    }

    public void doHurt(){
        lastHurt = System.currentTimeMillis();
    }

    public void doPhysical(){
        waitRegen = 2;
    }
}
