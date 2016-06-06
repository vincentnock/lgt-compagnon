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

    public String name;
    public String facebookID;
    public String imagePath;

    public Player() {
    }

    public Player(int id, String name, String facebookID, String imagePath) {
        this.id = id;
        this.name = name;
        this.facebookID = facebookID;
        this.imagePath = imagePath;
    }

    public Uri getImageUri() {

        Uri myUri = Uri.parse("https://graph.facebook.com/" + facebookID + "/picture?type=large");
        return myUri;
    }
}
