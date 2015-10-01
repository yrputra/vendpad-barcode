package com.example.yrp.nextapp.JsonParsingVolley;

/**
 * Created by YRP on 27/07/2015.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yrp.nextapp.JsonParsingVolley.adapter.CustomListAdapter;
import com.example.yrp.nextapp.JsonParsingVolley.app.AppController;
import com.example.yrp.nextapp.JsonParsingVolley.model.movie;
import com.example.yrp.nextapp.R;

public class MainVolleyParse extends Activity {

    //    Log tag
    private static final String TAG = MainVolleyParse.class.getSimpleName();

    //    movie json url
    private static final String url = "http://vendpad.com/api/product/4";
//    http://api.androidhive.info/json/movies.json
    private ProgressDialog pDialog;
    private List<movie> movieList = new ArrayList<movie>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_volley);

        listView = (ListView) findViewById(R.id.listMovie);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.show();

        //changing action bar color
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));

        //creting volley request
//        JsonArrayRequest movieReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d(TAG, response.toString());
//                pDialog.dismiss();
//
//                //parsing json
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject object = response.getJSONObject(i);
//                        movie mov = new movie();
//                        mov.setTitle(object.getString("title"));
//                        mov.setThumbnailUrl(object.getString("image"));
//                        mov.setRating(((Number) object.get("rating")).doubleValue());
//                        mov.setYear(object.getInt("releaseYear"));
//
//                        //genre is jsonArray
//                        JSONArray array = object.getJSONArray("genre");
//                        ArrayList<String> genre = new ArrayList<>();
//                        for (int j = 0; j < array.length();j++){
//                            genre.add((String) array.get(j));
//                        }
//                        mov.setGenre(genre);
//
//                        //adding movie to movie array
//                        movieList.add(mov);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                // notifying list adapter about data changes
//                // so that it renders the list view with updated data
//                adapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                VolleyLog.d(TAG,"Error: "+volleyError.getMessage());
//                pDialog.dismiss();
//            }
//        });
//        AppController.getInstance().addToRequestQueue(movieReq);
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("json", Integer.toString(response.length()));
                try{
                    Iterator iterator = response.keys();
                    while (iterator.hasNext()){
                        String key = (String) iterator.next();
                        JSONArray arr = response.getJSONArray(key);
                        for(int i=0;i<=arr.length();i++){
                            JSONObject object = arr.getJSONObject(i);
                            movie mov = new movie();
                            mov.setTitle(object.getString("name"));
                            mov.setYear(object.getInt("stock"));
                            mov.setThumbnailUrl(object.getString("picture"));
                        }

                        pDialog.dismiss();
                    }
                } catch (JSONException e){
                    pDialog.dismiss();
                    Log.d("response", e.toString());
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.d("error response", error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(obj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}