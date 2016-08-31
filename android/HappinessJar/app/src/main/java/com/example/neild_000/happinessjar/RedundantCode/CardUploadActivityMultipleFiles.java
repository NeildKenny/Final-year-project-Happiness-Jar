package com.example.neild_000.happinessjar.RedundantCode;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.CardFragments.Card;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by neild_000 on 27/01/2015.
 */
public class CardUploadActivityMultipleFiles extends ActionBarActivity{

    private ImageButton imageButton_main_Image, imageButton_left_Image, imageButton_center_Image,imageButton_right_Image;
    private EditText editText_title;
    private EditText editText_description;

    private Card CardObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_edit);

        editText_title = (EditText) findViewById(R.id.card_edit_title_editText);
        editText_description = (EditText) findViewById(R.id.card_edit_description_editText);

        CardObject = new Card();


    }


    public void onImageClick(View view){

        switchImages(view);

    }



    public void onImageLongClick(View view){

        deleteImage(view);

    }

    private void deleteImage(View view){

        ImageButton delete_image_button = (ImageButton) findViewById(view.getId());

        delete_image_button.setBackgroundResource(R.drawable.jar);
        delete_image_button.invalidate();
    }

    private void switchImages(View view) {

        if(view.getId()!= R.id.card_edit_main_imageButton){

            ImageButton clicked_image_button = (ImageButton) findViewById(view.getId());
            Drawable buffer_image_clicked = clicked_image_button.getDrawable();
            clicked_image_button.setImageDrawable(imageButton_main_Image.getDrawable());
            imageButton_main_Image.setImageDrawable(buffer_image_clicked);

        }
    }

    public void onUploadClick(View view){

        Bitmap bm  = BitmapFactory.decodeResource(this.getResources(), R.drawable.jar);

        if( !isEmpty(editText_description) ) {
            CardObject.setTitle( editText_title.getText().toString() );
        }

        CardObject.setDescription( editText_description.getText().toString() );

        CardObject.setImage_one_bitmap(bm);
        CardObject.setImage_two_bitmap(bm);
        CardObject.setImage_three_bitmap(bm);
        CardObject.setImage_four_bitmap(bm);


        new UploadMediaTask().execute(bm);
    }

    public class UploadMediaTask extends AsyncTask<Bitmap, Void, Void> {
        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            if(bitmaps[0] == null){
                return null;
            }

            Bitmap bitmap  = bitmaps[0];
            if( CardObject.getImage_one_bitmap() != null ){
                CardObject.setInputStream_image1(ImagePNGToByteArray(CardObject.getImage_one_bitmap()));
            }

            if( CardObject.getImage_two_bitmap() != null ){
                CardObject.setInputStream_image2( ImagePNGToByteArray( CardObject.getImage_two_bitmap() ) );
            }

            if( CardObject.getImage_three_bitmap() != null ){
                CardObject.setInputStream_image3( ImagePNGToByteArray( CardObject.getImage_three_bitmap() ) );
            }

            if( CardObject.getImage_four_bitmap() != null){
                CardObject.setInputStream_image4( ImagePNGToByteArray( CardObject.getImage_four_bitmap() ) );
            }

            InputStream in = ImagePNGToByteArray( bitmap );

            DefaultHttpClient httpClient = new DefaultHttpClient();



            try{

                HttpPost httpPost = new HttpPost( ConfigURLs.URL_PHOTO_UPLOAD );
                //HttpPost httpPost = new HttpPost(TestConfigURL.UPLOAD_MULTIPLE_TEST);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                if(CardObject.getInputStream_image1() != null) {
                    builder.addBinaryBody("image", CardObject.getInputStream_image1(), ContentType.create("image/jpg"), System.currentTimeMillis() + ".jpg");
                    //builder.addBinaryBody("image", CardObject.getInputStream_image1(), ContentType.create("image/png"), System.currentTimeMillis()+".jpg");
                }
               // Log.i(CardEditActivity.class.toString(), "image1 inputStream set");
                if(CardObject.getInputStream_image2() != null) {
                    builder.addBinaryBody("image", CardObject.getInputStream_image2(), ContentType.create("image/jpg"), System.currentTimeMillis() + ".jpg");
              //      Log.i(CardEditActivity.class.toString(), "image2 inputStream set");
                }
                if(CardObject.getInputStream_image3() != null){
                    builder.addBinaryBody("image", CardObject.getInputStream_image3(), ContentType.create("image/jpg"), System.currentTimeMillis() + ".jpg");
                //    Log.i(CardEditActivity.class.toString(), "image3 inputStream set");
                }

                if(CardObject.getInputStream_image4() != null){
                    builder.addBinaryBody("image", CardObject.getInputStream_image4(), ContentType.create("image/jpg"), System.currentTimeMillis() + ".jpg");
                 //   Log.i(CardEditActivity.class.toString(), "image4 inputStream set");
                }
                //builder.addTextBody("name", "test");
                // builder.addTextBody( "Title", CardObject.getTitle() );
                //builder.addTextBody( "Description", CardObject.getDescription() );

                httpPost.setEntity( builder.build() );

               // Log.i(CardEditActivity.class.toString(), "entity set" + builder.toString());



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
                    //    Log.i(CardEditActivity.class.toString(),"response: " + response.getStatusLine().toString());

                        String responseText = EntityUtils.toString(response.getEntity());
                  //      Log.d(CardEditActivity.class.toString(),"full response" + responseText );
                    }
                } catch(IOException e) {

                }finally{

                }
            }finally {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

       //     Log.i(CardEditActivity.class.toString(), "complete");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_edit, menu);
        return true;
    }

    private class PushCard extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

    private InputStream ImagePNGToByteArray( Bitmap bitmap ){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        InputStream in = new ByteArrayInputStream(stream.toByteArray());
        return in;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
