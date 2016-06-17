package com.vincentnock.lgt_compagnon.fragments;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.RolesAdapter;
import com.vincentnock.lgt_compagnon.models.Role;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yyouf on 12/06/2016.
 */
@EFragment(R.layout.fragment_roles)

public class RolesFragment extends DialogFragment {

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    Toolbar toolbar;

    @Bean
    RolesAdapter adapter;

    @FragmentArg
    int numberOfPlayers;
    private Realm realm;

    @AfterViews
    void init() {

        realm = Realm.getDefaultInstance();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        realm.where(Role.class).findAllAsync().asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(roles -> {
                    adapter.setItems(roles);
                });

        adapter.setNumberOfPlayers(numberOfPlayers);
    }

    @Override
    public void onStop() {
        realm.close();
        super.onStop();
    }

}
