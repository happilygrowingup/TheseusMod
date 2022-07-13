package com.growuphappily.theseus.world.player;

import com.growuphappily.theseus.world.player.talents.Talent;

import java.io.Serializable;
import java.util.ArrayList;

public class CharacterCard implements Serializable {
    public ArrayList<Talent> talents;
    public CardAttributes attrs;

    public CharacterCard(ArrayList<Talent> t, CardAttributes cardAttributes){
        talents = t;
        attrs = cardAttributes;
    }
}
