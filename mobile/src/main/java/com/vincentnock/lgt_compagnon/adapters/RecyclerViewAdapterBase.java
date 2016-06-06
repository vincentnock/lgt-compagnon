package com.vincentnock.lgt_compagnon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 05/03/2015.
 */
public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<T>();
    protected int numColumns;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public int getSpanCount(int position, int totalSpan) {
        return 1;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public int getNumColumns() {
        return numColumns;
    }
}
