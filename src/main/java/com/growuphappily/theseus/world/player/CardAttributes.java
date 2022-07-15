package com.growuphappily.theseus.world.player;

import net.minecraft.entity.ai.attributes.Attributes;
import org.w3c.dom.Attr;

import java.io.Serializable;
import java.util.Random;

public class CardAttributes implements Serializable {
    public int speed;
    public boolean speedLock;
    public int constitution;
    public boolean constitutionLock;
    public int strength;
    public boolean strengthLock;
    public int adaptive;
    public boolean adaptiveLock;
    public int spirit;
    public boolean spiritLock;
    public int mind;
    public boolean mindLock;
    public int knowledge;
    public int luck;
    public float criticalPercentage = 0.05f;
    public int genTime = 4;

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

    public boolean tryLock(EnumAttributes attr){
        if(genTime < 3){
            return false;
        }
        if(attr == EnumAttributes.ADAPTIVE){adaptiveLock = true;}
        if(attr == EnumAttributes.CONSTITUTION){constitutionLock = true;}
        if(attr == EnumAttributes.MIND){mindLock = true;}
        if(attr == EnumAttributes.SPEED){speedLock = true;}
        if(attr == EnumAttributes.SPIRIT){spiritLock = true;}
        if(attr == EnumAttributes.STRENGTH){strengthLock = true;}
        return true;
    }

    public void genAttribute(){
        if(genTime > 0){
            if(!speedLock){
                speed = new Random().nextInt(25) + 5;
            }
            if(!spiritLock){
                spirit = new Random().nextInt(25) + 5;
            }
            if(!adaptiveLock){
                adaptive = new Random().nextInt(25) + 5;
            }
            if(!strengthLock){
                strength = new Random().nextInt(25) + 5;
            }
            if(!mindLock){
                mind = new Random().nextInt(25) + 5;
            }
            luck = new Random().nextInt(50) + 30;
            genTime--;
        }
    }
}
