package com.vincentnock.lgt_compagnon.models;

import io.realm.RealmObject;

/**
 * Created by vincent on 05/06/2016.
 */
public class PlayerRole extends RealmObject {

    public Party party;
    public Player player;
    public Role role;
}
