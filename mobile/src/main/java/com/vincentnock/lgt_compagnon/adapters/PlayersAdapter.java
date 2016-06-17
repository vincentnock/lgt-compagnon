package com.vincentnock.lgt_compagnon.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.views.PlayerItemView;
import com.vincentnock.lgt_compagnon.views.PlayerItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 05/06/2016.
 */
@EBean
public class PlayersAdapter extends HeaderFooterRecyclerViewAdapter<Player, PlayerItemView> {

    @RootContext
    Context context;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyContentItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public List<Player> getSelectedItems() {
        List<Player> items =
                new ArrayList<Player>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(getItem(selectedItems.keyAt(i)));
        }
        return items;
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
    protected PlayerItemView onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return null;
    }

    @Override
    protected PlayerItemView onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return null;
    }

    @Override
    protected PlayerItemView onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        return PlayerItemView_.build(context);
    }

    @Override
    protected void onBindHeaderItemViewHolder(ViewWrapper<PlayerItemView> headerViewHolder, int position) {

    }

    @Override
    protected void onBindFooterItemViewHolder(ViewWrapper<PlayerItemView> footerViewHolder, int position) {

    }

    @Override
    protected void onBindContentItemViewHolder(ViewWrapper<PlayerItemView> contentViewHolder, int position) {

        PlayerItemView view = contentViewHolder.getView();
        view.bind(getItem(position), selectedItems.get(position, false));
    }
}
