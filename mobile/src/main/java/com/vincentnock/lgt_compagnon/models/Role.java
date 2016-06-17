package com.vincentnock.lgt_compagnon.models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 05/06/2016.
 */
public class Role extends RealmObject {

    @PrimaryKey
    int id;
    public String name;
    public String imagePath;
    public String description;
    public int maxCount;

    @Ignore
    public int currentCount;

    public Role() {
    }

    public Role(int id, String name, String imagePath, String description, int maxCount) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.maxCount = maxCount;
    }

}
