package com.example.yrp.nextapp.UploadImagePHPserver;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yrp.nextapp.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by YRP on 05/08/2015.
 */
public class MainUploadImageAH extends Activity {
    //LogCat tag
    private static final String TAG = MainUploadImageAH.class.getSimpleName();

    //camera activity request code
    private static final int GALLERY_REQUEST_CODE = 300;
    private static final int IMAGE_REQUEST_CODE = 100;
    private static final int VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_GALLERY = 3;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    //file uri
    private Uri fileUri;

    private Button btnChooseImage, btnCaptureImage, btnRecordVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_upload_ah_layout);

        // Changing action bar background color
        // These two lines are not needed
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));

        btnChooseImage = (Button) findViewById(R.id.btnPickImageGallery);
        btnCaptureImage = (Button) findViewById(R.id.btnCapturePicture);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });
        if(isDeviceSupportCamera()){
            Toast.makeText(getApplicationContext(),"Sorry! Your device doesn't support camera",Toast.LENGTH_SHORT).show();
//            finish();
//           if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//             your code using Camera API here - is between 1-20
//        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//             your code using Camera2 API here - is api 21 or higher
        }
    }



    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }


    private void chooseImageFromGallery() {
        Intent pickImg = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_GALLERY);
        pickImg.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(pickImg, GALLERY_REQUEST_CODE);
    }

    private void recordVideo() {
        Intent vid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        vid.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        vid.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(vid, VIDEO_REQUEST_CODE);
    }

    private void captureImage() {
        Intent img = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        img.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(img, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent intent) {
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                launchUploadActivity(true);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to Upload image", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
//                launchUploadActivity(true);
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "User cancelled image uploading", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to upload image", Toast.LENGTH_SHORT).show();

            }
        }else if (requestCode == VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                launchUploadActivity(false);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "User cancelled video uploading", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to record video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchUploadActivity(boolean isImage) {
        Intent i = new Intent(MainUploadImageAH.this, UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Returning image/video
     */

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), config.IMAGE_DIRECTORY_NAME);

        //create storage directory if it doesn't exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Ooops! Failed create " + config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        //create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }
}
