package com.growuphappily.theseus.world.player;

import net.minecraft.entity.ai.attributes.Attributes;

import java.io.Serializable;

public class CardAttributes implements Serializable {
    public int speed;
    public int constitution;
    public int strength;
    public int adaptive;
    public int spirit;
    public int mind;
    public int knowledge;
    public int luck;
    public float criticalPercentage = 0.05f;

    public void doAttrSync(Player p){
        p.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1 + speed*0.1);
    }

    public int getMaxHealth(){
        return constitution * 5 + adaptive * 2;
    }

    public int getMaxPhysical(){
        return strength * 5 + constitution * 3;
    }

    public int getMaxSurgical(){
        return spirit * 2 + mind + knowledge * 5;
    }
}
