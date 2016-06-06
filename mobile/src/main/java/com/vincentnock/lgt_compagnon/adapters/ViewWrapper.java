package com.vincentnock.lgt_compagnon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by vincent on 05/03/2015.
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}