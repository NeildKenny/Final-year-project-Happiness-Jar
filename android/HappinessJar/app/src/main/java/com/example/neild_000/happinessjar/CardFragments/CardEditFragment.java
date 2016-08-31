package com.example.neild_000.happinessjar.CardFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.neild_000.happinessjar.Camera.CameraActivity;
import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.ConfigFiles.SharedPrefConfig;
import com.example.neild_000.happinessjar.ImageHandler;
import com.example.neild_000.happinessjar.MainActivityFragments;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by neild_000 on 10/03/2015.
 */
public class CardEditFragment extends Fragment {
    private ImageButton imageButton_main_Image;
    private ImageButton imageButton_left_Image;
    private ImageButton imageButton_center_Image;
    private ImageButton imageButton_right_Image;

    private EditText editText_title;
    private EditText editText_description;
    private EditText editText_tag;

    private final int CAMERA_JAR_REQUEST_CODE = 1;
    private final int CAMERA_OTHER_REQUEST_CODE = 2;
    private final int GALLERY_REQUEST_CODE = 3;
    private View rootView;

    private String user_id = "1";

   // private String shared_preferences_settings = "settings";


    private Card cardObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_card_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id  = item.getItemId();
        if(id == R.id.menu_card_edit_camera){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefConfig.shared_preferences_settings, Context.MODE_PRIVATE);
            if(!sharedPreferences.getBoolean(SharedPrefConfig.share_preferences_camera, false) ||  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP  ){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_OTHER_REQUEST_CODE);

            }else{
                Intent jarCameraIntent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(jarCameraIntent, CAMERA_JAR_REQUEST_CODE);
            }


        }
        if(id == R.id.menu_card_edit_gallery){
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Grab a photo from your Gallery"), GALLERY_REQUEST_CODE);
        }

        if(id == R.id.menu_card_edit_submit){
            onUploadClick();
        }

        return super.onOptionsItemSelected(item);

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Log.e(CardEditFragment.class.toString(), "In on createview" + savedInstanceState.toString());


        Log.e(CardEditFragment.class.toString(), getArguments().getString("card_id"));

        cardObject = new Card();

        cardObject.setCard_id(getArguments().getString("card_id"));
        /*Bundle args = getArguments();
        if(getArguments() != null){
            Log.i(CardEditFragment.class.toString(), "in on create args" + args.getString("card_id"));
        }
        if(savedInstanceState != null){
            Log.i(CardEditFragment.class.toString(), "lalala saved instance" + savedInstanceState.getString("card_id)"));
            cardObject.setCard_id(savedInstanceState.getString("card_id"));
        }*/
        Log.i(CardEditFragment.class.toString(), " in on create,saved instance == null");

        getCardFromServer();

        View rootView = inflater.inflate(R.layout.card_edit_fragment, container, false);
        this.rootView = rootView;
        setHasOptionsMenu(true);
        initCardView(rootView);

        return rootView;


    }

    private void initCardView(View rootView){
        editText_title = (EditText) rootView.findViewById( R.id.card_edit_fragment_title_editText );
        editText_description = (EditText) rootView.findViewById( R.id.card_edit_fragment_description_editText );
        editText_tag = (EditText) rootView.findViewById( R.id.card_edit_fragment_tag_editText );

        imageButton_main_Image = (ImageButton) rootView.findViewById(R.id.card_edit_fragment_main_imageButton);
        imageButton_left_Image = (ImageButton) rootView.findViewById(R.id.card_edit_fragment_leftimage_imageButton);
        imageButton_center_Image = (ImageButton) rootView.findViewById(R.id.card_edit_fragment_centerImage_imageButton);
        imageButton_right_Image = (ImageButton) rootView.findViewById(R.id.card_edit_fragment_rightImage_imageButton);

        final RelativeLayout edit_image_buttons = (RelativeLayout) rootView.findViewById(R.id.card_edit_fragment_buttons);
        imageButton_main_Image.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {



                if(edit_image_buttons.getVisibility() == View.GONE) {
                    edit_image_buttons.setVisibility(View.VISIBLE);
                    edit_image_buttons.requestFocus();
                }else if(edit_image_buttons.getVisibility() == View.VISIBLE){
                    edit_image_buttons.setVisibility(View.GONE);


                }

                return false;
            }
        });

        Button camera = (Button) rootView.findViewById(R.id.card_edit_fragment_camera);
        camera.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onCameraClick();
            }
        });

        Button submit =(Button) rootView.findViewById(R.id.card_edit_fragment_gallery);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadClick();
            }
        });

        imageButton_center_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(imageButton_center_Image);
            }
        });

        imageButton_left_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(imageButton_left_Image);
            }
        });

        imageButton_right_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(imageButton_right_Image);
            }
        });
        ((MainActivityFragments) getActivity()).setActionBarTitle("Edit Card");

    }



    public void switchImages(ImageButton imagebutton) {

        Drawable buffer_image_clicked = imagebutton.getDrawable();
        imagebutton.setImageDrawable(imageButton_main_Image.getDrawable());
        imageButton_main_Image.setImageDrawable(buffer_image_clicked);
        Log.i(CardEditFragment .class.toString(), "in switch image");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(CardEditFragment .class.toString(), "In activity result");

        if (resultCode == Activity.RESULT_OK ) {
            Bundle extras = data.getExtras();
            if (requestCode != GALLERY_REQUEST_CODE && !extras.isEmpty()) {

                if (imageButton_main_Image != null && imageButton_main_Image.getDrawable() != null) {
                    Log.i(CardEditFragment .class.toString(), " image != null");
                    Bitmap bitmap = imageButton_main_Image.getDrawingCache();
                    if (bitmap != null && !bitmap.isRecycled()) {
                        ((BitmapDrawable) imageButton_main_Image.getDrawable()).getBitmap().recycle();
                    }
                }
                if (requestCode == CAMERA_OTHER_REQUEST_CODE) {

                    Log.i(CardEditFragment .class.toString(), " Third party camera");

                    imageButton_main_Image.setImageBitmap((Bitmap) extras.get("data"));

                } else if (requestCode == CAMERA_JAR_REQUEST_CODE) {

                    Log.i(CardEditFragment .class.toString(), " jar camera");
                    try {
                        imageButton_main_Image.setImageBitmap(ImageHandler.decodeUri(Uri.parse(extras.getString("data")), getActivity().getContentResolver()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else if (requestCode == GALLERY_REQUEST_CODE) {
                    try {

                        Log.i(CardEditFragment .class.toString(), "in Activity result, gallery request");
                        imageButton_main_Image.setImageBitmap(ImageHandler.decodeUri(data.getData(), getActivity().getContentResolver()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCameraClick(){
/*       SharedPreferences sharedPreferences = getSharedPreferences(shared_preferences_settings, Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(getShared_preferences_settins_camera, false) ||  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP  ){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_OTHER_REQUEST_CODE);

        }else{
            Intent jarCameraIntent = new Intent(getActivity(), CameraActivity.class);
            startActivityForResult(jarCameraIntent, CAMERA_JAR_REQUEST_CODE);
        }*/
        Intent jarCameraIntent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(jarCameraIntent, CAMERA_JAR_REQUEST_CODE);

    }


    public void onUploadClick(){

        /* For testing: get bitmap from drawable folder */
        Bitmap bm  = BitmapFactory.decodeResource(this.getResources(), R.drawable.jar);

        if( !isEmpty(editText_title) ) {
            cardObject.setTitle(editText_title.getText().toString());
        }

        if( !isEmpty(editText_description) ){
            cardObject.setDescription(editText_description.getText().toString());
        }

        if( !isEmpty(editText_tag) ){
            cardObject.setDescription(editText_tag.getText().toString());
        }



        if(imageButton_center_Image.getDrawable() != null  )
            cardObject.setImage_one_bitmap(((BitmapDrawable) imageButton_center_Image.getDrawable()).getBitmap());

        if(imageButton_left_Image.getDrawable() != null)
            cardObject.setImage_two_bitmap(((BitmapDrawable)imageButton_left_Image.getDrawable()).getBitmap());

        if(imageButton_main_Image.getDrawable() !=  null)
            cardObject.setImage_three_bitmap(((BitmapDrawable)imageButton_main_Image.getDrawable()).getBitmap());

        if(imageButton_right_Image.getDrawable() != null)
            cardObject.setImage_four_bitmap(((BitmapDrawable)imageButton_right_Image.getDrawable()).getBitmap());



        new UploadMediaTask().execute();
    }


    public class UploadMediaTask extends AsyncTask< Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {


            DefaultHttpClient httpClient = new DefaultHttpClient();

            if( cardObject.getImage_one_bitmap() != null && !cardObject.getImage_one_bitmap().isRecycled() ) {
                cardObject.setInputStream_image1(ImagePNGToByteArray(cardObject.getImage_one_bitmap()));
            }


            if( cardObject.getImage_two_bitmap() != null && !cardObject.getImage_two_bitmap().isRecycled() )
                cardObject.setInputStream_image2(ImagePNGToByteArray(cardObject.getImage_two_bitmap()));

            if( cardObject.getImage_three_bitmap() != null && !cardObject.getImage_three_bitmap().isRecycled() )
                cardObject.setInputStream_image3(ImagePNGToByteArray(cardObject.getImage_three_bitmap()));

            if( cardObject.getImage_four_bitmap() != null && !cardObject.getImage_three_bitmap().isRecycled())
                cardObject.setInputStream_image4(ImagePNGToByteArray(cardObject.getImage_four_bitmap()));
            Log.i(CardEditFragment .class.toString(),"before bitmap recycle");

            int recycle_count = cardObject.recyleBitmaps();
            Log.i(CardEditFragment .class.toString(),"bitmaps recycled" + Integer.toString(recycle_count));

            try{
                long i  = 0;
                HttpPost httpPost = new HttpPost( ConfigURLs.URL_PHOTO_UPLOAD );
                //HttpPost httpPost = new HttpPost(TestConfigURL.UPLOAD_MULTIPLE_TEST);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                //////////////////
                /// Image post logic
                /////////////////
                if( cardObject.getInputStream_image1() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image1(), ContentType.create("image/jpg"), System.currentTimeMillis()+ i + ".jpg");
                    Log.i(CardEditFragment .class.toString(), "image1 inputStream set");
                }
                //
                if( cardObject.getInputStream_image2() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image2(), ContentType.create("image/jpg"), System.currentTimeMillis()+ (i++) + ".jpg");
                    Log.i(CardEditFragment .class.toString(), "image2 inputStream set");
                }
                if( cardObject.getInputStream_image3() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image3(), ContentType.create("image/jpg"), System.currentTimeMillis()+ (i++) + ".jpg");
                    Log.i(CardEditFragment .class.toString(), "image3 inputStream set");
                }

                if( cardObject.getInputStream_image4() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image4(), ContentType.create("image/jpg"), System.currentTimeMillis()+(i++) + ".jpg");
                    Log.i(CardEditFragment .class.toString(), "image4 inputStream set");
                }


                ///////////
                /// Text post logic
                //////////

                builder.addTextBody("user_id", "1");
                if( cardObject.getTitle() != null && !cardObject.getTitle().isEmpty()) {
                    builder.addTextBody( "title", cardObject.getTitle() );
                }

                if( cardObject.getDescription() != null && !cardObject.getDescription().isEmpty() ) {
                    builder.addTextBody( "description", cardObject.getDescription() );
                }

                if( cardObject.getTag() != null && !cardObject.getTitle().isEmpty()){
                    builder.addTextBody( "tag", cardObject.getTag() );
                }

                httpPost.setEntity( builder.build() );

                Log.i(CardEditFragment .class.toString(), "entity set");


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
                        Log.i(CardEditFragment .class.toString(),"response: " + response.getStatusLine().toString());

                        String responseText = EntityUtils.toString(response.getEntity());
                        Log.d(CardEditFragment .class.toString(),"full response" + responseText );
                    }
                } catch(IOException e) {

                }finally{

                }
            }finally {

            }


            return null;
        }



        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);

            Log.i(CardEditFragment .class.toString(), "data posted");
        }
    }

    private void getCardFromServer() {
        //cardObject.setCard_id("51");
        user_id = "1";
        if( RESTCalls.isNetworkAvailable( getActivity() ) ) {

            String url = ConfigURLs.URL_GET_CARD_FROM_SERVER + cardObject.getCard_id() + "/" + user_id;
            Log.i(CardEditFragment.class.toString(), url);
            new CallCardFromServer().execute(ConfigURLs.URL_GET_CARD_FROM_SERVER + cardObject.getCard_id() + "/" + user_id);
        }else{
            Toast toast = Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    private class CallCardFromServer extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... url) {
            try{
                String card_data_from_server = RESTCalls.getJSONFromServer(url[0]);
                Log.i( CallCardFromServer.class.toString(), card_data_from_server );
                cardObject.parseCardJSON(card_data_from_server);


            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            getImageFromServer();
        }
    }

    private void getImageFromServer(){
        if(RESTCalls.isNetworkAvailable(getActivity())){
            new addImagesToView().execute();
        }else{
            Toast toast = Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private class addImagesToView extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... params) {

            try{

                if( cardObject.getImage_one_string().isEmpty() || cardObject.getImage_one_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_one_string() );
                    cardObject.setImage_one_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardEditFragment.class.toString(), "image one set");
                }
                if( cardObject.getImage_two_string().isEmpty() || cardObject.getImage_two_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_two_string() );
                    cardObject.setImage_two_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardEditFragment.class.toString(), "image one set");
                }
                if( cardObject.getImage_three_string().isEmpty() || cardObject.getImage_three_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_three_string() );
                    cardObject.setImage_three_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardEditFragment.class.toString(), "image one set");
                }
                if( cardObject.getImage_four_string().isEmpty() || cardObject.getImage_four_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_four_string() );
                    cardObject.setImage_four_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardEditFragment.class.toString(), "image one set");
                }
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            editText_title.setText(cardObject.getTitle());

            editText_description.setText(cardObject.getDescription());
            editText_tag.setText(cardObject.getTag());

            imageButton_main_Image.setImageBitmap( cardObject.getImage_one_bitmap() );
            imageButton_left_Image.setImageBitmap( cardObject.getImage_two_bitmap() );
            imageButton_center_Image.setImageBitmap( cardObject.getImage_three_bitmap() );
            imageButton_right_Image.setImageBitmap( cardObject.getImage_four_bitmap() );

        }
    }


    private InputStream ImagePNGToByteArray( Bitmap bitmap ){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, stream);
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
