package com.vincentnock.lgt_compagnon.views;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.models.Role;
import com.vincentnock.lgt_compagnon.models.events.DecrementRoleEvent;
import com.vincentnock.lgt_compagnon.models.events.IncrementRoleEvent;
import com.vincentnock.lgt_compagnon.models.events.RolesDistributionEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by yyouf on 12/06/2016.
 */
@EViewGroup(R.layout.view_role_item)
public class RoleItemView extends RelativeLayout {

    @ViewById
    TextView tvName, tvCount;

    @ViewById
    ImageView ivRole;


    @ViewById
    Button btnMinus, btnPlus;
    private Role role;

    public RoleItemView(Context context) {
        super(context);
    }

    public RoleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
        EventBus.getDefault().register(this);
    }

    public void bind(Role role) {
        this.role = role;
        Picasso.with(getContext()).load(role.imagePath).into(ivRole);
        tvName.setText(role.name);
        updateButtons();
    }

    void updateButtons() {
        btnMinus.setEnabled(role.currentCount > 0);
        btnPlus.setEnabled(role.currentCount < role.maxCount);
    }

    void updateCount() {
        tvCount.setText(String.valueOf(role.currentCount));
    }

    @Click
    void btnMinus() {
        role.currentCount--;
        updateCount();
        updateButtons();
        EventBus.getDefault().post(new DecrementRoleEvent());
    }

    @Click
    void btnPlus() {
        role.currentCount++;
        updateCount();
        updateButtons();
        EventBus.getDefault().post(new IncrementRoleEvent());
    }

    @Subscribe
    public void maxRolesDistributedReached(RolesDistributionEvent event) {
        btnPlus.setEnabled(!event.maxReached);
    }

    @Click
    void btnInfo() {
        new AlertDialog.Builder(getContext())
                .setTitle(role.name)
                .setMessage(role.description)
                .show();
    }
}
