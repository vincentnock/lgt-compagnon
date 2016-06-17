package com.vincentnock.lgt_compagnon.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.vincentnock.lgt_compagnon.models.PlayerRole;
import com.vincentnock.lgt_compagnon.views.PlayerRoleView;
import com.vincentnock.lgt_compagnon.views.PlayerRoleView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by yyouf on 11/06/2016.
 */
@EBean
public class PlayersRolesAdapter extends HeaderFooterRecyclerViewAdapter<PlayerRole, PlayerRoleView> {

    @RootContext
    Context context;

    public void addPlayerRole(PlayerRole playerRole) {
        items.add(playerRole);
        notifyContentItemInserted(items.size() - 1);
    }

    @Override
    protected int getHeaderItemCount() {
        return 0;
    }

    @Override
    protected int getFooterItemCount() {
        return 0;
    }

    @Override
    protected int getContentItemCount() {
        return items.size();
    }

    @Override
    protected PlayerRoleView onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return null;
    }

    @Override
    protected PlayerRoleView onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return null;
    }

    @Override
    protected PlayerRoleView onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        return PlayerRoleView_.build(context);
    }

    @Override
    protected void onBindHeaderItemViewHolder(ViewWrapper<PlayerRoleView> headerViewHolder, int position) {

    }

    @Override
    protected void onBindFooterItemViewHolder(ViewWrapper<PlayerRoleView> footerViewHolder, int position) {

    }

    @Override
    protected void onBindContentItemViewHolder(ViewWrapper<PlayerRoleView> contentViewHolder, int position) {
        contentViewHolder.getView().bind(getItem(position));
    }
}
