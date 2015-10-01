package com.example.yrp.nextapp.Controller;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Roland on 2015-06-23.
 */
public abstract class InvoiceBaseAdapter<E> extends BaseAdapter {
    protected Context context;
    protected ArrayList<E> listItems;

    public void add(Object item) {
        listItems.add((E) item);
    }

    @Override
    public int getCount() { return listItems.size(); }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }
}
