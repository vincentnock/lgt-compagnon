package com.vincentnock.lgt_compagnon;

import android.support.v7.app.AppCompatActivity;

import com.vincentnock.lgt_compagnon.fragments.PartiesFragment;
import com.vincentnock.lgt_compagnon.fragments.PartiesFragment_;
import com.vincentnock.lgt_compagnon.fragments.PlayersRolesFragment;
import com.vincentnock.lgt_compagnon.fragments.PlayersRolesFragment_;
import com.vincentnock.lgt_compagnon.managers.DataManager;
import com.vincentnock.lgt_compagnon.models.events.PartyEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    void init() {

        DataManager.createUsers();
        DataManager.createRoles();

        PartiesFragment fragment = PartiesFragment_.builder().build();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onPartySelected(PartyEvent event) {
        PlayersRolesFragment fragment = PlayersRolesFragment_.builder()
                .partyUuid(event.party.uuid)
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("parties")
                .commit();
    }

    @Override
    public void onBackPressed() {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        // Default action on back pressed
        else super.onBackPressed();
    }
}
