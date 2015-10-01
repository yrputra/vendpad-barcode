package com.example.yrp.nextapp.UploadImagePHP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yrp.nextapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

/**
 * Created by YRP on 04/08/2015.
 */
public class MainUploadImage extends Activity {

    ProgressDialog pDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_upload_layout);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }

    public void loadImageFromGallery(View view){
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && null != data){
                //get the image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                //move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                //set the image in imageView
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                //get the image filename
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length-1];
                //put the filename in the async http post params which used in
            }else{
                Toast.makeText(this, "You haven't picked up an image",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage(View v){
        if(imgPath != null && !imgPath.isEmpty()){
            pDialog.setMessage("Converting image to binary data");
            pDialog.show();
            encodeImageToString();

        }else{
            Toast.makeText(getApplicationContext(),"You must selected image from gallery before you try to upload",Toast.LENGTH_SHORT).show();
        }

    }

    public void encodeImageToString(){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //compress image to make easy upload
                bitmap.compress(Bitmap.CompressFormat.PNG,50,stream);
                byte[] byte_arr = stream.toByteArray();
                //encode image to string
                encodedString = Base64.encodeToString(byte_arr,0);
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                pDialog.setMessage("Calling upload");
                //put converted image string into Async http post params
                params.put("image", encodedString);
                //trigger image upload
                triggerImageUpload();
            }
        };
    }
    public void triggerImageUpload() {
        makeHTTPCall();
    }

    private void makeHTTPCall() {
        pDialog.setMessage("invoking php");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.2.5:9000/imgupload/upload_image.php",params, new AsyncHttpResponseHandler(){
            // When the response returned by REST has Http
            // response code '200'
            @Override
            public void onSuccess(String response) {
                pDialog.hide();
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }
            // When the response returned by REST has Http
            // response code other than '200' such as '404',
            // '500' or '403' etc
            @Override
            public void onFailure(int statusCode,Throwable error, String content) {
                pDialog.hide();
                if(statusCode==404){
                    Toast.makeText(getApplicationContext(),"Requested response not found",Toast.LENGTH_SHORT).show();
                } else if(statusCode==500){
                    Toast.makeText(getApplicationContext(),"Something went wrong at server end",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(),"Error occurred \n Most common Error: \n" +
                                    "1. Device not connected to Internet\n" +
                                    "2. Web App is not deployed in App server\n" +
                                    "3. App server is not running\n " +
                                    "HTTP Status code :"+statusCode,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pDialog!=null){
            pDialog.dismiss();
        }
    }
}
