package com.example.neild_000.happinessjar.RedundantCode;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by neild_000 on 05/02/2015.
 */
public class UploadMediaTask extends AsyncTask<Bitmap, Void, Void> {
    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        if(bitmaps[0] == null){
            return null;
        }

        Bitmap bitmap  = bitmaps[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        InputStream in = new ByteArrayInputStream(stream.toByteArray());

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "www.google.com";
        try{
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody( "myfile", in, ContentType.APPLICATION_OCTET_STREAM, System.currentTimeMillis() + ".jpg");
            builder.addTextBody( "name", "test" );
            httpPost.setEntity( builder.build() );

            Log.i(UploadMediaTask.class.toString(),"request " + httpPost.getRequestLine());
            HttpResponse response = null;
            try{
                response = httpClient.execute(httpPost);
            }catch(ClientProtocolException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            try{
                if(response != null){
                    Log.i(UploadMediaTask.class.toString(), "Response: " + response.getStatusLine().toString());
                }
            }finally {

            }

        }finally {

        }

        if( in != null){
            try{
                in.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Log.i(UploadMediaTask.class.toString(), " Upload complete");
    }
}
