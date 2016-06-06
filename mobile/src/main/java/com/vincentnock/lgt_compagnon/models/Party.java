package com.vincentnock.lgt_compagnon.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 05/06/2016.
 */
public class Party extends RealmObject {

    @PrimaryKey
    public String uuid;

    public RealmList<PlayerRole> playerRoles;
}
