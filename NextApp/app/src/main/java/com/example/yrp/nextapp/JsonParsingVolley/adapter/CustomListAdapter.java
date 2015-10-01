package com.example.yrp.nextapp.JsonParsingVolley.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.yrp.nextapp.JsonParsingVolley.app.AppController;
import com.example.yrp.nextapp.JsonParsingVolley.model.movie;
import com.example.yrp.nextapp.R;

import java.util.List;

/**
 * Created by YRP on 04/08/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<movie> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<movie> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int position) {
        return movieItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);

        TextView title = (TextView) convertView.findViewById(R.id.titleMovie);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

//        getting movie data from the row
        movie m = movieItems.get(position);

//        thumbnail image
        thumbnail.setImageUrl(m.getThumbnailUrl(), imageLoader);

//        title
        title.setText(m.getTitle());

//        rating
        rating.setText("Rating:" + String.valueOf(m.getRating()));
//        genre
        String genreStr = "";
        for (String str : m.getGenre()) {
            genreStr += str + ",";
        }
        genreStr = genreStr.length()>0?genreStr.substring(0,genreStr.length()-2):genreStr;
        genre.setText(genreStr);

//        release year
        year.setText(String.valueOf(m.getYear()));

        return convertView;
    }
}
