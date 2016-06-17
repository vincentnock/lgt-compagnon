package com.vincentnock.lgt_compagnon.fragments;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.PlayersAdapter;
import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.models.events.PlayersEvent;
import com.vincentnock.lgt_compagnon.utils.RecyclerItemClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A fragment representing a list of Items.
 * <p>
 */
@EFragment(R.layout.fragment_players_list)
public class PlayersFragment extends DialogFragment implements Toolbar.OnMenuItemClickListener {

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    Toolbar toolbar;

    @Bean
    PlayersAdapter adapter;

    @AfterViews
    void init() {

        Realm realm = Realm.getDefaultInstance();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        realm.where(Player.class).findAllAsync().asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(players -> {
                    adapter.setItems(players);
                });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            adapter.toggleSelection(position);
        }));

        toolbar.inflateMenu(R.menu.menu_players);
        toolbar.setOnMenuItemClickListener(this);
    }

    void menuNewPlayer() {

    }

    void menuValid() {
        EventBus.getDefault().post(new PlayersEvent(adapter.getSelectedItems()));
        dismiss();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menuAddPlayer) {
            menuNewPlayer();
        } else if (item.getItemId() == R.id.menuValid) {
            menuValid();
        }
        return true;
    }
}
