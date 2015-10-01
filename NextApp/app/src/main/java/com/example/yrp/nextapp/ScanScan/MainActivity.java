package com.example.yrp.nextapp.ScanScan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yrp.nextapp.R;
import com.example.yrp.nextapp.ScanScan.ui.camera.CameraSourcePreview;
import com.example.yrp.nextapp.ScanScan.ui.camera.GraphicOverlay;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YRP on 25/08/2015.
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;
    private TextView txtAtas, txtBawah;
    final int RQS_GooglePlayServices = 1;
//    Context context = getApplicationContext();

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_scan_layout);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.overlay);
        final Context context = getApplicationContext();

        txtAtas = (TextView) findViewById(R.id.txtView);
        txtBawah = (TextView) findViewById(R.id.txtlagi);


        if (checkPlayServices()) {
            Toast.makeText(getApplicationContext(), "support play service", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Support google play service");
        } else {
            Toast.makeText(getApplicationContext(), "not supported", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "not Support google play service");
        }
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, new GraphicTracker.Callback() {
            @Override
            public void onFound(String barcodeValue) {
//                atas.setText(barcodeValue);
//
                final String x = barcodeValue;
                new customMessage().execute(x);

            }
        });
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        mCameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1024, 1024)
                .setRequestedFps(15.0f)
                .build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        //...
        if (resultCode == ConnectionResult.SUCCESS) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, MainActivity.this, RQS_GooglePlayServices).show();
            if(resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED){
                Toast.makeText(getApplicationContext(), "Please update your Google Play Service", Toast.LENGTH_SHORT).show();
            } else if (resultCode == ConnectionResult.SERVICE_MISSING) {
                Toast.makeText(getApplicationContext(), "Please download Google Play Service", Toast.LENGTH_SHORT).show();
            } else if(resultCode == ConnectionResult.SERVICE_DISABLED){
                Toast.makeText(getApplicationContext(), "Please enable your Google Play Service", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraSource.release();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        try {
            mPreview.start(mCameraSource, mGraphicOverlay);
        } catch (IOException e) {
            Log.e(TAG, "Unable to start camera source.", e);
            mCameraSource.release();
            mCameraSource = null;
        }
    }

    public static boolean cameraFocus(@NonNull CameraSource cameraSource, @NonNull String focusMode) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
//                    Camera camera = (Camera) field.get(cameraSource);
                    android.hardware.Camera camera = (android.hardware.Camera) field.get(cameraSource);
                    if (camera != null) {
                        android.hardware.Camera.Parameters params = camera.getParameters();

                        if (!params.getSupportedFocusModes().contains(focusMode)) {
                            return false;
                        }

                        params.setFocusMode(focusMode);
                        camera.setParameters(params);
                        return true;
                    }

                    return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return false;
    }

    public static android.hardware.Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == android.hardware.Camera.class) {
                field.setAccessible(true);
                try {
//                     Camera camera = (Camera) field.get(cameraSource);
                    android.hardware.Camera camera = (android.hardware.Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }

                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return null;
    }

    class customMessage extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... voids) {
            String a = voids[0];
            return a;
        }

        @Override
        protected void onPostExecute(String aVoid) {
//            super.onPostExecute(aVoid);
            txtAtas.setText(aVoid);
            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(aVoid);//replace with string to compare
            if(m.find()) {
                Log.d(TAG, "contains url");
            }

//            Intent a = new Intent(getApplicationContext(), ResultView.class);
//            a.putExtra("barcode", aVoid);
//            startActivity(a);
        }


    }
}