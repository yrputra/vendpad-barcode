package com.example.yrp.nextapp.JsonParsingAsyntask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yrp.nextapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YRP on 27/07/2015.
 */
public class MainParseAsyncTask extends Activity {
    TextView provinceId, province,uid,name1,email1;
    Button btnGetData;


//    //URL to get JSON Array
//    private static String url = "http://10.0.2.2/JSON/";
//
//    //JSON Node Names
//    private static final String TAG_USER = "user";
//    private static final String TAG_ID = "id";
//    private static final String TAG_NAME = "name";
//    private static final String TAG_EMAIL = "email";

    //url to get json array
    private static String url = "http://vendpad.com/api/data/province";

    //json node names
    private static final String TAG_ID ="1";
    private static final String TAG_PROVINCE_ID = "province_id";
    private static final String TAG_PROVINCE = "province";

    JSONArray id= null;
    JSONObject obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parse_asynctask_layout);

        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonParse().execute();
            }
        });
    }

    private class JsonParse extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            provinceId = (TextView) findViewById(R.id.txtIdProvince);
//            province = (TextView) findViewById(R.id.txtProvince);
            uid = (TextView)findViewById(R.id.uid);
            name1 = (TextView)findViewById(R.id.name);
            email1 = (TextView)findViewById(R.id.email);
            pDialog = new ProgressDialog(MainParseAsyncTask.this);
            pDialog.setMessage("Getting data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {

            //Getting JSON from url
            JsonParser jsonParser = new JsonParser();
            JSONObject jsonObject = jsonParser.getJsonFromUrl(url);
            return jsonObject;

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            try{
                //getting json array
                id = jsonObject.getJSONArray(TAG_ID);
                JSONObject c = id.getJSONObject(0);

                //Storing json item in variable
                String id = c.getString(TAG_PROVINCE_ID);
                String prov = c.getString(TAG_PROVINCE);

                //Set JSON data in textView
                provinceId.setText(id);
                province.setText(prov);
//                user = jsonObject.getJSONArray(TAG_USER);
//                JSONObject c = user.getJSONObject(0);
//
//                // Storing  JSON item in a Variable
//                String id = c.getString(TAG_ID);
//                String name = c.getString(TAG_NAME);
//                String email = c.getString(TAG_EMAIL);
//
//                //Set JSON Data in TextView
//                uid.setText(id);
//                name1.setText(name);
//                email1.setText(email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
