package com.vincentnock.lgt_compagnon.models.events;

import com.vincentnock.lgt_compagnon.models.Player;

import java.util.List;

/**
 * Created by yyouf on 11/06/2016.
 */

public class PlayersEvent {

    public List<Player> players;

    public PlayersEvent(List<Player> players) {
        this.players = players;
    }
}
