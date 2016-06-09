package com.vincentnock.lgt_compagnon.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.vincentnock.lgt_compagnon.models.Party;
import com.vincentnock.lgt_compagnon.views.PartyItemView;
import com.vincentnock.lgt_compagnon.views.PartyItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by vincent on 08/06/2016.
 */
@EBean
public class PartiesAdapter extends RecyclerViewAdapterBase<Party, PartyItemView> {

    @RootContext
    Context context;

    public PartiesAdapter() {
    }

    public void addParty(Party party) {
        items.add(party);
        notifyItemInserted(0);
    }

    @Override
    protected PartyItemView onCreateItemView(ViewGroup parent, int viewType) {
        return PartyItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<PartyItemView> holder, int position) {
        holder.getView().bind(getItem(position));
    }
}
