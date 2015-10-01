package com.example.yrp.nextapp.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.yrp.nextapp.ModelOngkir.OngkirItem;
import com.example.yrp.nextapp.R;

import java.util.ArrayList;

/**
 * Created by YRP on 29/07/2015.
 */
public class OngkirListAdapter extends InvoiceBaseAdapter{
    public OngkirListAdapter(Context context, ArrayList<OngkirItem> ongkirItemArrayAdapter ){
        this.context =context;
        listItems=ongkirItemArrayAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView ==null){
//            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView=mInflater.inflate(R.layout.simple_dropdown_item_1line);
//        }
        return null;
    }
}
