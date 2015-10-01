package com.example.yrp.nextapp.ScanScan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.yrp.nextapp.R;

/**
 * Created by YRP on 26/08/2015.
 */
public class ResultView extends Activity {

    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view_activity);

        txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText(getIntent().getStringExtra("barcode"));
    }
}
