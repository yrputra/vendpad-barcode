package com.example.yrp.nextapp.VolleyExample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yrp.nextapp.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainParse extends Activity implements OnClickListener{

	private Button jsonObj,jsonArray;
	private TextView textview;
	private ProgressDialog mdialog;
	private String jsonObjUrl="http://www.androidtoppers.com/VolleyExample/ws_API/get_obj.php";
	private String jsonArrayUrl="http://www.androidtoppers.com/VolleyExample/ws_API/get_array.php";
	private static String TAG = MainParse.class.getSimpleName();
	private StringBuilder str;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		jsonObj=(Button)findViewById(R.id.json_obj);
		jsonObj.setOnClickListener(this);
		jsonArray=(Button)findViewById(R.id.json_array);
		jsonArray.setOnClickListener(this);
		textview=(TextView)findViewById(R.id.textview);
		textview.setMovementMethod(new ScrollingMovementMethod());
		mdialog=new ProgressDialog(this);
		mdialog.setMessage("Loading..");
		mdialog.setCancelable(false);
		str=new StringBuilder();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.json_obj:
			jsonArray.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
			jsonObj.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
			textview.setText("");
			str.delete(0, str.length());
			callJsonObjRequest();
			break;
		case R.id.json_array:
			jsonObj.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
			jsonArray.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
			textview.setText("");
			str.delete(0, str.length());
			callJsonArrayRequest();
			break;

		default:
			break;
		}
	}

	private void callJsonObjRequest() {
		// TODO Auto-generated method stub
		//show dialog
		showDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				jsonObjUrl, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				try {
					String name = response.getString("Name");
					String image=response.getString("image");
					String dec=response.getString("dec");
					str.append("Name : ").append(name).append("\n").append("Image : ").append(image).append("\n").append("Dec : ").append(dec);
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
				}
				textview.setText(str);
				dismissDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(),
						error.getMessage(), Toast.LENGTH_LONG).show();
				// hide the progress dialog
				dismissDialog();
			}
		});
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

	private void callJsonArrayRequest() {
		// TODO Auto-generated method stub
		//show dialog
		showDialog();
		JsonArrayRequest jsonarrayReq = new JsonArrayRequest(jsonArrayUrl,
				new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Log.d(TAG, response.toString());
				try {
					for (int i = 0; i < response.length(); i++) {
						JSONObject person = (JSONObject) response.get(i);
						String name = person.getString("Name");
						String image=person.getString("image");
						String dec=person.getString("dec");
						str.append("Name : ").append(name).append("\n").append("Image : ").append(image).append("\n").append("Dec : ").append(dec).append("\n\n");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
				}
				textview.setText(str);
				dismissDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(),
						error.getMessage(), Toast.LENGTH_SHORT).show();
				dismissDialog();
			}
		});
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonarrayReq);
	}

	private void dismissDialog() {
		// TODO Auto-generated method stub
		if(mdialog.isShowing()){
			mdialog.dismiss();
		}
	}

	private void showDialog() {
		// TODO Auto-generated method stub
		if(!mdialog.isShowing()){
			mdialog.show();
		}
	}
}
