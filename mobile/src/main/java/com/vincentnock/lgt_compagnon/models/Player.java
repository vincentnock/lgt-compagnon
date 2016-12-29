package com.vincentnock.lgt_compagnon.models;

import android.net.Uri;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 05/06/2016.
 */
public class Player extends RealmObject {

    @PrimaryKey
    public int id;

    public String uuid;
    public String name;
    public String facebookID;
    public String imagePath;
    public String phoneNumber;

    public Player() {
    }

    public Player(int id, String name, String facebookID, String imagePath, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.facebookID = facebookID;
        this.imagePath = imagePath;
        this.phoneNumber = phoneNumber;
    }

    private String getFacebookImageUrl() {
        return "https://graph.facebook.com/" + facebookID + "/picture?type=large";
    }

    public String getImageUrl() {
        if (imagePath != null)
            return imagePath;
        else
            return getFacebookImageUrl();
    }
}
