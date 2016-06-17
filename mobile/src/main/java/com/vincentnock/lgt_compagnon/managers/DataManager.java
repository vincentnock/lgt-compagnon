package com.vincentnock.lgt_compagnon.managers;

import android.util.Log;

import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.models.Role;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by yyouf on 11/06/2016.
 */

public class DataManager {

    public static void createUsers() {
        List<Player> players = new ArrayList<>();

        players.add(new Player(2, "Amaury", "100011987899127", null));
        players.add(new Player(1, "Vincent", "587814415", null));
        players.add(new Player(3, "Kevin", "634197245", null));

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(players);
        realm.commitTransaction();

        realm.close();

        Log.d("PLOP", realm.getPath());
    }

    public static void createRoles() {
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(1, "Loup-Garou", "http://www.cyberfab.fr/gfx/loupsgarous/carte_loups.jpg", "", Integer.MAX_VALUE));
        roles.add(new Role(2, "Voyante", "http://www.cyberfab.fr/gfx/loupsgarous/carte_voyante.jpg", "", 1));
        roles.add(new Role(3, "Villageois", "http://www.cyberfab.fr/gfx/loupsgarous/carte_villageois.jpg", "", Integer.MAX_VALUE));
        roles.add(new Role(4, "Sorcière", "http://www.cyberfab.fr/gfx/loupsgarous/carte_sorciere.jpg", "", 1));
        roles.add(new Role(5, "Chasseur", "http://www.cyberfab.fr/gfx/loupsgarous/carte_chasseur.jpg", "", 1));
        roles.add(new Role(6, "Cupidon", "http://www.cyberfab.fr/gfx/loupsgarous/carte_cupidon.jpg", "", 1));
        roles.add(new Role(7, "Petite fille", "http://www.cyberfab.fr/gfx/loupsgarous/carte_petite_fille.jpg", "", 1));
        roles.add(new Role(8, "Voleur", "http://www.cyberfab.fr/gfx/loupsgarous/carte_voleur.jpg", "", 1));

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(roles);
        realm.commitTransaction();

        realm.close();
    }
}
