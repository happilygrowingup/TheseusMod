package com.growuphappily.theseus.world.player;

import net.minecraft.entity.ai.attributes.Attributes;
import org.w3c.dom.Attr;

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
        p.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1 + speed*0.1);
        p.getEntity().getAttribute(Attributes.MAX_HEALTH).setBaseValue(getMaxHealth());
        p.getEntity().setHealth(p.attrs.health);
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
