package com.vincentnock.lgt_compagnon.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 05/06/2016.
 */
public class Role extends RealmObject {

    @PrimaryKey
    public String name;
    public String imagePath;

    public Role() {
    }

    public Role(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
