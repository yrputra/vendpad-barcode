package com.example.yrp.nextapp.SpannableTry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yrp.nextapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by YRP on 07/08/2015.
 */
public class MainActivity extends Activity {
    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_new);
        imageView = (ImageView) findViewById(android.R.id.icon);
    }

    public void pickPhoto(View view) {
        //TODO: launch the photo picker
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Bitmap bitmap = getPath(data.getData());
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    public void uploadPhoto(View view) {
        try {
            executeMultipartPost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void executeMultipartPost() throws Exception {

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

            Bitmap bitmap = drawable.getBitmap();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);

            byte[] data = bos.toByteArray();

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost postRequest = new HttpPost(

//                    "http://192.168.1.107:8888/files/upload_file.php");
                    "http://vendpad.com/api/user/4/photo");
            String fileName = String.format("File_%d.png",new Date().getTime());
            ByteArrayBody bab = new ByteArrayBody(data, fileName);

            // File file= new File("/mnt/sdcard/forest.png");

            // FileBody bin = new FileBody(file);

            MultipartEntity reqEntity = new MultipartEntity(

                    HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("photo", bab);
            reqEntity.addPart("id", new StringBody("4"));

            postRequest.setEntity(reqEntity);
            int timeoutConnection = 60000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpConnectionParams.setTcpNoDelay(httpParameters, true);

            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader reader = new BufferedReader(new InputStreamReader(

                    response.getEntity().getContent(), "UTF-8"));

            String sResponse;

            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {

                s = s.append(sResponse);

            }
            Log.d("Disini", "respons: "+s);
            System.out.println("Response: " + s);

        } catch (Exception e) {

            // handle exception here
            e.printStackTrace();

            // Log.e(e.getClass().getName(), e.getMessage());

        }

    }
}
