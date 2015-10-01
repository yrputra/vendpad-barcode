package com.example.yrp.nextapp.ScanBarcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yrp.nextapp.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

/**
 * Created by YRP on 25/08/2015.
 */
public class BarcodeActivity extends Activity{

    private static final String TAG = BarcodeActivity.class.getSimpleName();
    private TextView txtContent;
    private Button btnBarcode;
    private ImageView imgView;
    private Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_barcode);

        txtContent = (TextView) findViewById(R.id.txtContent);
        btnBarcode = (Button) findViewById(R.id.btnScan);
        imgView = (ImageView) findViewById(R.id.imgViewBar);

        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);
                imgView.setImageBitmap(myBitmap);
                process();
            }
        });
    }

    public void process(){
        BarcodeDetector bd = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE).build();
        if(!bd.isOperational()){
            txtContent.setText("Could not set the detector!");
            return;
        }
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Barcode> barcodes = bd.detect(frame);

        Barcode thisCode = barcodes.valueAt(0);
        txtContent.setText(thisCode.rawValue);
    }
}
