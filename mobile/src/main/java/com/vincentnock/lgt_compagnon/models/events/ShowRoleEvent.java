package com.vincentnock.lgt_compagnon.models.events;

import com.vincentnock.lgt_compagnon.models.PlayerRole;

/**
 * Created by yyouf on 19/06/2016.
 */
public class ShowRoleEvent {

    public PlayerRole playerRole;

    public ShowRoleEvent(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }
}
