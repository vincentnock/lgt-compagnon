package com.vincentnock.lgt_compagnon.models.events;

import com.vincentnock.lgt_compagnon.models.Player;

/**
 * Created by vincent on 25/12/2016.
 */
public class PlayerEvent {
    public Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }
}
