package com.example.yrp.nextapp.UploadImagePHPserver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.example.yrp.nextapp.R;

import java.io.File;

/**
 * Created by YRP on 20/08/2015.
 */
public class UploadInstagram extends Activity {

    String type = "image/*";
    String filename = "/IMG_20150813_114031.jpg";
//    String mediaPath = Environment.getExternalStorageDirectory() + filename;
    String mediaPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+filename;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_upload_layout);
        btnUpload = (Button) findViewById(R.id.btnUpl);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInstagramIntent(type, mediaPath);
            }
        });

    }



    private void createInstagramIntent(String type, String mediaPath){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));

    }
}
