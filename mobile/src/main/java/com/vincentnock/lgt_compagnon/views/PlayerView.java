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
import com.vincentnock.lgt_compagnon.models.Player;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by vincent on 05/06/2016.
 */
@EViewGroup(R.layout.view_player_item)
public class PlayerView extends RelativeLayout {

    @ViewById
    ImageView ivPlayer;

    @ViewById
    TextView tvPlayer;

    @ViewById
    ProgressBar pbLoading;

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(Player player) {

        pbLoading.setVisibility(VISIBLE);
        Picasso picasso = Picasso.with(getContext());
        picasso.setLoggingEnabled(true);
        picasso.load("https://graph.facebook.com/" + player.facebookID + "/picture?type=large").into(ivPlayer, new Callback() {
            @Override
            public void onSuccess() {
                pbLoading.setVisibility(GONE);
            }

            @Override
            public void onError() {
                Log.w("DEBUG", "failed loading image");
            }
        });
        tvPlayer.setText(player.name);
    }
}
