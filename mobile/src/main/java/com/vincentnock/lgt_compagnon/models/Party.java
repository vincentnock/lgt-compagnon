package com.vincentnock.lgt_compagnon.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 05/06/2016.
 */
public class Party extends RealmObject {

    @PrimaryKey
    public String uuid;

    public Date createdAt;

    public RealmList<PlayerRole> playerRoles;

    public int number;


    public CharSequence getCreatedAtFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM HH:mm", Locale.FRANCE);
        return sdf.format(createdAt);
    }
}
