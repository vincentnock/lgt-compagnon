package com.vincentnock.lgt_compagnon.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.vincentnock.lgt_compagnon.models.Role;
import com.vincentnock.lgt_compagnon.models.events.DecrementRoleEvent;
import com.vincentnock.lgt_compagnon.models.events.IncrementRoleEvent;
import com.vincentnock.lgt_compagnon.models.events.RolesDistributionEvent;
import com.vincentnock.lgt_compagnon.views.RoleItemView;
import com.vincentnock.lgt_compagnon.views.RoleItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by yyouf on 12/06/2016.
 */
@EBean
public class RolesAdapter extends RecyclerViewAdapterBase<Role, RoleItemView> {

    @RootContext
    Context context;

    int currentDistributedRoles;
    int numberOfPlayers;

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @AfterInject
    void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected RoleItemView onCreateItemView(ViewGroup parent, int viewType) {
        return RoleItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RoleItemView> holder, int position) {
        holder.getView().bind(getItem(position));
    }

    @Subscribe
    public void decrementEvent(DecrementRoleEvent event) {
        if (numberOfPlayers == currentDistributedRoles) {
            EventBus.getDefault().post(new RolesDistributionEvent(false));
        }
        currentDistributedRoles--;
    }

    @Subscribe
    public void incerementEvent(IncrementRoleEvent event) {
        currentDistributedRoles++;
        if (numberOfPlayers == currentDistributedRoles) {
            EventBus.getDefault().post(new RolesDistributionEvent(true));
        }
    }

}
