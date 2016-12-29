package com.vincentnock.lgt_compagnon.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.PlayersRolesAdapter;
import com.vincentnock.lgt_compagnon.models.Party;
import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.models.PlayerRole;
import com.vincentnock.lgt_compagnon.models.Role;
import com.vincentnock.lgt_compagnon.models.events.PlayersEvent;
import com.vincentnock.lgt_compagnon.models.events.RolesEvent;
import com.vincentnock.lgt_compagnon.models.events.SeeRolesEvent;
import com.vincentnock.lgt_compagnon.models.events.ShowRoleEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @ViewById
    Button btnSeeRoles;

    @ViewById
    ImageView ivRole;

    @ViewById
    View vRole;

    @Bean
    PlayersRolesAdapter adapter;

    @FragmentArg
    String partyUuid;

    List<Role> currentRoles;

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

        btnSeeRoles.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                EventBus.getDefault().post(new SeeRolesEvent(true));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                EventBus.getDefault().post(new SeeRolesEvent(false));
            }
            return true;
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
    void menuSeeRoles() {
        new RolesFragment_.FragmentBuilder_().numberOfPlayers(adapter.getItemCount()).build().show(getFragmentManager(), "see_roles");
    }

    @OptionsItem
    void menuAddPlayer() {
        new PlayersFragment_().show(getFragmentManager(), "add_player");
    }

    @OptionsItem
    void menuSetRoles() {

        if (currentRoles.size() != adapter.getItemCount()) {
            Toast.makeText(getContext(), "Mauvais nombre de rôles", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < currentRoles.size(); i++) {
            Role role = currentRoles.get(i);
            for (int j = 0; j < role.currentCount; j++) {
                indexes.add(i);
            }
        }
        Collections.shuffle(indexes);

        realm.executeTransaction(realm1 -> {
            int index = 0;
            for (PlayerRole playerRole : adapter.getItems()) {
                playerRole.role = currentRoles.get(indexes.get(index));
                index++;
            }
        });

        adapter.notifyDataSetChanged();

//        new RolesFragment_().show(getFragmentManager(), "select_roles");
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

    @OptionsItem
    void menuSaveSMS() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("player_roles");


        List<String> data = new ArrayList<>();
        for (PlayerRole playerRole : adapter.getItems()) {
            String phoneNumber = playerRole.player.phoneNumber;
            if (phoneNumber != null) {
                String s = phoneNumber + ":" + playerRole.role.name + ":" + playerRole.party.number;
                data.add(s);
            }
        }

        myRef.setValue(TextUtils.join(";", data));
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

    @Subscribe
    public void onRolesUpdated(RolesEvent event) {
        currentRoles = event.roles;
    }

    @Subscribe
    public void showRole(ShowRoleEvent showRoleEvent) {
        vRole.setVisibility(View.VISIBLE);
        Picasso.with(getContext()).load(showRoleEvent.playerRole.role.imagePath).into(ivRole);
    }

    @Click
    void btnClose() {
        vRole.setVisibility(View.GONE);
    }
}
