package com.example.yrp.nextapp.AutoComplete;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.yrp.nextapp.R;

/**
 * Created by YRP on 28/07/2015.
 */
public class AutoCompleteMain extends Activity {

    MultiAutoCompleteTextView autv = null;
    private ArrayAdapter<String> adapter;
    String[] name =new String[]{"budi","bubud"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete_layout);

        autv = (MultiAutoCompleteTextView)findViewById(R.id.autoComplete);
        adapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown_item_1line,name);
        autv.setThreshold(1);
        autv.setAdapter(adapter);
        autv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),"you pressed "+position+" "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
