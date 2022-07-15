package com.growuphappily.theseus.world.player.identity;

import com.growuphappily.theseus.world.player.Player;

import java.io.Serializable;

public interface IIdentity extends Serializable {
    void onSelect(Player p);

}
