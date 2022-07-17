package com.growuphappily.theseus.world.player;

import java.io.Serializable;

public class CharacterCard implements Serializable {
    public CardAttributes attrs;

    public CharacterCard(CardAttributes cardAttributes){
        attrs = cardAttributes;
    }
}
