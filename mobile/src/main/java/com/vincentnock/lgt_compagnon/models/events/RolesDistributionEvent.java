package com.vincentnock.lgt_compagnon.models.events;

/**
 * Created by yyouf on 12/06/2016.
 */
public class RolesDistributionEvent {

    public boolean maxReached;

    public RolesDistributionEvent(boolean maxReached) {
        this.maxReached = maxReached;
    }
}
