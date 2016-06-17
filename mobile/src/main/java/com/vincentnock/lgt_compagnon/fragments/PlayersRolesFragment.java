package com.vincentnock.lgt_compagnon.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.PlayersRolesAdapter;
import com.vincentnock.lgt_compagnon.models.Party;
import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.models.PlayerRole;
import com.vincentnock.lgt_compagnon.models.events.PlayersEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yyouf on 11/06/2016.
 */
@EFragment(R.layout.fragment_players_roles)
@OptionsMenu(R.menu.menu_players_roles)
public class PlayersRolesFragment extends Fragment {

    @ViewById
    RecyclerView recyclerView;

    @Bean
    PlayersRolesAdapter adapter;

    @FragmentArg
    String partyUuid;

    Realm realm;
    Party party;

    @AfterViews
    void init() {

        realm = Realm.getDefaultInstance();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        party = realm.where(Party.class).equalTo("uuid", partyUuid).findFirst();

        realm.where(PlayerRole.class).equalTo("party.uuid", partyUuid).findAllAsync().asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playerRoles -> {
                    adapter.setItems(playerRoles);
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        realm.close();
        super.onStop();
    }

    @OptionsItem
    void menuAddPlayer() {
        new PlayersFragment_().show(getFragmentManager(), "add_player");
    }

    @OptionsItem
    void menuSetRoles() {
        new RolesFragment_().show(getFragmentManager(), "select_roles");
//        List<Role> roles = realm.where(Role.class).findAll().subList(0, 3);
//
//        List<Integer> indexes = new ArrayList<>();
//        for (int i = 0; i < roles.size(); i++) {
//            indexes.add(i);
//        }
//        Collections.shuffle(indexes);
//
//        realm.executeTransaction(realm1 -> {
//            Party party = realm.createObject(Party.class);
//            party.uuid = UUID.randomUUID().toString();
//
//            for (int i = 0; i < players.size(); i++) {
//                PlayerRole playerRole = realm.createObject(PlayerRole.class);
//                playerRole.party = party;
//                playerRole.player = players.get(i);
//                playerRole.role = roles.get(indexes.get(i));
//                party.playerRoles.add(playerRole);
//            }
//        });
    }

    @Subscribe
    public void onPlayersAdded(PlayersEvent event) {
        realm.beginTransaction();
        for (Player player : event.players) {
            PlayerRole playerRole = realm.createObject(PlayerRole.class);
            playerRole.party = party;
            playerRole.player = player;
        }
        realm.commitTransaction();
    }
}
