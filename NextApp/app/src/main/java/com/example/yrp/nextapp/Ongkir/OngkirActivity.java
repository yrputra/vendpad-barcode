package com.example.yrp.nextapp.Ongkir;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yrp.nextapp.ModelOngkir.City;
import com.example.yrp.nextapp.ModelOngkir.OngkirItem;
import com.example.yrp.nextapp.ModelOngkir.OngkirResult;
import com.example.yrp.nextapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by YRP on 28/07/2015.
 */
public class OngkirActivity extends Activity {

    String urlCity = "http://vendpad.com/api/data/city";


    private AutoCompleteTextView destination, origin;
    private EditText eWeight;

    private TextView txtCourier, txtService, txtDescription, txtCost, txtEtd, txtNote;
    ListView list;

    private String originID, destinationID, city, weight, courier, sOrigin, sDest;
    private String mService, mDesc, mCost, mEtd, mNote;
    private String urlCost = null;
    private int city_id, kota, id_kota_origin, id_kota_dest;
    private Button btnCekOngkir, btnRefresh;
    private Spinner spinner;
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> ongkirList;
//    ListAdapter mAdapter;

    private static final String TAG_COURIER = "Courier";
    private static final String TAG_SERVICE = "Service";
    private static final String TAG_DESC = "Description";
    private static final String TAG_COST = "Cost";
    private static final String TAG_ETD = "etd";
    private static final String TAG_NOTE = "note";

    private AppController appController;

    private AlertDialog.Builder aDialog;
    AlertDialog ad;

    OngkirItem ongkirItem;
    OngkirResult or;

    ArrayList<String> cityArr, cityIdArr;
    ArrayList<City> arraySak;
    ArrayAdapter<String> adapter;

    getCityData getData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongkir_layout);

        origin = (AutoCompleteTextView) findViewById(R.id.origin);
        destination = (AutoCompleteTextView) findViewById(R.id.destination);
        eWeight = (EditText) findViewById(R.id.eWeight);
        btnCekOngkir = (Button) findViewById(R.id.btnCheckOngkir);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        spinner = (Spinner) findViewById(R.id.spinCourier);

        list = (ListView) findViewById(R.id.list_ongkos);
        txtCourier = (TextView) findViewById(R.id.courierName);
        txtService = (TextView) findViewById(R.id.typeService);
        txtDescription = (TextView) findViewById(R.id.detailService);
        txtCost = (TextView) findViewById(R.id.cost);
        txtEtd = (TextView) findViewById(R.id.etd);
        txtNote = (TextView) findViewById(R.id.note);

        ongkirList = new ArrayList<HashMap<String, String>>();

        ongkirItem = new OngkirItem();
        or = new OngkirResult();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courier = spinner.getSelectedItem().toString();
                or.setCourierName(courier);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pDialog = new ProgressDialog(OngkirActivity.this);
        pDialog.setCancelable(true);
        connectOnLoad();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectOnLoad();
            }
        });

        appController = AppController.getInstance();
        btnCekOngkir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();
                for (int i = 0; i < arraySak.size(); i++) {
                    if (arraySak.get(i).getName().toString().equals(origin.getText().toString())) {
                        id_kota_origin = arraySak.get(i).getId();
                        originID = Integer.toString(id_kota_origin);
//                        sOrigin = arraySak.get(i).getName();
                    } else if (arraySak.get(i).getName().toString().equals(destination.getText().toString())) {
                        id_kota_dest = arraySak.get(i).getId();
                        destinationID = Integer.toString(id_kota_dest);
//                        sDest = arraySak.get(i).getName();
                    }
                }
                urlCost = "http://vendpad.com/api/data/ongkir?origin=" + originID + "&destination=" + destinationID + "&weight=" + eWeight.getText() + "&courier=" + spinner.getSelectedItem().toString() + "";
                if (isNetworkConnected()) {
                    if (id_kota_origin != 0) {
                        if (id_kota_dest != 0) {
                            if (eWeight.getText().toString().length() > 0 && Integer.parseInt(eWeight.getText().toString()) <= 30000) {
                                connectOnCheck();
                            } else {
                                Toast.makeText(getApplicationContext(), "weight can't be null or over from 30000", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Destination null or not in the list", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Origin null or not in the list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            testOngkir();
                        }
                    });
                    ad = aDialog.create();
                    ad.show();
                }
            }
        });
    }


    public void testOngkir() {
        pDialog.show();
        mService = null;
        mDesc = null;
        mCost = null;
        mEtd = null;
        mNote = null;
        JsonObjectRequest jObj = new JsonObjectRequest(Request.Method.GET, urlCost, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("jsone", jsonObject.toString());
                try {
                    Iterator iterator = jsonObject.keys();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();

                        JSONArray jObj = jsonObject.getJSONArray(key);
                        JSONObject objj = jObj.getJSONObject(0);

                        mService = objj.getString("service");
                        mDesc = objj.getString("description");

                        JSONArray arrayCost = objj.getJSONArray("cost");
                        JSONObject olala = arrayCost.getJSONObject(0);

                        mCost = olala.getString("value");
                        mEtd = olala.getString("etd");
                        mNote = olala.getString("note");

                        pDialog.dismiss();

                        or.setService(mService);
                        or.setDetail(mDesc);
                        or.setCost("Rp. " + mCost + ",00");
                        or.setEtd(mEtd + " hari");
                        or.setNote(mNote);

                        origin.setText("");
                        destination.setText("");
                        eWeight.setText("");

                        Toast.makeText(getApplicationContext(), "courier: " + courier + " service: " + mService + " detail: " + mDesc + " value: " + mCost + " etd: " + mEtd, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "jeda sik", Toast.LENGTH_SHORT).show();
                        setmAdapter();
                        Log.d("mbos", "iki lho string serv " + mService + " " + mDesc + " " + mCost + "" + mEtd + "" + mNote);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            testOngkir();
                        }
                    });
                    ad = aDialog.create();
                    ad.show();
                    Toast.makeText(getApplicationContext(), "error " + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("asdf", "mung renek " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                showAlert();
                aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        testOngkir();
                    }
                });
                ad = aDialog.create();
                ad.show();
                Toast.makeText(getApplicationContext(), "error parsing " + volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToReqQueue(jObj, "jreq");
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setmAdapter() {
        final HashMap<String, String> map = new HashMap<String, String>();

        map.put(TAG_COURIER, or.getCourierName());
        map.put(TAG_SERVICE, or.getService());
        map.put(TAG_DESC, or.getDetail());
        map.put(TAG_COST, or.getCost());
        map.put(TAG_ETD, or.getEtd());
        map.put(TAG_NOTE, or.getNote());

        ongkirList.add(map);
        final ListAdapter mAdapter = new SimpleAdapter(OngkirActivity.this, ongkirList, R.layout.ongkir_list_layout,
                new String[]{TAG_COURIER, TAG_SERVICE, TAG_DESC, TAG_COST, TAG_ETD, TAG_NOTE},
                new int[]{R.id.courierName, R.id.typeService, R.id.detailService, R.id.cost, R.id.etd, R.id.note});
        list.setAdapter(mAdapter);
        ((SimpleAdapter) mAdapter).notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), ongkirList.get(position).get("mCost"),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadJson() {
        cityArr = new ArrayList<String>();
        cityIdArr = new ArrayList<String>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        for (int i = 0; i < arraySak.size(); i++) {
            cityArr.add(arraySak.get(i).getName());
        }
        adapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown_item_1line, cityArr);
        reqJsonOrigin();
        getOrigin();
        getDestination();
    }

    class getCityData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("Getting Data ...");
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getData = new getCityData();
                    getData.cancel(true);
                }
            });
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            reqJsonOrigin();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ArrayAdapter<String>(OngkirActivity.this, R.layout.simple_dropdown_item_1line, cityArr);
            getOrigin();
            getDestination();
        }
    }


    private void reqJsonOrigin() {
        pDialog.show();
        city = null;
        arraySak = new ArrayList<City>();
        cityArr = new ArrayList<String>();

        JsonArrayRequest jreq = new JsonArrayRequest(urlCity,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                city = jo.getString("city_name");
                                city_id = jo.getInt("city_id");
                                City zcity = new City();
                                zcity.setId(city_id);
                                String type = jo.getString("type");
                                if (type.equals("Kota")) {
                                    zcity.setName(city + " (Kota)");
                                } else {
                                    zcity.setName(city);
                                }
                                arraySak.add(zcity);
                                Log.d("Success", "city: " + city + " id : " + city_id);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                showAlert();
                                aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        connectOnLoad();
                                    }
                                });
                                ad = aDialog.create();
                                ad.show();
                                Toast.makeText(getApplicationContext(), "error " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < arraySak.size(); i++) {
                            sb.append(arraySak.get(i).getId() + " " + arraySak.get(i).getName());
                            cityArr.add(arraySak.get(i).getName());
                        }
                        Toast.makeText(OngkirActivity.this, "Success Getting Data", Toast.LENGTH_LONG).show();

                        pDialog.dismiss();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                showAlert();
                aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        connectOnLoad();
                    }
                });
                ad = aDialog.create();
                ad.show();
                Toast.makeText(OngkirActivity.this, "error bro " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToReqQueue(jreq, "jreq");
    }

    public void getOrigin() {

        origin.setAdapter(adapter);
        origin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < arraySak.size(); i++) {
                    if (arraySak.get(i).getName().toString().equals(origin.getText().toString())) {

                        id_kota_origin = arraySak.get(i).getId();
                        sOrigin = arraySak.get(i).getName();
                        Toast.makeText(getApplicationContext(), "ID :" + id_kota_origin, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getDestination() {
        destination.setAdapter(adapter);
        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < arraySak.size(); i++) {
                    if (arraySak.get(i).getName().toString().equals(destination.getText().toString())) {
                        sDest = arraySak.get(i).getName();
                        Toast.makeText(getApplicationContext(), "ID :" + arraySak.get(i).getId(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void showAlert() {
        aDialog = new AlertDialog.Builder(this);
        aDialog.setMessage("No Internet Connection! " + System.getProperty("line.separator")
                + "Please check your Internet Connection " + System.getProperty("line.separator")
                + "and try again");
//        aDialog.setView(LayoutInflater.from(this).inflate(R.layout.abc_alert_dialog_material, null));
        aDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public void connectOnLoad() {
        if (isNetworkConnected()) {
//            pDialog.show();
            new getCityData().execute();
        } else {
            pDialog.dismiss();
            showAlert();
            aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    connectOnLoad();
                }
            });
            ad = aDialog.create();
            ad.show();
            Toast.makeText(getApplicationContext(), "check your connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void connectOnCheck() {
        if (isNetworkConnected()) {
            testOngkir();
        } else {
            pDialog.dismiss();
            showAlert();
            aDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    connectOnCheck();
                }
            });
            ad = aDialog.create();
            ad.show();
            Toast.makeText(getApplicationContext(), "check your connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}
