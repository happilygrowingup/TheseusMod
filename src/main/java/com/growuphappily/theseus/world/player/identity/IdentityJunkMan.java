package com.growuphappily.theseus.world.player.identity;

import com.growuphappily.theseus.world.player.Player;

public class IdentityJunkMan implements IIdentity{
    @Override
    public void onSelect(Player p) {
        p.card.attrs.speed += 4;
        p.card.attrs.constitution += 4;
        p.card.attrs.strength += 4;
        p.card.attrs.adaptive += 4;
        p.card.attrs.spirit += 4;
        p.card.attrs.mind += 4;
        p.card.attrs.knowledge += 4;
        p.card.attrs.luck += 4;
    }
}
