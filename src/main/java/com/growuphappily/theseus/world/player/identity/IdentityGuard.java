package com.growuphappily.theseus.world.player.identity;

import com.growuphappily.theseus.world.player.Player;

public class IdentityGuard implements IIdentity{
    @Override
    public void onSelect(Player p) {
        p.card.attrs.constitution += 4;
    }
}
