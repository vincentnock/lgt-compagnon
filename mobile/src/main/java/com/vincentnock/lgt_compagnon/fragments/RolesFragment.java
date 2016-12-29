package com.vincentnock.lgt_compagnon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.RolesAdapter;
import com.vincentnock.lgt_compagnon.models.Role;
import com.vincentnock.lgt_compagnon.models.events.DecrementRoleEvent;
import com.vincentnock.lgt_compagnon.models.events.IncrementRoleEvent;
import com.vincentnock.lgt_compagnon.models.events.RolesDistributionEvent;
import com.vincentnock.lgt_compagnon.models.events.RolesEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yyouf on 12/06/2016.
 */
@EFragment(R.layout.fragment_roles)
public class RolesFragment extends DialogFragment implements Toolbar.OnMenuItemClickListener {

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    Toolbar toolbar;

    @Bean
    RolesAdapter adapter;

    @FragmentArg
    int numberOfPlayers;
    private Realm realm;

    int currentCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @AfterViews
    void init() {

        currentCount = numberOfPlayers;

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
        updateTitle(currentCount);

        toolbar.inflateMenu(R.menu.menu_add_player);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Subscribe
    public void onIncrementEvent(IncrementRoleEvent event) {
        updateTitle(--currentCount);
    }

    @Subscribe
    public void onDecerementEvent(DecrementRoleEvent event) {
        updateTitle(++currentCount);
    }

    void updateTitle(int count) {
        toolbar.setTitle(count + " restants à distribuer");
    }

    @Override
    public void onStop() {
        realm.close();
        super.onStop();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menuValid) {
            menuValid();
        }
        return true;
    }

    private void menuValid() {
        EventBus.getDefault().post(new RolesEvent(adapter.getItems()));
        dismiss();
    }
}
