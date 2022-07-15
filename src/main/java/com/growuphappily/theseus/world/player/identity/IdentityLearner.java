package com.growuphappily.theseus.world.player.identity;

import com.growuphappily.theseus.world.player.Player;

public class IdentityLearner implements IIdentity{
    @Override
    public void onSelect(Player p) {
        p.card.attrs.knowledge += 10;
        p.card.attrs.mind += 8;
    }
}
