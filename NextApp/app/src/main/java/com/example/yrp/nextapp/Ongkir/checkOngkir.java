package com.example.yrp.nextapp.Ongkir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yrp.nextapp.ModelOngkir.OngkirResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YRP on 30/07/2015.
 */
public class checkOngkir {

    //    ProgressDialog progressDialog;
//    Context context;
    public static void CobaOngkir(final ProgressDialog progressDialog, final Context context) {
//        ?origin=31&destination=32&weight=2000&courier=jne
        final String URL = "http://vendpad.com/api/data/ongkir";
        progressDialog.setMessage("Checking ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObjectRequest jObj = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject obj = jsonObject.getJSONObject("OKE");
//                    OngkirResultActivity oar = new OngkirResultActivity();
//                    OngkirResult oarr = new OngkirResult();
                    String service = obj.getString("service");
                    progressDialog.dismiss();
                    Toast.makeText(context, service, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AppController.getInstance().addToReqQueue(jObj, "jreq");
    }

    public static void checkOngkir(final Context context, final AppController appController, final String id_origin, final String id_destination, final String weight, final String courier, final ProgressDialog pDialog, final Activity activity) {
        final RequestQueue mrRequestQueue = appController.getRequestQueue();
        final String URL = "http://vendpad.com/api/data/ongkir";

        pDialog.setMessage("Checking ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("id_origin", id_origin);
        params.put("id_destination", id_destination);
        params.put("weight", weight);
        params.put("courier", courier);

        JsonObjectRequest jObj = new JsonObjectRequest(Request.Method.GET, URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject jObj = jsonObject.getJSONObject("OKE");
                        String service = jObj.getString("service");
                        pDialog.dismiss();
                        Toast.makeText(context, service, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AppController.getInstance().addToReqQueue(jObj, "jreq");
    }
}
