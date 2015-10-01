package com.example.yrp.nextapp.Ongkir;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yrp.nextapp.ModelOngkir.OngkirResult;
import com.example.yrp.nextapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YRP on 28/07/2015.
 */
public class OngkirResultActivity extends Activity {

    private TextView txtCourier, txtService, txtDescription, txtCost,txtEtd,txtNote;
    ListView list;

    private String courier,service,description,cost,etd, note;
    ArrayList<HashMap<String, String>> ongkirList = new ArrayList<HashMap<String, String>>();
    ListAdapter adapter;

    private static final String TAG_COURIER = "Courier";
    private static final String TAG_SERVICE = "Service";
    private static final String TAG_DESC = "Description";
    private static final String TAG_COST = "Cost";
    private static final String TAG_ETD = "etd";
    private static final String TAG_NOTE = "note";

    OngkirResult or;
    OngkirActivity oa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongkir_result_layout);

        txtCourier = (TextView) findViewById(R.id.courierName);
        txtService = (TextView) findViewById(R.id.typeService);
        txtDescription = (TextView) findViewById(R.id.detailService);
        txtCost = (TextView) findViewById(R.id.cost);
        txtEtd = (TextView) findViewById(R.id.etd);
        txtNote = (TextView) findViewById(R.id.note);

        or= new OngkirResult();

        courier = or.getCourierName();
        service = or.getService();
        description = or.getDetail();
        cost = or.getCost();
        etd = or.getEtd();
        note = or.getNote();

        oa= new OngkirActivity();
        oa.testOngkir();

//        txtCourier.setText(courier);
//        txtService.setText("lalala");
//        txtDescription.setText("lilili");
//        txtCost.setText("lololo");
//        txtEtd.setText("lululu");
//        txtNote.setText("lelele");

//        HashMap<String, String> map = new HashMap<String, String>();
//
//        map.put(TAG_COURIER, courier);
//        map.put(TAG_SERVICE, service);
//        map.put(TAG_DESC, description);
//        map.put(TAG_COST, cost);
//        map.put(TAG_ETD, etd);
//        map.put(TAG_NOTE, note);
//
//        ongkirList.add(map);
//        Toast.makeText(getApplicationContext(), String.valueOf(ongkirList.add(map)), Toast.LENGTH_SHORT).show();
////        adapter = new SimpleAdapter(OngkirResultActivity.this, ongkirList, R.layout.ongkir_list_layout,
////                new String[]{TAG_COURIER,TAG_SERVICE,TAG_DESC,TAG_COST,TAG_ETD,TAG_NOTE},
////                new int[]{R.id.courierName,R.id.typeService,R.id.detailService,R.id.cost,R.id.etd,R.id.note});
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"courier: "+courier+ "service: " + service + " detail: " + description + " cost: " + cost + " etd: " + etd, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
