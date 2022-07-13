package com.growuphappily.theseus.world.player;

import com.growuphappily.theseus.Theseus;
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

    // Regen health, con
    public void doRegen(){

    }
}
