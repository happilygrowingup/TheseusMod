package com.growuphappily.theseus.world.player.identity;

import com.growuphappily.theseus.world.player.Player;

public class IdentityInvestigator implements IIdentity{
    @Override
    public void onSelect(Player p) {
        p.card.attrs.speed += 4;
        p.card.attrs.strength += 2;
        p.card.attrs.spirit += 2;
    }
}
