package com.vincentnock.lgt_compagnon.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.models.PlayerRole;
import com.vincentnock.lgt_compagnon.models.events.SeeRolesEvent;
import com.vincentnock.lgt_compagnon.models.events.ShowRoleEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by yyouf on 11/06/2016.
 */
@EViewGroup(R.layout.view_player_role)
public class PlayerRoleView extends RelativeLayout {

    @ViewById
    ImageView ivPlayer, ivRole;

    @ViewById
    TextView tvPlayer;

    @ViewById
    ProgressBar pbLoading;

    PlayerRole playerRole;


    public PlayerRoleView(Context context) {
        super(context);
    }

    public PlayerRoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerRoleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
        EventBus.getDefault().register(this);
    }

    public void bind(PlayerRole playerRole) {
        this.playerRole = playerRole;
        pbLoading.setVisibility(VISIBLE);
        Picasso picasso = Picasso.with(getContext());
        picasso.setLoggingEnabled(true);
        picasso.load(playerRole.player.getImageUrl()).into(ivPlayer, new Callback() {
            @Override
            public void onSuccess() {
                pbLoading.setVisibility(GONE);
            }

            @Override
            public void onError() {
                Log.w("DEBUG", "failed loading image");
            }
        });

        if (playerRole.role != null) {
            picasso.load(playerRole.role.imagePath).into(ivRole);
        }

        tvPlayer.setText(playerRole.player.name);
    }

    @Subscribe
    public void seeRole(SeeRolesEvent event) {
        ivRole.setVisibility(event.seeRoles ? VISIBLE : INVISIBLE);
    }

    @Click
    void btnShowRole() {
        EventBus.getDefault().post(new ShowRoleEvent(playerRole));
    }

}
