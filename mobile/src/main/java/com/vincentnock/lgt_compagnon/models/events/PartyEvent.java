package com.vincentnock.lgt_compagnon.models.events;

import com.vincentnock.lgt_compagnon.models.Party;

/**
 * Created by yyouf on 11/06/2016.
 */
public class PartyEvent {
    public Party party;

    public PartyEvent(Party party) {
        this.party = party;
    }
}
