package com.vincentnock.lgt_compagnon.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.PartiesAdapter;
import com.vincentnock.lgt_compagnon.models.Party;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by vincent on 08/06/2016.
 */
@EFragment(R.layout.fragment_parties)
@OptionsMenu(R.menu.menu_parties)
public class PartiesFragment extends Fragment {

    @ViewById
    RecyclerView recyclerView;

    @Bean
    PartiesAdapter adapter;

    Realm realm = Realm.getDefaultInstance();

    @AfterViews
    void init() {

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);

        realm.where(Party.class).findAllAsync().sort("createdAt", Sort.DESCENDING).asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parties -> {
                    adapter.getItems().clear();
                    adapter.getItems().addAll(parties);
                });
    }

    @OptionsItem
    void menuNewParty() {

        realm.executeTransaction(realm1 -> {
            Party party = realm.createObject(Party.class);
            party.uuid = UUID.randomUUID().toString();
            party.createdAt = new Date();
            adapter.addParty(party);
            recyclerView.scrollToPosition(0);
        });


    }
}
