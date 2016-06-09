package com.vincentnock.lgt_compagnon.fragments;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.adapters.PlayersAdapter;
import com.vincentnock.lgt_compagnon.models.Player;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A fragment representing a list of Items.
 * <p>
 */
@EFragment(R.layout.fragment_players_list)
@OptionsMenu(R.menu.menu_board)
public class PlayersFragment extends DialogFragment {

    @ViewById
    RecyclerView list;

    @Bean
    PlayersAdapter adapter;

    @AfterViews
    void init() {

        Realm realm = Realm.getDefaultInstance();

        realm.where(Player.class).findAllAsync().asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(players -> {
                    adapter.setItems(players);
                });


        list.setAdapter(adapter);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_board, menu);

        MenuItem item = menu.findItem(R.id.menuPlayers);
        menu.add("PLOP");
    }
}
