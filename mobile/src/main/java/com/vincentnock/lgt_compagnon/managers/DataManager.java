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

        players.add(new Player(2, "Amaury", "100011987899127", null, "0637448364"));
        players.add(new Player(1, "Vincent", "587814415", null, "0634090691"));
        players.add(new Player(3, "Kevin", "634197245", null, "0643978352"));
//        players.add(new Player(4, "Sanders", "100007141547338", null));
        players.add(new Player(5, "Mike", "100012005266988", null, "0689321563"));
        players.add(new Player(6, "Florian", "1218935323", null, "0688634770"));
        players.add(new Player(7, "Damien", "1118089798", null, "0635288074"));
        players.add(new Player(8, "Franck", "542049118", null, "0629172361"));
        players.add(new Player(9, "Jessica", "100001310198179", null, "0629881811"));
//        players.add(new Player(10, "Abel", "653828377", null, null));
        players.add(new Player(11, "Valentina", "528891555", null, "0642655838"));
//        players.add(new Player(12, "Mathilde", "1615537148", null));
//        players.add(new Player(13, "Rosario", "100001679642132", null, null));
//        players.add(new Player(14, "Adrien", "1150166711", null, null));
//        players.add(new Player(15, "Elsa", "100000311010880", null, null));
        players.add(new Player(16, "Leila", "100000118576278", null, "0666466397"));
        players.add(new Player(17, "Betsy", "1463040486", null, "0674181720"));


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
//        roles.add(new Role(18, "Loup-Garou", "http://www.cyberfab.fr/gfx/loupsgarous/carte_loups.jpg", "", Integer.MAX_VALUE));
        roles.add(new Role(17, "Voyante", "http://www.cyberfab.fr/gfx/loupsgarous/carte_voyante.jpg", "", 1));
        roles.add(new Role(3, "Sorcière", "http://www.cyberfab.fr/gfx/loupsgarous/carte_sorciere.jpg", "", 1));
        roles.add(new Role(4, "Chasseur", "http://www.cyberfab.fr/gfx/loupsgarous/carte_chasseur.jpg", "", 1));
        roles.add(new Role(5, "Cupidon", "http://www.cyberfab.fr/gfx/loupsgarous/carte_cupidon.jpg", "", 1));
        roles.add(new Role(6, "Petite fille", "http://www.cyberfab.fr/gfx/loupsgarous/carte_petite_fille.jpg", "", 1));
        roles.add(new Role(7, "Voleur", "http://www.cyberfab.fr/gfx/loupsgarous/carte_voleur.jpg", "", 1));
        roles.add(new Role(8, "Joueur de flûte", "http://i55.servimg.com/u/f55/18/31/96/00/carte115.png", "", 1));
        roles.add(new Role(9, "Bouc émissaire", "http://i55.servimg.com/u/f55/18/31/96/00/carte114.png", "", 1));
        roles.add(new Role(10, "Idiot du village", "http://i55.servimg.com/u/f55/18/31/96/00/carte113.png", "", 1));
        roles.add(new Role(11, "Ancien", "http://djoach.free.fr/forum/sf/P67/carte_ancien.jpg", "", 1));
        roles.add(new Role(12, "Salvateur", "http://i55.servimg.com/u/f55/18/31/96/00/carte410.png", "", 1));
        roles.add(new Role(13, "Villageois", "http://www.cyberfab.fr/gfx/loupsgarous/carte_villageois.jpg", "", Integer.MAX_VALUE));
//        roles.add(new Role(14, "Villageois", "http://www.cyberfab.fr/gfx/loupsgarous/carte_villageois.jpg", "", Integer.MAX_VALUE));
//        roles.add(new Role(15, "Villageois", "http://www.cyberfab.fr/gfx/loupsgarous/carte_villageois.jpg", "", Integer.MAX_VALUE));
//        roles.add(new Role(16, "Villageois", "http://www.cyberfab.fr/gfx/loupsgarous/carte_villageois.jpg", "", Integer.MAX_VALUE));


        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.delete(Role.class);
        realm.copyToRealmOrUpdate(roles);
        realm.commitTransaction();

        realm.close();
    }

    public static void clean() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
}
