package com.vincentnock.lgt_compagnon;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vincent on 05/06/2016.
 */
public class LGTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
