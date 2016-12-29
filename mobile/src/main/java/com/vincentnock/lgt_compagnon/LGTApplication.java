package com.vincentnock.lgt_compagnon;

import android.app.Application;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vincent on 05/06/2016.
 */
public class LGTApplication extends Application {

    Cloudinary cloudinary;

    @Override
    public void onCreate() {
        super.onCreate();

        initCloudinary();
        initRealm();
    }

    private void initCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "lgt-compagnon");
        config.put("api_key", "241315319789945");
        config.put("api_secret", "F2jZrcHAxlVPMsU5VeCZO8oRLGs");
        cloudinary = new Cloudinary(config);
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }
}
