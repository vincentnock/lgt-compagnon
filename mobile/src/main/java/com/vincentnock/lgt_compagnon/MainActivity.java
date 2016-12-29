package com.vincentnock.lgt_compagnon;

import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vincentnock.lgt_compagnon.fragments.PartiesFragment;
import com.vincentnock.lgt_compagnon.fragments.PartiesFragment_;
import com.vincentnock.lgt_compagnon.fragments.PlayersRolesFragment;
import com.vincentnock.lgt_compagnon.fragments.PlayersRolesFragment_;
import com.vincentnock.lgt_compagnon.managers.DataManager;
import com.vincentnock.lgt_compagnon.models.events.PartyEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    boolean smsConfigured;

    private static final String TAG = "MAIN";

    @AfterViews
    void init() {

//        DataManager.clean();
        DataManager.createUsers();
        DataManager.createRoles();

        PartiesFragment fragment = PartiesFragment_.builder().build();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();

//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission);
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

    @OptionsItem
    void menuSendSMS() {
        if (smsConfigured)
            return;

        smsConfigured = true;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("player_roles");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if (value != null) {
                    String[] playersRoles = value.split(";");
                    for (int i = 0; i < playersRoles.length; i++) {
                        String playersRole = playersRoles[i];
                        String[] data = playersRole.split(":");
                        if (data.length == 3) {
                            String number = data[0];
                            String role = data[1];

                            String lineSep = ".\n";
                            String message = "Ton rôle pour la partie n° "+
                                    data[2]
                                    +" sera…\n" +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    lineSep +
                                    role;

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, message, null, null);
                        }
                    }

                    myRef.removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
