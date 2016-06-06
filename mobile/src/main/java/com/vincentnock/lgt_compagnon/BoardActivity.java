package com.vincentnock.lgt_compagnon;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.vincentnock.lgt_compagnon.fragments.PlayersFragment_;
import com.vincentnock.lgt_compagnon.models.Party;
import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.models.PlayerRole;
import com.vincentnock.lgt_compagnon.models.Role;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@EActivity(R.layout.activity_board)
@OptionsMenu(R.menu.menu_board)
public class BoardActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable;
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener;
    private final Runnable mHideRunnable;
    private final Runnable mShowPart2Runnable;
    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    public BoardActivity() {
        mHidePart2Runnable = () -> {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        };

        mDelayHideTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (AUTO_HIDE) {
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                }
                return false;
            }
        };

        mHideRunnable = this::hide;

        mShowPart2Runnable = () -> {
            // Delayed display of UI elements
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        };
    }

    @AfterViews
    void init() {

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(view -> toggle());

        createUsers();
    }

    @OptionsItem
    void menuPlayers() {
        new PlayersFragment_().show(getFragmentManager(), "players");
    }

    private void createUsers() {
        List<Player> players = new ArrayList<>();

        players.add(new Player(2, "Amaury", "100011987899127", null));
        players.add(new Player(1, "Vincent", "587814415", null));
        players.add(new Player(3, "Kevin", "634197245", null));

        List<Role> roles = new ArrayList<>();

        roles.add(new Role("Loup-Garou", "http://www.cyberfab.fr/gfx/loupsgarous/carte_loups.jpg"));
        roles.add(new Role("Voyante", "http://www.cyberfab.fr/gfx/loupsgarous/carte_voyante.jpg"));
        roles.add(new Role("Villageois", "http://www.cyberfab.fr/gfx/loupsgarous/carte_villageois.jpg"));
        roles.add(new Role("Sorcière", "http://www.cyberfab.fr/gfx/loupsgarous/carte_sorciere.jpg"));
        roles.add(new Role("Chasseur", "http://www.cyberfab.fr/gfx/loupsgarous/carte_chasseur.jpg"));
        roles.add(new Role("Cupidon", "http://www.cyberfab.fr/gfx/loupsgarous/carte_cupidon.jpg"));
        roles.add(new Role("Petite fille", "http://www.cyberfab.fr/gfx/loupsgarous/carte_petite_fille.jpg"));
        roles.add(new Role("Voleur", "http://www.cyberfab.fr/gfx/loupsgarous/carte_voleur.jpg"));

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(players);
        realm.copyToRealmOrUpdate(roles);
        realm.commitTransaction();

        Log.d("PLOP", realm.getPath());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Click
    void btnDummy() {
//        new PlayersFragment_().show(getFragmentManager(), "players");

        Realm realm = Realm.getDefaultInstance();
        List<Player> players = realm.where(Player.class).findAll();
        List<Role> roles = realm.where(Role.class).findAll().subList(0, 3);

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        realm.executeTransaction(realm1 -> {
            Party party = realm.createObject(Party.class);
            party.uuid = UUID.randomUUID().toString();

            for (int i = 0; i < players.size(); i++) {
                PlayerRole playerRole = realm.createObject(PlayerRole.class);
                playerRole.party = party;
                playerRole.player = players.get(i);
                playerRole.role = roles.get(indexes.get(i));
                party.playerRoles.add(playerRole);
            }
        });

        realm.where(Party.class).findAllAsync().asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parties -> {
                    for (Party party : parties) {
                        Log.i("PLOP", "" + party.uuid);

                        for (PlayerRole playerRole : party.playerRoles) {
                            Log.d("PLOP", "" + playerRole.player.name + " - " + playerRole.role.name);
                        }
                    }
                });
    }
}
