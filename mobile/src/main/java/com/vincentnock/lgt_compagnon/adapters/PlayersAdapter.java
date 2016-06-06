package com.vincentnock.lgt_compagnon.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.views.PlayerView;
import com.vincentnock.lgt_compagnon.views.PlayerView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by vincent on 05/06/2016.
 */
@EBean
public class PlayersAdapter extends HeaderFooterRecyclerViewAdapter<Player, PlayerView> {

    @RootContext
    Context context;

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
    protected PlayerView onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return null;
    }

    @Override
    protected PlayerView onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return null;
    }

    @Override
    protected PlayerView onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        return PlayerView_.build(context);
    }

    @Override
    protected void onBindHeaderItemViewHolder(ViewWrapper<PlayerView> headerViewHolder, int position) {

    }

    @Override
    protected void onBindFooterItemViewHolder(ViewWrapper<PlayerView> footerViewHolder, int position) {

    }

    @Override
    protected void onBindContentItemViewHolder(ViewWrapper<PlayerView> contentViewHolder, int position) {

        contentViewHolder.getView().bind(getItem(position));
    }
}
